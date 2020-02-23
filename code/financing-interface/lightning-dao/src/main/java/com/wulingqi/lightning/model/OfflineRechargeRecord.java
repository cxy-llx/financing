package com.wulingqi.lightning.model;

import java.io.Serializable;
import java.util.Date;

public class OfflineRechargeRecord implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 充值金额
     */
    private String value;

    /**
     * 收款账户名
     */
    private String collectionAccountName;

    /**
     * 收款银行名称
     */
    private String collectionBankName;

    /**
     * 收款银行卡号
     */
    private String collectionBankCardNo;

    /**
     * 支付账户名
     */
    private String paymentAccountName;

    /**
     * 支付银行名称
     */
    private String paymentBankName;

    /**
     * 支付银行卡号
     */
    private String paymentBankCardNo;

    /**
     * 支付(截图)凭证
     */
    private String paymentScreenshot;

    /**
     * 充值状态: 0->充值中; 1->成功; 2->失败
     */
    private Integer rechargeStatus;

    /**
     * 失败原因
     */
    private String failedReason;

    /**
     * 充值时间
     */
    private Date rechargeTime;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCollectionAccountName() {
        return collectionAccountName;
    }

    public void setCollectionAccountName(String collectionAccountName) {
        this.collectionAccountName = collectionAccountName;
    }

    public String getCollectionBankName() {
        return collectionBankName;
    }

    public void setCollectionBankName(String collectionBankName) {
        this.collectionBankName = collectionBankName;
    }

    public String getCollectionBankCardNo() {
        return collectionBankCardNo;
    }

    public void setCollectionBankCardNo(String collectionBankCardNo) {
        this.collectionBankCardNo = collectionBankCardNo;
    }

    public String getPaymentAccountName() {
        return paymentAccountName;
    }

    public void setPaymentAccountName(String paymentAccountName) {
        this.paymentAccountName = paymentAccountName;
    }

    public String getPaymentBankName() {
        return paymentBankName;
    }

    public void setPaymentBankName(String paymentBankName) {
        this.paymentBankName = paymentBankName;
    }

    public String getPaymentBankCardNo() {
        return paymentBankCardNo;
    }

    public void setPaymentBankCardNo(String paymentBankCardNo) {
        this.paymentBankCardNo = paymentBankCardNo;
    }

    public String getPaymentScreenshot() {
        return paymentScreenshot;
    }

    public void setPaymentScreenshot(String paymentScreenshot) {
        this.paymentScreenshot = paymentScreenshot;
    }

    public Integer getRechargeStatus() {
        return rechargeStatus;
    }

    public void setRechargeStatus(Integer rechargeStatus) {
        this.rechargeStatus = rechargeStatus;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
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
        sb.append(", value=").append(value);
        sb.append(", collectionAccountName=").append(collectionAccountName);
        sb.append(", collectionBankName=").append(collectionBankName);
        sb.append(", collectionBankCardNo=").append(collectionBankCardNo);
        sb.append(", paymentAccountName=").append(paymentAccountName);
        sb.append(", paymentBankName=").append(paymentBankName);
        sb.append(", paymentBankCardNo=").append(paymentBankCardNo);
        sb.append(", paymentScreenshot=").append(paymentScreenshot);
        sb.append(", rechargeStatus=").append(rechargeStatus);
        sb.append(", failedReason=").append(failedReason);
        sb.append(", rechargeTime=").append(rechargeTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}