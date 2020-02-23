package com.wulingqi.lightning.portal.task;

import java.util.Date;
import java.util.Map;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.wulingqi.lightning.portal.service.RedisService;
import com.wulingqi.lightning.portal.vo.HeartbeatVo;
import com.wulingqi.lightning.portal.websocket.WebSocketServer;

/**
 * websocket心跳任务
 */
@Component
public class WebsocketHeartbeatTask implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(WebsocketHeartbeatTask.class);
	
	@Autowired
    private RedisService redisService;

	@Override
	public void run(String... args) throws Exception {
		Map<String, Session> connections = null;
		Gson gson = new Gson();
		while(true) {
			
			long startTime = System.currentTimeMillis();   //获取开始时间
			
			connections = WebSocketServer.getAllSession();
			HeartbeatVo heartbeat = new HeartbeatVo();
			for (Map.Entry<String, Session> entry : connections.entrySet()) {
				if(redisService.get(entry.getKey()) == null) {
					connections.remove(entry.getKey());
				} else {
					heartbeat.setPing(String.valueOf(new Date().getTime()));
					WebSocketServer.sendInfo(gson.toJson(heartbeat), entry.getKey());
				}
			}
			
			long endTime = System.currentTimeMillis(); //获取结束时间
			
			logger.info("心跳运行时间: " + (endTime - startTime) + "ms");
			
			Thread.sleep(5000); //5秒
		}
		
	}

}
