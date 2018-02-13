package com.yryz.quanhu.dymaic.vo;

import com.yryz.common.constant.CommonConstants;
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
     * 置顶状态,10未置顶，11置顶(默认不置顶)
     */
    private Byte topFlag = CommonConstants.TOP_NO;

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

    public Byte getTopFlag() {
        return topFlag;
    }

    public void setTopFlag(Byte topFlag) {
        this.topFlag = topFlag;
    }
}
