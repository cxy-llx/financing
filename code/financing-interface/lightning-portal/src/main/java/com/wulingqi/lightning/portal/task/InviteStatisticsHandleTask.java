package com.wulingqi.lightning.portal.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wulingqi.lightning.portal.component.RedisLock;
import com.wulingqi.lightning.portal.service.StatisticsHandleService;

import lombok.extern.slf4j.Slf4j;

/**
 * 邀请统计信息处理任务
 */
@Component
@Slf4j
@EnableScheduling
public class InviteStatisticsHandleTask {
	
	/**
	 * 分布式锁 redis key
	 */
	public static final String INVITE_STATISTICS_HANDLE_LOCK = "invite_statistics_handle_lock";
	
	public static final int TIMEOUT = 60 * 60 * 1000; //超时时间60分钟
	
	@Autowired
	private StatisticsHandleService service;
	
	@Autowired
	private RedisLock redisLock;
	
	@Scheduled(cron = "0 0/2 * * * ? ") // 间隔2分钟执行
	public void run() {
		
		log.info("Invite statistic handle task start...");
		
		long time = System.currentTimeMillis() + TIMEOUT;
		if(redisLock.lock(INVITE_STATISTICS_HANDLE_LOCK, String.valueOf(time))) {
			
			service.handle();
			
			redisLock.unlock(INVITE_STATISTICS_HANDLE_LOCK, String.valueOf(time));
		} else {
			log.error("Invite statistic get lock failed");
		}
		
		log.info("Invite statistic handle task end...");
		
	}

}
