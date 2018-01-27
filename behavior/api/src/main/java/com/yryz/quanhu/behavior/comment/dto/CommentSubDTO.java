package com.yryz.quanhu.behavior.comment.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yryz.common.response.PageList;

import java.io.Serializable;

/**
 * @Author:sun
 * @version:2.0
 * @Description:举报
 * @Date:Created in 11:57 2018/1/24
 */
public class CommentSubDTO extends PageList implements Serializable {
    private static final long serialVersionUID = 5278107300199813679L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long kid;

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }
}
