package com.yryz.quanhu.dymaic.vo;

import java.io.Serializable;

/**
 * 动态基础信息
 *
 * @author xiepeng
 * @version 1.0
 * @data 2018/1/19 0019 29
 */
public class Dymaic implements Serializable {

    /**
     * 动态ID
     */
    private Long kid;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 资源类型模块ID
     * 1000私圈,1001用户,1002转发,1003文章,1004话题,1005帖子,1006问题,1007答案
     */
    private String moduleEnum;

    /**
     * 资源ID
     */
    private Long resourceId;

    /**
     * 转发理由
     */
    private String transmitNote;

    /**
     * 转发资源类型模块ID
     * 1000私圈,1001用户,1002转发,1003文章,1004话题,1005帖子,1006问题,1007答案
     */
    private String transmitType;

    /**
     * 文章、话题、私圈等标准json扩展属性
     */
    private String extJson;

    /**
     * 下架标记
     * 10正常，11已下架
     */
    private Integer shelveFlag;

    /**
     * 删除标记
     * 10正常，11已删除
     */
    private Integer delFlag;

    /**
     * 发布时间
     */
    private String createDate;


    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getTransmitNote() {
        return transmitNote;
    }

    public void setTransmitNote(String transmitNote) {
        this.transmitNote = transmitNote;
    }

    public String getTransmitType() {
        return transmitType;
    }

    public void setTransmitType(String transmitType) {
        this.transmitType = transmitType;
    }

    public String getExtJson() {
        return extJson;
    }

    public void setExtJson(String extJson) {
        this.extJson = extJson;
    }

    public Integer getShelveFlag() {
        return shelveFlag;
    }

    public void setShelveFlag(Integer shelveFlag) {
        this.shelveFlag = shelveFlag;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
