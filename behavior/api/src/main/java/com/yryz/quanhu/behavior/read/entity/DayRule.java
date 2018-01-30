package com.yryz.quanhu.behavior.read.entity;

/**
 * Created by admin on 2017/12/6.
 */
public class DayRule {

    /**
     * 文章发布后的时间点
     */
    private int hours;

    /**
     * 高峰期取值区间起始数
     */
    private int hotStartRange;

    /**
     * 高峰期取值区间结束数
     */
    private int hotEndRange;

    /**
     * 非高峰期取值区间起始数
     */
    private int normalStartRange;

    /**
     * 非高峰期取值区间结束数
     */
    private int normalEndRange;

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getHotStartRange() {
        return hotStartRange;
    }

    public void setHotStartRange(int hotStartRange) {
        this.hotStartRange = hotStartRange;
    }

    public int getHotEndRange() {
        return hotEndRange;
    }

    public void setHotEndRange(int hotEndRange) {
        this.hotEndRange = hotEndRange;
    }

    public int getNormalStartRange() {
        return normalStartRange;
    }

    public void setNormalStartRange(int normalStartRange) {
        this.normalStartRange = normalStartRange;
    }

    public int getNormalEndRange() {
        return normalEndRange;
    }

    public void setNormalEndRange(int normalEndRange) {
        this.normalEndRange = normalEndRange;
    }

}
