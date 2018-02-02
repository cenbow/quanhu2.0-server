package com.yryz.quanhu.openapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.gift.api.GiftInfoApi;
import com.yryz.quanhu.behavior.gift.dto.GiftInfoDto;
import com.yryz.quanhu.behavior.gift.entity.GiftInfo;
import com.yryz.quanhu.openapi.ApplicationOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
* @Description: 礼物
* @author wangheng
* @date 2018年1月26日 下午5:19:21
*/
@Api(description = "礼物接口")
@RestController
@RequestMapping(value = "/services/app")
public class GiftInfoController {

    @Reference(lazy = true, check = false, timeout = 10000)
    private GiftInfoApi giftInfoApi;

    @ApiOperation("礼物列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "currentPage", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", paramType = "query", required = true) })
    @GetMapping(value = "{version}/gift/list")
    public Response<PageList<GiftInfo>> list(@RequestParam Integer currentPage, @RequestParam Integer pageSize) {
        GiftInfoDto dto = new GiftInfoDto();
        dto.setCurrentPage(currentPage);
        dto.setPageSize(pageSize);
        return giftInfoApi.pageByCondition(dto, false);
    }

    @ApiOperation("礼物更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "kid", paramType = "query", required = true),
            @ApiImplicitParam(name = "giftName", paramType = "query", required = true),
            @ApiImplicitParam(name = "giftImage", paramType = "query", required = true),
            @ApiImplicitParam(name = "giftPrice", paramType = "query", required = true) })
    @PostMapping(value = "{version}/gift/info")
    public Response<Integer> update(@RequestParam Long kid, @RequestParam String giftName,
            @RequestParam String giftImage, @RequestParam Long giftPrice) {

        GiftInfo record = new GiftInfo();
        record.setGiftImage(giftImage);
        record.setGiftName(giftName);
        record.setGiftPrice(giftPrice);
        record.setKid(kid);
        return giftInfoApi.updateByKid(record);
    }
}
