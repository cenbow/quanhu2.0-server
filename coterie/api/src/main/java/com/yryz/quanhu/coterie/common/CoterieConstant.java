package com.yryz.quanhu.coterie.common;
/**
 * 
 * @author jiangkun
 * @version 1.0
 * @date 2017年10月18日 上午9:44:31
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
public class CoterieConstant {
	/**
	 * 私圈状态
	 * //审批通过就是上架状态
	 */
	public static enum Status{
		/**
		 * 待审
		 */
		WAIT(0,"待审"),
		/**
		 * 未通过
		 */
		NOTPASS(2,"未通过"),
		/**
		 * 上架
		 */
		PUTON(3,"上架"),
		/**
		 * 下架
		 */
		PULLOFF(4,"下架");
		private final int status;
		private final String name;
		Status(int status,String name){
			this.status=status;
			this.name=name;
		}
		public byte getStatus() {
			return (byte)status;
		}
		public String getName() {
			return name;
		}
	}
	
	/**
	 * 推荐状态
	 */
	public static enum Recommend{
		/**
		 * 非推荐
		 */
		NO(0,"非推荐"),
		/**
		 * 推荐
		 */
		YES(1,"推荐");
		private final int status;
		private final String name;
		Recommend(int status,String name){
			this.status=status;
			this.name=name;
		}
		public byte getStatus() {
			return (byte)status;
		}
		public String getName() {
			return name;
		}
	}
	
	/**
	 * 达人状态
	 */
	public static enum Expert{
		/**
		 * 非达人
		 */
		NO(0,"非达人"),
		/**
		 * 达人
		 */
		YES(1,"达人");
		private final int status;
		private final String name;
		Expert(int status,String name){
			this.status=status;
			this.name=name;
		}
		public byte getStatus() {
			return (byte)status;
		}
		public String getName() {
			return name;
		}
	}
}
