package com.yryz.quanhu.behavior.report.service;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.report.dto.ReportDTO;
import com.yryz.quanhu.behavior.report.entity.Report;
import com.yryz.quanhu.behavior.report.vo.ReportVo;
import com.yryz.quanhu.behavior.report.vo.ReportVoForAdmin;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author:sun
 * @version:2.0
 * @Description:举报
 * @Date:Created in 16:15 2018/1/20
 */
public interface ReportApi {
    /**
     * desc:添加举报{前端}
     * @param:report
     * @return:map
     **/
    Response<Map<String,Integer>> submit(ReportDTO dto);
    /**
     * 违规描述列表
     * @return
     */
    Response<List<ReportVo>> getReportType(ReportDTO dto);
}
