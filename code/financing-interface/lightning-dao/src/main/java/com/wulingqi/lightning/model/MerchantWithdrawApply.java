package com.wulingqi.lightning.model;

import java.io.Serializable;
import java.util.Date;

public class MerchantWithdrawApply implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 商户主键id
     */
    private Long merchantId;

    /**
     * 值
     */
    private String value;

    /**
     * 手续费
     */
    private String poundage;

    /**
     * 扣出手续费后的值
     */
    private String actualValue;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行账户名
     */
    private String bankAccount;

    /**
     * 银行卡卡号
     */
    private String bankCardNo;

    /**
     * 提现状态: 0->申请中; 1->成功; 2->失败
     */
    private Integer withdrawStatus;

    /**
     * 失败原因
     */
    private String failedReason;

    /**
     * 到账时间
     */
    private Date arrivedTime;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPoundage() {
        return poundage;
    }

    public void setPoundage(String poundage) {
        this.poundage = poundage;
    }

    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public Integer getWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(Integer withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public Date getArrivedTime() {
        return arrivedTime;
    }

    public void setArrivedTime(Date arrivedTime) {
        this.arrivedTime = arrivedTime;
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
        sb.append(", value=").append(value);
        sb.append(", poundage=").append(poundage);
        sb.append(", actualValue=").append(actualValue);
        sb.append(", bankName=").append(bankName);
        sb.append(", bankAccount=").append(bankAccount);
        sb.append(", bankCardNo=").append(bankCardNo);
        sb.append(", withdrawStatus=").append(withdrawStatus);
        sb.append(", failedReason=").append(failedReason);
        sb.append(", arrivedTime=").append(arrivedTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}