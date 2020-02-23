package com.wulingqi.lightning.model;

import java.io.Serializable;
import java.util.Date;

public class ManagerOperateMemberAccountLog implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 管理员用户id
     */
    private Long userId;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 账户类型: 0->积分
     */
    private Integer accountType;

    /**
     * 操作类型: 0->增加; 1->减少
     */
    private Integer operateType;

    /**
     * 交易值
     */
    private String value;

    /**
     * 交易前余额
     */
    private String beforeValue;

    /**
     * 交易后余额
     */
    private String afterValue;

    /**
     * 备注
     */
    private String note;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getBeforeValue() {
        return beforeValue;
    }

    public void setBeforeValue(String beforeValue) {
        this.beforeValue = beforeValue;
    }

    public String getAfterValue() {
        return afterValue;
    }

    public void setAfterValue(String afterValue) {
        this.afterValue = afterValue;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", memberId=").append(memberId);
        sb.append(", accountType=").append(accountType);
        sb.append(", operateType=").append(operateType);
        sb.append(", value=").append(value);
        sb.append(", beforeValue=").append(beforeValue);
        sb.append(", afterValue=").append(afterValue);
        sb.append(", note=").append(note);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}