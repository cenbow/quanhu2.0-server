package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/22 17:00
 * Created by huangxy
 */
public class UserRelationCountVo implements Serializable{

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
