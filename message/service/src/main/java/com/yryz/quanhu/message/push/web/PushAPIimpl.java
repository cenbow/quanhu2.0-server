package com.yryz.quanhu.message.push.web;


import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.message.push.api.PushAPI;
import com.yryz.quanhu.message.push.entity.PushReceived;
import com.yryz.quanhu.message.push.entity.PushReqVo;
import com.yryz.quanhu.message.push.entity.PushReqVo.*;
import com.yryz.quanhu.message.push.service.PushService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年8月10日 下午3:16:59
 * @Description 推送
 */
@Service(filter = {"appInfoFilter"})
public class PushAPIimpl implements PushAPI {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PushService pushService;

    @Override
    public Response<Boolean> commonSendAlias(PushReqVo reqVo) {
        if (reqVo == null || reqVo.getPushType() == null || StringUtils.isBlank(reqVo.getMsg())
                ) {
            return ResponseUtils.returnCommonException("reqVo pushType msg can not be null");
        }
        if ((reqVo.getPushType() == CommonPushType.BY_ALIAS
                || reqVo.getPushType() == CommonPushType.BY_ALIASS)
                && CollectionUtils.isEmpty(reqVo.getCustIds())) {
            return ResponseUtils.returnCommonException("custIds can not be null");
        }
        if ((reqVo.getPushType() == CommonPushType.BY_ALIAS_REGISTRATIONID
                || reqVo.getPushType() == CommonPushType.BY_REGISTRATIONID
                || reqVo.getPushType() == CommonPushType.BY_REGISTRATIONIDS) && CollectionUtils.isEmpty(reqVo.getRegistrationIds())) {
            return ResponseUtils.returnCommonException("registrationIds can not be null");
        }
        if ((reqVo.getPushType() == CommonPushType.BY_TAG || reqVo.getPushType() == CommonPushType.BY_TAGS)
                && CollectionUtils.isEmpty(reqVo.getTags())) {
            return ResponseUtils.returnCommonException("tags can not be null");
        }
        try {
            if (reqVo.getNotifyStatus() == null) {
                reqVo.setNotifyStatus(false);
            }
            if (reqVo.getMsgStatus() == null) {
                reqVo.setMsgStatus(true);
            }
            pushService.commonSendAlias(reqVo);
            return ResponseUtils.returnSuccess();
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("[push message]", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<List<PushReceived>> getReceived(List<String> msgIds) {
        if (CollectionUtils.isEmpty(msgIds)) {
            return ResponseUtils.returnCommonException("msgIds can not be null");
        }
        try {
            return ResponseUtils.returnObjectSuccess(pushService.getPushReceived(msgIds));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("[getReceived]", e);
            return ResponseUtils.returnException(e);
        }
    }

}
