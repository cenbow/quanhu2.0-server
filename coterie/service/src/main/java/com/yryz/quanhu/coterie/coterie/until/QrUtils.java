package com.yryz.quanhu.coterie.coterie.until;

import com.google.gson.JsonObject;
import com.yryz.quanhu.coterie.coterie.exception.BaseException;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * 二维码
 * 
 * @author jk
 *
 */
public class QrUtils {
	private static Logger logger = Logger.getLogger(QrUtils.class);
	private static String format = "png";
	private static int width = 200;
	private static int height = 200;

	public static String createQr(String circleId,String coterieId) throws BaseException {
		String tmpPath=QrUtils.class.getResource("/").getPath();
		tmpPath=tmpPath.replace("\\", "/");
		tmpPath=tmpPath.replace("//", "/");
		if(!tmpPath.endsWith("/")){
			tmpPath=tmpPath + System.getProperty("file.separator");
		}
		tmpPath=tmpPath.replace("classes/", "");
		tmpPath=tmpPath+"temp/";
		if(tmpPath.indexOf(":")!=-1){
			tmpPath=tmpPath.substring(1);
		}
		String fileName = coterieId + "_qr." + format;
		String localPath = tmpPath +fileName;
		System.out.println("tmpPath:"+tmpPath);
		String qrUrl = null;

		// 本地生成图片
		try {
			if (logger.isDebugEnabled()) {
				logger.info("====>create Qr " + localPath);
			}

			JsonObject json = new JsonObject();
			json.addProperty("coterieId", coterieId);
			json.addProperty("circleId", circleId);
			String contents = json.toString();
			logger.warn("二维码信息："+contents);
			File file=new File(tmpPath);
			if(!file.exists()){
				logger.warn("开始创建文件夹");
				file.mkdirs();
				logger.warn("创建文件夹完成");
			}
			ZxingHandler.encode2(contents, width, height, format, tmpPath, fileName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("localPath:"+localPath+",fileName:"+fileName, e);
			throw new BaseException("sys error");
		}

		//  todo 上传至oss 删除本地
//		File file = null;
//		String uploadPath = OssManager.PIC + OssManager.Coterie_QR;
//		try {
//			file = new File(localPath);
//			qrUrl = OssManager.getInstance().uploadFile(fileName, file, uploadPath);
//			System.out.println(qrUrl);
//		} catch (Exception e) {
//			logger.error("[Qr create]", e);
//			throw new BaseException("sys error");
//		} finally {
//			// 删除本地文件
//			logger.debug("====>delete tmpQr " + localPath);
//			if (file != null){
//				file.deleteOnExit();
//			}
//		}

		return qrUrl;
	}
	
	public static void main(String[] args) {
		System.out.println(QrUtils.class.getResource("/").getPath());
	}
}
