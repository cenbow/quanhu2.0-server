package com.yryz.quanhu.message.common.remote;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.yryz.common.appinfo.AppInfoUtils;
import com.yryz.common.constant.Constants;
import com.yryz.common.response.Response;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.message.common.im.yunxin.YunxinConfig;
import com.yryz.quanhu.support.config.api.BasicConfigApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/2/7
 * @description
 */
@Service
public class YunXinConfigRemote {
    private static final Logger logger = LoggerFactory.getLogger(YunXinConfigRemote.class);

    public static final String YUNXIN_IM_CONFIG = "yunxin.im.config.";

    @Reference
    private BasicConfigApi configApi;

    public YunxinConfig getYunxinConfig() {
        YunxinConfig config = null;
        try {
            String appId = RpcContext.getContext().getAttachment(Constants.APP_ID);
            Response<String> value = configApi.getValue(YUNXIN_IM_CONFIG + appId);
            logger.info("configApi.getValue result: {}", value);
            config = GsonUtils.json2Obj(value.getData(), YunxinConfig.class);
        } catch (Exception e) {
            logger.error("getYunxinConfig error", e);
        }
        return config;
    }
}
