/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月18日
 * Id: NotifyParam.java, 2017年9月18日 上午10:50:17 yehao
 */
package com.yryz.quanhu.order.notify;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年9月18日 上午10:50:17
 * @Description 回调参数管理
 */
public class NotifyParam {
	
	private static Map<Integer, Integer> notifyParams;
	
	private static final String SUCCESS_VALUE = "OK";
	
	static {
		notifyParams = new HashMap<>();
		notifyParams.put(1, 0);
		notifyParams.put(2, 5);
		notifyParams.put(3, 5);
		notifyParams.put(4, 10);
		notifyParams.put(5, 10);
		notifyParams.put(6, 60);
		notifyParams.put(7, 60);
		notifyParams.put(8, 60);
		notifyParams.put(9, 600);
		notifyParams.put(10, 600);
	}
	
	public static String getSuccessValue(){
		return SUCCESS_VALUE;
	}
	
	/**
	 * 获取当前间隔的
	 * @param times
	 * @return
	 */
	public static Integer getNotifyTime(int times){
		return notifyParams.get(times);
	}

}
