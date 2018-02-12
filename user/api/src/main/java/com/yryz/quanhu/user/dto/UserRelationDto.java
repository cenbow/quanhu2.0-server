package com.yryz.quanhu.user.dto;

import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.entity.UserRelationEntity;
import com.yryz.quanhu.user.service.UserRelationApi;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;

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
    /**
     * 原关系状态
     */
    private int orgRelationStatus;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 备注名
     */
    private String userRemarkName;
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

    public int getOrgRelationStatus() {
        return orgRelationStatus;
    }

    public void setOrgRelationStatus(int orgRelationStatus) {
        this.orgRelationStatus = orgRelationStatus;
    }

    public String getUserRemarkName() {
        return userRemarkName;
    }

    public void setUserRemarkName(String userRemarkName) {
        this.userRemarkName = userRemarkName;
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
