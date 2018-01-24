package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.behavior.count.dto.CountDto;
import com.yryz.quanhu.behavior.report.entity.Report;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.openapi.controller
 * @Desc: 行为系统
 * @Date: 2018/1/24.
 */
@Api(tags = "行为系统")
@RestController
public class BehaviorController {
    @Reference
    private CountApi countApi;

    @NotLogin
    @ApiOperation("查询计数")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "/services/app/{version}/behavior/getCount")
    public Response<Map<String, Long>> accretion(@RequestBody CountDto countDto, HttpServletRequest request) {
        countApi.countCommit(BehaviorEnum.Comment, countDto.getKid(), null, 1L);
        countApi.countCommit(BehaviorEnum.Like, countDto.getKid(), null, 1L);
        return countApi.getCount(countDto.getCountType(), countDto.getKid());
    }
}
