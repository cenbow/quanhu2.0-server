package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * 第三方用户信息
 * 
 * @author Pxie
 *
 */
@SuppressWarnings("serial")
public class ThirdUser implements Serializable {
	/**
	 * 第三方唯一id
	 */
	private String thirdId;
	private String token;
	/**
	 * 第三方昵称
	 */
	private String nickName;
	/**
	 * 第三方头像
	 */
	private String headImg;
	/**
	 * 性别
	 */
	private String gender;
	private String location;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getThirdId() {
		return thirdId;
	}

	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}

	@Override
	public String toString() {
		return "ThirdUser [thirdId=" + thirdId + ", token=" + token + ", nickName=" + nickName + ", headImg=" + headImg
				+ ", gender=" + gender + ", location=" + location + "]";
	}

}
