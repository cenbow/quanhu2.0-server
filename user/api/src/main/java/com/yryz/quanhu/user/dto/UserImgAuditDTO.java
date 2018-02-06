package com.yryz.quanhu.user.dto;

import com.yryz.common.entity.GenericEntity;
import com.yryz.quanhu.user.contants.Constants.ImgAuditStatus;

/**
 * 用户头像审核
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class UserImgAuditDTO extends GenericEntity{
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户头像
     */
    private String userImg;
    /**
     * 审核状态 {@link ImgAuditStatus}
     */
    private Byte auditStatus;
    public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Byte getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Byte auditStatus) {
		this.auditStatus = auditStatus;
	}

	public UserImgAuditDTO() {
		super();
	}

	public UserImgAuditDTO(String userId, String userImg, Byte auditStatus) {
		super();
		this.userId = userId;
		this.userImg = userImg;
		this.auditStatus = auditStatus;
	}


}
