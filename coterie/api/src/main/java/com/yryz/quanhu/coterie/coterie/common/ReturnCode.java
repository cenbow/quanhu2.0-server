package com.yryz.quanhu.coterie.coterie.common;

/**
 * api通用状态返回
 * @author Pxie
 *
 */
public class ReturnCode {
		public final static int UNLOGIN = -1;   //未登录
		public final static int SUCCESS = 1;    //成功
		public final static int WARN = 2;       //后端提醒
		public final static int ERROR = 3;      //系统错误
		public final static int TOKEN_OUT = 4;  //登录超时
		public final static int SIGN_ERROR = 5; //加密签名错误
	
		public final static String SUCCESSMSG = "success";
		public final static String FAILMSG = "fail";
		public final static String PARAMETERERROR = "参数缺少或者不对";
		
	 	private Integer ret = 1; 
 	 	private String msg = "success";  //消息   原因
	 	private Object data;        //响应数据

	 	public ReturnCode() {
		}
	 	public ReturnCode(int ret) {
			this.ret = ret;
		}
	 	public ReturnCode(int ret, Object o) {
	 		this.ret = ret;
	 		data=o;
		}
	 	public ReturnCode(int ret, String msg) {
			this.ret = ret;
			this.msg = msg;
		}
	 	public ReturnCode(int ret, Object data,int act) {
			this.ret = ret;
			this.data = data;
		}
		public ReturnCode(int ret, int act) {
			super();
			this.ret = ret;
		}
		public ReturnCode(int ret, String msg, Object data) {
			this.ret = ret;
			this.msg = msg;
			this.data = data;
		}
		
		public Integer getRet() {
			return ret;
		}
		public void setRet(Integer ret) {
			this.ret = ret;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public Object getData() {
			return data;
		}
		public void setData(Object data) {
			this.data = data;
		}
		
}
