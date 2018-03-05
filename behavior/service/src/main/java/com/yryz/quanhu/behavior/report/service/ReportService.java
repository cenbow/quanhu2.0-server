package com.yryz.quanhu.behavior.report.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.behavior.report.dto.ReportDTO;
import com.yryz.quanhu.behavior.report.entity.Report;
import com.yryz.quanhu.behavior.report.vo.ReportVo;
import com.yryz.quanhu.behavior.report.vo.ReportVoForAdmin;

import java.util.List;

/**
 * @Author:sun
 * @version:2.0
 * @Description:举报
 * @Date:Created in 14:43 2018/1/22
 */
public interface ReportService {

    /**
     * 举报提交
     * @param dto
     * @return
     */
    int submit(ReportDTO dto);

    /**
     * 解决处理
     * @param dto
     * @return
     */
    Boolean solution(ReportDTO dto);

    /**
     * 获取举报类型
     * @param dto
     * @return
     */
    List<ReportVo> getReportType(ReportDTO dto);

    /**
     * 查询举报列表
     * @param dto
     * @return
     */
    PageList<ReportDTO> selectBy(ReportDTO dto);

    /**
     * 查询举报明细
     * @param dto
     * @return
     */
    ReportDTO getReport(ReportDTO dto);

}
