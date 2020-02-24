package com.wulingqi.lightning.portal.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wulingqi.lightning.model.DirectInviteStatistics;
import com.wulingqi.lightning.model.Member;
import com.wulingqi.lightning.model.MemberStatistics;
import com.wulingqi.lightning.model.StatisticsHandleQueue;
import com.wulingqi.lightning.model.TeamInviteStatistics;
import com.wulingqi.lightning.model.TeamLevel;
import com.wulingqi.lightning.portal.vo.IntegrationDetailListVo;
import com.wulingqi.lightning.portal.vo.RechargeDetailListVo;
import com.wulingqi.lightning.portal.vo.TeamListVo;

public interface PortalMemberMapper {

	Member selectMemberByAccount(@Param("account") String account);
	
	Member selectMemberByPhone(@Param("phone") String phone);
	
	Member selectMemberByInviteCode(@Param("inviteCode") String inviteCode);
	
	TeamLevel selectTeamLevelByMemberId(@Param("memberId") Long memberId);
	
	List<StatisticsHandleQueue> selectStatisticsHandleQueue();
	
	TeamInviteStatistics selectTeamInviteStatisticsByMemberIdAndStatisticsDate(@Param("memberId") Long memberId, @Param("statisticsDate") Date statisticsDate);
	
	DirectInviteStatistics selectDirectInviteStatisticsByMemberIdAndStatisticsDate(@Param("memberId") Long memberId, @Param("statisticsDate") Date statisticsDate);
	
	MemberStatistics selectMemberStatisticsByMemberId(@Param("memberId") Long memberId);
	
	List<TeamListVo> selectMemberTeamList(@Param("memberId") Long memberId);
	
	int updateMemberByPrimaryKey(Member record);
	
	BigDecimal selectMemberTotalRecharge(@Param("memberId") Long memberId);
	
	BigDecimal selectMemberRechargeByRechargeStatus(@Param("memberId") Long memberId, @Param("rechargeStatus") Integer rechargeStatus);
	
	List<RechargeDetailListVo> selectMemberRechargeDetail(@Param("memberId") Long memberId);
	
	BigDecimal selectMemberTotalIncome(@Param("memberId") Long memberId);
	
	BigDecimal selectMemberTotalExpend(@Param("memberId") Long memberId);
	
	List<IntegrationDetailListVo> selectMemberIntegrationDetail(@Param("memberId") Long memberId);
	
}
