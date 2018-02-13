/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018年1月18日
 * Id: ResourceController.java, 2018年1月18日 下午6:02:50 yehao
 */
package com.yryz.quanhu.openapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yryz.common.constant.CommonConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.dymaic.service.DymaicService;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import com.yryz.quanhu.dymaic.vo.DymaicVo;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

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

    @Reference(check = false)
    private UserApi userApi;


    //    @UserBehaviorValidation(login = true)
    @ApiOperation("动态tab的所关注的全部动态")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "/{version}/dymaic/gettimeline")
    public Response<List<DymaicVo>> getTimeLine(@RequestHeader Long userId, @RequestParam Long kid, @RequestParam Long limit, HttpServletRequest request) {
        return dymaicService.getTimeLine(userId, kid, limit);
    }

    @ApiOperation("用户个人主页")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "/{version}/dymaic/homepage")
    public Response<List<DymaicVo>> coterieRecommend(@RequestHeader Long userId, @RequestParam Long targetUserId, @RequestParam Long kid, @RequestParam Long limit, HttpServletRequest request) {
        List<DymaicVo> list = ResponseUtils.getResponseData(dymaicService.getSendList(userId, targetUserId, kid, limit));
        if (list == null || list.size() == 0) {
            return ResponseUtils.returnSuccess();
        }
        if (kid == null || kid == 0L) {
            DymaicVo topDymaic = ResponseUtils.getResponseData(dymaicService.getTopDymaic(userId));
            topDymaic.setTopFlag(CommonConstants.TOP_YES);
            list.add(0, topDymaic);
        }
        return ResponseUtils.returnObjectSuccess(list);
    }

    @ApiOperation("动态详情ID")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "/{version}/dymaic/getDymaic")
    public Response<DymaicVo> getDymaic(@RequestParam Long kid) {
        if (kid == null) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        try {
            Dymaic dymaic = ResponseUtils.getResponseData(dymaicService.get(kid));
            if (dymaic == null || dymaic.getKid() == null) {
                return ResponseUtils.returnSuccess();
            }
            DymaicVo dymaicVo = new DymaicVo();
            BeanUtils.copyProperties(dymaic, dymaicVo);
            UserSimpleVO userSimpleVO = ResponseUtils.getResponseData(userApi.getUserSimple(dymaic.getUserId()));
            dymaicVo.setUser(userSimpleVO);
            return ResponseUtils.returnObjectSuccess(dymaicVo);
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.BusiException);
        }
    }

    @ApiOperation("删除动态")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/dymaic/delete")
    public Response<Boolean> delete(@RequestHeader Long userId, @RequestBody Dymaic dymaic) {
        if (dymaic == null || dymaic.getKid() == null) {
            return ResponseUtils.returnException(QuanhuException.busiError("kid参数为空"));
        }
        return dymaicService.delete(userId, dymaic.getKid());
    }

    @ApiOperation("动态置顶")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/dymaic/addTopDymaic")
    public Response<Boolean> addTopDymaic(@RequestHeader Long userId, @RequestBody Dymaic dymaic) {
        if (dymaic == null || dymaic.getKid() == null) {
            return ResponseUtils.returnException(QuanhuException.busiError("kid参数为空"));
        }
        return dymaicService.addTopDymaic(userId, dymaic.getKid());
    }


    @ApiOperation("动态取消置顶")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/dymaic/deleteTopDymaic")
    public Response<Boolean> deleteTopDymaic(@RequestHeader Long userId, @RequestBody Dymaic dymaic) {
        if (dymaic == null || dymaic.getKid() == null) {
            return ResponseUtils.returnException(QuanhuException.busiError("kid参数为空"));
        }
        return dymaicService.deleteTopDymaic(userId, dymaic.getKid());
    }

    @ApiOperation("动态置顶状态查询")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "/{version}/dymaic/getTopDymaic")
    public Response<DymaicVo> getTopDymaic(@RequestHeader Long userId) {
        return dymaicService.getTopDymaic(userId);
    }
}