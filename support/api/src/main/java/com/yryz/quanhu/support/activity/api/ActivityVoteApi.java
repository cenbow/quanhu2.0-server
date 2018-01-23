package com.yryz.quanhu.support.activity.api;

import com.yryz.common.response.Response;
import com.yryz.quanhu.support.activity.vo.ActivityInfoVo;
import com.yryz.quanhu.support.activity.vo.ActivityVoteInfoVo;

public interface ActivityVoteApi {

    /**
     * 获取投票活动详情
     * @param   kid
     * @param   userId
     * @return
     * */
    Response<ActivityVoteInfoVo> detail(Long kid, Long userId);

}
