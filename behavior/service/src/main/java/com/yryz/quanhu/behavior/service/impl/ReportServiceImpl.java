package com.yryz.quanhu.behavior.service.impl;

import com.github.pagehelper.PageHelper;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.behavior.dao.ReportDao;
import com.yryz.quanhu.behavior.dto.ReportDTO;
import com.yryz.quanhu.behavior.entity.Report;
import com.yryz.quanhu.behavior.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:sun
 * @version:2.0
 * @Description:举报
 * @Date:Created in 14:44 2018/1/22
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

    @Override
    public PageList<Report> queryReportForAdmin(ReportDTO reportDTO) {
        PageHelper.startPage(reportDTO.getCurrentPage(),reportDTO.getPageSize());
        List<Report> reports=reportDao.queryReportForAdmin(reportDTO);
        return null;//new PageModel<Report>().getPageList(reports);
    }

    @Override
    public int accretion(Report report) {
        return reportDao.accretion(report);
    }

    @Override
    public Report querySingleReport(Report report) {
        return reportDao.querySingleReport(report);
    }
}
