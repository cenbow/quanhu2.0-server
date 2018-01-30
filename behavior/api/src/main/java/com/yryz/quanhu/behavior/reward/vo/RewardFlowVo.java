/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2017年9月13日
 * Id: RewardDetailVo.java, 2017年9月13日 下午5:23:27 Administrator
 */
package com.yryz.quanhu.behavior.reward.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 打赏流水明细
 */
public class RewardFlowVo implements Serializable {

    private static final long serialVersionUID = -6942318696291561069L;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 支出或者受赏金额
     */
    private Long amount;

    /**
     * 打赏描述
     */
    private String rewardDesc;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getRewardDesc() {
        return rewardDesc;
    }

    public void setRewardDesc(String rewardDesc) {
        this.rewardDesc = rewardDesc;
    }
}
