package com.yryz.quanhu.support.activity.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.support.activity.dto.ActivityVoteDto;
import com.yryz.quanhu.support.activity.entity.ActivityUserPrizes;
import com.yryz.quanhu.support.activity.entity.ActivityVoteRecord;
import com.yryz.quanhu.support.activity.vo.ActivityInfoVo;
import com.yryz.quanhu.support.activity.vo.ActivityPrizesVo;
import com.yryz.quanhu.support.activity.vo.ActivityUserPrizesVo;
import com.yryz.quanhu.support.activity.vo.ActivityVoteInfoVo;

import java.util.Map;

public interface ActivityVoteApi {

    /**
     * 获取投票活动详情
     * @param   kid
     * @param   userId
     * @return
     * */
    Response<ActivityVoteInfoVo> detail(Long kid, Long userId);

    /**
     * 确认投票
     * @param   record
     * @return
     * */
    Response<Map<String, Object>> single(ActivityVoteRecord record);

    /**
     * 奖品列表
     * @param   activityVoteDto
     * @return
     * */
    Response<PageList<ActivityPrizesVo>> prizeslist(ActivityVoteDto activityVoteDto);

    /**
     * 领取奖品
     * @param   activityInfoId
     * @param   phone
     * @param   userId
     * @return
     * */
    Response<ActivityUserPrizesVo> getPrize(Long activityInfoId, String phone, Long userId);

    /**
     * 无奖品文案
     * @param   activityInfoId
     * @return
     * */
    Response<Map<String, String>> noPrize(Long activityInfoId);

    /**
     * 我的卡劵
     * @param   activityVoteDto
     * @return
     * */
    Response<PageList<ActivityUserPrizes>> userPrizesList(ActivityVoteDto activityVoteDto);

}
