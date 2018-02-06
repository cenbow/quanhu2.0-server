package com.yryz.quanhu.user.dto;

import com.yryz.common.entity.GenericEntity;

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

	public UserImgAuditDTO() {
		super();
	}
	/**
	 * 批量头像审核
	 * @param userId
	 * @param userImg
	 */
	public UserImgAuditDTO(String userId, String userImg) {
		super();
		this.userId = userId;
		this.userImg = userImg;
	}


}
