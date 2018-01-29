package com.yryz.quanhu.other.activity.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.other.activity.api.ActivitySignUpApi;
import com.yryz.quanhu.other.activity.entity.ActivityEnrolConfig;
import com.yryz.quanhu.other.activity.entity.ActivityRecord;
import com.yryz.quanhu.other.activity.service.ActivitySignUpService;
import com.yryz.quanhu.other.activity.vo.ActivitySignUpHomeAppVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Service(interfaceClass = ActivitySignUpApi.class)
public class ActivitySignUpProvider implements ActivitySignUpApi {
    private Logger logger = LoggerFactory.getLogger(ActivitySignUpProvider.class);

    @Autowired
    ActivitySignUpService activitySignUpService;

    /**
     * （前台）获取报名活动首页信息
     * @param activityInfo
     * @param custId
     * @return
     */
    @Override
    public Response<ActivitySignUpHomeAppVo> getActivitySignUpHome(Long activityInfoId, String custId) {
        ActivitySignUpHomeAppVo activitySignUpHomeAppVo = null;
        try {
            activitySignUpHomeAppVo = activitySignUpService.getActivitySignUpHome(activityInfoId, custId);
        } catch (Exception e) {
            logger.error("查询获取报名活动首页信息失败");
            return ResponseUtils.returnException(e);
        }
        return ResponseUtils.returnObjectSuccess(activitySignUpHomeAppVo);
    }


    /**
     * 根据活动id获取配置信息
     * @param activityInfoId
     * @param custId
     * @return
     */
    @Override
    public Response<ActivityEnrolConfig> getActivitySignUpFrom(Long activityInfoId, String custId) {
        ActivityEnrolConfig activityEnrolConfig = null;
        try {
            activityEnrolConfig = activitySignUpService.getActivitySignUpFrom(activityInfoId, custId);
        } catch (Exception e) {
            logger.error("查询活动id:"+activityInfoId+"配置信息失败");
            return ResponseUtils.returnException(e);
        }
        return ResponseUtils.returnObjectSuccess(activityEnrolConfig);
    }

    /**
     * （前台）提交报名信息
     * @param activityRecord
     * @param custId
     * @param activityEnrolConfig
     * @return
     */
    @Override
    public Response<ActivityRecord> activitySignUpSubmit(ActivityRecord activityRecord, String custId) {
        try {
            activityRecord = activitySignUpService.activitySignUpSubmit(activityRecord, custId);
        } catch (Exception e) {
            logger.error("提交报名信息失败,用户id:"+custId+",activityRecord:"+activityRecord);
            return ResponseUtils.returnException(e);
        }
        return ResponseUtils.returnObjectSuccess(activityRecord);
    }

    /**
     * 根据活动id查询当前用户报名状态
     * @param activityInfoId
     * @param custId
     * @return
     */
    @Override
    public Response<Map<String, Integer>> getActivitySignUpStatus(Long activityInfoId, String custId) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            map.put("activitySignUpStatus",activitySignUpService.getEnrolStatusByCustId(custId,activityInfoId,null));
        } catch (Exception e) {
            logger.error("查询报名状态失败,活动Id:"+activityInfoId+",用户id:"+custId);
            return ResponseUtils.returnException(e);
        }
        return ResponseUtils.returnObjectSuccess(map);
    }
}
