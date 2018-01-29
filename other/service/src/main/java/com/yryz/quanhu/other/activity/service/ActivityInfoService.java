package com.yryz.quanhu.other.activity.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.other.activity.vo.ActivityInfoAppListVo;
import com.yryz.quanhu.other.activity.vo.ActivityInfoVo;

public interface ActivityInfoService {

    PageList<ActivityInfoAppListVo> getActivityInfoMyAppListVoPageList(Integer pageNum, Integer pageSize, Long custId);

    Integer selectMylistCount(Long custId);

    PageList<ActivityInfoAppListVo> getActivityInfoAppListVoPageList(Integer pageNum, Integer pageSize, Integer type);

    ActivityInfoVo getActivityInfoVo(Long kid, Integer type);

    void updateJoinCount(Long kid, Integer userNum);
}
