package com.yryz.quanhu.message.entity;

/**
 * 圈子状态
 * @author xiepeng
 *
 */
public class CircleStatus {
	
	/** 上线*/
	public static final int ONLINE = 0;
	
	/** 等待上线 */
	public static final int WAIT = 1;
	
	/** 未上线  (default)*/
	public static final int SUSPEND = 2;
	
	/** 下线 */
	public static final int OFFLINE = 3;
	
	/** 已删除*/
	public static final int DELETE = 4;
	
	/**
	 * @author danshiyu
	 * @version 1.0
	 * @date 2017年8月10日 下午6:42:06
	 * @Description 推送状态
	 */
	public enum PushStatus{
		/** 开启 */
		OPEN(0),
		/** 关闭 */
		CLOSE(1);
		private int status;
		
		PushStatus(int status) {
			this.status = status;
		}
		
		public int getStatus(){
			return this.status;
		}
	}
	
	/**
	 * 
	 * @author danshiyu
	 * @version 1.0
	 * @date 2017年8月10日 下午6:51:08
	 * @Description 短信开关
	 */
	public enum SmsStatus{
		/** 开启 */
		OPEN(0),
		/** 关闭 */
		CLOSE(1);
		private int status;
		
		SmsStatus(int status) {
			this.status = status;
		}
		
		public int getStatus(){
			return this.status;
		}
	}
	
	/**
	 * 
	 * @author danshiyu
	 * @version 1.0
	 * @date 2017年8月10日 下午6:51:08
	 * @Description 应用类型
	 */
	public enum AppType{
		/** 内部 */
		INSIDE(0),
		/** 外部 */
		OUT(1);
		private int type;
		
		AppType(int type) {
			this.type = type;
		}
		
		public int getType(){
			return this.type;
		}
	}
}
