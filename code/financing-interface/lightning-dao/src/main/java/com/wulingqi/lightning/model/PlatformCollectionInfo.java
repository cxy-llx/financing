package com.wulingqi.lightning.model;

import java.io.Serializable;
import java.util.Date;

public class PlatformCollectionInfo implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 收款类型: 0->银行卡; 1->支付宝; 2->微信; 3->USDT
     */
    private Integer collectionType;

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
     * 收款二维码
     */
    private String qrCode;

    /**
     * USDT地址
     */
    private String usdtAddress;

    /**
     * 默认标识(不同收款类型可以有一个是默认状态): 0->否; 1->是
     */
    private Integer defaultFlag;

    /**
     * 删除状态: 0->未删除; 1->已删除
     */
    private Integer deleteStatus;

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

    public Integer getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(Integer collectionType) {
        this.collectionType = collectionType;
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

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getUsdtAddress() {
        return usdtAddress;
    }

    public void setUsdtAddress(String usdtAddress) {
        this.usdtAddress = usdtAddress;
    }

    public Integer getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(Integer defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
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
        sb.append(", collectionType=").append(collectionType);
        sb.append(", bankName=").append(bankName);
        sb.append(", bankAccount=").append(bankAccount);
        sb.append(", bankCardNo=").append(bankCardNo);
        sb.append(", qrCode=").append(qrCode);
        sb.append(", usdtAddress=").append(usdtAddress);
        sb.append(", defaultFlag=").append(defaultFlag);
        sb.append(", deleteStatus=").append(deleteStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}