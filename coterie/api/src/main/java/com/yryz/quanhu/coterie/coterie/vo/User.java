package com.yryz.quanhu.coterie.coterie.vo;

import java.io.Serializable;

/**
 * Created by KF on 2018/1/29.
 */
public class User implements Serializable{

    private String   authStatus	;//是否认证 : 10未认证，11认证）
    private String headImg;	//头像	string
    private String nickName;//	昵称
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
