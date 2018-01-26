package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/22 17:00
 * Created by huangxy
 */
public class UserRelationQueryVo implements Serializable{

    private int toFollowStatus;
    private int fromFollowStatus;
    private int friendStatus;
    private int toBlackStatus;
    private int fromBlackStatus;

    private String userId;
    private String userName;
    private String userRemarkName;
    private int userStarFlag;
    private String userHeadImg;
    private String userSummary;

    public String getUserRemarkName() {
        return userRemarkName;
    }

    public void setUserRemarkName(String userRemarkName) {
        this.userRemarkName = userRemarkName;
    }

    public int getToFollowStatus() {
        return toFollowStatus;
    }

    public void setToFollowStatus(int toFollowStatus) {
        this.toFollowStatus = toFollowStatus;
    }

    public int getFromFollowStatus() {
        return fromFollowStatus;
    }

    public void setFromFollowStatus(int fromFollowStatus) {
        this.fromFollowStatus = fromFollowStatus;
    }

    public int getToBlackStatus() {
        return toBlackStatus;
    }

    public void setToBlackStatus(int toBlackStatus) {
        this.toBlackStatus = toBlackStatus;
    }

    public int getFromBlackStatus() {
        return fromBlackStatus;
    }

    public void setFromBlackStatus(int fromBlackStatus) {
        this.fromBlackStatus = fromBlackStatus;
    }

    public int getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(int friendStatus) {
        this.friendStatus = friendStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserStarFlag() {
        return userStarFlag;
    }

    public void setUserStarFlag(int userStarFlag) {
        this.userStarFlag = userStarFlag;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public String getUserSummary() {
        return userSummary;
    }

    public void setUserSummary(String userSummary) {
        this.userSummary = userSummary;
    }
}
