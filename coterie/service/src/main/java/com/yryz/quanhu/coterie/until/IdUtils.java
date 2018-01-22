package com.yryz.quanhu.coterie.until;

/**
 * id生成工具
 * @author jk
 *
 */
public class IdUtils {
	public static String randomappId() {
		String str = "0123456789abcdefghijklmnopqrstuvwxyz";

		StringBuffer custId = new StringBuffer("");

		for (int i = 0; i < 12; i++) {
			int j = (int) (35 * Math.random());
			custId.append(str.charAt(j));
		}
		return custId.toString();
	}
}
