package com.wulingqi.lightning.portal.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wulingqi.lightning.model.DirectInviteStatistics;
import com.wulingqi.lightning.model.InviteStatisticsHandleQueue;
import com.wulingqi.lightning.model.Member;
import com.wulingqi.lightning.model.MemberStatistics;
import com.wulingqi.lightning.model.TeamInviteStatistics;
import com.wulingqi.lightning.model.TeamLevel;

public interface PortalMemberMapper {

	Member selectMemberByAccount(@Param("account") String account);
	
	Member selectMemberByInviteCode(@Param("inviteCode") String inviteCode);
	
	TeamLevel selectTeamLevelByMemberId(@Param("memberId") Long memberId);
	
	List<InviteStatisticsHandleQueue> selectInviteStatisticsHandleQueue();
	
	TeamInviteStatistics selectTeamInviteStatisticsByMemberIdAndStatisticsDate(@Param("memberId") Long memberId, @Param("statisticsDate") Date statisticsDate);
	
	DirectInviteStatistics selectDirectInviteStatisticsByMemberIdAndStatisticsDate(@Param("memberId") Long memberId, @Param("statisticsDate") Date statisticsDate);
	
	MemberStatistics selectMemberStatisticsByMemberId(@Param("memberId") Long memberId);
	
}
