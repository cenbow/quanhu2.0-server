package com.yryz.quanhu.user.vo;

import java.util.Date;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/2/1
 * @description
 */
public class EventAccountVO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 总积分
     */
    private Long score;

    /**
     * 成长值
     */
    private Long grow = 0L;

    /**
     * 成长级别
     */
    private String growLevel;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getGrow() {
        return grow;
    }

    public void setGrow(Long grow) {
        this.grow = grow;
    }

    public String getGrowLevel() {
        return growLevel;
    }

    public void setGrowLevel(String growLevel) {
        this.growLevel = growLevel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
