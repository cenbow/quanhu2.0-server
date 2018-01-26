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
     * 获取参与者详情
     * @param   activityVoteDto
     * @return
     * */
    ActivityVoteDetailVo detail(ActivityVoteDto activityVoteDto);

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
