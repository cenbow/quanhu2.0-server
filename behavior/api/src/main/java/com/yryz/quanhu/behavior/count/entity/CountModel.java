package com.yryz.quanhu.behavior.count.entity;

import java.time.LocalDate;
import java.util.Date;

/**
 * @Author:liupan
 * @version:2.0
 * @Description:计数
 * @Date:Created in 11:58 2018/1/23
 */
public class CountModel {

    /**
     * 行为类型
     */
    private String type;

    /**
     * 业务表的主键
     */
    private String kid;

    /**
     * 页面，给活动统计pv专用
     */
    private String page;

    /**
     * 行为类型的计数
     */
    private Long count;

    /**
     * 日期。例：20180124
     */
    private Integer date;

    /**
     *
     */
    private Integer time;

    /**
     * 创建时间戳
     */
    private Long createTime;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public CountModel(String type, String kid, String page, Long count, Integer date, Integer time, Long createTime) {
        this.type = type;
        this.kid = kid;
        this.page = page;
        this.count = count;
        this.date = date;
        this.time = time;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "CountModel{" +
                "type='" + type + '\'' +
                ", kid='" + kid + '\'' +
                ", page='" + page + '\'' +
                ", count=" + count +
                ", date=" + date +
                ", time=" + time +
                ", createTime=" + createTime +
                '}';
    }
}
