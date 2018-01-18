package com.yryz.quanhu.support.id.common;

public class CharUtils {

	/**
	 * 将long值转换为字符数组，不足位数用0填充<br/>
	 * eg : <br/>
	 * 1、value = 123 chars[5] ,返回值为：00123的字符数组<br/>
	 * 2、value = 123 chars[2] ,返回值为：23的字符数组<br/>
	 * 
	 * @param value
	 *            long值
	 * @param chars
	 *            字符数组
	 * @return 字符数组
	 */
	public static char[] longTochars(long value, char[] chars) {
		for (int i = 0; i < chars.length; i++) {
			chars[i] = '0';
		}
		char[] values = String.valueOf(value).toCharArray();
		int calc = 0;
		for (int i = values.length - 1; i >= 0; i--) {
			chars[chars.length - calc - 1] = values[i];
			if (chars.length - calc - 1 == 0) {
				break;
			}
			calc++;
		}
		return chars;
	}

	/**
	 * chars后添加add字符<br/>
	 * eg: chars：123 ， add：4 ；返回 1234
	 * 
	 * @param chars
	 *            原始字符数组
	 * @param add
	 *            需要添加的字符
	 * @return chars 添加字符后的字符数组
	 */
	public static char[] charsAdd(char[] chars, char add) {
		char[] result = new char[chars.length + 1];
		for (int i = 0; i < chars.length; i++) {
			result[i] = chars[i];
		}
		result[result.length - 1] = add;
		return result;
	}

	/**
	 * chars后添加add字符<br/>
	 * eg: chars：123 ， add：45 ；返回 12345
	 * 
	 * @param chars
	 *            原始字符数组
	 * @param add
	 *            需要添加的字符数组
	 * @return chars 添加字符后的字符数组
	 */
	public static char[] charsAdd(char[] chars, char[] adds) {
		char[] result = new char[chars.length + adds.length];
		for (int i = 0; i < chars.length; i++) {
			result[i] = chars[i];
		}
		for (int i = 0; i < adds.length; i++) {
			result[chars.length + i] = adds[i];
		}
		return result;
	}

	/**
	 * 前提chars都是数字，否则会抛转化异常<br/>
	 * eg: chars：000123 ；返回 long 123
	 * 
	 * @param chars
	 *            原始字符数组
	 * @return long 字符数组对应的数值
	 */
	public static long charsToLong(char[] chars) {
		StringBuilder sBuilder = new StringBuilder();
		boolean firstZero = true;
		for (int i = 0; i < chars.length; i++) {
			if (Integer.parseInt(chars[i] + "") == 0) {
				if (firstZero) {
					continue;
				} else {
					sBuilder.append(chars[i]);
				}
			} else {
				sBuilder.append(chars[i]);
				firstZero = false;
			}
		}
		long result = 0;
		if (sBuilder.length() > 0) {
			result = Long.parseLong(sBuilder.toString());
		}
		return result;
	}
}
