package com.yryz.quanhu.support.activity.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.support.activity.dto.ActivityVoteDto;
import com.yryz.quanhu.support.activity.vo.ActivityVoteConfigVo;
import com.yryz.quanhu.support.activity.vo.ActivityVoteDetailVo;

public interface ActivityCandidateApi {

    /**
     * 参与投票活动
     * @param   activityInfoId
     * @return
     * */
    Response<ActivityVoteConfigVo> config(Long activityInfoId);

    /**
     * 获取参与者详情
     * @param   activityInfoId
     * @param   candidateId
     * @param   userId
     * @return
     * */
    Response<ActivityVoteDetailVo> detail(Long activityInfoId, Long candidateId, Long userId);

    /**
     * 参与者列表
     * @param   activityVoteDto
     * @return
     * */
    Response<PageList<ActivityVoteDetailVo>> list(ActivityVoteDto activityVoteDto);

    /**
     * 排行榜
     * @param   activityVoteDto
     * @return
     * */
    Response<PageList<ActivityVoteDetailVo>> rank(ActivityVoteDto activityVoteDto);

}
