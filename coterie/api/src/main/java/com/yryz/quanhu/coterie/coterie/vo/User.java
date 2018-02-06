package com.yryz.quanhu.coterie.coterie.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by KF on 2018/1/29.
 */
public class User implements Serializable{
	private static final long serialVersionUID = -886802207285084885L;

	@ApiModelProperty("是否达人 : 10未认证，11认证")
    private String   authStatus	;
	
	@ApiModelProperty("头像")
    private String headImg;	
	
	@ApiModelProperty("昵称")
    private String nickName;
	
    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
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


}
