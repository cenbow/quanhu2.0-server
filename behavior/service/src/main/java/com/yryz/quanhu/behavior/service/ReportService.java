package com.yryz.quanhu.behavior.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.behavior.dto.ReportDTO;
import com.yryz.quanhu.behavior.entity.Report;

import java.util.List;

/**
 * @Author:sun
 * @version:2.0
 * @Description:举报
 * @Date:Created in 14:43 2018/1/22
 */
public interface ReportService {

    PageList<Report> queryReportForAdmin(ReportDTO reportDTO);

    int accretion(Report report);

    Report querySingleReport(Report report);
}