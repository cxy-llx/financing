package com.wulingqi.lightning.portal.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wulingqi.lightning.mapper.DirectInviteStatisticsMapper;
import com.wulingqi.lightning.mapper.InviteStatisticsHandleQueueMapper;
import com.wulingqi.lightning.mapper.MemberStatisticsMapper;
import com.wulingqi.lightning.mapper.TeamInviteStatisticsMapper;
import com.wulingqi.lightning.model.DirectInviteStatistics;
import com.wulingqi.lightning.model.InviteStatisticsHandleQueue;
import com.wulingqi.lightning.model.MemberStatistics;
import com.wulingqi.lightning.model.TeamInviteStatistics;
import com.wulingqi.lightning.model.TeamLevel;
import com.wulingqi.lightning.portal.component.RedisLock;
import com.wulingqi.lightning.portal.mapper.PortalMemberMapper;
import com.wulingqi.lightning.utils.LightningConstant;

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
	private InviteStatisticsHandleQueueMapper inviteStatisticsHandleQueueMapper;
	
	@Autowired
	private TeamInviteStatisticsMapper teamInviteStatisticsMapper;
	
	@Autowired
	private DirectInviteStatisticsMapper directInviteStatisticsMapper;
	
	@Autowired
	private MemberStatisticsMapper memberStatisticsMapper;
	
	@Autowired
	private PortalMemberMapper portalMemberMapper;
	
	@Autowired
	private RedisLock redisLock;
	
	@Scheduled(cron = "0 0/2 * * * ? ") // 间隔2分钟执行
	public void run() {
		
		log.info("Invite statistic handle task start...");
		
		long time = System.currentTimeMillis() + TIMEOUT;
		if(redisLock.lock(INVITE_STATISTICS_HANDLE_LOCK, String.valueOf(time))) {
			
			List<InviteStatisticsHandleQueue> list = portalMemberMapper.selectInviteStatisticsHandleQueue();
			
			for(InviteStatisticsHandleQueue handleQueue : list) {
				log.info("Invite statistic handle memberId: {}", handleQueue.getMemberId());
				
				TeamLevel teamLevel = portalMemberMapper.selectTeamLevelByMemberId(handleQueue.getMemberId());
				
				String allParentIdStr = teamLevel.getAllParentId().substring(1, teamLevel.getAllParentId().length() -1);
				String [] allParentId = allParentIdStr.split("/");
				TeamInviteStatistics teamInviteStatistics = null;
				MemberStatistics teamCount = null;
				
				//会员统计信息表
				MemberStatistics inviteCount = portalMemberMapper.selectMemberStatisticsByMemberId(teamLevel.getParentId());
				inviteCount.setInviteCount(inviteCount.getInviteCount() + 1);
				memberStatisticsMapper.updateByPrimaryKey(inviteCount);
				
				//会员直接邀请统计信息表
				DirectInviteStatistics directInviteStatistics = portalMemberMapper.selectDirectInviteStatisticsByMemberIdAndStatisticsDate(teamLevel.getParentId(), handleQueue.getCreateTime()); 
				if(directInviteStatistics == null) {
					directInviteStatistics = new DirectInviteStatistics();
					directInviteStatistics.setMemberId(teamLevel.getParentId());
					directInviteStatistics.setStatisticsDate(handleQueue.getCreateTime());
					directInviteStatistics.setCount(1);
					directInviteStatisticsMapper.insert(directInviteStatistics);
				} else {
					directInviteStatistics.setCount(directInviteStatistics.getCount() + 1);
					directInviteStatisticsMapper.updateByPrimaryKey(directInviteStatistics);
				}
				
				//会员团队邀请统计信息表
				for(String parentId : allParentId) {
					teamInviteStatistics = portalMemberMapper.selectTeamInviteStatisticsByMemberIdAndStatisticsDate(Long.valueOf(parentId), handleQueue.getCreateTime());
					if(teamInviteStatistics == null) {
						teamInviteStatistics = new TeamInviteStatistics();
						teamInviteStatistics.setMemberId(Long.valueOf(parentId));
						teamInviteStatistics.setStatisticsDate(handleQueue.getCreateTime());
						teamInviteStatistics.setCount(1);
						teamInviteStatisticsMapper.insert(teamInviteStatistics);
					} else {
						teamInviteStatistics.setCount(teamInviteStatistics.getCount() + 1);
						teamInviteStatisticsMapper.updateByPrimaryKey(teamInviteStatistics);
					}
					
					//会员统计信息表
					teamCount = portalMemberMapper.selectMemberStatisticsByMemberId(Long.valueOf(parentId));
					teamCount.setTeamCount(teamCount.getTeamCount() + 1);
					memberStatisticsMapper.updateByPrimaryKey(teamCount);
					
				}
				
				handleQueue.setHandleStatus(LightningConstant.HANDLE_STATUS_YES);
				handleQueue.setUpdateTime(new Date());
				inviteStatisticsHandleQueueMapper.updateByPrimaryKey(handleQueue);
			}
			
			redisLock.unlock(INVITE_STATISTICS_HANDLE_LOCK, String.valueOf(time));
		} else {
			log.error("Invite statistic get lock failed");
		}
		
		log.info("Invite statistic handle task end...");
		
	}

}
