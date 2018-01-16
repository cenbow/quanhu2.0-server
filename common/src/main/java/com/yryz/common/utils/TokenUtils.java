/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月5日
 * Id: TokenUtils.java, 2018年1月5日 下午6:17:52 Administrator
 */
package com.yryz.common.utils;

import java.io.IOException;

/**
 * token工具
 * @author danshiyu
 * @version 1.0
 * @date 2018年1月5日 下午6:17:52
 */
public class TokenUtils {
	private static final String RM_STR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * token 生成工具
	 * 
	 * @param custId
	 * @return
	 */
	public static String constructToken(String userId) {
		StringBuffer token = new StringBuffer(userId).append("-");
		for (int i = 0; i < 12; i++) {
			int j = (int) (61 * Math.random());
			token.append(RM_STR.charAt(j));
		}
		token.append(System.currentTimeMillis());
		return token.toString();
	}
	
	/**
	 * 给token使用userId最后8位加盐
	 * @param userId
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static String getDesToken(String userId,String token) throws Exception{
		return DesUtils.encrypt(token, StringUtils.substring(userId,userId.length()-8));
	}
	
	/**
	 * 根据userId最后8位解密前端的token得到真实的token
	 * @param token
	 * @param userId
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 */
	public static String getTokenByDesToken(String token,String userId) throws IOException, Exception{
		return DesUtils.decrypt(token, StringUtils.substring(userId,userId.length()-8));
	}
}
