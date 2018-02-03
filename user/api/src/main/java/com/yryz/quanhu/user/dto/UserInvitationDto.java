package com.yryz.quanhu.user.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/1 15:30
 * Created by huangxy
 */
public class UserInvitationDto implements Serializable{

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户手机
     */
    private String userPhone;
    /**
     * 用户昵称
     */
    private String userNickName;

    /**
     * 邀请数量
     */
    private long userRegInviterNum;
    /**
     * 被邀请人ID
     */
    private Long userInviterId;
    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 用户最后登录时间
     */
    private Date userLastLoginDate;

    /**
     * 被邀请人
     */
    private UserInvitationDto fromUser;
    /**
     * 积分成长值
     */
    private long userScoreNum;

    private int pageNo;
    private int pageSize;


    private String startDate;

    private String endDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public long getUserRegInviterNum() {
        return userRegInviterNum;
    }

    public void setUserRegInviterNum(long userRegInviterNum) {
        this.userRegInviterNum = userRegInviterNum;
    }

    public Long getUserInviterId() {
        return userInviterId;
    }

    public void setUserInviterId(Long userInviterId) {
        this.userInviterId = userInviterId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUserLastLoginDate() {
        return userLastLoginDate;
    }

    public void setUserLastLoginDate(Date userLastLoginDate) {
        this.userLastLoginDate = userLastLoginDate;
    }

    public UserInvitationDto getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserInvitationDto fromUser) {
        this.fromUser = fromUser;
    }

    public long getUserScoreNum() {
        return userScoreNum;
    }

    public void setUserScoreNum(long userScoreNum) {
        this.userScoreNum = userScoreNum;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
