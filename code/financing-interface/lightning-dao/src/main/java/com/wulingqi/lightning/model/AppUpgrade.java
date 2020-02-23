package com.wulingqi.lightning.model;

import java.io.Serializable;
import java.util.Date;

public class AppUpgrade implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 类型: 0->ANDROID; 1->IOS
     */
    private Integer type;

    /**
     * 版本号
     */
    private String version;

    /**
     * 下载地址
     */
    private String url;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新说明
     */
    private String msg;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", version=").append(version);
        sb.append(", url=").append(url);
        sb.append(", createTime=").append(createTime);
        sb.append(", msg=").append(msg);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}