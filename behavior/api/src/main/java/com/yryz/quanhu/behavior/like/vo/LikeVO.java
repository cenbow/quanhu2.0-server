package com.yryz.quanhu.behavior.like.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

/**
 * @Author:sun
 * @version:2.0
 * @Description:点赞
 * @Date:Created in 14:52 2018/1/24
 */
public class LikeVO implements Serializable{

    private static final long serialVersionUID = 4043275150693984318L;
    /**
     * 业务ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private long kid;

    /**
     * 资源ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private long resourceId;

    /**
     * 点赞时间
     */
    private String createDate;

    /**
     * 用户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private long userId;

    /**
     * 用户昵称
     */
    private String userNickName;

    /**
     * 用户头像
     */
    private String userImg;

    public long getKid() {
        return kid;
    }

    public void setKid(long kid) {
        this.kid = kid;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
}
