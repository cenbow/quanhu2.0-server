package com.yryz.quanhu.other.activity.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.other.activity.dto.AdminActivityCountDto;
import com.yryz.quanhu.other.activity.vo.AdminActivityCountVo;

public interface AdminActivityCountApi {

    /**
     * 获取活动统计数量
     * @param   adminActivityCountDto
     * @return
     * */
    Response<PageList<AdminActivityCountVo>> activityCount(AdminActivityCountDto adminActivityCountDto);

    /**
     * 获取活动统计数量总和
     * @param   adminActivityCountDto
     * @return
     * */
    Response<AdminActivityCountVo> activityTotalCount(AdminActivityCountDto adminActivityCountDto);

}
