package com.yryz.quanhu.user.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户基础信息表
 * @author suyongcheng
 * @date 2017年11月09日
 */
@SuppressWarnings("serial")
public class UserBaseInfoVO implements Serializable{

    /**
     * 用户账户id
     */
    private String userId;
    /**
     * 昵称
     */
    private String custNickName;
    /**
     * 头像
     */
    private String custImg;
    /**
     * 用户签名
     */
    private String custSignature;
    /**
     * 年龄
     */
    private int custAge;
    /**
     * 用户简介
     */
    private String custDesc;
    /**
     * 用户二维码地址
     */
    private String custQr;
    /**
     * 用户性别 0-女 1-男
     */
    private int custGenders;
    /**
     * 用户城市位置(湖北武汉)
     */
    private String custLocation;
    /**
     * 用户推送设备号id
     */
    private String custDeviceId;
    /**
     * 城市代码
     */
    private String cityCode;
    /**
     * 用户角色 0:普通用户 1:实名用户
     */
    private int custRole;
    /**
     * 删除标识 0:有效  1:删除
     */
    private int delFlag;
    /**
     * 创建时间
     */
    private Date createDate;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserNickName() {
        return custNickName;
    }
    public void setUserNickName(String custNickName) {
        this.custNickName = custNickName;
    }
    public String getUserImg() {
        return custImg;
    }
    public void setUserImg(String custImg) {
        this.custImg = custImg;
    }
    public String getUserSignature() {
        return custSignature;
    }
    public void setUserSignature(String custSignature) {
        this.custSignature = custSignature;
    }
    public int getUserAge() {
        return custAge;
    }
    public void setUserAge(int custAge) {
        this.custAge = custAge;
    }
    public String getUserDesc() {
        return custDesc;
    }
    public void setUserDesc(String custDesc) {
        this.custDesc = custDesc;
    }
    public String getUserQr() {
        return custQr;
    }
    public void setUserQr(String custQr) {
        this.custQr = custQr;
    }
    public int getUserGenders() {
        return custGenders;
    }
    public void setUserGenders(int custGenders) {
        this.custGenders = custGenders;
    }
    public String getUserLocation() {
        return custLocation;
    }
    public void setUserLocation(String custLocation) {
        this.custLocation = custLocation;
    }
    public String getUserDeviceId() {
        return custDeviceId;
    }
    public void setUserDeviceId(String custDeviceId) {
        this.custDeviceId = custDeviceId;
    }
    public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public int getUserRole() {
        return custRole;
    }
    public void setUserRole(int custRole) {
        this.custRole = custRole;
    }
    public int getDelFlag() {
        return delFlag;
    }
    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}