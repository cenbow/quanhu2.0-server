///**
// * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
// * All rights reserved.
// * 
// * Created on 2017年9月12日
// * Id: CallBackUtils.java, 2017年9月12日 下午2:43:53 yehao
// */
//package com.yryz.service.order.commom.utils;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.rongzhong.component.common.utils.StringUtils;
//
///**
// * @author yehao
// * @version 1.0
// * @date 2017年9月12日 下午2:43:53
// * @Description 消息回调
// */
//public class CallBackUtils {
//	
//	private static Logger logger = LoggerFactory.getLogger(CallBackUtils.class);
//	
//	private static final String SUCCESS_RESULT = "ok";
//	
//	public static void callback(String url ,String msg){
//		THREADPOOL.execute(new Runnable() {
//			@Override
//			public void run() {
//				Map<String, String> map = new HashMap<>(1);
//				map.put("msg", msg);
//				String result = HTTPWeb.post(url, map);
//				if(StringUtils.isNotEmpty(result) && StringUtils.equals(SUCCESS_RESULT, result)){
//					logger.info("order callback success ... url :" + url + "...msg" + msg);
//				} else {
//					logger.info("order callback faild ... url :" + url + "...msg" + msg);
//				}
//			}
//		});
//	}
//
//}
