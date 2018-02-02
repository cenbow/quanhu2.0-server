package com.yryz.quanhu.behavior.count.vo;

public class CountStatisticsVo {

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
     * 日期
     * */
    private String date;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
