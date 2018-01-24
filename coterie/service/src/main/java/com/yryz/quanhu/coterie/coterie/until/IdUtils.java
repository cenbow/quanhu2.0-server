package com.yryz.quanhu.coterie.coterie.until;

/**
 * id生成工具
 * @author jk
 *
 */
public class IdUtils {
	public static String randomappId() {
		String str = "123456789";
		StringBuffer custId = new StringBuffer("");
		for (int i = 0; i < 10; i++) {
			int j = (int) (9 * Math.random());
			custId.append(str.charAt(j));
		}
		return custId.toString();
	}
}
