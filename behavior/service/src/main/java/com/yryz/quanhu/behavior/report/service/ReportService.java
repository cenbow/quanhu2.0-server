package com.yryz.quanhu.behavior.report.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.behavior.report.dto.ReportDTO;
import com.yryz.quanhu.behavior.report.entity.Report;
import com.yryz.quanhu.behavior.report.vo.ReportVoForAdmin;

/**
 * @Author:sun
 * @version:2.0
 * @Description:举报
 * @Date:Created in 14:43 2018/1/22
 */
public interface ReportService {

    PageList<ReportVoForAdmin> queryReportForAdmin(ReportDTO reportDTO);

    int accretion(Report report);

    Report querySingleReport(Report report);
}
