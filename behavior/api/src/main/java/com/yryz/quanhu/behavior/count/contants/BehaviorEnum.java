package com.yryz.quanhu.behavior.count.contants;

import com.google.common.collect.Lists;
import com.yryz.common.utils.StringUtils;

import java.util.List;

/**
 * @Author: liupan
 * @Path: BehaviorEnum
 * @Desc: 行为枚举类
 * @Date: 2018/1/23
 */
public enum BehaviorEnum {
    Comment("10", "commentCount", "评论"),
    Like("11", "likeCount", "点赞"),
    Read("12", "readCount", "浏览"),
    Transmit("13", "transmitCount", "转发"),
    Reward("14", "rewardCount", "打赏"),
    Collection("15", "collectionCount", "收藏"),
    Share("16", "shareCount", "分享"),
    Report("17", "reportCount", "举报"),
    Release("18", "releaseCount", "发布数"),
    Coterie("19", "coterieCount", "私圈数"),
    Activity("20", "activityCount", "活动数"),
    RealRead("21", "realReadCount", "真实浏览数");

    private String code;

    private String key;

    private String desc;

    BehaviorEnum(String code, String key, String desc) {
        this.code = code;
        this.key = key;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
