package com.yryz.quanhu.order.sdk.constant;

/**
 * 分费规则
 *
 * @author admin
 */
public class FeeDetail {

    /**
     * 用户ID
     */
    private String custId;

    /**
     * 分费占比
     */
    private Long fee;

    /**
     * 账户规则，0消费减费，1消费加费，2积分减费，3积分加费
     */
    private int type;

    /**
     * 分费描述
     */
    private String feeDesc;

    public FeeDetail(String custId, Long fee, int type, String feeDesc) {
        super();
        this.custId = custId;
        this.fee = fee;
        this.type = type;
        this.feeDesc = feeDesc;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFeeDesc() {
        return feeDesc;
    }

    public void setFeeDesc(String feeDesc) {
        this.feeDesc = feeDesc;
    }
}
