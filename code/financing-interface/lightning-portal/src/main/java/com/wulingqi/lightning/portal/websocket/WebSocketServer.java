package com.wulingqi.lightning.portal.websocket;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wulingqi.lightning.portal.service.RedisService;

/**
 * websocket服务端
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * @ServerEndpoint 可以把当前类变成websocket服务类
 */
@ServerEndpoint("/websocket/{memberId}")
@Component
public class WebSocketServer {

	private static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    //存储连接用户的session
    private static Map<String, Session> connections = new ConcurrentHashMap<>();
    
    private static RedisService redisService;
    
    @Autowired
    public void setRedisService(RedisService redisService){
    	WebSocketServer.redisService = redisService;
    }
    
    private static final long TIMEOUT = 20; //20 second过期
    
    /**
     * 打开连接
     * @param session
     * @param sid
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("memberId") String memberId) {
    	logger.info("[{}]连接成功", memberId);
    	connections.put(memberId, session);
    	Date currentDate = new Date();
    	redisService.set(memberId, String.valueOf(currentDate.getTime()));
    	redisService.expire(memberId, TIMEOUT);
    }

    /**
     * 接收消息
     * @param text
     */
    @OnMessage
    public void onMessage(String text, @PathParam("memberId") String memberId) {
    	//JSONObject object = JSONObject.parseObject(text);
    	//Long time = object.getLong("pong");
    	if(connections.get(memberId) != null) {
    		Date currentDate = new Date();
        	redisService.set(memberId, String.valueOf(currentDate.getTime()));
        	redisService.expire(memberId, TIMEOUT);
    	}
    	
        logger.info("收到来自[{}]的心跳回复: {}", memberId, text);
    }

    /**
     * 异常处理
     * @param throwable
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 关闭连接
     * @param sid
     */
    @OnClose
    public void onClosing(@PathParam("memberId") String memberId) throws IOException {
    	connections.remove(memberId);
    	redisService.remove(memberId);
    }

    /**
     * 根据IP发送消息
     * @param sid
     * @param text
     */
    public static void sendInfo(String text, String memberId) {
        try {
            Session session = connections.get(memberId);
            if (session != null && session.isOpen()) {
                session.getAsyncRemote().sendText(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 遍历群发消息
     * @param text
     */
    public static void send(String text) {
        for (Map.Entry<String, Session> entry : connections.entrySet()) {
            sendInfo(text, entry.getKey());
        }
    }
    
    public static Map<String, Session> getAllSession() {
    	return connections;
    }
	
}
