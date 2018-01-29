package com.yryz.quanhu.behavior.transmit.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.transmit.dto.TransmitInfoDto;
import com.yryz.quanhu.behavior.transmit.entity.TransmitInfo;
import com.yryz.quanhu.behavior.transmit.vo.TransmitInfoVo;

public interface TransmitApi {

    /**
     * 转发
     * @param   transmitInfo
     * */
    Response single(TransmitInfo transmitInfo);

    /**
     * 转发列表
     * @param   transmitInfoDto
     * @return
     * */
    Response<PageList<TransmitInfoVo>> list(TransmitInfoDto transmitInfoDto);

}
