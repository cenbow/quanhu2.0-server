package com.yryz.quanhu.support.activity.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.support.activity.dto.ActivityVoteDto;
import com.yryz.quanhu.support.activity.entity.ActivityUserPrizes;
import com.yryz.quanhu.support.activity.entity.ActivityVoteConfig;
import com.yryz.quanhu.support.activity.entity.ActivityVoteRecord;
import com.yryz.quanhu.support.activity.vo.ActivityPrizesVo;
import com.yryz.quanhu.support.activity.vo.ActivityUserPrizesVo;
import com.yryz.quanhu.support.activity.vo.ActivityVoteInfoVo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     *  确认投票
     *  @param  record
     *  @param  activityVoteInfoVo
     *  @return
     * */
    int voteRecord(ActivityVoteRecord record, ActivityVoteInfoVo activityVoteInfoVo);

    /**
     * 查看用户是否有可用的投票卷
     * @param   createUserId
     * @return
     * */
    int selectUserRoll(Long createUserId);

    /**
     *  奖品列表
     *  @param  activityVoteDto
     *  @return
     * */
    PageList<ActivityPrizesVo> prizesList(ActivityVoteDto activityVoteDto);

    /**
     * 领取奖品
     * @param   activityInfoId
     * @param   phone
     * @param   userId
     * @return
     * */
    ActivityUserPrizesVo getPrizes(Long activityInfoId, String phone, Long userId);

    /**
     * 我的卡劵
     * @param   activityVoteDto
     * @return
     * */
    PageList<ActivityUserPrizes> userPrizesList(ActivityVoteDto activityVoteDto);

}
