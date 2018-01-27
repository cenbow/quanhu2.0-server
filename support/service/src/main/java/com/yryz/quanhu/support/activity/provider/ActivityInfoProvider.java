package com.yryz.quanhu.support.activity.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.support.activity.api.ActivityInfoApi;
import com.yryz.quanhu.support.activity.dao.ActivityInfoDao;
import com.yryz.quanhu.support.activity.service.ActivityInfoService;
import com.yryz.quanhu.support.activity.vo.ActivityInfoAppListVo;
import com.yryz.quanhu.support.activity.vo.ActivityInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
            logger.error("查询用户:"+custId+"活动列表失败");
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
            logger.error("查询用户:"+custId+"活动数失败");
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
            logger.error("查询活动列表失败");
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
            logger.error("查询活动id:"+kid+"基本信息失败");
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
            pageList = activityInfoService.getActivityInfoAppListVoPageList(1, type==2?5:2, type);
        } catch (Exception e) {
            logger.error("查询活动列表失败");
            return ResponseUtils.returnException(e);
        }
        return ResponseUtils.returnObjectSuccess(pageList);
    }

}
