package com.yryz.quanhu.coterie.member.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chengyunfei
 */
public class CoterieMemberVoForJoin implements Serializable{

	/**
     * 订单ID(收费时才返回，其它情况不返回)
     */
    private Long orderId;
    
    /**
     * 审请加入时返回的状态(收费:10 免费不审核：20， 免费要审核时：30，私圈人数已满 40)
     */
    private Byte status;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
