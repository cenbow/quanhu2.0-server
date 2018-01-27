/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018年1月18日
 * Id: ResourceController.java, 2018年1月18日 下午6:02:50 yehao
 */
package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.Login;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.response.Response;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.dymaic.service.DymaicService;
import com.yryz.quanhu.dymaic.vo.DymaicVo;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author liupan
 * @version 2.0
 * @date 2018年1月27日
 * @Description 动态管理API实现
 */
@Api(tags = "动态管理")
@RestController
@RequestMapping(value = "/services/app")
public class DymaicController {

    @Reference(check = false)
    private DymaicService dymaicService;


//    @UserBehaviorValidation(login = true)
    @ApiOperation("动态tab的所关注的全部动态")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "/{version}/dymaic/gettimeline")
    public Response<List<DymaicVo>> getTimeLine(@RequestHeader Long userId, @RequestParam("上页最后数据的kid") Long kid, @RequestParam("列表长度") Long limit, HttpServletRequest request) {
        return dymaicService.getTimeLine(userId, kid, limit);
    }

//    @UserBehaviorValidation(login = true)
    @ApiOperation("用户个人主页")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "/{version}/dymaic/homepage")
    public Response<List<DymaicVo>> coterieRecommend(@RequestHeader Long userId, @RequestParam("目标用户ID") Long targetUserId, @RequestParam("上页最后数据的kid") Long kid, @RequestParam("列表长度") Long limit, HttpServletRequest request) {
        return dymaicService.getSendList(userId, targetUserId, kid, limit);
    }
}