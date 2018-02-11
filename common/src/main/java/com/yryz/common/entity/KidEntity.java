package com.yryz.common.entity;

import java.io.Serializable;

/**
* @author wangheng
* @date 2018年2月11日 下午3:31:53
*/
public class KidEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    
    /**  
    * @Fields : 唯一ID
    */
    private Long kid;


    public Long getKid() {
        return kid;
    }


    public void setKid(Long kid) {
        this.kid = kid;
    }
}

