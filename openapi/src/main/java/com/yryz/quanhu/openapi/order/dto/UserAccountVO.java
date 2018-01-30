/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018年1月23日
 * Id: UserAccountDTO.java, 2018年1月23日 上午11:13:16 yehao
 */
package com.yryz.quanhu.openapi.order.dto;

import java.io.Serializable;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月23日 上午11:13:16
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
public class UserAccountVO implements Serializable {

    private static final long serialVersionUID = -7028133316947050556L;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 账户余额
     */
    private Long accountSum;
    /**
     * 积分余额
     */
    private Long integralSum;
    /**
     * 累计余额
     */
    private Long costSum;
    /**
     * 是否开通小额免密，0，不开通；1，开通
     */
    private Integer smallNopass;
    /**
     * 账户状态
     */
    private Integer accountState;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getAccountSum() {
        return accountSum;
    }

    public void setAccountSum(Long accountSum) {
        this.accountSum = accountSum;
    }

    public Long getIntegralSum() {
        return integralSum;
    }

    public void setIntegralSum(Long integralSum) {
        this.integralSum = integralSum;
    }

    public Long getCostSum() {
        return costSum;
    }

    public void setCostSum(Long costSum) {
        this.costSum = costSum;
    }

    public Integer getSmallNopass() {
        return smallNopass;
    }

    public void setSmallNopass(Integer smallNopass) {
        this.smallNopass = smallNopass;
    }

    public Integer getAccountState() {
        return accountState;
    }

    public void setAccountState(Integer accountState) {
        this.accountState = accountState;
    }

    @Override
    public String toString() {
        return "UserAccount [userId=" + userId + ", accountSum=" + accountSum + ", integralSum=" + integralSum
                + ", costSum=" + costSum + ", accountState=" + accountState + "]";
    }

}
