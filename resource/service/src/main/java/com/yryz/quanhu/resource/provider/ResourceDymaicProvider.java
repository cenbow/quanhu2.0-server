package com.yryz.quanhu.resource.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.resource.api.ResourceDymaicApi;
import com.yryz.quanhu.resource.enums.ResourceEnum;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoApi;
import com.yryz.quanhu.resource.release.info.provider.ReleaseInfoProvider;
import com.yryz.quanhu.resource.vo.ResourceTotal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.resource.provider
 * @Desc:
 * @Date: 2018/1/27.
 */
@Service(interfaceClass = ResourceDymaicApi.class)
public class ResourceDymaicProvider implements ResourceDymaicApi {

    private Logger logger = LoggerFactory.getLogger(ResourceDymaicProvider.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Response<Object> commitResourceDymaic(ResourceTotal resourceTotal) {
        if (StringUtils.isEmpty(resourceTotal.getIntimate())) {
            resourceTotal.setIntimate(ResourceEnum.INTIMATE_FALSE);
        }
        if (resourceTotal.getPrice() == null || resourceTotal.getPrice() < 0) {
            resourceTotal.setPrice(0L);
        }
        String msg = GsonUtils.parseJson(resourceTotal);
        logger.debug("commitResourceDymaic msg :" + msg);
        rabbitTemplate.setExchange("RESOURCE_DYNAMIC_FANOUT_EXCHANGE");
        rabbitTemplate.convertAndSend(msg);
        logger.debug("commitResourceDymaic msg resourceId:" + resourceTotal.getResourceId());
        return ResponseUtils.returnSuccess();
    }
}
