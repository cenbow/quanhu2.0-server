package com.yryz.quanhu.support.config.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.support.config.dto.BasicConfigDto;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 15:14
 * Created by huangxy
 */
public interface BasicConfigForAdminApi {

    public Response<Boolean> save(BasicConfigDto dto);

    public Response<BasicConfigDto> get(BasicConfigDto dto);

    public Response<List<BasicConfigDto>> list(BasicConfigDto dto);

    public Response<PageList<BasicConfigDto>> pages(BasicConfigDto dto);

}
