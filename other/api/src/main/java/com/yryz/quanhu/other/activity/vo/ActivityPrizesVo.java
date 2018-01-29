package com.yryz.quanhu.other.activity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.Date;

public class ActivityPrizesVo implements Serializable {

    /**
     * id
     * */
    private Long id;

    /**
     * 唯一di
     * */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long kid;

    /**
     * 活动id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private  Long activityInfoId;

    /**
     * 奖品名称
     */
    private  String prizesName;

    /**
     * 奖品类型（1投票卷 2自定义奖品）
     */
    private  Integer prizesType;

    /**
     * 发放张数
     */
    private  Integer issueNum;

    /**
     * 可使用次数
     */
    private  Integer canNum;

    /**
     * 奖品数值
     */
    private  Integer prizesNum;

    /**
     * 数值单位
     */
    private  String prizesUnit;

    /**
     * 使用开始时间
     */
    private Date beginTime;

    /**
     * 使用结束时间
     */
    private  Date endTime;

    /**
     * 使用说明
     */
    private  String remark;

    /**
     * 排序
     */
    private  Integer sort;

    /**
     * 创建时间
     * */
    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public Long getActivityInfoId() {
        return activityInfoId;
    }

    public void setActivityInfoId(Long activityInfoId) {
        this.activityInfoId = activityInfoId;
    }

    public String getPrizesName() {
        return prizesName;
    }

    public void setPrizesName(String prizesName) {
        this.prizesName = prizesName;
    }

    public Integer getPrizesType() {
        return prizesType;
    }

    public void setPrizesType(Integer prizesType) {
        this.prizesType = prizesType;
    }

    public Integer getIssueNum() {
        return issueNum;
    }

    public void setIssueNum(Integer issueNum) {
        this.issueNum = issueNum;
    }

    public Integer getCanNum() {
        return canNum;
    }

    public void setCanNum(Integer canNum) {
        this.canNum = canNum;
    }

    public Integer getPrizesNum() {
        return prizesNum;
    }

    public void setPrizesNum(Integer prizesNum) {
        this.prizesNum = prizesNum;
    }

    public String getPrizesUnit() {
        return prizesUnit;
    }

    public void setPrizesUnit(String prizesUnit) {
        this.prizesUnit = prizesUnit;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
