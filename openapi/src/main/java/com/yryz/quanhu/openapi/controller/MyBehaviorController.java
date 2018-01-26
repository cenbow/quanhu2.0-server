package com.yryz.quanhu.openapi.controller;

import com.alibaba.fastjson.JSON;
import com.yryz.common.annotation.UserBehaviorArgs;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/25 11:05
 * Created by huangxy
 */
@RestController
@RequestMapping("services/app")
public class MyBehaviorController {

    private static final Logger logger = LoggerFactory.getLogger(MyBehaviorController.class);

    @UserBehaviorValidation(event = "testRequest")
    @UserBehaviorArgs(loginUserId = "request.head.userId",
                        loginToken = "request.head.userToken",
                        sourceType = "request.body.sourceType")
    @RequestMapping("/request")
    public Response<String> testRequest(HttpServletRequest request){
        return null;
    }




    @UserBehaviorValidation(event = "资源发布Map", blacklist = true, illegalWords = true,login = false)
    @UserBehaviorArgs(sourceTitle = "map.title",sourceContext = "map.contentSource",sourceUserId = "map.createUserId")

    @RequestMapping("/map")
    public Response<String> testMap(HttpServletRequest request, @RequestBody Map<String,Object> map){
        logger.info("map={}",map);
        return null;
    }


    @UserBehaviorValidation(event = "资源发布", illegalWords = true)
    @UserBehaviorArgs(sourceTitle = "object.ReleaseInfo.title", sourceContext = "object.ReleaseInfo.contentSource")


    @RequestMapping("/info")
    public Response<String> testInfo(HttpServletRequest request, @RequestBody ReleaseInfo info){
        logger.info("comment:"+ JSON.toJSONString(info));
        return null;
    }

    @UserBehaviorValidation(event = "发表评论", illegalWords = true, blacklist = true)
    @UserBehaviorArgs(sourceUserId = "object.Comment.targetUserId",sourceContext = "object.Comment.contentComment")

    @RequestMapping("/dto")
    public Response<String> testDto(HttpServletRequest request, @RequestBody Comment comment){
        logger.info("comment:"+ JSON.toJSONString(comment));
        return null;
    }
}
