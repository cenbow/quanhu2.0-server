package com.yryz.quanhu.support.activity.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.support.activity.api.ActivityCandidateApi;
import com.yryz.quanhu.support.activity.dto.ActivityVoteDto;
import com.yryz.quanhu.support.activity.service.ActivityCandidateService;
import com.yryz.quanhu.support.activity.vo.ActivityVoteConfigVo;
import com.yryz.quanhu.support.activity.vo.ActivityVoteDetailVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass=ActivityCandidateApi.class)
public class ActivityCandidateProvider implements ActivityCandidateApi {

    private static final Logger logger = LoggerFactory.getLogger(ActivityCandidateProvider.class);

    @Autowired
    private ActivityCandidateService activityCandidateService;

    /**
     * 参与投票活动
     * @param   activityInfoId
     * @return
     * */
    public Response<ActivityVoteConfigVo> config(Long activityInfoId) {
        try {
            return ResponseUtils.returnObjectSuccess(activityCandidateService.config(activityInfoId));
        } catch (Exception e) {
            logger.error("参与投票活动 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

    /**
     * 获取参与者详情
     * @param   activityInfoId
     * @param   candidateId
     * @param   userId
     * @return
     * */
    public Response<ActivityVoteDetailVo> detail(Long activityInfoId, Long candidateId, Long userId) {
        try {
            return ResponseUtils.returnObjectSuccess(activityCandidateService.detail(activityInfoId, candidateId, userId));
        } catch (Exception e) {
            logger.error("获取参与者详情 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

    /**
     * 参与者列表
     * @param   activityVoteDto
     * @return
     * */
    public Response<PageList<ActivityVoteDetailVo>> list(ActivityVoteDto activityVoteDto) {
        try {
            return ResponseUtils.returnObjectSuccess(activityCandidateService.list(activityVoteDto));
        } catch (Exception e) {
            logger.error("参与投票活动 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

    /**
     * 排行榜
     * @param   activityVoteDto
     * @return
     * */
    public Response<PageList<ActivityVoteDetailVo>> rank(ActivityVoteDto activityVoteDto) {
        try {
            return ResponseUtils.returnObjectSuccess(activityCandidateService.rank(activityVoteDto));
        } catch (Exception e) {
            logger.error("排行榜 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

}
