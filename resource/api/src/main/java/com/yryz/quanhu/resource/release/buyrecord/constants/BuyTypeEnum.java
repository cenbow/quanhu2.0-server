package com.yryz.quanhu.resource.release.buyrecord.constants;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/29 14:36
 * Created by lifan
 */
public enum BuyTypeEnum {
    RELEASE(10, "私圈文章付费购买");
    private Integer buyType;
    private String remark;

    BuyTypeEnum(Integer buyType, String remark) {
        this.buyType = buyType;
        this.remark = remark;
    }

    public Integer getBuyType() {
        return buyType;
    }

    public String getRemark() {
        return remark;
    }
}
