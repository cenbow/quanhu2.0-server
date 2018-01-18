package com.yryz.quanhu.user.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户登录以及我的页面返回vo
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class UserLoginSimpleVO implements Serializable {
	 /**
     * 用户账户id
     */
    private String userId;
    /**
     * 应用id
     */
    private String appId;

    /**
     * 昵称
     */
    private String userNickName;

    /**
     * 头像
     */
    private String userImg;

    /**
     * 用户签名
     */
    private String userSignature;

    /**
     * 用户手机号码
     */
    private String userPhone;

    /**
     * 用户二维码地址
     */
    private String userQr;
    
    /**
     * 年龄
     */
    private Byte userAge;

    /**
     * 用户性别 0-女 1-男
     */
    private Byte userGenders;

    /**
     * 出生年月日
     */
    private String userBirthday;

    /**
     * 用户城市位置(湖北武汉)
     */
    private String userLocation;

    /**
     * 城市代码
     */
    private String cityCode;

    /**
     * 用户角色 10:普通用户 11:实名用户
     */
    private Byte custRole;
    
    /**
     * 认证状态 10-未认证 11-已认证
     */
    private Byte authStatus;

    /**
     * 最终热度值
     */
    private Integer lastHeat;

    /**
     * 用户简介
     */
    private String userDesc;
    /**
     * 注册时间
     */
    private Date createDate;
    /**
     * 用户等级
     */
    private String userLevel;
    /**
     * 用户积分
     */
    private long userScore;
    
    /**
     * 好友备注名
     */
    private String nameNotes;
    /**
     * 关系类型 0-陌生人 1-关注 2-粉丝 3-好友 4-黑名单
     */
    private Integer relationStatus;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getUserSignature() {
		return userSignature;
	}
	public void setUserSignature(String userSignature) {
		this.userSignature = userSignature;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserQr() {
		return userQr;
	}
	public void setUserQr(String userQr) {
		this.userQr = userQr;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public Byte getCustRole() {
		return custRole;
	}
	public void setCustRole(Byte custRole) {
		this.custRole = custRole;
	}
	public Byte getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(Byte authStatus) {
		this.authStatus = authStatus;
	}
	public Integer getLastHeat() {
		return lastHeat;
	}
	public void setLastHeat(Integer lastHeat) {
		this.lastHeat = lastHeat;
	}
	public String getUserDesc() {
		return userDesc;
	}
	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}
	public long getUserScore() {
		return userScore;
	}
	public void setUserScore(long userScore) {
		this.userScore = userScore;
	}
	public Byte getUserAge() {
		return userAge;
	}
	public void setUserAge(Byte userAge) {
		this.userAge = userAge;
	}
	public Byte getUserGenders() {
		return userGenders;
	}
	public void setUserGenders(Byte userGenders) {
		this.userGenders = userGenders;
	}
	public String getUserBirthday() {
		return userBirthday;
	}
	public void setUserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}
	public String getUserLocation() {
		return userLocation;
	}
	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}
	public String getNameNotes() {
		return nameNotes;
	}
	public void setNameNotes(String nameNotes) {
		this.nameNotes = nameNotes;
	}
	public Integer getRelationStatus() {
		return relationStatus;
	}
	public void setRelationStatus(Integer relationStatus) {
		this.relationStatus = relationStatus;
	}
	@Override
	public String toString() {
		return "UserLoginSimpleVO [userId=" + userId + ", appId=" + appId + ", userNickName=" + userNickName
				+ ", userImg=" + userImg + ", userSignature=" + userSignature + ", userPhone=" + userPhone + ", userQr="
				+ userQr + ", cityCode=" + cityCode + ", custRole=" + custRole 
				+ ", authStatus=" + authStatus + ", lastHeat=" + lastHeat + ", userDesc=" + userDesc + ", createDate="
				+ createDate + ", userLevel=" + userLevel + ", userScore=" + userScore + "]";
	}
}
