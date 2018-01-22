package com.yryz.quanhu.behavior.dao;

import com.yryz.quanhu.behavior.dto.ReportDTO;
import com.yryz.quanhu.behavior.entity.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author:sun
 * @version:2.0
 * @Description:举报
 * @Date:Created in 13:37 2018/1/22
 */
@Mapper
public interface ReportDao {

    List<Report> queryReportForAdmin(ReportDTO reportDTO);

    int accretion(Report report);

    Report querySingleReport(Report report);
}

