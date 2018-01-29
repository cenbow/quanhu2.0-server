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
	private Long kid;
    /**
     * 用户账户id
     */
    private Long userId;
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
     * 年龄
     */
    private Byte userAge;

    /**
     * 用户二维码地址
     */
    private String userQr;

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
     * 用户推送设备号id
     */
    private String userDeviceId;

    /**
     * 用户状态 10-正常 11-冻结 12-黑名单
     */
    private Byte userStatus;

    /**
     * 国家
     */
    private String userCountry;

    /**
     * 省份
     */
    private String userProvince;

    /**
     * 城市
     */
    private String userCity;

    /**
     * 城市代码
     */
    private String cityCode;

    /**
     * 用户角色 10:普通用户 11:实名用户
     */
    private Byte userRole;

    /**
     * 是否马甲 10:否 11:是
     */
    private Byte userVest;

    /**
     * 认证状态 10-未认证 11-已认证
     */
    private Byte authStatus;

    /**
     * 最终热度值
     */
    private Long lastHeat;

    /**
     * 删除标识 10:有效  11:删除
     */
    private Byte delFlag;

    /**
     * 冻结最大时间点
     */
    private Date banPostTime;

    /**
     * 用户简介
     */
    private String userDesc;
    /**
     * 注册时间
     */
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
	public Long getLastHeat() {
		return lastHeat;
	}
	public void setLastHeat(Long lastHeat) {
		this.lastHeat = lastHeat;
	}
	public Byte getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Byte delFlag) {
		this.delFlag = delFlag;
	}
	public Date getBanPostTime() {
		return banPostTime;
	}
	public void setBanPostTime(Date banPostTime) {
		this.banPostTime = banPostTime;
	}
	public String getUserDesc() {
		return userDesc;
	}
	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}
	public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
	public UserBaseInfoVO(Long userId, String appId, String userNickName, String userImg, String userSignature,
			String userPhone, Byte userAge, String userQr, Byte userGenders, String userBirthday, String userLocation,
			String userDeviceId, Byte userStatus, String userCountry, String userProvince, String userCity,
			String cityCode, Byte userRole, Byte userVest, Byte authStatus, Long lastHeat, Date banPostTime,
			String userDesc, Date createDate) {
		super();
		this.userId = userId;
		this.appId = appId;
		this.userNickName = userNickName;
		this.userImg = userImg;
		this.userSignature = userSignature;
		this.userPhone = userPhone;
		this.userAge = userAge;
		this.userQr = userQr;
		this.userGenders = userGenders;
		this.userBirthday = userBirthday;
		this.userLocation = userLocation;
		this.userDeviceId = userDeviceId;
		this.userStatus = userStatus;
		this.userCountry = userCountry;
		this.userProvince = userProvince;
		this.userCity = userCity;
		this.cityCode = cityCode;
		this.userRole = userRole;
		this.userVest = userVest;
		this.authStatus = authStatus;
		this.lastHeat = lastHeat;
		this.banPostTime = banPostTime;
		this.userDesc = userDesc;
		this.createDate = createDate;
	}
	public UserBaseInfoVO() {
		super();
	}
	@Override
	public String toString() {
		return "UserBaseInfoVO [userId=" + userId + ", appId=" + appId + ", userNickName=" + userNickName + ", userImg="
				+ userImg + ", userSignature=" + userSignature + ", userPhone=" + userPhone + ", userAge=" + userAge
				+ ", userQr=" + userQr + ", userGenders=" + userGenders + ", userBirthday=" + userBirthday
				+ ", userLocation=" + userLocation + ", userDeviceId=" + userDeviceId + ", userStatus=" + userStatus
				+ ", userCountry=" + userCountry + ", userProvince=" + userProvince + ", userCity=" + userCity
				+ ", cityCode=" + cityCode + ", userRole=" + userRole + ", userVest=" + userVest + ", authStatus="
				+ authStatus + ", lastHeat=" + lastHeat + ", delFlag=" + delFlag + ", banPostTime=" + banPostTime
				+ ", userDesc=" + userDesc + ", createDate=" + createDate + "]";
	}
    
}