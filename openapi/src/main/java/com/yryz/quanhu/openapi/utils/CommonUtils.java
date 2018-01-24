/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月22日
 * Id: CommonUtils.java, 2018年1月22日 下午4:23:24 yehao
 */
package com.yryz.quanhu.openapi.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;

import com.yryz.common.utils.StringUtils;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月22日 下午4:23:24
 * @Description 小工具
 */
public class CommonUtils {
	
	private static final String NUMREG = "[0-9]*";
	private static final String TOKEN = "token";

	/**
	 * 比较客户端版本号大小
	 * 
	 * @param version1
	 * @param version2
	 * @return version1 >= version2
	 */
	public static boolean compareVersion(String version1, String version2) {
		if (StringUtils.isEmpty(version1) || StringUtils.isEmpty(version2)) {
			return false;
		}
		int versionNum1 = getVersionNum(version1);
		int versionNum2 = getVersionNum(version2);
		return versionNum1 >= versionNum2;
	}

	/**
	 * 判断版本号是否是正确
	 * 
	 * @param version
	 * @return
	 */
	public static boolean isNumberByVersion(String version) {
		if (StringUtils.isEmpty(version)) {
			return false;
		}
		String versionNum = version.replaceAll("\\.", "");
		return versionNum.matches("\\d*");

	}

	/**
	 * 得到客户端版本号
	 * 
	 * @param version
	 * @return
	 */
	public static int getVersionNum(String version) {
		if (StringUtils.isEmpty(version)) {
			return 0;
		}
		String[] versionArray = version.split("\\.");
		String versionNum = version;
		if (versionArray.length > 3) {
			versionNum = versionArray[0] + versionArray[1] + versionArray[2];
		} else {
			versionNum = version.replaceAll("\\.", "");
		}

		if (isNumberByVersion(version)) {
			return Integer.parseInt(versionNum);
		} else {
			return 0;
		}
	}

	/**
	 * 判断字符串是否数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile(NUMREG);
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 根据字符串得到转发价格
	 * 
	 * @param str
	 * @return
	 */
	public static Long getClubPrice(String str) {
		if (isNumeric(str.substring(0, 1)) && isNumeric(str.substring(1, 2))) {
			return Long.parseLong(str) * 100;
		}
		if (isNumeric(str.substring(0, 1)) && !isNumeric(str.substring(1, 2))) {
			return Long.parseLong(str.substring(0, 1)) * 100;
		} else {
			return null;
		}
	}

	/**
	 * 根据token获取custId
	 * 
	 * @param token
	 * @return
	 */
	public static String getCustIdByToken(HttpServletRequest request) {
		String token = request.getHeader(TOKEN);
		if (StringUtils.isBlank(token)) {
			return null;
		}
		String[] tokenArray = token.split("-");
		if (ArrayUtils.isEmpty(tokenArray) || tokenArray.length < 2) {
			return null;
		}
		return tokenArray[0];
	}

	/**
	 * 生成图像验证码图片
	 * 
	 * @param code
	 * @param response
	 */
	public static void getSmsImgByCode(String code, HttpServletResponse response) {
		Random random = new Random();
		// 在服务器端内存中生成一个缓冲图片
		int width = 230;
		int height = 100;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 获取指向该图像上的Graphic画笔
		Graphics g = image.getGraphics();

		// 绘制填充矩形
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, width, height);
		// 绘制边框
		g.setColor(new Color(0, 0, 0));
		g.drawRect(0, 0, width - 1, height - 1);

		// 生成干扰线条
		for (int i = 0; i < 600; i++) {
			g.setColor(getColor(100, 200, random));
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			int x2 = random.nextInt(20);
			int y2 = random.nextInt(20);
			g.drawLine(x1, y1, x1 + x2, y1 + y2);
		}
		int codeLength = code.length();
		// 生成验证码
		for (int i = 0; i < codeLength; i++) {
			// 设置字体
			Font font = new Font("Times New Roman", Font.BOLD | Font.ITALIC, 80);
			g.setFont(font);
			// 设置随机颜色
			g.setColor(getColor(0, 100, random));
			g.drawString(String.valueOf(code.charAt(i)), 20 + i * 40, 60 + random.nextInt(15));
		}
		// 将服务器缓冲区中的图像输出到客户端浏览器
		try {
			ImageIO.write(image, "jpeg", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取随机颜色
	 */
	private static Color getColor(int start, int end, Random random) {
		Integer r = start + random.nextInt(end - start);
		Integer g = start + random.nextInt(end - start);
		Integer b = start + random.nextInt(end - start);

		return new Color(r, g, b);
	}

	public static String getHeaderValue(HttpServletRequest request, String name) {
		String value = request.getHeader(name);
		if (StringUtils.isBlank(value)) {
			value = request.getAttribute(name) == null ? null : request.getAttribute(name).toString();
		}
		return value;
	}

	/**
	 * 得到隐藏前面12位的银行卡号
	 * 
	 * @param bankCard
	 * @return
	 */
	public static String getBankCard(String bankCard) {
		if (StringUtils.isBlank(bankCard)) {
			return bankCard;
		}
		return String.format("**** **** **** %s", StringUtils.substring(bankCard, 12));
	}

	/**
	 * 得到隐藏年月日的的证件号
	 * 
	 * @param bankCard
	 * @return
	 */
	public static String getIdCardNo(String idCardNo) {
		if (StringUtils.isBlank(idCardNo)) {
			return idCardNo;
		}
		return String.format("%s********%s", StringUtils.substring(idCardNo, 0, 6),
				StringUtils.substring(idCardNo, 14));
	}
	
	/**
	 * 得到隐藏姓氏的真实姓名
	 * @param realName
	 * @return
	 */
	public static String getRealName(String realName){
		if (StringUtils.isBlank(realName)) {
			return realName;
		}
		return String.format("*%s", StringUtils.substring(realName, 1));
	}
	
	/**
	 * 三大运营商手机号匹配正则表达式
	 */
	private static final String PHONE_REGEX = "^(13[0-9]|15[012356789]|16[6]|17[0135678]|18[0-9]|14[56789]|19[89])[0-9]{8}$";

	/**
	 * 验证手机号码是否有效
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean checkPhone(String phone) {
		if (StringUtils.isBlank(phone)) {
			return false;
		}
		Pattern pattern = Pattern.compile(PHONE_REGEX);
		Matcher matcher = pattern.matcher(phone);
		return matcher.matches();
	}

	/**
	 * 得到中间4位隐藏的手机号
	 * 
	 * @param phone
	 * @return
	 */
	public static String getPhone(String phone) {
		if (StringUtils.isBlank(phone)) {
			return phone;
		}
		return String.format("%s****%s", StringUtils.substring(phone, 0, 3), StringUtils.substring(phone, 7));
	}

}
