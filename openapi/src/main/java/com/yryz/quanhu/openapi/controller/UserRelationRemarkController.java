package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.dto.UserRelationRemarkDto;
import com.yryz.quanhu.user.service.UserRelationRemarkApi;
import com.yryz.quanhu.user.vo.UserRelationRemarkVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/22 14:09
 * Created by huangxy
 */
@Api(description = "用户关系-备注名接口")
@RestController
public class UserRelationRemarkController {

    @Reference(check = false)
    private UserRelationRemarkApi userRelationRemarkApi;

    /**
     * 设置备注名
     * @param request
     * @return
     */
    @ApiOperation("用户备注名-设置/重置")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @RequestMapping("{version}/user/relation/remark")
    public Response<Boolean> setRemarkName(HttpServletRequest request){

        String userId       = request.getHeader("userId");
        String remarkType   = request.getParameter("remarkType");
        String remarkValue  = request.getParameter("remarkValue");
        String targetUserId = request.getParameter("targetUserId");
        String eventType    = request.getParameter("eventType");

        //check
        Assert.notNull(userId,"userId不能为空");
        Assert.notNull(remarkType,"remarkType不能为空");
        Assert.notNull(remarkValue,"remarkValue不能为空");
        Assert.notNull(targetUserId,"targetUserId不能为空");

        //package
        UserRelationRemarkDto dto = new UserRelationRemarkDto();
        dto.setSourceUserId(userId);
        dto.setTargetUserId(targetUserId);
        dto.setRemarkType(Integer.parseInt(remarkType));
        dto.setRemarkValue(remarkValue);

        //invoke
        if(1 == Integer.parseInt(eventType)){
            return userRelationRemarkApi.setRemarkName(dto);
        }else if(0 == Integer.parseInt(eventType)){
            return userRelationRemarkApi.resetRemarkName(dto);
        }else{
            return ResponseUtils.returnException(new RuntimeException("eventType is illegal:"+eventType));
        }
    }

    /**
     * 查询用户所有备注信息
     * @param request
     * @return
     */
    @ApiOperation("用户备注名-查询")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @RequestMapping("{version}/user/relation/remark/query")
    public Response<List<UserRelationRemarkVo>> selectBy(HttpServletRequest request){

        String userId       = request.getHeader("userId");
        String remarkType   = request.getParameter("remarkType");

        //check
        Assert.notNull(userId,"userId不能为空");
        Assert.notNull(remarkType,"remarkType不能为空");

        Response<List<UserRelationRemarkDto>>  rpc =  userRelationRemarkApi.selectBy(userId, UserRelationConstant.TYPE.get(Integer.parseInt(remarkType)));

        //转换vo
        if(rpc.success()){
            List<UserRelationRemarkDto> list = rpc.getData();
            List<UserRelationRemarkVo> voList = new ArrayList<>();
            for(int i = 0 ; i < list.size() ; i++){
                UserRelationRemarkVo vo = new UserRelationRemarkVo();
                BeanUtils.copyProperties(list.get(i),vo);
                voList.add(vo);
            }
            return new Response<List<UserRelationRemarkVo>>(true, rpc.getCode(), rpc.getMsg(), rpc.getErrorMsg(), voList);
        }else{
            return new Response<List<UserRelationRemarkVo>>(false, rpc.getCode(), rpc.getMsg(), rpc.getErrorMsg(), null);
        }
    }

}
