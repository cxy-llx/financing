package com.wulingqi.lightning.model;

import java.io.Serializable;

public class MemberStatistics implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 邀请人数
     */
    private Integer inviteCount;

    /**
     * 团队人数
     */
    private Integer teamCount;

    /**
     * 直接邀请有效会员人数
     */
    private Integer inviteEffectiveCount;

    /**
     * 团队有效会员人数
     */
    private Integer teamEffectiveCount;

    /**
     * 累计代理收益
     */
    private String agentIncome;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getInviteCount() {
        return inviteCount;
    }

    public void setInviteCount(Integer inviteCount) {
        this.inviteCount = inviteCount;
    }

    public Integer getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(Integer teamCount) {
        this.teamCount = teamCount;
    }

    public Integer getInviteEffectiveCount() {
        return inviteEffectiveCount;
    }

    public void setInviteEffectiveCount(Integer inviteEffectiveCount) {
        this.inviteEffectiveCount = inviteEffectiveCount;
    }

    public Integer getTeamEffectiveCount() {
        return teamEffectiveCount;
    }

    public void setTeamEffectiveCount(Integer teamEffectiveCount) {
        this.teamEffectiveCount = teamEffectiveCount;
    }

    public String getAgentIncome() {
        return agentIncome;
    }

    public void setAgentIncome(String agentIncome) {
        this.agentIncome = agentIncome;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", inviteCount=").append(inviteCount);
        sb.append(", teamCount=").append(teamCount);
        sb.append(", inviteEffectiveCount=").append(inviteEffectiveCount);
        sb.append(", teamEffectiveCount=").append(teamEffectiveCount);
        sb.append(", agentIncome=").append(agentIncome);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}