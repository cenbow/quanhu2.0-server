package com.yryz.quanhu.support.activity.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.support.activity.api.ActivityVoteApi;
import com.yryz.quanhu.support.activity.dto.ActivityVoteDto;
import com.yryz.quanhu.support.activity.entity.ActivityVoteRecord;
import com.yryz.quanhu.support.activity.service.ActivityVoteService;
import com.yryz.quanhu.support.activity.vo.ActivityInfoVo;
import com.yryz.quanhu.support.activity.vo.ActivityPrizesVo;
import com.yryz.quanhu.support.activity.vo.ActivityVoteInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Service(interfaceClass=ActivityVoteApi.class)
public class ActivityVoteProvider implements ActivityVoteApi {

    private static final Logger logger = LoggerFactory.getLogger(ActivityVoteProvider.class);

    @Autowired
    private ActivityVoteService activityVoteService;

    /**
     * 获取投票活动详情
     * @param   kid
     * @param   userId
     * @return
     * */
    public Response<ActivityVoteInfoVo> detail(Long kid, Long userId) {
        try {
            Assert.notNull(kid, "activityInfoId不能为空");
            return ResponseUtils.returnObjectSuccess(activityVoteService.detail(kid, userId));
        } catch (Exception e) {
            logger.error("获取活动详情 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

    /**
     * 确认投票
     * @param   record
     * @return
     * */
    public Response<Map<String, Object>> single(ActivityVoteRecord record) {
        try {
            Assert.notNull(record.getActivityInfoId(), "activityInfoId不能为空");
            Assert.notNull(record.getCandidateId(), "candidateId不能为空");
            Assert.notNull(record.getOtherFlag(), "otherFlag不能为空");
            Assert.notNull(record.getVoteNo(), "voteNo不能为空");
            int voteCount = activityVoteService.voteRecord(record);
            Map<String, Object> map = new HashMap<>();
            map.put("haveFreeVote", ++voteCount);
            map.put("otherFlag", record.getOtherFlag());
            map.put("userRollFlag", activityVoteService.selectUserRoll(record.getCreateUserId()));
            return ResponseUtils.returnObjectSuccess(map);
        } catch (Exception e) {
            logger.error("确认投票 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

    /**
     * 奖品列表
     * @param   activityVoteDto
     * @return
     * */
    public Response<PageList<ActivityPrizesVo>> prizeslist(ActivityVoteDto activityVoteDto) {
        try {
            Assert.notNull(activityVoteDto.getActivityInfoId(), "activityInfoId不能为空");
            return ResponseUtils.returnObjectSuccess(activityVoteService.prizesList(activityVoteDto));
        } catch (Exception e) {
            logger.error("奖品列表 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

}
