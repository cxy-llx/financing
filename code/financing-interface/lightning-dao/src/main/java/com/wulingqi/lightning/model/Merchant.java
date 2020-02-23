package com.wulingqi.lightning.model;

import java.io.Serializable;
import java.util.Date;

public class Merchant implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 商户名称
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐
     */
    private String passwdSalt;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 商户id
     */
    private String mchId;

    /**
     * 商户秘钥
     */
    private String mchKey;

    /**
     * 余额
     */
    private String balance;

    /**
     * 冻结余额
     */
    private String freezeBalance;

    /**
     * 结算费率
     */
    private String settlementRate;

    /**
     * 最后登录时间
     */
    private Date loginTime;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 帐号启用状态: 0->禁用; 1->启用
     */
    private Integer status;

    /**
     * 删除状态: 0->未删除; 1->已删除
     */
    private Integer deleteStatus;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 注册时间
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswdSalt() {
        return passwdSalt;
    }

    public void setPasswdSalt(String passwdSalt) {
        this.passwdSalt = passwdSalt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(String freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public String getSettlementRate() {
        return settlementRate;
    }

    public void setSettlementRate(String settlementRate) {
        this.settlementRate = settlementRate;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        sb.append(", userId=").append(userId);
        sb.append(", name=").append(name);
        sb.append(", password=").append(password);
        sb.append(", passwdSalt=").append(passwdSalt);
        sb.append(", phone=").append(phone);
        sb.append(", avatarUrl=").append(avatarUrl);
        sb.append(", mchId=").append(mchId);
        sb.append(", mchKey=").append(mchKey);
        sb.append(", balance=").append(balance);
        sb.append(", freezeBalance=").append(freezeBalance);
        sb.append(", settlementRate=").append(settlementRate);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", loginIp=").append(loginIp);
        sb.append(", status=").append(status);
        sb.append(", deleteStatus=").append(deleteStatus);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", version=").append(version);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}