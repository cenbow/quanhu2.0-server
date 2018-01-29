package com.yryz.quanhu.other.activity.api;


import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.other.activity.vo.ActivityInfoAppListVo;
import com.yryz.quanhu.other.activity.vo.ActivityInfoVo;

public interface ActivityInfoApi {
    /**
     * (前台)我的活动
     * @param pageNum
     * @param pageSize
     * @param custId
     * @return
     */
    Response<PageList<ActivityInfoAppListVo>> myList(Integer pageNum, Integer pageSize, Long custId);

    /**
     * 获取我的活动数
     * @param custId
     * @return
     */
    Response<Integer> myListCount(Long custId);

    /**
     * 获取activityInfo列表
     * @param pageNum
     * @param pageSize
     * @param type
     * @return
     */
    Response<PageList<ActivityInfoAppListVo>> appList(Integer pageNum, Integer pageSize, Integer type);

    /**
     * 获取activityInfo明细
     * @param kid
     * @param type
     * @return
     */
    Response<ActivityInfoVo> get(Long kid, Integer type);

    /**
     * 获取activityInfo固定列表
     * @param type
     * @return
     */
    Response<PageList<ActivityInfoAppListVo>> fixedList(Integer type);
}
