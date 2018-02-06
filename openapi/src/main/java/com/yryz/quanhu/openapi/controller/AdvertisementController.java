package com.yryz.quanhu.openapi.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.response.Response;
import com.yryz.common.utils.PatternUtils;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.resource.advertisement.api.AdvertisementAPI;
import com.yryz.quanhu.resource.advertisement.dto.AdvertisementDto;
import com.yryz.quanhu.resource.advertisement.vo.AdvertisementVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("广告")
@RestController
@RequestMapping("services/app")
public class AdvertisementController {

    @Reference(check = false, timeout = 30000)
    private AdvertisementAPI advertisementAPI;

    
    @ApiOperation("广告")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/ad/list")
    public Response<List<AdvertisementVo>> list(AdvertisementDto advertisementDto) {
        Assert.notNull(advertisementDto, "缺少参数或参数错误！");
        Assert.isTrue(PatternUtils.matcher(advertisementDto.getAdvType(), "10|20"), "未知广告类型！");
        return advertisementAPI.list(advertisementDto);
    }

}
