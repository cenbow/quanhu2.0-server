package com.yryz.quanhu.resource.release.buyrecord.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.resource.release.buyrecord.dto.ReleaseBuyRecordDto;
import com.yryz.quanhu.resource.release.buyrecord.entity.ReleaseBuyRecord;
import com.yryz.quanhu.resource.release.buyrecord.vo.ReleaseBuyRecordVo;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 17:34
 * Created by lifan
 */
public interface ReleaseBuyRecordService {

    int insert(ReleaseBuyRecord releaseBuyRecord);

    PageList<ReleaseBuyRecordVo> selectByCondition(ReleaseBuyRecordDto releaseBuyRecordDto);
}
