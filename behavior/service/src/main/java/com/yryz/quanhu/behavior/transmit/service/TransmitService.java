package com.yryz.quanhu.behavior.transmit.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.behavior.transmit.dto.TransmitInfoDto;
import com.yryz.quanhu.behavior.transmit.entity.TransmitInfo;
import com.yryz.quanhu.behavior.transmit.vo.TransmitInfoVo;

public interface TransmitService {

    /**
     * 转发
     * @param   transmitInfo
     * */
    void single(TransmitInfo transmitInfo);

    /**
     * 转发列表
     * @param   transmitInfoDto
     * @return
     * */
    PageList<TransmitInfoVo> list(TransmitInfoDto transmitInfoDto);

    /**
     * 更新上下架状态
     * @param   transmitId      transmitInfo.kid
     * @param   shelvesFlag     上下架状态：10上架  11下架
     * */
    Integer updateShelvesFlag(Long transmitId, Integer shelvesFlag);

    /**
     * 删除转发记录
     * @param   transmitId
     * */
    Integer removeTransmit(Long transmitId);

}
