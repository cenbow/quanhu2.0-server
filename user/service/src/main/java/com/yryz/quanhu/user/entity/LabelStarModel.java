package com.yryz.quanhu.user.entity;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/25
 * @description
 */
public class LabelStarModel extends UserStarAuth {
    /**
     * 热度值(最终热度)
     */
    private Integer lastHeat;

    public Integer getLastHeat() {
        return lastHeat;
    }

    public void setLastHeat(Integer lastHeat) {
        this.lastHeat = lastHeat;
    }
}
