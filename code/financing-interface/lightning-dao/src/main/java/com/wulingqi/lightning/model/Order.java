package com.wulingqi.lightning.model;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 商户主键id
     */
    private Long merchantId;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 系统订单编号
     */
    private String orderNo;

    /**
     * 商户订单编号
     */
    private String merchantOrderNo;

    /**
     * 订单编号(支付宝)
     */
    private String payOrderNo;

    /**
     * 支付方式: 0->支付宝
     */
    private Integer payType;

    /**
     * 支付金额
     */
    private String amount;

    /**
     * 支付码
     */
    private String payCode;

    /**
     * 成功回调地址
     */
    private String successUrl;

    /**
     * 失败回调地址
     */
    private String errorUrl;

    /**
     * 订单状态: 0->等待下发支付码; 1->未支付; 2->已支付; 3->订单超时
     */
    private Integer status;

    /**
     * 回调状态: 0->未回调; 2->已回调
     */
    private Integer callbackStatus;

    /**
     * 支付码超时时间
     */
    private Date deadlineTime;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 乐观锁版本号
     */
    private Long version;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCallbackStatus() {
        return callbackStatus;
    }

    public void setCallbackStatus(Integer callbackStatus) {
        this.callbackStatus = callbackStatus;
    }

    public Date getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(Date deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", merchantId=").append(merchantId);
        sb.append(", memberId=").append(memberId);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", merchantOrderNo=").append(merchantOrderNo);
        sb.append(", payOrderNo=").append(payOrderNo);
        sb.append(", payType=").append(payType);
        sb.append(", amount=").append(amount);
        sb.append(", payCode=").append(payCode);
        sb.append(", successUrl=").append(successUrl);
        sb.append(", errorUrl=").append(errorUrl);
        sb.append(", status=").append(status);
        sb.append(", callbackStatus=").append(callbackStatus);
        sb.append(", deadlineTime=").append(deadlineTime);
        sb.append(", payTime=").append(payTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", version=").append(version);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}