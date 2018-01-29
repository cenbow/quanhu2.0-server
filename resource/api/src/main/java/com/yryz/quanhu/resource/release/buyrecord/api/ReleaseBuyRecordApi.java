package com.yryz.quanhu.resource.release.buyrecord.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.release.buyrecord.dto.ReleaseBuyRecordDto;
import com.yryz.quanhu.resource.release.buyrecord.entity.ReleaseBuyRecord;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 17:34
 * Created by lifan
 */
public interface ReleaseBuyRecordApi {

    Response<PageList<ReleaseBuyRecord>> listPage(ReleaseBuyRecordDto releaseBuyRecordDto);
}
