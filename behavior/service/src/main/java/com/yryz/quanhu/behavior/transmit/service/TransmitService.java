package com.yryz.quanhu.behavior.transmit.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.behavior.transmit.dto.TransmitInfoDto;
import com.yryz.quanhu.behavior.transmit.entity.TransmitInfo;

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
    PageList<TransmitInfo> list(TransmitInfoDto transmitInfoDto);

}
