/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年10月12日
 * Id: RequstLog.java, 2017年10月12日 下午4:08:25 yehao
 */
package com.yryz.quanhu.support.requestlog.entity;

import java.io.Serializable;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年10月12日 下午4:08:25
 * @Description 业务请求实体
 */
public class RequestLog implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2440108941412534333L;

	/**
	 * 请求ID
	 */
	private String requestId;
	
	/**
	 * 请求URL
	 */
	private String uri;
	
	/**
	 * 请求IP
	 */
	private String requestIp;
	
	/**
	 * 远端名字
	 */
	private String remoteName;
	
	/**
	 * 请求方法
	 */
	private String method;
	
	/**
	 * 请求参数
	 */
	private String request;
	
	/**
	 * 请求header
	 */
	private String header;
	
	/**
	 * 请求时间，格林时间，long
	 */
	private Long startTime;
	
	/**
	 * 结束时间，格林时间，endTime
	 */
	private Long endTime;
	
	/**
	 * 请求耗时，单位：毫秒
	 */
	private Long countTime;
	
	/**
	 * 请求结果
	 */
	private String responseBody;
	
	/**
	 * 异常信息
	 */
	private String errorMessage;
	
	/**
	 * 请求时间,字符串
	 */
	private String requestTime;
	
	/**
	 * 
	 * @exception 
	 */
	public RequestLog() {
		super();
	}
	
	/**
	 * @param requestId
	 * @param uri
	 * @param requestIp
	 * @param remoteName
	 * @param method
	 * @param request
	 * @param header
	 * @param startTime
	 * @param endTime
	 * @param countTime
	 * @exception 
	 */
	public RequestLog(String requestId, String uri, String requestIp, String remoteName, String method, String request,
                      String header, long startTime, long endTime, long countTime) {
		super();
		this.requestId = requestId;
		this.uri = uri;
		this.requestIp = requestIp;
		this.remoteName = remoteName;
		this.method = method;
		this.request = request;
		this.header = header;
		this.startTime = startTime;
		this.endTime = endTime;
		this.countTime = countTime;
	}



	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the requestIp
	 */
	public String getRequestIp() {
		return requestIp;
	}

	/**
	 * @param requestIp the requestIp to set
	 */
	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	/**
	 * @return the remoteName
	 */
	public String getRemoteName() {
		return remoteName;
	}

	/**
	 * @param remoteName the remoteName to set
	 */
	public void setRemoteName(String remoteName) {
		this.remoteName = remoteName;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the request
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(String request) {
		this.request = request;
	}

	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * @return the startTime
	 */
	public Long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Long getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the countTime
	 */
	public Long getCountTime() {
		return countTime;
	}

	/**
	 * @param countTime the countTime to set
	 */
	public void setCountTime(Long countTime) {
		this.countTime = countTime;
	}
	
	/**
	 * @return the responseBody
	 */
	public String getResponseBody() {
		return responseBody;
	}

	/**
	 * @param responseBody the responseBody to set
	 */
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the requestTime
	 */
	public String getRequestTime() {
		return requestTime;
	}

	/**
	 * @param requestTime the requestTime to set
	 */
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	
}
