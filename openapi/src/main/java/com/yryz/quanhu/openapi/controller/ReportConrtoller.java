package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;


import com.yryz.common.annotation.UserBehaviorArgs;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.report.dto.ReportDTO;
import com.yryz.quanhu.behavior.report.entity.Report;
import com.yryz.quanhu.behavior.report.service.ReportApi;
import com.yryz.quanhu.behavior.report.vo.ReportVo;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author:sun
 * @version:2.0
 * @Description:举报
 * @Date:Created in 15:08 2018/1/22
 */
@Api(tags = "举报")
@RestController
public class ReportConrtoller {

    @Reference(check = false)
    private ReportApi reportApi;

    @ApiOperation("用户举报")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "/services/app/{version}/report/submit")

    @UserBehaviorValidation(login = true)
    public Response<Map<String, Integer>> submit(@RequestBody ReportDTO report, @RequestHeader Long userId, HttpServletRequest request) {

        Assert.notNull(report.getModuleEnum(),"moduleEnum不能为空");
        Assert.notNull(report.getResourceId(),"resourceId不能为空");

        Assert.notNull(report.getReportType(),"reportType不能为空");
        Assert.notNull(report.getReportContext(),"reportContext不能为空");

        report.setCreateUserId(userId);
        return reportApi.submit(report);
    }

    
    @ApiOperation("举报类型")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/report/types")
    public Response<List<ReportVo>> types() {
        return reportApi.getReportType(null);
    }


}
