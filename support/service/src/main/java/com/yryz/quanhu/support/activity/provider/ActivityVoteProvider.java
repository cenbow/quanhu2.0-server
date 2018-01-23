package com.yryz.quanhu.support.activity.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.support.activity.api.ActivityVoteApi;
import com.yryz.quanhu.support.activity.service.ActivityVoteService;
import com.yryz.quanhu.support.activity.vo.ActivityInfoVo;
import com.yryz.quanhu.support.activity.vo.ActivityVoteInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
            return ResponseUtils.returnObjectSuccess(activityVoteService.detail(kid, userId));
        } catch (Exception e) {
            logger.error("获取活动详情 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

}
