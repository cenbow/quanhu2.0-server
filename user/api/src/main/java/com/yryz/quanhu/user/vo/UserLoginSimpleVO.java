package com.yryz.quanhu.user.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
	@JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

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
    private Byte userRole;
    
    /**
     * 认证状态 10-未认证 11-已认证
     */
    private Byte authStatus;

    /**
     * 最终热度值
     */
    private Long lastHeat;

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
     * 关系状态 {@link #UserRelationConstant.STATUS}
     */
    private Integer relationStatus;
    /**
     * 好友备注名
     */
    private String nameNotes;
    /**
     * 达人行业
     */
    private String starTradeField;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public Byte getUserRole() {
		return userRole;
	}
	public void setUserRole(Byte userRole) {
		this.userRole = userRole;
	}
	public Byte getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(Byte authStatus) {
		this.authStatus = authStatus;
	}
	public Long getLastHeat() {
		return lastHeat;
	}
	public void setLastHeat(Long lastHeat) {
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
		return nameNotes == null ? "" : nameNotes.trim();
	}
	public void setNameNotes(String nameNotes) {
		this.nameNotes = nameNotes == null ? "" : nameNotes.trim();
	}
	
	public Integer getRelationStatus() {
		return relationStatus;
	}
	public void setRelationStatus(Integer relationStatus) {
		this.relationStatus = relationStatus;
	}
	public String getStarTradeField() {
		return starTradeField;
	}
	public void setStarTradeField(String starTradeField) {
		this.starTradeField = starTradeField;
	}
	public UserLoginSimpleVO() {
		super();
	}
	public UserLoginSimpleVO(Long userId, String userNickName, String userImg, String userSignature, String userPhone,
			String userQr, Byte userAge, Byte userGenders, String userBirthday, String userLocation, String cityCode,
			Byte userRole, Byte authStatus, Long lastHeat, String userDesc, Date createDate) {
		super();
		this.userId = userId;
		this.userNickName = userNickName;
		this.userImg = userImg;
		this.userSignature = userSignature;
		this.userPhone = userPhone;
		this.userQr = userQr;
		this.userAge = userAge;
		this.userGenders = userGenders;
		this.userBirthday = userBirthday;
		this.userLocation = userLocation;
		this.cityCode = cityCode;
		this.userRole = userRole;
		this.authStatus = authStatus;
		this.lastHeat = lastHeat;
		this.userDesc = userDesc;
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "UserLoginSimpleVO [userId=" + userId + ", userNickName=" + userNickName
				+ ", userImg=" + userImg + ", userSignature=" + userSignature + ", userPhone=" + userPhone + ", userQr="
				+ userQr + ", cityCode=" + cityCode + ", userRole=" + userRole 
				+ ", authStatus=" + authStatus + ", lastHeat=" + lastHeat + ", userDesc=" + userDesc + ", createDate="
				+ createDate + ", userLevel=" + userLevel + ", userScore=" + userScore + "]";
	}
}
