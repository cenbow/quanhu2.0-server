package com.yryz.quanhu.coterie.member.constants;

/**
 * 成员常量
 * @author chengyunfei
 *
 */
public class MemberConstant {
	/**
	 * 私圈成员状态
	 */
	public static enum Status{
		/**
		 * 待审
		 */
		WAIT(30,"待审"),
		/**
		 * 未通过
		 */
		NOTPASS(20,"未通过"),
		/**
		 * 通过
		 */
		PASS(10,"通过");

		private final Integer status;
		private final String name;
		Status(Integer status,String name){
			this.status=status;
			this.name=name;
		}
		public Integer getStatus() {
			return status;
		}
		public String getName() {
			return name;
		}
	}
	
	/**
	 * 私圈成员状态
	 */
	public static enum BanSpeak{
		/**
		 * 禁言
		 */
		BANSPEAK(10,"禁言"),
		/**
		 * 未禁言
		 */
		NORMAL(20,"未禁言");

		private final Integer status;
		private final String name;
		BanSpeak(Integer status,String name){
			this.status=status;
			this.name=name;
		}
		public Integer getStatus() {
			return status;
		}
		public String getName() {
			return name;
		}
	}
	
	/**
	 * 加入方式
	 */
	public static enum JoinType {
		/**
		 * 免费
		 */
		FREE(10,"免费"),
		/**
		 * 收费
		 */
		NOTFREE(20,"收费");

		private final Integer status;
		private final String name;
		JoinType(Integer status,String name){
			this.status=status;
			this.name=name;
		}
		public Integer getStatus() {
			return status;
		}
		public String getName() {
			return name;
		}
	}

	/**
	 * 私圈成员状态
	 */
	public static enum DelFlag{
		/**
		 * 正常
		 */
		NORMAL(10,"正常"),
		/**
		 * 已删
		 */
		DELETED(11,"已删");

		private final Integer status;
		private final String name;
		DelFlag(Integer status, String name){
			this.status=status;
			this.name=name;
		}
		public Integer getStatus() {
			return status;
		}
		public String getName() {
			return name;
		}
	}

	/**
	 * 私圈成员状态
	 */
	public static enum Permission{
		/**
		 * 圈主
		 */
		OWNER(10,"圈主"),
		/**
		 * 成员
		 */
		MEMBER(20,"成员"),
		/**
		 * 路人未审核
		 */
		STRANGER_NON_CHECK(30,"路人未审核"),
		/**
		 * 路人待审核
		 */
		STRANGER_WAITING_CHECK(40,"路人待审核");

		private final Integer status;
		private final String name;
		Permission(Integer status, String name){
			this.status=status;
			this.name=name;
		}
		public Integer getStatus() {
			return status;
		}
		public String getName() {
			return name;
		}
	}
}
