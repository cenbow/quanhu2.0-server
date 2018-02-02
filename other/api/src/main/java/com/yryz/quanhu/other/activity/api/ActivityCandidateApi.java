package com.yryz.quanhu.other.activity.api;


import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.other.activity.dto.ActivityVoteDto;
import com.yryz.quanhu.other.activity.vo.ActivityVoteConfigVo;
import com.yryz.quanhu.other.activity.vo.ActivityVoteDetailVo;

import java.util.Map;

public interface ActivityCandidateApi {

    /**
     * 增加参与者
     * @param activityVoteDto
     * @return
     * */
    Response<Map<String, Object>> join(ActivityVoteDto activityVoteDto);

    /**
     * 参与投票活动
     * @param   activityInfoId
     * @return
     * */
    Response<ActivityVoteConfigVo> config(Long activityInfoId);

    /**
     * 获取参与者详情
     * @param   activityVoteDto
     * @return
     * */
    Response<ActivityVoteDetailVo> detail(ActivityVoteDto activityVoteDto);

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
