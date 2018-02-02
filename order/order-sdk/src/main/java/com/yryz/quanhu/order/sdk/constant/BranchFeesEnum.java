package com.yryz.quanhu.order.sdk.constant;

import com.google.common.collect.Lists;
import com.yryz.quanhu.order.enums.AccountEnum;
import com.yryz.quanhu.order.enums.OrderDescEnum;

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
    JOIN_COTERIE("JOIN_COTERIE"),
    //付费活动报名
    ACTIVITY_SIGNUP("ACTIVITY_SIGNUP"),
    //打赏
    REWARD("REWARD");

    private String constant;

    BranchFeesEnum(String constant) {
        this.constant = constant;
    }

    public List<FeeDetail> getFee() {
        List<FeeDetail> list = null;
        switch (constant) {
            case "QUESTION":
                list = getQuestionFee();
                break;
            case "ANSWER":
                list = getAnswerFee();
                break;
            case "NO_ANSWER":
                list = getNoAnswerFee();
                break;
            case "READ":
                list = getReadFee();
                break;
            case "JOIN_COTERIE":
                list = getJoinCoterieFee();
                break;
            case "ACTIVITY_SIGNUP":
                list = getActivitySignUpFee();
                break;
            case "REWARD":
                list = getRewardFee();
                break;
        }
        return list;
    }

    // 0消费减费，1消费加费，2积分减费，3积分加费
    private List<FeeDetail> getQuestionFee() {
        FeeDetail detail1 = new FeeDetail("fromId", 100L, 0, OrderDescEnum.COTERIE_ASK_PAY);
        FeeDetail detail2 = new FeeDetail(AccountEnum.SYSID, 100L, 3, OrderDescEnum.COTERIE_ASK_PAY);
        return Lists.newArrayList(detail1, detail2);
    }

    // 0消费减费，1消费加费，2积分减费，3积分加费
    private List<FeeDetail> getAnswerFee() {
        FeeDetail detail1 = new FeeDetail("toId", 90L, 3, OrderDescEnum.INTEGRA_ASK_ANSWER);
        FeeDetail detail2 = new FeeDetail(AccountEnum.SYSID, 100L, 2, OrderDescEnum.INTEGRA_ASK_ANSWER);
        FeeDetail detail3 = new FeeDetail(AccountEnum.SYSID, 10L, 3, OrderDescEnum.INTEGRA_ASK_ANSWER);
        return Lists.newArrayList(detail1, detail2, detail3);
    }

    // 0消费减费，1消费加费，2积分减费，3积分加费
    private List<FeeDetail> getNoAnswerFee() {
        FeeDetail detail1 = new FeeDetail("toId", 100L, 1, OrderDescEnum.COTERIE_ASK_RETURN);
        FeeDetail detail2 = new FeeDetail(AccountEnum.SYSID, 100L, 2, OrderDescEnum.COTERIE_ASK_RETURN);
        return Lists.newArrayList(detail1, detail2);
    }

    // 0消费减费，1消费加费，2积分减费，3积分加费
    private List<FeeDetail> getReadFee() {
        FeeDetail detail1 = new FeeDetail("fromId", 100L, 0, OrderDescEnum.RESOURCE_PAY);
        FeeDetail detail2 = new FeeDetail("toId", 90L, 3, OrderDescEnum.RESOURCE_CHARGE);
        FeeDetail detail3 = new FeeDetail(AccountEnum.SYSID, 10L, 3, OrderDescEnum.RESOURCE_PAY);
        return Lists.newArrayList(detail1, detail2, detail3);
    }

    // 0消费减费，1消费加费，2积分减费，3积分加费
    private List<FeeDetail> getJoinCoterieFee() {
        FeeDetail detail1 = new FeeDetail("fromId", 100L, 0, OrderDescEnum.COTERIE_JOIN);
        FeeDetail detail2 = new FeeDetail("toId", 90L, 3, OrderDescEnum.MEMBER_JOIN_COTERIE_CHARGE);
        FeeDetail detail3 = new FeeDetail(AccountEnum.SYSID, 10L, 3, OrderDescEnum.COTERIE_JOIN);
        return Lists.newArrayList(detail1, detail2, detail3);
    }

    // 0消费减费，1消费加费，2积分减费，3积分加费
    private List<FeeDetail> getActivitySignUpFee() {
        FeeDetail detail1 = new FeeDetail("fromId", 100L, 0, OrderDescEnum.ACCOUNT_ACTIVITY_APPLY);
        FeeDetail detail2 = new FeeDetail(AccountEnum.SYSID, 100L, 3, OrderDescEnum.ACCOUNT_ACTIVITY_APPLY);
        return Lists.newArrayList(detail1, detail2);
    }

    // 0消费减费，1消费加费，2积分减费，3积分加费
    private List<FeeDetail> getRewardFee() {
        FeeDetail detail1 = new FeeDetail("fromId", 100L, 0, OrderDescEnum.ACCOUNT_REWARD_ORIGINAL);
        FeeDetail detail2 = new FeeDetail("toId", 80L, 3, OrderDescEnum.INTEGRAL_REWARD);
        FeeDetail detail3 = new FeeDetail(AccountEnum.SYSID, 20L, 3, OrderDescEnum.ACCOUNT_REWARD_ORIGINAL);
        return Lists.newArrayList(detail1, detail2, detail3);
    }

}
