package com.yryz.quanhu.support.id.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.support.id.service.DefaultUidService;
import com.yryz.quanhu.support.id.service.IIdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/12/8
 * @description
 */
@Service
public class IdAPIImpl implements IdAPI {

    private static final Logger logger = LoggerFactory.getLogger(IdAPIImpl.class);

    /**
     * 基于本地DB发号缓冲池的发号服务
     */
    @Autowired
    @Qualifier("idService")
    private IIdService idService;

    /**
     * 基于Twitter的分布式自增ID服务
     */
    @Autowired
    private DefaultUidService uidService;

    @Override
    public Response<String> getId(String type) {
        String orderId = idService.getId(type);
        try {
            return ResponseUtils.returnObjectSuccess(orderId);
        } catch (Exception e) {
            logger.error("getId error", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Long> getKid(String type) {
        try {
            return ResponseUtils.returnObjectSuccess(idService.getKid(type));
        } catch (Exception e) {
            logger.error("getKid error", e);
            return ResponseUtils.returnException(e);
        }
    }

    /**
     * 基于Twitter的分布式自增ID服务
     *
     */

    @Override
    public Response<String> getUserId() {
        try {
            String userId = String.valueOf(uidService.getUID());
            return ResponseUtils.returnObjectSuccess(userId);
        } catch (Exception e) {
            logger.error("getUserId error", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Long> getSnowflakeId() {
        try {
            return ResponseUtils.returnObjectSuccess(uidService.getUID());
        } catch (Exception e) {
            logger.error("getSnowflakeId error", e);
            return ResponseUtils.returnException(e);
        }
    }
}
