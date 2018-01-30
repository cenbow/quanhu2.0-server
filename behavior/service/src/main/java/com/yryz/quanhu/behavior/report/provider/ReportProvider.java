package com.yryz.quanhu.behavior.report.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.report.contants.ReportConstatns;
import com.yryz.quanhu.behavior.report.dto.ReportDTO;
import com.yryz.quanhu.behavior.report.entity.Report;
import com.yryz.quanhu.behavior.report.service.ReportService;
import com.yryz.quanhu.behavior.report.service.ReportApi;
import com.yryz.quanhu.behavior.report.vo.ReportVo;
import com.yryz.quanhu.support.config.api.BasicConfigApi;
import com.yryz.quanhu.support.id.api.IdAPI;
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

    @Reference(check = false)
    private IdAPI idAPI;

    @Reference(check = false)
    private BasicConfigApi basicConfigApi;

    @Override
    public Response<Map<String, Integer>> accretion(Report report) {
        try {
            report.setKid(idAPI.getSnowflakeId().getData());
            Map<String,Integer> map=new HashMap<String,Integer>();
            int count = reportService.accretion(report);
            if(count>0){
                map.put("result",1);
            }else{
                map.put("result",0);
            }
            return ResponseUtils.returnObjectSuccess(map);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }

    }

    @Override
    public Response<PageList<Report>> queryReportForAdmin(ReportDTO reportDTO) {
        try{
            return ResponseUtils.returnObjectSuccess(reportService.queryReportForAdmin(reportDTO));
        }catch (Exception e){
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }

    }

    @Override
    public Response<Report> querySingleReport(Report report) {
        try{
            return ResponseUtils.returnObjectSuccess(reportService.querySingleReport(report));
        }catch (Exception e){
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }

    }

    @Override
    public Response<List<ReportVo>> queryInformDesc() {
        Response<String> informDescResponse=null;
        try{
            informDescResponse = basicConfigApi.getValue("reportDesc");
            String[] array= informDescResponse.getData().split(",");
            List<ReportVo> reportVos=new ArrayList<ReportVo>();
            for(int i=0;i<array.length;i++){
                ReportVo reportVo=new ReportVo();
                reportVo.setInformDesc(array[i]);
                reportVos.add(reportVo);
            }
            return ResponseUtils.returnObjectSuccess(reportVos);
        }catch (Exception e){
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

}
