/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018年1月18日
 * Id: ResourceController.java, 2018年1月18日 下午6:02:50 yehao
 */
package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.enums.ResourceEnum;
import com.yryz.quanhu.resource.vo.ResourceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月18日 下午6:02:50
 * @Description 资源管理API实现
 */
@Api(tags = "资源管理")
@RestController
@RequestMapping(value = "/services/app")
public class ResourceController {

    @Reference(check = false)
    private ResourceApi resourceApi;


    @ApiOperation("首页资源推荐")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "/{version}/resource/appRecommend")
    public Response<PageList<ResourceVo>> appRecommend(@RequestParam Integer currentPage, @RequestParam Integer pageSize) {
        int start = 0;
        if (pageSize == null) {
            pageSize = 10;
        }
        if (currentPage != null && currentPage >= 1) {
            start = (currentPage - 1) * pageSize;
        }
        PageList<ResourceVo> pageList = new PageList<>();
        pageList.setCurrentPage(currentPage);
        pageList.setEntities(ResponseUtils.getResponseData(resourceApi.appRecommend(start, pageSize)));
        pageList.setPageSize(pageSize);
        return ResponseUtils.returnObjectSuccess(pageList);

    }

    //    @UserBehaviorValidation(login = true)
    @ApiOperation("私圈首页动态")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "/{version}/resource/coterieRecommend")
    public Response<PageList<ResourceVo>> coterieRecommend(@RequestParam String coterieId, @RequestParam Integer currentPage, @RequestParam Integer pageSize) {
        int start = 0;
        if (pageSize == null) {
            pageSize = 10;
        }
        if (currentPage != null && currentPage >= 1) {
            start = (currentPage - 1) * pageSize;
        }
        ResourceVo resourceVo = new ResourceVo();
        resourceVo.setPublicState(ResourceEnum.PUBLIC_STATE_FALSE);
        resourceVo.setCoterieId(coterieId);
        PageList<ResourceVo> pageList = new PageList<>();
        pageList.setCurrentPage(currentPage);
        pageList.setEntities(ResponseUtils.getResponseData(resourceApi.getResources(resourceVo, "createTime", start, pageSize, null, null)));
        pageList.setPageSize(pageSize);
        return ResponseUtils.returnObjectSuccess(pageList);
    }

    //    @UserBehaviorValidation(login = true)
    @ApiOperation("我的发布")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "/{version}/resource/myRelease")
    public Response<PageList<ResourceVo>> myRelease(@RequestHeader Long userId, @RequestParam Integer currentPage, @RequestParam Integer pageSize) {
        int start = 0;
        if (pageSize == null) {
            pageSize = 10;
        }
        if (currentPage != null && currentPage >= 1) {
            start = (currentPage - 1) * pageSize;
        }
        //（私圈外）上线的文章和话题帖子，按照发布时间倒序排列；
        ResourceVo resourceVo = new ResourceVo();
        resourceVo.setModuleEnum(ModuleContants.RELEASE + "," + ModuleContants.TOPIC_POST);
        //0，只查平台文章。1，只查所有私圈文章。xxxx，查具体某个私圈的数据。
        resourceVo.setCoterieId("0");
        PageList<ResourceVo> pageList = new PageList<>();
        pageList.setCurrentPage(currentPage);
        pageList.setEntities(ResponseUtils.getResponseData(resourceApi.getResources(resourceVo, "createTime", start, pageSize, null, null)));
        pageList.setPageSize(pageSize);
        return ResponseUtils.returnObjectSuccess(pageList);
    }
}