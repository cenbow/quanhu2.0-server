package com.yryz.quanhu.message.notice.utils;

import com.yryz.common.context.Context;
import com.yryz.common.utils.DateUtils;
import com.yryz.quanhu.message.notice.constants.NoticeConstants;
import com.yryz.quanhu.message.notice.entity.Notice;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdvertUtil {
	private static Logger logger = Logger.getLogger(AdvertUtil.class);

	public static final String GG = "GG";

	/**
	 * 生成广告编号
	 * 
	 * @return
	 */
	/*public static String getAdsCode() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(GG).append(DateUtils.formatYMdHmsDateTime(new Date()));

		buffer.append(UUIDUtil.getThreeRandomNum());

		return buffer.toString();
	}*/

	/**
	 * 验证用机号码
	 * 
	 * @param msisdn
	 * @return
	 */
	public static boolean validateMsisdn(String msisdn) {

		Pattern p1 = Pattern.compile("(\\+86|86)?(13[0-9]|14[57]|15[0-3]|15[5-9]|17[01678]|18[0-9])\\d{8}(,)?");

		Matcher m1 = p1.matcher(msisdn);
		if (m1.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 获取项目webapp下的绝对路径
	 * 
	 * @return
	 */
	public static String getRealPath() {
		String folderPath = AdvertUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();

		if (folderPath.indexOf("target/classes") > 0) {
			folderPath = folderPath.substring(0, folderPath.indexOf("target/classes")) + "src/main/resources";
		}
		return folderPath;
	}

	/**
	 * 得到公告上传后的路径
	 * 
	 * @param notice
	 * @return
	 */
	public static String getUploadNoticeHtmlUrl(Notice notice, OssManager manager) {
		Map<String, String> model = new HashMap<String, String>(3);
		model.put("title", notice.getTitle());
		model.put("content", notice.getContent());
		model.put("timestamp", String.valueOf(System.currentTimeMillis()));
		model.put("createTime", DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
		File file = null;
		try {
			String fileName = FreeMarkers.createFile(NoticeConstants.NOTICETHTML, model);

			String filePath = new StringBuffer().append(AdvertUtil.getRealPath())
					.append(Context.getProperty("web.newPageUrl")).append(fileName).toString();

			file = new File(filePath);

			StringBuffer uploadPath = new StringBuffer();

			uploadPath.append(OssManager.H5).append(NoticeConstants.ADSPATH).append(NoticeConstants.NOTICETHTML).append("/");

			String uploadFilePath = manager.uploadFile(fileName, file, uploadPath.toString());

			if (logger.isDebugEnabled()) {
				logger.debug("title:     " + notice.getTitle());
				logger.debug("content:     " + notice.getContent());
				logger.debug("fileName:     " + fileName);
				logger.debug("filePath:     " + filePath);
				logger.debug("uploadFilePath:     " + uploadFilePath);
			}

			return uploadFilePath;

		} catch (IOException e) {
			logger.error("file upload error", e);
			return null;
		} finally {
			if (file != null)
				file.delete();
		}

	}

	/**
	 * 获取期望上刊时间
	 * 
	 * @param wishUpDate
	 * @return
	 */
	public static Date getWishUpDate(Date wishUpDate) {
		Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
		cal.setTime(wishUpDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取过期时间
	 * 
	 * @param wishUpDate
	 * @param day
	 * @return
	 */
	public static Date getExpireDate(Date wishUpDate, int day) {
		Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
		if (wishUpDate == null) {
			cal.add(Calendar.DATE, day + 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
		} else {
			cal.setTime(wishUpDate);
			cal.add(Calendar.DATE, day);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
		}
		return cal.getTime();
	}

}
