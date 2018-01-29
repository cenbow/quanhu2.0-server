package com.yryz.quanhu.support.config.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.support.config.dto.BasicConfigDto;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 15:54
 * Created by huangxy
 */
public interface BasicConfigService {

    public String getValue(String key);

    public Map<String,String> getValues(String key);

    public Boolean save(BasicConfigDto dto);

    public Boolean updateKeyStatus(BasicConfigDto dto);

    public BasicConfigDto get(BasicConfigDto dto);

    public List<BasicConfigDto> list(BasicConfigDto dto);

    public PageList<BasicConfigDto> pages(BasicConfigDto dto);

}
