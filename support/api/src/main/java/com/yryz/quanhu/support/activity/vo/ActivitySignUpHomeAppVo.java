package com.yryz.quanhu.support.activity.vo;

import com.yryz.quanhu.support.activity.entity.ActivityInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 2018/1/22.
 */
public class ActivitySignUpHomeAppVo extends ActivityInfo {

    /**
     *报名状态 11可参与 12已报名 13已支付完成，请勿重复支付
     */
    private Integer enrolStatus;
    /**
     *报名人数上限
     */
    private Integer enrolUpper;
    /**
     *活动状态 11未开始 12进行中 13结束
     */
    private Integer activityStatus;
    /**
     *当前时间
     */
    private Date currentDate;

    public Integer getEnrolStatus() {
        return enrolStatus;
    }

    public void setEnrolStatus(Integer enrolStatus) {
        this.enrolStatus = enrolStatus;
    }

    public Integer getEnrolUpper() {
        return enrolUpper;
    }

    public void setEnrolUpper(Integer enrolUpper) {
        this.enrolUpper = enrolUpper;
    }

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }
}
