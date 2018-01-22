package com.yryz.quanhu.message.common.constant;

public class PushConstant {
	/** 系统角色 */
	public static final String SYSINFO = "system";
	
	/**
	 * 推送类型
	 * @author danshiyu
	 *
	 */
	public static enum PushType{
		/***
		 * 单用户推送
		 */
		BY_ALIAS("byAlias"),
		/***
		 * 批量用户推送
		 */
		BY_ALIASS("byAliass"),
		/**
		 * 单tag推送
		 */
		BY_TAG("byTag"),
		/**
		 * 批量tag推送
		 */
		BY_TAGS("byTags");
		private final String type;
		
		PushType(String type) {
			this.type = type;
		}
		public String getType()
		{
			return this.type;
		}
	}
	
	/** 栏目类型 */
	public static enum ColumnType {
		/** 系统消息 */
		SYSTEM(1),
		/** 账户与安全 */
		ACCOUNT(2),
		/** 我的动态 */
		MYTIDINGS(3),
		/** 好友动态 */
		FRIENDTIDINGS(4);

		private final int type;

		ColumnType(int type) {
			this.type = type;
		}

		public int getType() {
			return this.type;
		}
	}
	
	/**
	 * 消息类型枚举
	 * 
	 * @author xiepeng
	 */
	public static enum MessageType {
		
		/** 有人申请加我为好友 */
		FRIEND_REQUIRE(3001,"申请加好友"),
		
		/** 密码修改 */
		LOGIN_PWD_UPDATE(2008,"登录密码修改成功"),
		
		/**用户警告*/
		USER_WARN_MESSAGE(1007,"警告");
		
		private final int value;

		private final String title;
		MessageType(int value,String title) {
			this.value = value;
			this.title = title;
		}

		public int getValue() {
			return value;
		}
		public String getTitle()
		{
			return title;
		}
	}
}
