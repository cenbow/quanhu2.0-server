package com.yryz.quanhu.resource.release.buyrecord.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.resource.release.buyrecord.api.ReleaseBuyRecordApi;
import com.yryz.quanhu.resource.release.buyrecord.dto.ReleaseBuyRecordDto;
import com.yryz.quanhu.resource.release.buyrecord.service.ReleaseBuyRecordService;
import com.yryz.quanhu.resource.release.buyrecord.vo.ReleaseBuyRecordVo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 17:57
 * Created by lifan
 */
@Service(interfaceClass = ReleaseBuyRecordApi.class)
public class ReleaseBuyRecordProvider implements ReleaseBuyRecordApi {

    @Autowired
    private ReleaseBuyRecordService releaseBuyRecordService;

    @Override
    public Response<PageList<ReleaseBuyRecordVo>> listPage(ReleaseBuyRecordDto releaseBuyRecordDto) {
        return ResponseUtils.returnObjectSuccess(releaseBuyRecordService.selectByCondition(releaseBuyRecordDto));
    }
}
