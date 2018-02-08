package com.yryz.quanhu.support.id.bufferedid.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrimaryUtils {
	private static final Logger logger = LoggerFactory.getLogger(PrimaryUtils.class);

	public static final String PRIMARY_ORDER = "primary_order";

	/** 第十五校验位的权重，由14个数字组成 */
	private static final int[] WEIGHT = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9,
			10, 5 };
	/** 第十五校验位的校验码，由11个数字组成,按照模11的方式获取 */
	private static final char[] VALIDATE = { '1', '0', '5', '9', '8', '7', '6',
			'5', '4', '3', '2' };
	/** seq对应的订单ID 14位数 */
	private static final int NUM = 14;
	/** 除数：用于内部校验码生成 */
	private static final int MOD = 11;

	public static long getNextId(String primaryKey) {
		// 获取步长除数
		long modConfig = GenIdConstant.MOD_INC_DEFAULT;
		// 获取步长
		int inc = (int) (System.currentTimeMillis() % modConfig + 1);
		// 获取前14位
		long seq = GenPrimaryKeyFactory.nextPrimaryKey(primaryKey, inc);

		// 获取内部校验位
//		char char014[] = CharUtils.longTochars(seq14, new char[NUM]);
//		char char015 = getValidateCode(char014);
		// 拼接出数据库保存的订单ID
//		char char15[] = CharUtils.charsAdd(char014, char015);


		return seq;
	}

	/**
	 * 获取内部校验码，以char的形式返回
	 * 
	 * @param char14
	 * @return char 校验码 （数字型字符）
	 */
	public static char getValidateCode(char char14[]) {
		int sum = 0;
		int mode = 0;
		for (int i = 0; i < char14.length; i++) {
			sum = sum + Integer.parseInt(String.valueOf(char14[i])) * WEIGHT[i];
		}
		mode = sum % MOD;
		return VALIDATE[mode];
	}

}
