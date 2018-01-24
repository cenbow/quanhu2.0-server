package com.yryz.quanhu.user.dto;

import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.entity.UserRelationEntity;
import com.yryz.quanhu.user.service.UserRelationApi;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/18 13:52
 * Created by huangxy
 */
public class UserRelationDto extends UserRelationEntity{

    private boolean isNewRecord = false;

    /**页码*/
    private Integer currentPage = 1;
    /**每页大小*/
    private Integer pageSize = 10;

    private int toFollowStatus;

    private int fromFollowStatus;

    private int toBlackStatus;

    private int fromBlackStatus;

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 头像
     */
    private String userHeadImg;
    /**
     * 达人标识
     */
    private int userStarFlag;
    /**
     * 用户简介
     */
    private String userSummary;


    public int getToBlackStatus() {
        return toBlackStatus;
    }

    public void setToBlackStatus(int toBlackStatus) {
        this.toBlackStatus = toBlackStatus;
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

    public int getFromBlackStatus() {
        return fromBlackStatus;
    }

    public void setFromBlackStatus(int fromBlackStatus) {
        this.fromBlackStatus = fromBlackStatus;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
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

    public String getUserSummary() {
        return userSummary;
    }

    public void setUserSummary(String userSummary) {
        this.userSummary = userSummary;
    }

    public boolean isNewRecord() {
        return isNewRecord;
    }

    public void setNewRecord(boolean newRecord) {
        isNewRecord = newRecord;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
