package com.yryz.quanhu.other.activity.vo;



import com.yryz.quanhu.other.activity.entity.ActivityUserPrizes;

import java.io.Serializable;
import java.util.List;

public class ActivityUserPrizesVo implements Serializable{

    private List<ActivityUserPrizes> prizes;

    
    private Long userId;

    private Integer receiveFlag;

    private Integer remainingFlag;

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

    public Integer getReceiveFlag() {
        return receiveFlag;
    }

    public void setReceiveFlag(Integer receiveFlag) {
        this.receiveFlag = receiveFlag;
    }

    public Integer getRemainingFlag() {
        return remainingFlag;
    }

    public void setRemainingFlag(Integer remainingFlag) {
        this.remainingFlag = remainingFlag;
    }
}
