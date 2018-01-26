package com.yryz.quanhu.dymaic.vo;

import com.yryz.quanhu.user.vo.UserSimpleVO;

import java.io.Serializable;
import java.util.Map;

/**
 * @author xiepeng
 * @version 1.0
 * @data 2018/1/19 0019 29
 */
public class DymaicVo extends Dymaic implements Serializable {

    /**
     * 用户信息
     */
    private UserSimpleVO user;

    /**
     * 统计信息（评论、转发、点赞）
     */
    private Map<String, Long> statistics;

    public UserSimpleVO getUser() {
        return user;
    }

    public void setUser(UserSimpleVO user) {
        this.user = user;
    }

    public Map<String, Long> getStatistics() {
        return statistics;
    }

    public void setStatistics(Map<String, Long> statistics) {
        this.statistics = statistics;
    }
}
