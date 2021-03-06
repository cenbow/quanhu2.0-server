package com.yryz.quanhu.other.activity.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.other.activity.api.ActivityInfoApi;
import com.yryz.quanhu.other.activity.service.ActivityInfoService;
import com.yryz.quanhu.other.activity.vo.ActivityInfoAppListVo;
import com.yryz.quanhu.other.activity.vo.ActivityInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass = ActivityInfoApi.class)
public class ActivityInfoProvider implements ActivityInfoApi {
    private Logger logger = LoggerFactory.getLogger(ActivityInfoProvider.class);

    @Autowired
    ActivityInfoService activityInfoService;


    /**
     * (前台)我的活动
     * @param pageNum
     * @param pageSize
     * @param custId
     * @return
     */
    @Override
    public Response<PageList<ActivityInfoAppListVo>> myList(Integer pageNum, Integer pageSize, Long custId) {
        PageList<ActivityInfoAppListVo> pageList = null;
        try {
            pageList = activityInfoService.getActivityInfoMyAppListVoPageList(pageNum, pageSize,custId);
        } catch (Exception e) {
            logger.error("查询用户的活动列表失败",e);
            return ResponseUtils.returnException(e);
        }
        return ResponseUtils.returnObjectSuccess(pageList);
    }

    /**
     * (前台)我的活动数
     * @param custId
     * @return
     */
    @Override
    public Response<Integer> myListCount(Long custId) {
        try {
            return ResponseUtils.returnObjectSuccess(activityInfoService.selectMylistCount(custId));
        } catch (Exception e) {
            logger.error("查询用户活动数失败",e);
            return ResponseUtils.returnException(e);
        }
    }

    /**
     * 活动列表
     * @param pageNum
     * @param pageSize
     * @param type
     * @return
     */
    @Override
    public Response<PageList<ActivityInfoAppListVo>> appList(Integer pageNum, Integer pageSize, Integer type) {
        PageList<ActivityInfoAppListVo> pageList = null;
        try {
            pageList = activityInfoService.getActivityInfoAppListVoPageList(pageNum, pageSize, type);
        } catch (Exception e) {
            logger.error("查询活动列表失败",e);
            return ResponseUtils.returnException(e);
        }
        return ResponseUtils.returnObjectSuccess(pageList);
    }

    /**
     * 获取活动基本信息
     * @param kid
     * @param type
     * @return
     */
    @Override
    public Response<ActivityInfoVo> get(Long kid, Integer type) {
        ActivityInfoVo activityInfoVo1 = null;
        try {
            activityInfoVo1 = activityInfoService.getActivityInfoVo(kid, type);
        } catch (Exception e) {
            logger.error("查询活动基本信息失败",e);
            return ResponseUtils.returnException(e);
        }
        return ResponseUtils.returnObjectSuccess(activityInfoVo1);
    }

    /**
     * 固定活动列表
     * @param type
     * @return
     */
    @Override
    public Response<PageList<ActivityInfoAppListVo>> fixedList(Integer type) {
        PageList<ActivityInfoAppListVo> pageList = null;
        try {
            pageList = activityInfoService.getActivityInfoAppListVoPageList(1, type==2?2:5, type);
        } catch (Exception e) {
            logger.error("查询活动列表失败",e);
            return ResponseUtils.returnException(e);
        }
        for(ActivityInfoAppListVo activityInfoAppListVo:pageList.getEntities()){
            activityInfoAppListVo.setListType(type);
        }
        return ResponseUtils.returnObjectSuccess(pageList);
    }

}
