package com.yryz.common.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

/**
 * Long类型id主键基础接口
 */
public abstract class GenericEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -9166087599903341661L;

    //主键ID
    private Long id;

    //唯一ID
    @JsonSerialize(using = ToStringSerializer.class)
    private Long kid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

}
