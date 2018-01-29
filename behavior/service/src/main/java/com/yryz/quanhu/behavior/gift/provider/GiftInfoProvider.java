package com.yryz.quanhu.behavior.gift.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.gift.api.GiftInfoApi;
import com.yryz.quanhu.behavior.gift.dto.GiftInfoDto;
import com.yryz.quanhu.behavior.gift.entity.GiftInfo;
import com.yryz.quanhu.behavior.gift.service.GiftInfoService;

/**
* @Description: 礼物 API
* @author wangheng
* @date 2018年1月26日 下午4:43:42
*/
@Service(interfaceClass = GiftInfoApi.class)
public class GiftInfoProvider implements GiftInfoApi {

    private Logger logger = LoggerFactory.getLogger(GiftInfoProvider.class);

    @Autowired
    private GiftInfoService giftInfoService;

    @Override
    public Response<PageList<GiftInfo>> pageByCondition(GiftInfoDto dto, boolean isCount) {
        try {
            return ResponseUtils.returnObjectSuccess(giftInfoService.pageByCondition(dto, isCount));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("平台发布文章异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

}
