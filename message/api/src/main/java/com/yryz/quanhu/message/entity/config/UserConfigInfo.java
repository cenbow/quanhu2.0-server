package com.yryz.quanhu.message.entity.config;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户配置
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class UserConfigInfo implements Serializable {
	/**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private String custId;

    /**
     * 推送消息开关 0:开启 1:关闭
     */
    private Byte pushStatus;

    /**
     * 申请时间
     */
    private Date createTime;

    /**
     * 操作时间
     */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public Byte getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Byte pushStatus) {
        this.pushStatus = pushStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	@Override
	public String toString() {
		return "CustConfig [id=" + id + ", custId=" + custId + ", pushStatus=" + pushStatus + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}
}
