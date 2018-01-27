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
import com.yryz.quanhu.dymaic.service.DymaicService;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.enums.ResourceEnum;
import com.yryz.quanhu.resource.vo.ResourceVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    DymaicService dymaicService;

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
        Response<Long> idResult = idAPI.getSnowflakeId();
        if(!idResult.success()) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        transmitInfo.setKid(idResult.getData());
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
    public PageList<TransmitInfo> list(TransmitInfoDto transmitInfoDto) {
        return  null;
    }

    private void sendDymaic(TransmitInfo transmitInfo, ResourceVo resourceVo) {
        Dymaic dymaic = new Dymaic();
        dymaic.setUserId(transmitInfo.getCreateUserId());
        dymaic.setModuleEnum(Integer.valueOf(ModuleContants.TRANSMIT));
        dymaic.setResourceId(transmitInfo.getResourceId());
        dymaic.setTransmitNote(transmitInfo.getContent());
        dymaic.setTransmitType(transmitInfo.getModuleEnum());
        dymaic.setExtJson(resourceVo.getExtJson());
//        dymaic.setShelveFlag();

        dymaicService.send(dymaic);
    }


}
