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
	public static enum MemberStatus{
		/**
		 * 待审
		 */
		WAIT((byte)30,"待审"),
		/**
		 * 未通过
		 */
		NOTPASS((byte)20,"未通过"),
		/**
		 * 通过
		 */
		PASS((byte)10,"通过");

		private final Byte status;
		private final String name;
		MemberStatus(Byte status,String name){
			this.status=status;
			this.name=name;
		}
		public Byte getStatus() {
			return status;
		}
		public String getName() {
			return name;
		}
	}

	/**
	 * 私圈踢出状态
	 */
	public static enum KickStatus{
		/**
		 * 被踢
		 */
		KICKED((byte)10,"被踢"),
		/**
		 * 未通过
		 */
		NORMAL((byte)20,"未踢");

		private final Byte status;
		private final String name;
		KickStatus(Byte status,String name){
			this.status=status;
			this.name=name;
		}
		public Byte getStatus() {
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
		BANSPEAK((byte)10,"禁言"),
		/**
		 * 未禁言
		 */
		NORMAL((byte)20,"未禁言");

		private final Byte status;
		private final String name;
		BanSpeak(Byte status,String name){
			this.status=status;
			this.name=name;
		}
		public Byte getStatus() {
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
		FREE((byte)10,"免费"),
		/**
		 * 收费
		 */
		NOTFREE((byte)20,"收费");

		private final Byte status;
		private final String name;
		JoinType(Byte status,String name){
			this.status=status;
			this.name=name;
		}
		public Byte getStatus() {
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
		NORMAL((byte)10,"正常"),
		/**
		 * 已删
		 */
		DELETED((byte)11,"已删");

		private final Byte status;
		private final String name;
		DelFlag(Byte status, String name){
			this.status=status;
			this.name=name;
		}
		public Byte getStatus() {
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
		OWNER((byte)10,"圈主"),
		/**
		 * 成员
		 */
		MEMBER((byte)20,"成员"),
		/**
		 * 路人未审核
		 */
		STRANGER_NON_CHECK((byte)30,"路人未审核"),
		/**
		 * 路人待审核
		 */
		STRANGER_WAITING_CHECK((byte)40,"路人待审核");

		private final Byte status;
		private final String name;
		Permission(Byte status, String name){
			this.status=status;
			this.name=name;
		}
		public Byte getStatus() {
			return status;
		}
		public String getName() {
			return name;
		}
	}
}
