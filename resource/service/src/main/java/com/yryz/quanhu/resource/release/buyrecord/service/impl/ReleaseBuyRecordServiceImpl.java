package com.yryz.quanhu.resource.release.buyrecord.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.release.buyrecord.dao.ReleaseBuyRecordDao;
import com.yryz.quanhu.resource.release.buyrecord.dto.ReleaseBuyRecordDto;
import com.yryz.quanhu.resource.release.buyrecord.entity.ReleaseBuyRecord;
import com.yryz.quanhu.resource.release.buyrecord.service.ReleaseBuyRecordService;
import com.yryz.quanhu.resource.release.buyrecord.vo.ReleaseBuyRecordVo;
import com.yryz.quanhu.resource.vo.ResourceVo;
import com.yryz.quanhu.support.id.api.IdAPI;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 17:34
 * Created by lifan
 */
@Service
@Transactional
public class ReleaseBuyRecordServiceImpl implements ReleaseBuyRecordService {

    @Reference
    private IdAPI idAPI;

    @Reference
    private ResourceApi resourceApi;

    @Reference
    private CoterieApi coterieApi;

    @Autowired
    private ReleaseBuyRecordDao releaseBuyRecordDao;

    @Override
    public int insert(ReleaseBuyRecord releaseBuyRecord) {
        Response<Long> idResponse = idAPI.getKid("qh_release_buy_record");
        Long kid = ResponseUtils.getResponseData(idResponse);
        releaseBuyRecord.setKid(kid);
        return releaseBuyRecordDao.insert(releaseBuyRecord);
    }

    @Override
    public PageList<ReleaseBuyRecordVo> selectByCondition(ReleaseBuyRecordDto releaseBuyRecordDto) {
        if (null == releaseBuyRecordDto) {
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "参数不能为空");
        }
        if (null == releaseBuyRecordDto.getUserId()) {
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "用户ID不能为空");
        }
        if (null == releaseBuyRecordDto.getCurrentPage()) {
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "页码不能为空");
        }
        if (null == releaseBuyRecordDto.getPageSize()) {
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "每页条数不能为空");
        }
        //创建返回值对象
        PageList<ReleaseBuyRecordVo> pageList = new PageList<>();
        pageList.setCurrentPage(releaseBuyRecordDto.getCurrentPage());
        pageList.setPageSize(releaseBuyRecordDto.getPageSize());
        //组装分页
        PageHelper.startPage(releaseBuyRecordDto.getCurrentPage(), releaseBuyRecordDto.getPageSize(),false);
        List<ReleaseBuyRecord> list = releaseBuyRecordDao.selectByCondition(releaseBuyRecordDto);
        //创建结果集
        List<ReleaseBuyRecordVo> resultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            List<String> idList = list.stream().map(value -> String.valueOf(value.getResourceId())).collect(Collectors.toList());
            //组装资源
            Response<Map<String, ResourceVo>> response = resourceApi.getResourcesByIds(Sets.newHashSet(idList));
            if (null != response && response.success()) {
                Map<String, ResourceVo> resourceVoMap = response.getData();
                if (!CollectionUtils.isEmpty(resourceVoMap)) {
                    idList.forEach(id -> {
                        ResourceVo resourceVo = resourceVoMap.get(id);
                        if (null != resourceVo) {
                            ReleaseBuyRecordVo releaseBuyRecordVo = new ReleaseBuyRecordVo();
                            BeanUtils.copyProperties(resourceVo, releaseBuyRecordVo);
                            //组装私圈名称
                            if (StringUtils.isNotBlank(releaseBuyRecordVo.getCoterieId())) {
                                Response<CoterieInfo> coterieRs = coterieApi.queryCoterieInfo(Long.valueOf(releaseBuyRecordVo.getCoterieId()));
                                if (null != coterieRs && coterieRs.success() && null != coterieRs.getData()) {
                                    releaseBuyRecordVo.setCoterieName(coterieRs.getData().getName());
                                }
                            }
                            resultList.add(releaseBuyRecordVo);
                        }
                    });
                }
            }
        }
        pageList.setEntities(resultList);
        return pageList;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public PageList<ReleaseBuyRecord> pageByCondition(ReleaseBuyRecordDto releaseBuyRecordDto, boolean isCount) {
        //创建返回值对象
        PageList<ReleaseBuyRecord> pageList = new PageList<>();
        pageList.setCurrentPage(releaseBuyRecordDto.getCurrentPage());
        pageList.setPageSize(releaseBuyRecordDto.getPageSize());
        //组装分页
        Page page = PageHelper.startPage(releaseBuyRecordDto.getCurrentPage(), releaseBuyRecordDto.getPageSize(),
                isCount);
        List<ReleaseBuyRecord> list = releaseBuyRecordDao.selectByCondition(releaseBuyRecordDto);
        pageList.setEntities(list);
        pageList.setCount(page.getTotal());
        return pageList;
    }
}
