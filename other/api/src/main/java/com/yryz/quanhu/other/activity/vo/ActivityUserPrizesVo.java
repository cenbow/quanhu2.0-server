package com.yryz.quanhu.other.activity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yryz.quanhu.other.activity.entity.ActivityUserPrizes;

import java.io.Serializable;
import java.util.List;

public class ActivityUserPrizesVo implements Serializable{

    private List<ActivityUserPrizes> prizes;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    public List<ActivityUserPrizes> getPrizes() {
        return prizes;
    }

    public void setPrizes(List<ActivityUserPrizes> prizes) {
        this.prizes = prizes;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
