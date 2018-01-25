package com.yryz.quanhu.coterie.member.entity;

/**
 * Created by Think on 9/18/17.
 */
public class CoterieMemberNotify {

    //当前用户ID
    Long userId;

    //私圈ID
    Long coterieId;

    //加入原因
    String reason;

    //私圈名称
    String coterieName;

    //私圈封面图
    String icon;

    //圈主
    String owner;

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCoterieName() {
        return coterieName;
    }

    public void setCoterieName(String coterieName) {
        this.coterieName = coterieName;
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

    public Long getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(Long coterieId) {
        this.coterieId = coterieId;
    }
}
