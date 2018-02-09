package com.yryz.quanhu.coterie.coterie.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by KF on 2018/1/29.
 */
public class User implements Serializable{
	private static final long serialVersionUID = -886802207285084885L;

	@ApiModelProperty("认证状态 10-未认证 11-已认证")
    private Byte authStatus	;
	
	@ApiModelProperty("是否达人 : 10:普通用户 11:达人")
	private Byte userRole;
	
	@ApiModelProperty("头像")
    private String headImg;	
	
	@ApiModelProperty("昵称")
    private String nickName;
	
    public Byte getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Byte authStatus) {
        this.authStatus = authStatus;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

	public Byte getUserRole() {
		return userRole;
	}

	public void setUserRole(Byte userRole) {
		this.userRole = userRole;
	}

}
