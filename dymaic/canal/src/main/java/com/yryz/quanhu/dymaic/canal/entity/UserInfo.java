package com.yryz.quanhu.dymaic.canal.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 用户搜索实体
 * 
 * @author jk
 */
@Document(indexName = "quanhu-v2", type = "userInfo", refreshInterval = "-1")
public class UserInfo implements Serializable {
	private static final long serialVersionUID = -2312110729335920029L;
	@Field(type=FieldType.Long)
	private Long kid;
	/**
	 * 用户账户id
	 */
	@Id
	private Long userId;

	/**
	 * 应用id
	 */
	@Field(type=FieldType.text)
	private String appId;

	/**
	 * 昵称
	 */
	@Field(type=FieldType.text)
	private String userNickName;

	/**
	 * 头像
	 */
	@Field(type=FieldType.text)
	private String userImg;

	/**
	 * 用户签名
	 */
	@Field(type=FieldType.text)
	private String userSignature;

	/**
	 * 用户手机号码
	 */
	@Field(type=FieldType.text)
	private String userPhone;

	/**
	 * 年龄
	 */
	@Field(type=FieldType.Integer)
	private Byte userAge;

	/**
	 * 用户二维码地址
	 */
	@Field(type=FieldType.text)
	private String userQr;

	/**
	 * 用户性别 0-女 1-男
	 */
	@Field(type=FieldType.Integer)
	private Byte userGenders;

	/**
	 * 出生年月日
	 */
	@Field(type=FieldType.text)
	private String userBirthday;

	/**
	 * 用户城市位置(湖北武汉)
	 */
	@Field(type=FieldType.text)
	private String userLocation;

	/**
	 * 用户推送设备号id
	 */
	@Field(type=FieldType.text)
	private String userDeviceId;

	/**
	 * 用户状态 10-正常 11-冻结 12-黑名单
	 */
	@Field(type=FieldType.Integer)
	private Byte userStatus;

	/**
	 * 国家
	 */
	@Field(type=FieldType.text)
	private String userCountry;

	/**
	 * 省份
	 */
	@Field(type=FieldType.text)
	private String userProvince;

	/**
	 * 城市
	 */
	@Field(type=FieldType.text)
	private String userCity;

	/**
	 * 城市代码
	 */
	@Field(type=FieldType.text)
	private String cityCode;

	/**
	 * 用户角色 10:普通用户 11:实名用户
	 */
	@Field(type=FieldType.Integer)
	private Byte userRole;

	/**
	 * 是否马甲 10:否 11:是
	 */
	@Field(type=FieldType.Integer)
	private Byte userVest;

	/**
	 * 认证状态 10-未认证 11-已认证
	 */
	@Field(type=FieldType.Integer)
	private Byte authStatus;

	/**
	 * 最终热度值
	 */
	@Field(type=FieldType.Integer)
	private Integer lastHeat;

	/**
	 * 删除标识 10:有效 11:删除
	 */
	@Field(type=FieldType.Integer)
	private Byte delFlag;

	/**
	 * 冻结最大时间点
	 */
	@Field(type=FieldType.Date)
	private Date banPostTime;

	/**
	 * 用户简介
	 */
	@Field(type=FieldType.text)
	private String userDesc;
	/**
	 * 注册时间
	 */
	@Field(type=FieldType.Date)
	private Date createDate;

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

	public Byte getUserAge() {
		return userAge;
	}

	public void setUserAge(Byte userAge) {
		this.userAge = userAge;
	}

	public String getUserQr() {
		return userQr;
	}

	public void setUserQr(String userQr) {
		this.userQr = userQr;
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

	public String getUserDeviceId() {
		return userDeviceId;
	}

	public void setUserDeviceId(String userDeviceId) {
		this.userDeviceId = userDeviceId;
	}

	public Byte getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Byte userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserCountry() {
		return userCountry;
	}

	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}

	public String getUserProvince() {
		return userProvince;
	}

	public void setUserProvince(String userProvince) {
		this.userProvince = userProvince;
	}

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
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

	public Byte getUserVest() {
		return userVest;
	}

	public void setUserVest(Byte userVest) {
		this.userVest = userVest;
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

	public Byte getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Byte delFlag) {
		this.delFlag = delFlag;
	}

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public Date getBanPostTime() {
		return banPostTime;
	}

	public void setBanPostTime(Date banPostTime) {
		this.banPostTime = banPostTime;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
