package com.wulingqi.lightning.portal.mapper;

import org.apache.ibatis.annotations.Param;

import com.wulingqi.lightning.model.Member;
import com.wulingqi.lightning.model.TeamLevel;

public interface PortalMemberMapper {

	Member selectMemberByPhone(@Param("phone") String phone);
	
	Member selectMemberByInviteCode(@Param("inviteCode") String inviteCode);
	
	TeamLevel selectTeamLevelByMemberId(@Param("memberId") Long memberId);
	
}
