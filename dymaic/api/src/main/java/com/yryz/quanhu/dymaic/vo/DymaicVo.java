package com.yryz.quanhu.dymaic.vo;

import java.io.Serializable;

/**
 * @author xiepeng
 * @version 1.0
 * @data 2018/1/19 0019 29
 */
public class DymaicVo extends Dymaic implements Serializable {

    /**
     * 用户信息
     */
    private Dymaic user;

    /**
     * 统计信息（评论、转发、点赞）
     */
    private Dymaic statistics;


    public Dymaic getUser() {
        return user;
    }

    public void setUser(Dymaic user) {
        this.user = user;
    }

    public Dymaic getStatistics() {
        return statistics;
    }

    public void setStatistics(Dymaic statistics) {
        this.statistics = statistics;
    }
}
