package com.wulingqi.lightning.model;

import java.io.Serializable;

public class TeamLevel implements Serializable {
    /**
     * 主键Id
     */
    private Long id;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 推荐人id
     */
    private Long parentId;

    /**
     * 所有上级(格式: /1001/1002/1003/)
     */
    private String allParentId;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAllParentId() {
        return allParentId;
    }

    public void setAllParentId(String allParentId) {
        this.allParentId = allParentId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", parentId=").append(parentId);
        sb.append(", allParentId=").append(allParentId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}