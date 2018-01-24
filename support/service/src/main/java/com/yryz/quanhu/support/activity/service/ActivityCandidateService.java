package com.yryz.quanhu.support.activity.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.support.activity.dto.ActivityVoteDto;
import com.yryz.quanhu.support.activity.vo.ActivityVoteConfigVo;
import com.yryz.quanhu.support.activity.vo.ActivityVoteDetailVo;

public interface ActivityCandidateService {

    /**
     * 增加参与者
     * @param activityVoteDto
     * */
    void join(ActivityVoteDto activityVoteDto);

    /**
     * 活动配置信息
     * @param   activityInfoId
     * @return
     * */
    ActivityVoteConfigVo config(Long activityInfoId);

    /**
     * 获取参与者详情
     * @param   activityInfoId
     * @param   candidateId
     * @param   userId
     * @return
     * */
    ActivityVoteDetailVo detail(Long activityInfoId, Long candidateId, Long userId);

    /**
     * 获取参与者列表
     * @param   activityVoteDto
     * @return
     * */
    PageList<ActivityVoteDetailVo> list(ActivityVoteDto activityVoteDto);

    /**
     * 排行榜
     * @param   activityVoteDto
     * @return
     * */
    PageList<ActivityVoteDetailVo> rank(ActivityVoteDto activityVoteDto);

}
