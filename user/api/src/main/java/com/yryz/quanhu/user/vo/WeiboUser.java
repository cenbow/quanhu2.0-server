/*
Copyright (c) 2007-2009, Yusuke Yamamoto
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
 * Neither the name of the Yusuke Yamamoto nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * A data class representing Basic user information element
 * 
 * @author xiepeng
 */
@SuppressWarnings("serial")
public class WeiboUser implements Serializable {
	/**
	 * 错误码 0-成功 其他失败
	 */
	private int error_code = 0;
	/**
	 * 用户UID
	 */
	private String id;
	/**
	 * 微博昵称
	 */
	private String screen_name;
	/**
	 * 友好显示名称，如Bill Gates,名称中间的空格正常显示(此特性暂不支持)
	 */
	private String name;
	/**
	 * 省份编码（参考省份编码表）
	 */
	private int province;
	/**
	 * 城市编码（参考城市编码表）
	 */
	private int city;
	/**
	 * 地址
	 */
	private String location;
	/**
	 * 个人描述
	 */
	private String description;
	/**
	 * 用户博客地址
	 */
	private String url;
	/**
	 * 自定义图像
	 */
	private String profile_image_url;
	/**
	 * 性别,m--男，f--女,n--未知
	 */
	private String gender;
	/**
	 * 粉丝数
	 */
	private int followersCount;
	/**
	 * 关注数
	 */
	private int friendsCount;
	/**
	 * 微博数
	 */
	private int statusesCount;
	/**
	 * 收藏数
	 */
	private int favouritesCount;
	/**
	 * 备注信息，在查询用户关系时提供此字段。
	 */
	private String remark;

	public int getError_code() {
		return error_code;
	}

	public void setError_code(int error_code) {
		this.error_code = error_code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProvince() {
		return province;
	}

	public void setProvince(int province) {
		this.province = province;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProfile_image_url() {
		return profile_image_url;
	}

	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(int followersCount) {
		this.followersCount = followersCount;
	}

	public int getFriendsCount() {
		return friendsCount;
	}

	public void setFriendsCount(int friendsCount) {
		this.friendsCount = friendsCount;
	}

	public int getStatusesCount() {
		return statusesCount;
	}

	public void setStatusesCount(int statusesCount) {
		this.statusesCount = statusesCount;
	}

	public int getFavouritesCount() {
		return favouritesCount;
	}

	public void setFavouritesCount(int favouritesCount) {
		this.favouritesCount = favouritesCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "WeiboUser [error_code=" + error_code + ", id=" + id + ", screen_name=" + screen_name + ", name=" + name
				+ ", province=" + province + ", city=" + city + ", location=" + location + ", description="
				+ description + ", url=" + url + ", profile_image_url=" + profile_image_url + ", gender=" + gender
				+ ", followersCount=" + followersCount + ", friendsCount=" + friendsCount + ", statusesCount="
				+ statusesCount + ", favouritesCount=" + favouritesCount + ", remark=" + remark + "]";
	}

}
