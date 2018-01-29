package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.collection.api.CollectionInfoApi;
import com.yryz.quanhu.behavior.collection.dto.CollectionInfoDto;
import com.yryz.quanhu.behavior.collection.vo.CollectionInfoVo;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(description = "收藏")
@RestController
public class CollectionInfoController {

    @Reference(check = false)
    private CollectionInfoApi collectionInfoApi;

    /**
     * 收藏
     * @param   collectionInfoDto
     * @return
     * */
    @UserBehaviorValidation(login = true)
    @ApiOperation("收藏")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "services/app/{version}/collection/single")
    public Response single(@RequestBody CollectionInfoDto collectionInfoDto, HttpServletRequest request) {
        String userId = request.getHeader("userId");
        Assert.hasText(userId, "userId不能为空");
        collectionInfoDto.setCreateUserId(Long.valueOf(userId));
        return collectionInfoApi.single(collectionInfoDto);
    }

    @UserBehaviorValidation(login = true)
    @ApiOperation("取消收藏")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "services/app/{version}/collection/del")
    public Response del(@RequestBody CollectionInfoDto collectionInfoDto, HttpServletRequest request) {
        String userId = request.getHeader("userId");
        Assert.hasText(userId, "userId不能为空");
        collectionInfoDto.setCreateUserId(Long.valueOf(userId));
        return collectionInfoApi.del(collectionInfoDto);
    }

    @UserBehaviorValidation(login = true)
    @ApiOperation("收藏列表")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "services/app/{version}/collection/list")
    public Response<PageList<CollectionInfoVo>> list(CollectionInfoDto collectionInfoDto, HttpServletRequest request) {
        String userId = request.getHeader("userId");
        Assert.hasText(userId, "userId不能为空");
        collectionInfoDto.setCreateUserId(Long.valueOf(userId));
        return collectionInfoApi.list(collectionInfoDto);
    }


}
