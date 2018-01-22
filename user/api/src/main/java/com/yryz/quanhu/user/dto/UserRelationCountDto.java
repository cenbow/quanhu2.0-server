package com.yryz.quanhu.user.dto;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/18 14:30
 * Created by huangxy
 */
public class UserRelationCountDto implements Serializable{

    private boolean isNewRecord;

    private String targetUserId;
    /**
     * 粉丝数
     */
    private long fansCount;
    /**
     * 关注数
     */
    private long followCount;
    /**
     * 主动拉黑数
     */
    private long toBlackCount;
    /**
     * 被动拉黑数
     */
    private long fromBlackCount;
    /**
     * 好友数
     */
    private long friendCount;

    public boolean isNewRecord() {
        return isNewRecord;
    }

    public void setNewRecord(boolean newRecord) {
        isNewRecord = newRecord;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public long getFansCount() {
        return fansCount;
    }

    public void setFansCount(long fansCount) {
        this.fansCount = fansCount;
    }

    public long getFollowCount() {
        return followCount;
    }

    public void setFollowCount(long followCount) {
        this.followCount = followCount;
    }

    public long getToBlackCount() {
        return toBlackCount;
    }

    public void setToBlackCount(long toBlackCount) {
        this.toBlackCount = toBlackCount;
    }

    public long getFromBlackCount() {
        return fromBlackCount;
    }

    public void setFromBlackCount(long fromBlackCount) {
        this.fromBlackCount = fromBlackCount;
    }

    public long getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(long friendCount) {
        this.friendCount = friendCount;
    }
}
