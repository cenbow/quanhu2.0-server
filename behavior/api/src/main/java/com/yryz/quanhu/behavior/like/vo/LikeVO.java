package com.yryz.quanhu.behavior.like.vo;

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
    private long kid;

    /**
     * 资源ID
     */
    private long resourceId;

    /**
     * 点赞时间
     */
    private String createDate;

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 用户昵称
     */
    private String userNickName;

    /**
     * 用户头像
     */
    private String userImg;

    public String getUserNickName() {
        return userNickName;
    }

    public LikeVO setUserNickName(String userNickName) {
        this.userNickName = userNickName;
        return this;
    }

    public String getUserImg() {
        return userImg;
    }

    public LikeVO setUserImg(String userImg) {
        this.userImg = userImg;
        return this;
    }

    public long getKid() {
        return kid;
    }

    public LikeVO setKid(long kid) {
        this.kid = kid;
        return this;
    }

    public long getResourceId() {
        return resourceId;
    }

    public LikeVO setResourceId(long resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public LikeVO setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public LikeVO setUserId(long userId) {
        this.userId = userId;
        return this;
    }





}
