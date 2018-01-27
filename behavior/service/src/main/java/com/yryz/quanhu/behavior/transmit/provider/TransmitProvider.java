package com.yryz.quanhu.behavior.transmit.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.transmit.api.TransmitApi;
import com.yryz.quanhu.behavior.transmit.dto.TransmitInfoDto;
import com.yryz.quanhu.behavior.transmit.entity.TransmitInfo;
import com.yryz.quanhu.behavior.transmit.service.TransmitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass=TransmitProvider.class)
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
            return ResponseUtils.returnObjectSuccess(null);
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
    public Response<PageList<TransmitInfo>> list(TransmitInfoDto transmitInfoDto) {
        try {
            return ResponseUtils.returnObjectSuccess(null);
        } catch (Exception e) {
            logger.error("转发列表 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

}