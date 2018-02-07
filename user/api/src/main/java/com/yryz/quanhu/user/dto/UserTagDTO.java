package com.yryz.quanhu.user.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserTagDTO implements Serializable{
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 标签类型 标签类型 10-用户自选 11-运营设置达人推荐标签
	 */
	private Byte tagType;

	private Long tagId;

	private String tagName;

	/**
	 * 用户标签类型
	 *
	 */
	public enum UserTagType{
		US_SELECT((byte)11),
		OPERATION_SELECT((byte)12);
		private byte type;
		UserTagType(byte type) {
			this.type = type;
		}
		public byte getType(){
			return this.type;
		}
	}
	/**
	 * 标签id 多个以逗号隔开
	 */
	private String tagIds;
	/**
	 * 操作人id
	 */
	private String tagNames;

	private Long updateUserId;

	public String getTagNames() {
		return tagNames;
	}

	public void setTagNames(String tagNames) {
		this.tagNames = tagNames;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Byte getTagType() {
		return tagType;
	}
	public void setTagType(Byte tagType) {
		this.tagType = tagType;
	}
	public String getTagIds() {
		return tagIds;
	}
	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}
	public Long getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
}
