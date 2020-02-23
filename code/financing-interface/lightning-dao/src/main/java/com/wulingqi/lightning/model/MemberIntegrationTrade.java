package com.wulingqi.lightning.model;

import java.io.Serializable;
import java.util.Date;

public class MemberIntegrationTrade implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 会员id
     */
    private Long memberId;

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
     * 交易标题
     */
    private String title;

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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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
        sb.append(", memberId=").append(memberId);
        sb.append(", tradeType=").append(tradeType);
        sb.append(", tradeItem=").append(tradeItem);
        sb.append(", value=").append(value);
        sb.append(", beforeValue=").append(beforeValue);
        sb.append(", afterValue=").append(afterValue);
        sb.append(", title=").append(title);
        sb.append(", note=").append(note);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}