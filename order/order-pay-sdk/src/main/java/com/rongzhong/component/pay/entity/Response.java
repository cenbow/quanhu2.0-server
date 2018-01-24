package com.rongzhong.component.pay.entity;


/**
 * 回复结果
 * @author Administrator
 *
 */
public class Response {
	public static final int SUCCESS = 0;  // 成功
	public static final int FAILURE = 1;  // 失败
	public static final int VERIFY_FAILURE = 2; // 校验签名失败
	public static final int PROCESSING = 3; // 请求正在处理中
	public static final int EXCEPTION = 4; // 异常
	
	private int result = -1;
	private String message;
	private String sn; // 交易流水号
	
	public Response() {
	}

	public Response(int result, String message, String sn) {
		super();
		this.result = result;
		this.message = message;
		this.sn = sn;
	}
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	
}
