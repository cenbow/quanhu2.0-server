/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018年1月18日
 * Id: ResourceController.java, 2018年1月18日 下午6:02:50 yehao
 */
package com.yryz.quanhu.openapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoApi;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.coterie.member.constants.MemberConstant;
import com.yryz.quanhu.coterie.member.service.CoterieMemberAPI;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.enums.ResourceEnum;
import com.yryz.quanhu.resource.vo.ResourceVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

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

    @Reference
    private ResourceApi resourceApi;

    @Reference
    private CountApi countApi;

    @Reference
    private CoterieMemberAPI coterieMemberAPI;

    @Reference
    private ReleaseInfoApi releaseInfoApi;

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

    @ApiOperation("私圈首页动态")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "/{version}/resource/coterieRecommend")
    public Response<PageList<ResourceVo>> coterieRecommend(@RequestHeader(value = "userId", required = false) Long userId, @RequestParam String coterieId, @RequestParam Integer currentPage, @RequestParam Integer pageSize) {
        int start = 0;
        if (pageSize == null) {
            pageSize = 10;
        }
        if (currentPage != null && currentPage >= 1) {
            start = (currentPage - 1) * pageSize;
        }
        if (StringUtils.isEmpty(coterieId)) {
            return ResponseUtils.returnException(QuanhuException.busiError("coterieId为空"));
        }
        Integer permiss = coterieMemberAPI.permission(userId, new Long(coterieId)).getData();
        ResourceVo resourceVo = new ResourceVo();
        if (permiss == null || MemberConstant.Permission.STRANGER_NON_CHECK.getStatus() == permiss || MemberConstant.Permission.STRANGER_WAITING_CHECK.getStatus() == permiss) {
            resourceVo.setModuleEnum(ModuleContants.RELEASE + "," + ModuleContants.TOPIC + ",");
            resourceVo.setPrice(0L);
            pageSize = 3;
        } else if (MemberConstant.Permission.OWNER.getStatus() == permiss || MemberConstant.Permission.MEMBER.getStatus() == permiss) {
            resourceVo.setModuleEnum(ModuleContants.RELEASE + "," + ModuleContants.TOPIC + "," + ModuleContants.ANSWER + "," + ModuleContants.ACTIVITY_COTERIE);
        }
        //  resourceVo.setPublicState(ResourceEnum.PUBLIC_STATE);
        resourceVo.setIntimate(ResourceEnum.INTIMATE_FALSE);
        resourceVo.setCoterieId(coterieId);
        PageList<ResourceVo> pageList = new PageList<>();
        pageList.setCurrentPage(currentPage);
        List<ResourceVo> list = ResponseUtils.getResponseData(resourceApi.getResources(resourceVo, "sort,createTime", start, pageSize, null, null));
        if (list != null && list.size() > 0 && userId != null) {
            addCount(list, userId);
        }
        pageList.setEntities(list);
        pageList.setPageSize(pageSize);
        return ResponseUtils.returnObjectSuccess(pageList);
    }

    @ApiOperation("我的发布/他人个人主页发布")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "/{version}/resource/myRelease")
    public Response<PageList<ResourceVo>> myRelease(@RequestParam Long userId, @RequestParam Integer currentPage,
                                                    @RequestParam Integer pageSize) {
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
        resourceVo.setUserId(userId);
        PageList<ResourceVo> pageList = new PageList<>();
        pageList.setCurrentPage(currentPage);
        pageList.setEntities(ResponseUtils
                .getResponseData(resourceApi.getResources(resourceVo, "createTime", start, pageSize, null, null)));
        pageList.setPageSize(pageSize);
        return ResponseUtils.returnObjectSuccess(pageList);
    }

    @UserBehaviorValidation(login = true)
    @ApiOperation("置顶")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/resource/top")
    public Response<Object> top(@RequestHeader Long userId, @RequestBody ResourceVo resourceVo) {
        ResourceVo resource = ResponseUtils.getResponseData(resourceApi.getResourcesById(resourceVo.getResourceId()));
        if (resource == null || resource.getResourceId() == null) {
            return ResponseUtils.returnException(QuanhuException.busiError("资源ID不存在"));
        }
        if (userId == null || !userId.equals(resource.getUserId())) {
            return ResponseUtils.returnException(QuanhuException.busiError("没有权限置顶此文章"));
        }
        resource.setSort(99999L);
        //查询当前私圈的置顶数据
        ResourceVo resourceParam = new ResourceVo();
        resourceParam.setSort(99999L);
        resourceParam.setCoterieId(resource.getCoterieId());
        List<ResourceVo> list = resourceApi.getResources(resourceParam, "", 0, 0, null, null).getData();
        //将置顶数据取消置顶
        for (ResourceVo rv : list) {
            rv.setSort(0L);
        }
        list.add(resource);
        resourceApi.updateResource(list);
        return ResponseUtils.returnSuccess();
    }

    @UserBehaviorValidation(login = true)
    @ApiOperation("取消置顶")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/resource/canceltop")
    public Response<Object> canceltop(@RequestHeader Long userId, @RequestBody ResourceVo resourceVo) {
        ResourceVo resource = ResponseUtils.getResponseData(resourceApi.getResourcesById(resourceVo.getResourceId()));
        if (resource == null || resource.getResourceId() == null) {
            return ResponseUtils.returnException(QuanhuException.busiError("资源ID不存在"));
        }
        if (userId == null || !userId.equals(resource.getUserId())) {
            return ResponseUtils.returnException(QuanhuException.busiError("没有权限置顶此文章"));
        }
        resource.setSort(0L);
        resourceApi.updateResource(Lists.newArrayList(resource));
        return ResponseUtils.returnSuccess();
    }

    /**
     * 添加统计数(批量)
     *
     * @param list
     * @return
     */
    private List<ResourceVo> addCount(List<ResourceVo> list, Long userId) {
        if (CollectionUtils.isNotEmpty(list)) {
            List<Long> resourceIds = new ArrayList<>();
            for (ResourceVo resourceVo : list) {
                resourceIds.add(Long.parseLong(resourceVo.getResourceId()));
            }
            Response<Map<Long, Map<String, Long>>> response = countApi.getCountFlag(BehaviorEnum.Like.getCode() + "," + BehaviorEnum.Comment.getCode(), resourceIds, null, userId);
            if (response.success()) {
                Map<Long, Map<String, Long>> map = response.getData();
                for (ResourceVo resourceVo : list) {
                    Map<String, Long> statistics = map.get(Long.parseLong(resourceVo.getResourceId()));
                    if (statistics != null) {
                        resourceVo.setStatistics(statistics);
                    } else {
                        resourceVo.setStatistics(new HashMap<>());
                    }
                }
            }
        }
        return list;
    }

    @UserBehaviorValidation(login = true)
    @ApiOperation("资源删除")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @PostMapping(value = "/{version}/resource/delete")
    public Response<Object> delete(@RequestHeader Long userId, @RequestBody ResourceVo resourceVo) {
        if (StringUtils.isEmpty(resourceVo.getResourceId())) {
            return ResponseUtils.returnException(new QuanhuException(ExceptionEnum.PARAM_MISSING));
        }
        Long kid = new Long(resourceVo.getResourceId());
        switch (resourceVo.getModuleEnum()) {
            case ModuleContants.RELEASE://文章
                ReleaseInfo upInfo = new ReleaseInfo();
                upInfo.setKid(kid);
                upInfo.setLastUpdateUserId(userId);
                releaseInfoApi.deleteBykid(upInfo);
                break;
            case ModuleContants.TOPIC://话题
                // TODO
                break;
        }
        return ResponseUtils.returnSuccess();
    }

    @ApiOperation("资源状态")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.COMPATIBLE_VERSION, required = true)
    @GetMapping(value = "/{version}/resource/state")
    public Response<Map<String, Object>> state(@RequestParam Long resourceId, @RequestParam(required = false) Long moduleEnum) {
        Map<String, Object> result = Maps.newHashMap();
        Integer resourceState = ResourceEnum.DEL_FLAG_TRUE;
        ResourceVo resourceVo = ResponseUtils.getResponseData(this.resourceApi.getResourcesById(String.valueOf(resourceId)));
        if (null != resourceVo) {
            resourceState = resourceVo.getDelFlag();
        }
        result.put("resourceState", resourceState);

        return ResponseUtils.returnObjectSuccess(result);
    }
}