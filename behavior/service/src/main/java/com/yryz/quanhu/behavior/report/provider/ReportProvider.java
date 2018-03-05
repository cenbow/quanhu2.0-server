package com.yryz.quanhu.behavior.report.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.behavior.report.dto.ReportDTO;
import com.yryz.quanhu.behavior.report.entity.Report;
import com.yryz.quanhu.behavior.report.service.ReportService;
import com.yryz.quanhu.behavior.report.service.ReportApi;
import com.yryz.quanhu.behavior.report.vo.ReportVo;
import com.yryz.quanhu.behavior.report.vo.ReportVoForAdmin;
import com.yryz.quanhu.support.config.api.BasicConfigApi;
import com.yryz.quanhu.support.id.api.IdAPI;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


/**
 * @Author:sun
 * @version:2.0
 * @Description:举报
 * @Date:Created in 15:00 2018/1/22
 */
@Service(interfaceClass = ReportApi.class)
public class ReportProvider implements ReportApi {

    private static final Logger logger = LoggerFactory.getLogger(ReportProvider.class);

    @Autowired
    private ReportService reportService;


    @Override
    public Response<Map<String, Integer>> submit(ReportDTO dto) {
        try{
            logger.info("submit.start={}", JSON.toJSON(dto));

            int out = reportService.submit(dto);
            Map<String,Integer> outMap = Maps.newHashMap();
            outMap.put("result",out);

            logger.info("submit.finish={},out={}", JSON.toJSON(dto),JSON.toJSON(outMap));

            return ResponseUtils.returnObjectSuccess(outMap);
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
}
