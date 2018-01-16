package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * 微信用户信息获取接口返回对象
 * 
 * @author xiepeng
 *
 */
@SuppressWarnings("serial")
public class WxUser implements Serializable {
	/**
	 * 错误码 0-成功 其他失败
	 */
	private int errcode = 0;
	/**
	 * 错误描述
	 */
	private String errmsg;
	/**
	 * 用户的标识，对当前公众号唯一
	 */
	private String openid;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	private Integer sex;

	private String city;

	private String province;

	private String country;
	/**
	 * 头像
	 */
	private String headimgurl;
	/**
	 * 多个公众号之间用户帐号互通UnionID机制
	 */
	private String unionid;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	@Override
	public String toString() {
		return "WxUser [errcode=" + errcode + ", errmsg=" + errmsg + ", openid=" + openid + ", nickname=" + nickname
				+ ", sex=" + sex + ", city=" + city + ", province=" + province + ", country=" + country
				+ ", headimgurl=" + headimgurl + ", unionid=" + unionid + "]";
	}

}
