package com.yryz.quanhu.behavior.read.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.DateUtils;
import com.yryz.quanhu.behavior.read.api.ReadApi;
import com.yryz.quanhu.behavior.read.service.ReadService;
import com.yryz.quanhu.score.enums.EventEnum;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.vo.EventInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.behavior.read.provider
 * @Desc:
 * @Date: 2018/1/30.
 */
@Service(interfaceClass = ReadApi.class)
public class ReadProvider implements ReadApi {

    @Autowired
    private ReadService readService;

    @Reference
    private EventAPI eventAPI;

    @Override
    public Response<Object> read(Long kid, Long userId) {
        readService.read(kid);
        EventInfo eventInfo = new EventInfo();
        eventInfo.setEventCode(EventEnum.READ.getCode());
        eventInfo.setOwnerId(userId.toString());
        eventInfo.setResourceId(kid.toString());
        eventInfo.setCreateTime(DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        eventAPI.commit(eventInfo);
        return ResponseUtils.returnSuccess();
    }

}
