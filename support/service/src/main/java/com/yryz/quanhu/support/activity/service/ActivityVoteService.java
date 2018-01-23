package com.yryz.quanhu.support.activity.service;

import com.yryz.quanhu.support.activity.entity.ActivityVoteConfig;
import com.yryz.quanhu.support.activity.vo.ActivityVoteInfoVo;

public interface ActivityVoteService {

    /**
     *  投票活动主信息
     *  @param kid
     *  @param userId
     *  @return
     * */
    ActivityVoteInfoVo detail(Long kid, Long userId);

    /**
     * 设置投票详细信息
     * @param   joinCount
     * @param   activityVoteConfig
     * */
    void setVoteConfig(Integer joinCount, ActivityVoteConfig activityVoteConfig);

    /**
     * 获取投票活动详情
     * @param kid
     * @return
     * */
    ActivityVoteInfoVo getVoteInfo(Long kid);

}
