package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/2/2
 * @description 用户聚合数据
 */
public class UserInfoVO implements Serializable {

    private static final long serialVersionUID = -581574272982013024L;

    /**
     * 用户基础信息
     */
    private UserBaseInfoVO userBaseInfo;

    /**
     * 用户标签信息
     */
    private UserTagInfoVO userTagInfo;


    /**
     * 达人信息
     */
    private StarAuthInfoVO userStarInfo;

    /**
     * 积分事件信息
     */
    private EventAccountVO eventAccountInfo = new EventAccountVO();

    /**
     * 注册渠道信息
     */
    private UserRegLogVO userRegLog;
    /**
     * 用户收入
     */
    private String userOrderIntegralTotal = "0";

    public UserBaseInfoVO getUserBaseInfo() {
        return userBaseInfo;
    }

    public void setUserBaseInfo(UserBaseInfoVO userBaseInfo) {
        this.userBaseInfo = userBaseInfo;
    }

    public UserTagInfoVO getUserTagInfo() {
        return userTagInfo;
    }

    public void setUserTagInfo(UserTagInfoVO userTagInfo) {
        this.userTagInfo = userTagInfo;
    }

    public StarAuthInfoVO getUserStarInfo() {
        return userStarInfo;
    }

    public void setUserStarInfo(StarAuthInfoVO userStarInfo) {
        this.userStarInfo = userStarInfo;
    }

    public EventAccountVO getEventAccountInfo() {
        return eventAccountInfo;
    }

    public void setEventAccountInfo(EventAccountVO eventAccountInfo) {
        this.eventAccountInfo = eventAccountInfo;
    }

    public UserRegLogVO getUserRegLog() {
        return userRegLog;
    }

    public void setUserRegLog(UserRegLogVO userRegLog) {
        this.userRegLog = userRegLog;
    }

    public String getUserOrderIntegralTotal() {
        return userOrderIntegralTotal;
    }

    public void setUserOrderIntegralTotal(String userOrderIntegralTotal) {
        this.userOrderIntegralTotal = userOrderIntegralTotal;
    }
}
