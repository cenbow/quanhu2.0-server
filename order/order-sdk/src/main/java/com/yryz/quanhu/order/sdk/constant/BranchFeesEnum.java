package com.yryz.quanhu.order.sdk.constant;

import com.google.common.collect.Lists;
import com.yryz.quanhu.order.enums.AccountEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public enum BranchFeesEnum {

    // 付费提问，分成规则
    QUESTION("QUESTION"),
    // 圈主回答，分成规则
    ANSWER("ANSWER"),
    // 未回答，分成规则
    NO_ANSWER("NO_ANSWER"),
    // 付费阅读，分成规则
    READ("READ"),
    // 付费加入私圈，分成规则
    JOIN_COTERIE("JOIN_COTERIE");

    private String constant;

    BranchFeesEnum(String constant) {
        this.constant = constant;
    }

    public List<FeeDetail> getFee() {
        List<FeeDetail> list = null;
        switch (constant) {
            case "QUESTION":
                list = getFee1();
                break;
            case "ANSWER":
                list = getFee2();
                break;
            case "NO_ANSWER":
                list = getFee3();
                break;
            case "READ":
                list = getFee4();
                break;
            case "JOIN_COTERIE":
                list = getFee5();
                break;
        }
        return list;
    }

    // 0消费减费，1消费加费，2积分减费，3积分加费
    private List<FeeDetail> getFee1() {
        FeeDetail detail1 = new FeeDetail("fromId", 100, 0);
        FeeDetail detail2 = new FeeDetail(AccountEnum.SYSID, 100, 3);
        return Lists.newArrayList(detail1, detail2);
    }

    // 0消费减费，1消费加费，2积分减费，3积分加费
    private List<FeeDetail> getFee2() {
        FeeDetail detail1 = new FeeDetail("toId", 90, 3);
        FeeDetail detail2 = new FeeDetail(AccountEnum.SYSID, 100, 2);
        FeeDetail detail3 = new FeeDetail(AccountEnum.SYSID, 10, 3);
        return Lists.newArrayList(detail1, detail2, detail3);
    }

    // 0消费减费，1消费加费，2积分减费，3积分加费
    private List<FeeDetail> getFee3() {
        FeeDetail detail1 = new FeeDetail("fromId", 100, 1);
        FeeDetail detail2 = new FeeDetail(AccountEnum.SYSID, 100, 2);
        return Lists.newArrayList(detail1, detail2);
    }

    // 0消费减费，1消费加费，2积分减费，3积分加费
    private List<FeeDetail> getFee4() {
        FeeDetail detail1 = new FeeDetail("fromId", 100, 0);
        FeeDetail detail2 = new FeeDetail("toId", 90, 3);
        FeeDetail detail3 = new FeeDetail(AccountEnum.SYSID, 10, 3);
        return Lists.newArrayList(detail1, detail2, detail3);
    }

    // 0消费减费，1消费加费，2积分减费，3积分加费
    private List<FeeDetail> getFee5() {
        FeeDetail detail1 = new FeeDetail("fromId", 100, 0);
        FeeDetail detail2 = new FeeDetail("toId", 90, 3);
        FeeDetail detail3 = new FeeDetail(AccountEnum.SYSID, 10, 3);
        return Lists.newArrayList(detail1, detail2, detail3);
    }

    public FeeDetail getFeeDetail(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        List<FeeDetail> feeList = getFee();
        if (CollectionUtils.isEmpty(feeList)) {
            return null;
        }

        for (FeeDetail fee : feeList) {
            if (StringUtils.equals(fee.getCustId(), key)) {
                return fee;
            }
        }
        return null;
    }

}
