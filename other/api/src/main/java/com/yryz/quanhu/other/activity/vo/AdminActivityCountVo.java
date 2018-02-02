package com.yryz.quanhu.other.activity.vo;

import java.io.Serializable;

public class AdminActivityCountVo implements Serializable {

    private String date;

    private Integer detailCount;

    private Integer candidateDetailCount;

    private Integer totalNo;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getDetailCount() {
        return detailCount;
    }

    public void setDetailCount(Integer detailCount) {
        this.detailCount = detailCount;
    }

    public Integer getCandidateDetailCount() {
        return candidateDetailCount;
    }

    public void setCandidateDetailCount(Integer candidateDetailCount) {
        this.candidateDetailCount = candidateDetailCount;
    }

    public Integer getTotalNo() {
        return totalNo;
    }

    public void setTotalNo(Integer totalNo) {
        this.totalNo = totalNo;
    }

}
