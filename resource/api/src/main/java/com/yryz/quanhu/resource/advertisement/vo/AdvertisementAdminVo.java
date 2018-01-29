package com.yryz.quanhu.resource.advertisement.vo;

import com.yryz.quanhu.resource.advertisement.entity.Advertisement;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/24 13:48
 * @Author: pn
 */
public class AdvertisementAdminVo extends Advertisement {
    /**
     * 下架栏目中，判断当前广告状态：10待开始，20进行中，30已结束
     */
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
