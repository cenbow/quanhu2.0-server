package com.yryz.quanhu.resource.release.buyrecord.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.resource.release.buyrecord.dao.ReleaseBuyRecordDao;
import com.yryz.quanhu.resource.release.buyrecord.dto.ReleaseBuyRecordDto;
import com.yryz.quanhu.resource.release.buyrecord.entity.ReleaseBuyRecord;
import com.yryz.quanhu.resource.release.buyrecord.service.ReleaseBuyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 17:34
 * Created by lifan
 */
@Service
@Transactional
public class ReleaseBuyRecordServiceImpl implements ReleaseBuyRecordService {

    @Autowired
    private ReleaseBuyRecordDao releaseBuyRecordDao;

    @Override
    public int insert(ReleaseBuyRecord releaseBuyRecord) {
        return releaseBuyRecordDao.insert(releaseBuyRecord);
    }

    @Override
    public PageList<ReleaseBuyRecord> selectByCondition(ReleaseBuyRecordDto releaseBuyRecordDto) {
        if (null == releaseBuyRecordDto) {
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "参数不能为空");
        }
        if (null == releaseBuyRecordDto.getUserId()) {
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "用户ID不能为空");
        }
        if (null == releaseBuyRecordDto.getPageNo()) {
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "页码不能为空");
        }
        if (null == releaseBuyRecordDto.getPageSize()) {
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "每页条数不能为空");
        }
        Page<ReleaseBuyRecord> page = PageHelper.startPage(releaseBuyRecordDto.getPageNo(), releaseBuyRecordDto.getPageSize());
        PageList<ReleaseBuyRecord> pageList = new PageList<>();
        pageList.setCurrentPage(releaseBuyRecordDto.getPageNo());
        pageList.setPageSize(releaseBuyRecordDto.getPageSize());
        pageList.setCount(page.getTotal());
        pageList.setEntities(releaseBuyRecordDao.selectByCondition(releaseBuyRecordDto));
        return pageList;
    }
}
