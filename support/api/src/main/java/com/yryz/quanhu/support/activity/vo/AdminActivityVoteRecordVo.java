package com.yryz.quanhu.support.activity.vo;


import com.yryz.quanhu.support.activity.entity.ActivityVoteRecord;

public class AdminActivityVoteRecordVo extends ActivityVoteRecord {

    private String nickName;

    private String phone;
    private String otherNickName;
    private String other;
    private Integer voteCount;

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public String getOtherNickName() {
        return otherNickName;
    }

    public void setOtherNickName(String otherNickName) {
        this.otherNickName = otherNickName;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}