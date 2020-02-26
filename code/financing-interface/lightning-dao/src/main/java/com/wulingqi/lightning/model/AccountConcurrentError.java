package com.wulingqi.lightning.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountConcurrentError implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 会员id/商户主键id
     */
    private Long userId;

    /**
     * 用户类型: 0->会员; 1->商户
     */
    private Integer userType;

    /**
     * 交易类型: 0->收入; 1->支出
     */
    private Integer tradeType;

    /**
     * 交易项目
     */
    private Integer tradeItem;

    /**
     * 交易金额
     */
    private BigDecimal value;

    /**
     * 交易标题
     */
    private String title;

    /**
     * 备注
     */
    private String note;

    /**
     * 处理状态: 0->未处理; 1->已处理
     */
    private Integer handleStatus;

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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getTradeItem() {
        return tradeItem;
    }

    public void setTradeItem(Integer tradeItem) {
        this.tradeItem = tradeItem;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
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
        sb.append(", userType=").append(userType);
        sb.append(", tradeType=").append(tradeType);
        sb.append(", tradeItem=").append(tradeItem);
        sb.append(", value=").append(value);
        sb.append(", title=").append(title);
        sb.append(", note=").append(note);
        sb.append(", handleStatus=").append(handleStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}