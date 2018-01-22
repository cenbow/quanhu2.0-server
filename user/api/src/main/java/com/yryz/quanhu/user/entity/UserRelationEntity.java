package com.yryz.quanhu.user.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/18 13:40
 * Created by huangxy
 */
public class UserRelationEntity extends GenericEntity {
    /**
     * 用户源
     */
    private String sourceUserId;
    /**
     * 用户目标
     */
    private String targetUserId;

    /**
     * 关注状态
     * 10 否，11 是(source为target的粉丝)
     *
     */
    private int followStatus;
    /**
     * 黑名单状态
     * 10 否，11是(target在source黑名单中)
     */
    private int blackStatus;
    /**
     * 好友状态
     * 10 否，11是(主要同步双方互粉情况下)
     */
    private int friendStatus;

    /**
     * 删除标记
     */
    private int delFlag;
    /**
     * 版本号
     */
    private int version;



    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(String sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public int getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(int followStatus) {
        this.followStatus = followStatus;
    }

    public int getBlackStatus() {
        return blackStatus;
    }

    public void setBlackStatus(int blackStatus) {
        this.blackStatus = blackStatus;
    }

    public int getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(int friendStatus) {
        this.friendStatus = friendStatus;
    }
}
