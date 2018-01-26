package com.yryz.quanhu.support.activity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

public class ActivityVoteConfigVo implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
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
