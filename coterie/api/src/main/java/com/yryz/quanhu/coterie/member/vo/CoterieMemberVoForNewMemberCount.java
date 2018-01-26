package com.yryz.quanhu.coterie.member.vo;

import java.io.Serializable;

/**
 * @author chengyunfei
 */
public class CoterieMemberVoForNewMemberCount implements Serializable {

    /**
     * 新审请的成员数
     */
    private Integer count;

    public CoterieMemberVoForNewMemberCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

