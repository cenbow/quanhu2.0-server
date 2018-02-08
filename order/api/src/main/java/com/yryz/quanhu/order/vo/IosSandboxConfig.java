package com.yryz.quanhu.order.vo;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/8 16:13
 * Created by lifan
 */
public class IosSandboxConfig implements Serializable {
    private static final long serialVersionUID = 8756823255263812313L;

    public static final int OPEN = 1;

    private int openFlag = 0;
    private String userId;

    public int getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(int openFlag) {
        this.openFlag = openFlag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
