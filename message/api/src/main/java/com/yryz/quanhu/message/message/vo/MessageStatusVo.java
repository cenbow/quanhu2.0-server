package com.yryz.quanhu.message.message.vo;

import java.io.Serializable;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/22 17:48
 * @Author: pn
 */
public class MessageStatusVo implements Serializable {

    public MessageStatusVo() {
    }

    public MessageStatusVo(Boolean flag) {
        this.flag = flag;
    }

    /**
     * 返回状态标记
     */
    private Boolean flag;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

}
