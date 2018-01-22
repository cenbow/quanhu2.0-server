package com.yryz.quanhu.support.activity.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.support.activity.entity.ActivityInfo;
import com.yryz.quanhu.support.activity.vo.ActivityInfoAppListVo;
import com.yryz.quanhu.support.activity.vo.ActivityInfoVo;

public interface ActivityInfoService {

    PageList<ActivityInfoAppListVo> getActivityInfoMyAppListVoPageList(Integer pageNum, Integer pageSize, Long custId);

    Integer selectMylistCount(Long custId);

    PageList<ActivityInfoAppListVo> getActivityInfoAppListVoPageList(Integer pageNum, Integer pageSize, Integer type);

    ActivityInfoVo getActivityInfoVo(Long kid, Integer type);

    void updateJoinCount(ActivityInfo activityInfo);
}
