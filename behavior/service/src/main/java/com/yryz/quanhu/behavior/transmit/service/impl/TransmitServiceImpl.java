package com.yryz.quanhu.behavior.transmit.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.behavior.transmit.dao.TransmitMongoDao;
import com.yryz.quanhu.behavior.transmit.dto.TransmitInfoDto;
import com.yryz.quanhu.behavior.transmit.entity.TransmitInfo;
import com.yryz.quanhu.behavior.transmit.service.TransmitService;
import com.yryz.quanhu.behavior.transmit.vo.TransmitInfoVo;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.api.ResourceDymaicApi;
import com.yryz.quanhu.resource.enums.ResourceEnum;
import com.yryz.quanhu.resource.vo.ResourceTotal;
import com.yryz.quanhu.resource.vo.ResourceVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransmitServiceImpl implements TransmitService {

    private static final Logger logger = LoggerFactory.getLogger(TransmitServiceImpl.class);

    @Reference
    IdAPI idAPI;

    @Autowired
    TransmitMongoDao transmitMongoDao;

    @Reference
    CountApi countApi;

    @Reference
    ResourceApi resourceApi;

    @Reference
    ResourceDymaicApi resourceDymaicApi;

    @Reference
    UserApi userApi;

    /**
     * 转发
     * @param   transmitInfo
     * */
    public void single(TransmitInfo transmitInfo) {
        Response<ResourceVo> result = resourceApi.getResourcesById(transmitInfo.getResourceId().toString());
        if(!result.success()) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        ResourceVo resourceVo = result.getData();
        if(resourceVo == null || ResourceEnum.DEL_FLAG_TRUE.equals(resourceVo.getDelFlag())) {
            throw QuanhuException.busiError("资源不存在或者已删除");
        }
        //发送动态
        this.sendDymaic(transmitInfo, resourceVo);
        Response<Long> idResult = idAPI.getSnowflakeId();
        if(!idResult.success()) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        transmitInfo.setKid(idResult.getData());
        transmitInfo.setCreateDate(new Date());
        transmitInfo.setCreateDateLong(transmitInfo.getCreateDate().getTime());
        //保存转发记录
        transmitMongoDao.save(transmitInfo);
        try {
            //递增收藏数
            countApi.commitCount(BehaviorEnum.Transmit, transmitInfo.getParentId(), null, 1L);
        } catch (Exception e) {
            logger.error("递增转发数 失败", e);
        }
    }

    /**
     * 转发列表
     * @param   transmitInfoDto
     * @return
     * */
    public PageList<TransmitInfoVo> list(TransmitInfoDto transmitInfoDto) {
        List<TransmitInfoVo> resultList = new ArrayList<>();
        Query query = new Query();
        query.addCriteria(Criteria.where("parentId").is(transmitInfoDto.getParentId()));
        query.addCriteria(Criteria.where("moduleEnum").is(transmitInfoDto.getModuleEnum()));
        long count = transmitMongoDao.count(query);
        if(count > 0) {
            query.with(new Sort(Sort.Direction.DESC, "createDateLong"));
            List<TransmitInfo> list = transmitMongoDao.findPage(transmitInfoDto.getCurrentPage(), transmitInfoDto.getPageSize(), query);
            if(!CollectionUtils.isEmpty(list)) {
                resultList = list.stream().map(transmitInfo -> {
                    TransmitInfoVo transmitInfoVo = new TransmitInfoVo();
                    BeanUtils.copyProperties(transmitInfo, transmitInfoVo);
                    return transmitInfoVo;
                }).collect(Collectors.toList());
                this.setUserInfo(resultList);
            }
        }
        PageList<TransmitInfoVo> pageList = new PageList<>();
        pageList.setCurrentPage(transmitInfoDto.getCurrentPage());
        pageList.setPageSize(transmitInfoDto.getPageSize());
        pageList.setEntities(resultList);
        pageList.setCount(count);
        return pageList;
    }

    private void sendDymaic(TransmitInfo transmitInfo, ResourceVo resourceVo) {
        ResourceTotal resourceTotal = new ResourceTotal();
        resourceTotal.setUserId(transmitInfo.getCreateUserId());
        resourceTotal.setModuleEnum(Integer.valueOf(ModuleContants.TRANSMIT));
        resourceTotal.setResourceId(transmitInfo.getResourceId());
        resourceTotal.setExtJson(resourceVo.getExtJson());
        resourceTotal.setTransmitNote(transmitInfo.getContent());
        resourceTotal.setTransmitType(transmitInfo.getModuleEnum());

        try {
            resourceDymaicApi.commitResourceDymaic(resourceTotal);
        } catch (Exception e) {
            logger.error("发送动态 失败", e);
        }
    }

    private void setUserInfo(List<TransmitInfoVo> list) {
        Set<String> set = new HashSet<>();
        list.stream()
                .filter(transmitInfo -> transmitInfo.getCreateUserId() != null)
                .forEach(transmitInfo -> set.add(transmitInfo.getCreateUserId().toString()));
        Response<Map<String, UserSimpleVO>> result = userApi.getUserSimple(set);
        if(result.success()) {
            Map<String, UserSimpleVO> simple = result.getData();
            list.stream()
                    .filter(transmitInfo -> transmitInfo.getCreateUserId() != null)
                    .forEach(transmitInfo -> transmitInfo.setUser(simple.get(transmitInfo.getCreateUserId().toString())));
        }
    }

}
