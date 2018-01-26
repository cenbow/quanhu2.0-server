package com.yryz.quanhu.support.activity.vo;

import com.yryz.quanhu.support.activity.entity.ActivityUserPrizes;

import java.io.Serializable;
import java.util.List;

public class ActivityUserPrizesVo implements Serializable{

    private List<ActivityUserPrizes> prizes;

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
