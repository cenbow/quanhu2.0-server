package com.yryz.quanhu.behavior.transmit.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.transmit.api.TransmitApi;
import com.yryz.quanhu.behavior.transmit.dto.TransmitInfoDto;
import com.yryz.quanhu.behavior.transmit.entity.TransmitInfo;
import com.yryz.quanhu.behavior.transmit.service.TransmitService;
import com.yryz.quanhu.behavior.transmit.vo.TransmitInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@Service(interfaceClass=TransmitApi.class)
public class TransmitProvider implements TransmitApi {

    private static final Logger logger = LoggerFactory.getLogger(TransmitProvider.class);

    @Autowired
    private TransmitService transmitService;

    /**
     * 转发
     * @param   transmitInfo
     * */
    public Response single(TransmitInfo transmitInfo) {
        try {
            Assert.notNull(transmitInfo.getModuleEnum(), "moduleEnum不能为空");
            Assert.notNull(transmitInfo.getParentId(), "parentId不能为空");
            Assert.notNull(transmitInfo.getResourceId(), "resourceId不能为空");
            Assert.notNull(transmitInfo.getTargetUserId(), "targetUserId不能为空");
            transmitService.single(transmitInfo);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("转发 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

    /**
     * 转发列表
     * @param   transmitInfoDto
     * @return
     * */
    public Response<PageList<TransmitInfoVo>> list(TransmitInfoDto transmitInfoDto) {
        try {
            Assert.notNull(transmitInfoDto.getModuleEnum(), "moduleEnum不能为空");
            Assert.notNull(transmitInfoDto.getParentId(), "parentId不能为空");
            return ResponseUtils.returnObjectSuccess(transmitService.list(transmitInfoDto));
        } catch (Exception e) {
            logger.error("转发列表 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

    /**
     * 更新上下架状态
     * @param   transmitId      transmitInfo.kid
     * @param   shelvesFlag     上下架状态：10上架  11下架
     * */
    public Response<Integer> updateShelvesFlag(Long transmitId, Integer shelvesFlag) {
        try {
            Assert.notNull(transmitId, "transmitId不能为空");
            return ResponseUtils.returnObjectSuccess(transmitService.updateShelvesFlag(transmitId, shelvesFlag));
        } catch (Exception e) {
            logger.error("更新上下架状态 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

    /**
     * 删除转发记录
     * @param   transmitId
     * */
    public Response<Integer> removeTransmit(Long transmitId) {
        try {
            Assert.notNull(transmitId, "transmitId不能为空");
            return ResponseUtils.returnObjectSuccess(transmitService.removeTransmit(transmitId));
        } catch (Exception e) {
            logger.error("删除转发记录 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

}
