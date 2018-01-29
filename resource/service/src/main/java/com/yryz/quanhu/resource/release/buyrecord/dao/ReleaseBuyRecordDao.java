package com.yryz.quanhu.resource.release.buyrecord.dao;

import com.yryz.quanhu.resource.release.buyrecord.dto.ReleaseBuyRecordDto;
import com.yryz.quanhu.resource.release.buyrecord.entity.ReleaseBuyRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 17:34
 * Created by lifan
 */
@Mapper
public interface ReleaseBuyRecordDao {

    int insert(ReleaseBuyRecord releaseBuyRecord);

    List<ReleaseBuyRecord> selectByCondition(ReleaseBuyRecordDto releaseBuyRecordDto);
}
