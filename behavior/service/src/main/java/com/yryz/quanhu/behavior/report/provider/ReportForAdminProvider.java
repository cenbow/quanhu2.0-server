package com.yryz.quanhu.behavior.report.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.report.dto.ReportDTO;
import com.yryz.quanhu.behavior.report.service.ReportApi;
import com.yryz.quanhu.behavior.report.service.ReportForAdminApi;
import com.yryz.quanhu.behavior.report.service.ReportService;
import com.yryz.quanhu.behavior.report.vo.ReportVo;
import com.yryz.quanhu.behavior.report.vo.ReportVoForAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/3/2 14:33
 * Created by huangxy
 *
 * 举报管理端接口
 *
 */
@Service(interfaceClass = ReportForAdminApi.class)
public class ReportForAdminProvider implements ReportForAdminApi{

    private static final Logger logger = LoggerFactory.getLogger(ReportForAdminProvider.class);

    @Autowired
    private ReportService reportService;


    @Override
    public Response<PageList<ReportDTO>> selectBy(ReportDTO dto) {
        try{

            logger.info("selectBy.start={}", JSON.toJSON(dto));
            PageList<ReportDTO> out = reportService.selectBy(dto);
            logger.info("selectBy.finish={},out={}", JSON.toJSON(dto),JSON.toJSON(out));

            return ResponseUtils.returnObjectSuccess(out);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<List<ReportVo>> getReportType(ReportDTO dto) {
        try{

            logger.info("getReportType.start={}", JSON.toJSON(dto));
            List<ReportVo> out = reportService.getReportType(dto);
            logger.info("getReportType.finish={},out={}", JSON.toJSON(dto),JSON.toJSON(out));

            return ResponseUtils.returnObjectSuccess(out);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<ReportDTO> getReport(ReportDTO dto) {
        try{

            logger.info("getReport.start={}", JSON.toJSON(dto));
            ReportDTO out = reportService.getReport(dto);
            logger.info("getReport.finish={},out={}", JSON.toJSON(dto),JSON.toJSON(out));

            return ResponseUtils.returnObjectSuccess(out);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }
    }


    @Override
    public Response<Boolean> solution(ReportDTO dto) {
        try{

            logger.info("solution.start={}", JSON.toJSON(dto));
            Boolean out = reportService.solution(dto);
            logger.info("solution.finish={},out={}", JSON.toJSON(dto),JSON.toJSON(out));

            return ResponseUtils.returnObjectSuccess(out);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }
    }
}
