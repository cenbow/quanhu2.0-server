package com.yryz.quanhu.support.activity.vo;

import java.io.Serializable;

public class ActivityVoteConfigVo implements Serializable {

    private Long activityInfoId;

    private String configSources;

    public Long getActivityInfoId() {
        return activityInfoId;
    }

    public void setActivityInfoId(Long activityInfoId) {
        this.activityInfoId = activityInfoId;
    }

    public String getConfigSources() {
        return configSources;
    }

    public void setConfigSources(String configSources) {
        this.configSources = configSources;
    }
}
