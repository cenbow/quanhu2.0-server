package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.service.UserRelationApi;
import com.yryz.quanhu.user.vo.UserRelationCountVo;
import com.yryz.quanhu.user.vo.UserRelationEventVo;
import com.yryz.quanhu.user.vo.UserRelationQueryVo;
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
 * Created on 2018/1/22 14:30
 * Created by huangxy
 */
@Api(description = "用户关系接口")
@RestController
public class UserRelationController {

    @Reference(check = false)
    private UserRelationApi userRelationApi;

    @ApiOperation("用户关系-关注/取消关注")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @RequestMapping("{version}/user/relation/follow")
    public Response<UserRelationEventVo> setFollow(HttpServletRequest request){

        String userId       = request.getHeader("userId");
        String targetUserId = request.getParameter("targetUserId");
        String eventType    = request.getParameter("eventType");

        //check
        Assert.notNull(userId,"userId不能为空");
        Assert.notNull(targetUserId,"targetUserId不能为空");
        Assert.notNull(eventType,"eventType不能为空");

        //invoke
        Response<UserRelationDto> rpc = null;
        if(1 == Integer.parseInt(eventType)){
            rpc = userRelationApi.setRelation(userId,targetUserId, UserRelationConstant.EVENT.SET_FOLLOW);
        }else if(0 == Integer.parseInt(eventType)){
            rpc = userRelationApi.setRelation(userId,targetUserId,UserRelationConstant.EVENT.CANCEL_FOLLOW);
        }else{
            return ResponseUtils.returnException(new RuntimeException("eventType value is illegal:"+eventType));
        }

        //转换vo
        if(rpc.success()){
            UserRelationEventVo vo = new UserRelationEventVo();
            BeanUtils.copyProperties(rpc.getData(),vo);
            return new Response<UserRelationEventVo>(true, rpc.getCode(), rpc.getMsg(), rpc.getErrorMsg(), vo);
        }else{
            return new Response<UserRelationEventVo>(false, rpc.getCode(), rpc.getMsg(), rpc.getErrorMsg(), null);
        }
    }


    @ApiOperation("用户关系-拉黑/取消拉黑")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @RequestMapping("{version}/user/relation/black")
    public Response<UserRelationEventVo> setBlack(HttpServletRequest request){

        String userId       = request.getHeader("userId");
        String targetUserId = request.getParameter("targetUserId");
        String eventType    = request.getParameter("eventType");

        //check
        Assert.notNull(userId,"userId不能为空");
        Assert.notNull(targetUserId,"targetUserId不能为空");
        Assert.notNull(eventType,"eventType不能为空");

        //invoke
        Response<UserRelationDto> rpc = null;
        if(1 == Integer.parseInt(eventType)){
            rpc = userRelationApi.setRelation(userId,targetUserId,UserRelationConstant.EVENT.SET_BLACK);
        }else if(0 == Integer.parseInt(eventType)){
            rpc = userRelationApi.setRelation(userId,targetUserId,UserRelationConstant.EVENT.CANCEL_BLACK);
        }else{
            return ResponseUtils.returnException(new RuntimeException("eventType value is illegal:"+eventType));
        }

        //转换vo
        if(rpc.success()){
            UserRelationEventVo vo = new UserRelationEventVo();
            BeanUtils.copyProperties(rpc.getData(),vo);
            return new Response<UserRelationEventVo>(true, rpc.getCode(), rpc.getMsg(), rpc.getErrorMsg(), vo);
        }else{
            return new Response<UserRelationEventVo>(false, rpc.getCode(), rpc.getMsg(), rpc.getErrorMsg(), null);
        }


    }

    @ApiOperation("用户关系-查询（一对一）")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @RequestMapping("{version}/user/relation/mapper")
    public Response<UserRelationQueryVo> getRelation(HttpServletRequest request){
        String userId       = request.getHeader("userId");
        String targetUserId = request.getParameter("targetUserId");

        //check
        Assert.notNull(userId,"userId不能为空");
        Assert.notNull(targetUserId,"targetUserId不能为空");

        //invoke
        Response<UserRelationDto> rpc =  userRelationApi.getRelation(userId,targetUserId);

        if(rpc.success()){
            UserRelationQueryVo vo = new UserRelationQueryVo();
            BeanUtils.copyProperties(rpc.getData(),vo);
            return new Response<UserRelationQueryVo>(true, rpc.getCode(), rpc.getMsg(), rpc.getErrorMsg(), vo);
        }else{
            return new Response<UserRelationQueryVo>(false, rpc.getCode(), rpc.getMsg(), rpc.getErrorMsg(), null);
        }

    }


    @ApiOperation("用户关系-查询（指定条件）")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @RequestMapping("{version}/user/relation/query")
    public Response<PageList<UserRelationQueryVo>> getRelations(HttpServletRequest request){

        String userId       = request.getHeader("userId");
        String targetUserId = request.getParameter("targetUserId");
        String relationType = request.getParameter("relationType");
        String currentPage  = request.getParameter("currentPage");
        String pageSize     = request.getParameter("pageSize");


        //check
        Assert.notNull(userId,"userId不能为空");
        Assert.notNull(targetUserId,"targetUserId不能为空");
        Assert.notNull(relationType,"relationType不能为空");
        Assert.notNull(currentPage,"currentPage不能为空");
        Assert.notNull(pageSize,"pageSize不能为空");


        //package
        UserRelationDto dto = new UserRelationDto();
        dto.setPageSize(Integer.parseInt(pageSize));
        dto.setCurrentPage(Integer.parseInt(currentPage));
        dto.setSourceUserId(userId);
        dto.setTargetUserId(targetUserId);
        UserRelationConstant.STATUS  status = UserRelationConstant.STATUS.get(Integer.parseInt(relationType));

        //invoke
        Response<PageList<UserRelationDto>> rpc = userRelationApi.selectByPage(dto,status);
        if(rpc.success()){
            PageList<UserRelationQueryVo> voPageList = new PageList<>();

            //逐条转换
            List<UserRelationQueryVo> voList = new ArrayList<>();
            List<UserRelationDto> dtoList = rpc.getData().getEntities();
            for(int i = 0 ; i < dtoList.size() ; i++){
                UserRelationQueryVo vo = new UserRelationQueryVo();
                BeanUtils.copyProperties(dtoList.get(i),vo);
                voList.add(vo);
            }

            //重新赋值
            voPageList.setEntities(voList);
            voPageList.setCurrentPage(rpc.getData().getCurrentPage());
            voPageList.setCount(rpc.getData().getCount());
            voPageList.setPageSize(rpc.getData().getPageSize());
            return new Response<PageList<UserRelationQueryVo>>(true, rpc.getCode(), rpc.getMsg(), rpc.getErrorMsg(), voPageList);
        }else{
            return new Response<PageList<UserRelationQueryVo>>(false, rpc.getCode(), rpc.getMsg(), rpc.getErrorMsg(), null);
        }

    }

    @ApiOperation("用户关系-统计查询")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @RequestMapping("{version}/user/relation/count")
    public Response<UserRelationCountVo> getRelationCount(HttpServletRequest request){

        String userId       = request.getHeader("userId");
        String targetUserId = request.getParameter("targetUserId");

        //check
        Assert.notNull(userId,"userId不能为空");
        Assert.notNull(targetUserId,"targetUserId不能为空");

        Response<UserRelationCountDto> rpc = userRelationApi.totalBy(targetUserId);
        if(rpc.success()){
            UserRelationCountVo vo = new UserRelationCountVo();
            BeanUtils.copyProperties(rpc.getData(),vo);
            return new Response<UserRelationCountVo>(true, rpc.getCode(), rpc.getMsg(), rpc.getErrorMsg(), vo);
        }else{
            return new Response<UserRelationCountVo>(false, rpc.getCode(), rpc.getMsg(), rpc.getErrorMsg(), null);
        }
    }

}
