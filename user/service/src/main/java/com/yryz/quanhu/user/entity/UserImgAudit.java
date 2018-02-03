/*
 * UserImgAudit.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-12 Created
 */
package com.yryz.quanhu.user.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yryz.common.entity.GenericEntity;

/**
 * 用户头像审核表
 * 
 * @author xxx
 * @version 1.0 2018-01-12
 * @since 1.0
 */
@SuppressWarnings("serial")
public class UserImgAudit extends GenericEntity{

    /**
     * 用户id
     */
	@JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 被审核头像
     */
    private String userImg;

    /**
     * 审核状态 10-待审核 11-审核通过 12-审核失败
     */
    private Byte auditStatus;

    /**
     * 操作人名称
     */
    private String operational;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg == null ? null : userImg.trim();
    }

    public Byte getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Byte auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getOperational() {
        return operational;
    }

    public void setOperational(String operational) {
        this.operational = operational == null ? null : operational.trim();
    }
    /**
	 * 头像审核状态
	 *
	 */
	public enum ImgAuditStatus{
		/** 待审核 */
		NO_AUDIT(10),
		/** 审核成功 */
		SUCCESS(11),
		/** 审核失败 */
		FAIL(12);
		
		private int status;
		
		ImgAuditStatus(int status) {
			this.status = status;
		}
		public int getStatus(){
			return status;
		}
	}
}