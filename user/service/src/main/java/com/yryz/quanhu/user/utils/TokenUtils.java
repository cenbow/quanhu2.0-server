package com.yryz.quanhu.user.utils;

import java.io.IOException;

import com.yryz.common.utils.DesUtils;
import com.yryz.common.utils.StringUtils;

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

	public static void main(String[] args) throws Exception {
		String rr = getDesToken("738729736354512896","738729736354512896-Y6UPq6o15W6L1517305815638");
		System.out.println(rr);
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
