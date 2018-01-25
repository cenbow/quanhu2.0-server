package com.yryz.quanhu.behavior.count.contants;

/**
 * @Author: liupan
 * @Path: BehaviorEnum
 * @Desc: 行为枚举类
 * @Date: 2018/1/23
 */
public enum BehaviorEnum {
    Comment("10", "commentCount", false, "评论"),
    Like("11", "likeCount", true, "点赞"),
    Read("12", "readCount", true, "浏览"),
    Transmit("13", "transmitCount", false, "转发"),
    Reward("14", "rewardCount", false, "打赏"),
    Collection("15", "collectionCount", false, "收藏"),
    Share("16", "shareCount", true, "分享"),
    Report("17", "reportCount", false, "举报"),
    Release("18", "releaseCount", false, "发布数"),
    Coterie("19", "coterieCount", false, "私圈数"),
    Activity("20", "activityCount", false, "活动数"),
    RealRead("21", "realReadCount", true, "真实浏览数");

    private String code;

    private String key;

    /**
     * 是否走一级缓存。true走一级缓存，会导致数据延迟更新，适用于并发量大场景。
     * false不走一级缓存，实时更新redis，可以实时得到真实数据，适用于并发低，准确度高的场景
     */
    private boolean memoryFlag;

    private String desc;

    BehaviorEnum(String code, String key, boolean memoryFlag, String desc) {
        this.code = code;
        this.key = key;
        this.memoryFlag = memoryFlag;
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

    public boolean getMemoryFlag() {
        return memoryFlag;
    }

    public void setMemoryFlag(boolean memoryFlag) {
        this.memoryFlag = memoryFlag;
    }
}
