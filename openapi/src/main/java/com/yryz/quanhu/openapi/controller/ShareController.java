package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.collection.dto.CollectionInfoDto;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(description = "分享")
@RestController
public class ShareController {

    private Logger logger = LoggerFactory.getLogger(ShareController.class);

    @Reference(check = false)
    private CountApi countApi;

    @UserBehaviorValidation
    @ApiOperation("分享")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "services/app/{version}/share/single")
    public Response single(@RequestBody CollectionInfoDto collectionInfoDto, HttpServletRequest request) {
        Assert.notNull(collectionInfoDto.getResourceId(), "resourceId不能为空");
        return countApi.commitCount(BehaviorEnum.Share, collectionInfoDto.getResourceId(), null, 1L);
    }

}
