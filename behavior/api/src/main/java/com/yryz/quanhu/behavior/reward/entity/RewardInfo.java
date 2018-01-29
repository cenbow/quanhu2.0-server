package com.yryz.quanhu.behavior.reward.entity;

import com.yryz.common.entity.GenericEntity;

/**
 * @author wangheng
 * @table qh_reward
*/
public class RewardInfo extends GenericEntity {

    private static final long serialVersionUID = 1L;

    /**
    * 对应受赏价值（分）（分费后金额）
    */
    private Long rewardPrice;

    /**
    * 打赏状态（10:待支付，11:支付成功）
    */
    private Byte rewardStatus;

    /**
    * 礼物ID
    */
    private Long giftId;

    /**
    * 礼物数量
    */
    private Integer giftNum;

    /**
    * 礼物价值（分）
    */
    private Long giftPrice;

    /**
    * 订单ID
    */
    private Long orderId;

    /**
    * 资源功能枚举
    */
    private String moduleEnum;

    /**
    * 资源id
    */
    private Long resourceId;

    /**
    * 私圈id
    */
    private Long coterieId;

    /**
    * 被打赏人
    */
    private Long toUserId;

    /**
    * 租户id
    */
    private String appId;

    /**
    * 版本号
    */
    private Integer revision;

    public Long getRewardPrice() {
        return rewardPrice;
    }

    public void setRewardPrice(Long rewardPrice) {
        this.rewardPrice = rewardPrice;
    }

    public Byte getRewardStatus() {
        return rewardStatus;
    }

    public void setRewardStatus(Byte rewardStatus) {
        this.rewardStatus = rewardStatus;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public Integer getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(Integer giftNum) {
        this.giftNum = giftNum;
    }

    public Long getGiftPrice() {
        return giftPrice;
    }

    public void setGiftPrice(Long giftPrice) {
        this.giftPrice = giftPrice;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(Long coterieId) {
        this.coterieId = coterieId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }
}