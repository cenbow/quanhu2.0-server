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
     * 分费比例(%)
     */
    private double fee;

    /**
     * 账户规则，0消费减费，1消费加费，2积分减费，3积分加费
     */
    private int type;

    public FeeDetail(String custId, double fee, int type) {
        super();
        this.custId = custId;
        this.fee = fee;
        this.type = type;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
