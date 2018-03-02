package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.yryz.common.annotation.UserBehaviorArgs;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.like.Service.LikeApi;
import com.yryz.quanhu.behavior.like.dto.LikeFrontDTO;
import com.yryz.quanhu.behavior.like.entity.Like;
import com.yryz.quanhu.behavior.like.vo.LikeInfoVO;
import com.yryz.quanhu.behavior.like.vo.LikeVO;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author:sun
 * @version:2.0
 * @Description:点赞
 * @Date:Created in 16:57 2018/1/24
 */
@Api(tags = "点赞")
@RestController
public class LikeController {

    @Reference(check = false)
    private LikeApi likeApi;

    
    @ApiOperation("用户点赞/取消点赞")
    @UserBehaviorArgs(sourceUserId="object.Like.resourceId")
    @UserBehaviorValidation(login = true, mute = false, blacklist = false, illegalWords = false)
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "/services/app/{version}/like/dian")
    public Response<Map<String, Object>> accretion(@RequestBody Like like, @RequestHeader Long userId) {
        like.setUserId(userId);
        return likeApi.dian(like);
    }

    
    @ApiOperation("点赞列表")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/like/frontlist")
    public Response<PageList<LikeVO>> queryLikers(LikeFrontDTO likeFrontDTO){
        return likeApi.queryLikers(likeFrontDTO);
    }

    
    @ApiOperation("点赞列表")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    //@GetMapping(value = "/services/app/{version}/like/frontlist")
    public Response<PageList<LikeInfoVO>> listLiker(LikeFrontDTO likeFrontDTO){
        return likeApi.listLike(likeFrontDTO);
    }
}
