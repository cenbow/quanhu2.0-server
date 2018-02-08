package com.yryz.quanhu.support.id.bufferedid.service.impl;

import com.yryz.quanhu.support.id.bufferedid.common.PrimaryUtils;
import com.yryz.quanhu.support.id.bufferedid.service.IIdService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/12/8
 * @description
 */
@Service("idService")
public class IdServiceImpl implements IIdService {
    @Override
    public String getOrderId(String type) {
        long id = PrimaryUtils.getNextId(type);
        String head = DateFormatUtils.format(new Date(), "yyyyMMdd");
        return head + id;
    }

    @Override
    public Long getKid(String type) {

        return PrimaryUtils.getNextId(type);
    }

    @Override
    public String getUserId() {
        return null;
    }
}
