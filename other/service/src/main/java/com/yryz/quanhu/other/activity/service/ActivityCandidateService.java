package com.yryz.quanhu.other.activity.service;


import com.yryz.common.response.PageList;
import com.yryz.quanhu.other.activity.dto.ActivityVoteDto;
import com.yryz.quanhu.other.activity.vo.ActivityVoteDetailVo;

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

    /**
     * 设置参与者列表
     * @param   activityInfoId
     * */
    void setList(Long activityInfoId);

    /**
     * 设置参与者排名
     * @param   activityInfoId
     * */
    void setRank(Long activityInfoId);

}
