package com.yryz.quanhu.support.activity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.support.activity.dao.ActivityInfoDao;
import com.yryz.quanhu.support.activity.entity.ActivityInfo;
import com.yryz.quanhu.support.activity.service.ActivityInfoService;
import com.yryz.quanhu.support.activity.vo.ActivityInfoAppListVo;
import com.yryz.quanhu.support.activity.vo.ActivityInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityInfoServiceImpl implements ActivityInfoService {

    private Logger logger = LoggerFactory.getLogger(ActivityInfoServiceImpl.class);

    @Autowired
    ActivityInfoDao activityInfoDao;

    @Override
    public PageList<ActivityInfoAppListVo> getActivityInfoMyAppListVoPageList(Integer pageNum, Integer pageSize, Long custId) {
        Page<ActivityInfoAppListVo> page = PageHelper.startPage(pageNum, pageSize);
        PageList<ActivityInfoAppListVo> pageList = new PageList<ActivityInfoAppListVo>();
        pageList.setCurrentPage(pageNum);
        pageList.setPageSize(pageSize);
        pageList.setEntities(activityInfoDao.selectMyList(custId));
        return pageList;
    }

    @Override
    public Integer selectMylistCount(Long custId) {
        return activityInfoDao.selectMylistCount(custId);
    }

    @Override
    public PageList<ActivityInfoAppListVo> getActivityInfoAppListVoPageList(Integer pageNum, Integer pageSize, Integer type) {
        Page<ActivityInfoAppListVo> page = PageHelper.startPage(pageNum,pageSize);
        PageList<ActivityInfoAppListVo> pageList = new PageList<ActivityInfoAppListVo>();
        pageList.setCurrentPage(pageNum);
        pageList.setPageSize(pageSize);
        pageList.setEntities(activityInfoDao.selectAppList(type));
        return pageList;
    }

    @Override
    public ActivityInfoVo getActivityInfoVo(Long kid, Integer type) {
        ActivityInfoVo activityInfoVo = activityInfoDao.selectByKid(kid);
        if(type!=null){
            ActivityInfoVo activityInfoVo1 = new ActivityInfoVo();
            switch (type){
                case 1:
                    activityInfoVo1.setTitle(activityInfoVo.getTitle());
                    break;
                case 2:
                    activityInfoVo1.setActivityChannelCode(activityInfoVo.getActivityChannelCode());
                    break;
                default:
                    activityInfoVo1 = activityInfoVo;
                    return activityInfoVo1;
            }
        }
        return activityInfoVo;
    }

    @Override
    public void updateJoinCount(ActivityInfo activityInfo) {
//        activityInfoDao.updateJoinCount(activityInfo);
    }
}
