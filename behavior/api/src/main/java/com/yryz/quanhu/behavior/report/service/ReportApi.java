package com.yryz.quanhu.behavior.report.service;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.report.dto.ReportDTO;
import com.yryz.quanhu.behavior.report.entity.Report;
import com.yryz.quanhu.behavior.report.vo.ReportVo;

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
    Response<Map<String,Integer>> accretion(Report report);

    /**
     * desc:举报分页列表{管理后台}
     * @param:reportDTO
     * @return:PageList
     **/
    Response<PageList<Report>> queryReportForAdmin(ReportDTO reportDTO);

    /**
     * desc:详情{管理后台}
     * @param:report
     * @return:Report
     **/
    Response<Report> querySingleReport(Report report);

    /**
     * 违规描述列表
     * @return
     */
    Response<List<ReportVo>> queryInformDesc();
}
