package com.yryz.quanhu.other.activity.api;

import com.yryz.common.response.Response;
import com.yryz.quanhu.other.activity.entity.ActivityEnrolConfig;
import com.yryz.quanhu.other.activity.entity.ActivityRecord;
import com.yryz.quanhu.other.activity.vo.ActivitySignUpHomeAppVo;

import java.util.Map;

public interface ActivitySignUpApi {
    /**
     * （前台）获取报名活动首页信息
     * @param activityInfo
     * @param custId
     * @return
     */
    Response<ActivitySignUpHomeAppVo> getActivitySignUpHome(Long activityInfoId, String custId);

    /**
     * 根据活动id获取配置信息
     * @param activityInfoId
     * @param custId
     * @return
     */
    Response<ActivityEnrolConfig> getActivitySignUpFrom(Long activityInfoId, String custId);

    /**
     * （前台）提交报名信息
     * @param activityRecord
     * @param custId
     * @param activityEnrolConfig
     * @return
     */
    Response<ActivityRecord> activitySignUpSubmit(ActivityRecord activityRecord, String custId);

    /**
     * 根据活动id查询当前用户报名状态
     * @param activityInfoId
     * @param custId
     * @return
     */
    Response<Map<String,Integer>> getActivitySignUpStatus(Long activityInfoId, String custId);

}
