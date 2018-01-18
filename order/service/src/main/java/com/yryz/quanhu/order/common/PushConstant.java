package com.yryz.quanhu.order.common;

/**
 * 推送常量
 * 
 * @author danshiyu
 *
 */
public class PushConstant {
	/**
	 * 系统
	 */
	public static final String SYSINFO = "system";

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
	 * 消息类型
	 * 
	 * @author danshiyu
	 *
	 */
	public static enum MessageType {
		/**
		 * 充值
		 */
		CHARGE(2004, "充值成功"),
		/**
		 * 积转消
		 */
		POINTS_ACCOUNT(2006, "账户兑换成功"),
		/** 提现*/
		CASH(2007, "提现成功"),
		/**
		 * 支付密码修改
		 */
		PAY_PWD_UPDATE(2009,"支付密码安全提醒");
		
		private final int value;

		private final String title;

		MessageType(int value, String title) {
			this.value = value;
			this.title = title;
		}

		public int getValue() {
			return value;
		}

		public String getTitle() {
			return title;
		}
	}
}
