package com.yryz.quanhu.coterie.member.vo;

/**
 *
 * @author chengyunfei
 *
 */
public class CoterieMemberNotifyVo {

    //当前用户ID
    Long userId;

    //私圈ID
    String coterieId;

    //加入原因
    String reason;

    //私圈名称
    String coterieName;

    //私圈封面图
    String icon;

    //圈主
    Long ownerId;

    //支付金额
    Long amount;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCoterieName() {
        return coterieName;
    }

    public void setCoterieName(String coterieName) {
        this.coterieName = coterieName;
    }

    public String getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(String coterieId) {
        this.coterieId = coterieId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
