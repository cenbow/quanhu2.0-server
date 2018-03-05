package com.yryz.quanhu.behavior.report.service;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.report.dto.ReportDTO;
import com.yryz.quanhu.behavior.report.vo.ReportVo;
import com.yryz.quanhu.behavior.report.vo.ReportVoForAdmin;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/3/2 14:01
 * Created by huangxy
 *
 * 举报管理端Api
 */
public interface ReportForAdminApi {

    /**
     * 查询举报内容列表
     * @param dto
     * @return
     */
    public Response<PageList<ReportDTO>> selectBy(ReportDTO dto);

    /**
     * 违规描述列表
     * @return
     */
    Response<List<ReportVo>> getReportType(ReportDTO dto);


    /**
     * 获取单一举报信息，和处理结果
     * @param dto
     * @return
     */
    public Response<ReportDTO> getReport(ReportDTO dto);

    /**
     * 解决
     * @param dto
     * @return
     */
    public Response<Boolean> solution(ReportDTO dto);


}
