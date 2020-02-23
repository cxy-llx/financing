package com.wulingqi.lightning.model;

import java.io.Serializable;
import java.util.Date;

public class VerificationCode implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 手机号/邮箱
     */
    private String receiver;

    /**
     * 接收类型: 0->手机号; 1->邮箱
     */
    private Integer receiverType;

    /**
     * 验证类型: 0->注册; 1->忘记密码; 2->修改密码
     */
    private Integer verifyType;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 内容
     */
    private String content;

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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Integer getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(Integer receiverType) {
        this.receiverType = receiverType;
    }

    public Integer getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(Integer verifyType) {
        this.verifyType = verifyType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        sb.append(", receiver=").append(receiver);
        sb.append(", receiverType=").append(receiverType);
        sb.append(", verifyType=").append(verifyType);
        sb.append(", ip=").append(ip);
        sb.append(", content=").append(content);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}