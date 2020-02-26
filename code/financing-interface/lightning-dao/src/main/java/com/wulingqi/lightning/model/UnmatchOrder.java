package com.wulingqi.lightning.model;

import java.io.Serializable;
import java.util.Date;

public class UnmatchOrder implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 商户主键id
     */
    private Long merchantId;

    /**
     * 商户订单编号
     */
    private String merchantOrderNo;

    /**
     * 支付方式: 0->支付宝
     */
    private Integer payType;

    /**
     * 支付金额
     */
    private String amount;

    /**
     * 成功回调地址
     */
    private String successUrl;

    /**
     * 失败回调地址
     */
    private String errorUrl;

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

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
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
        sb.append(", merchantId=").append(merchantId);
        sb.append(", merchantOrderNo=").append(merchantOrderNo);
        sb.append(", payType=").append(payType);
        sb.append(", amount=").append(amount);
        sb.append(", successUrl=").append(successUrl);
        sb.append(", errorUrl=").append(errorUrl);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}