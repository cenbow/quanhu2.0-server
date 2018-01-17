package com.yryz.quanhu.user.manager;

import java.io.File;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.utils.QrcodeUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.user.oss.OssManager;

/**
 * 二维码管理
 * 1，创建qr - zxing
 * 2，存储qr - oss
 * @author Pxie
 *
 */

public class QrManager {
	private static Logger logger = Logger.getLogger(QrManager.class);
	
	/**
	 * 临时文件路径 //default
	 */
	private String tmpPath = "/app/tmp/img"; 
	private static final String FORMAT = "png";
	private int width = 200;
	private int height = 200;

	private static final QrManager QRMANAGER = new QrManager();
	
	public static QrManager getInstance() {
		return QRMANAGER;
	}
	
	private QrManager() {
		//this.tmpPath = Context.getProperty("TMP_FILEPATH");
		
		if (StringUtils.isBlank(tmpPath)) {
			return;
		}
		
		 File folder = new File(tmpPath);
		 if (!folder.exists() || !folder.isDirectory()) {
			 folder.mkdirs();
		 }
	}
	
	/**
	 * 创建二维码，上传至oss
	 * @param uid
	 * @return qr Url
	 * @throws Exception
	 */
	public String createQr(String uid) {
		
		String fileName = uid + "_qr." + FORMAT;
		String localPath = tmpPath + System.getProperty("file.separator") + fileName;
		
		String qrUrl = null;
		
		//本地生成图片
		try {
			if (logger.isDebugEnabled()) {
				logger.info("====>create Qr "+  localPath );
			}
			
			JsonObject json = new JsonObject();
			json.addProperty("uid", uid);
			String contents = json.toString();
			
			QrcodeUtils.encode2(contents, width, height, FORMAT, tmpPath, fileName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[Qr create]", e);
			throw QuanhuException.busiError("sys error");
		}
		
		//上传至oss 删除本地
		File file = null;
		String uploadPath = OssManager.PIC + OssManager.M_QR;
		try {
			file = new File(localPath);
			qrUrl = OssManager.getInstance().uploadFile(fileName, file, uploadPath);
		} catch (Exception e) {
			logger.error("[Qr create]", e);
			throw QuanhuException.busiError("sys error");
		} finally {
			//删除本地文件
			logger.debug("====>delete tmpQr "+  localPath );
			if (file != null) {
				file.deleteOnExit();
			}	
		}
		
		return qrUrl;
	}
	
	/**
	 * 拼取二维码oss的存储路径
	 * @param uid
	 * @return
	 */
	public static String getQrUrl(String uid) {
		String fileName = uid + "_qr." + FORMAT;
		String uploadPath = OssManager.PIC + OssManager.M_QR;
		
		return OssManager.getStaticUrl(uploadPath, fileName);
	}
	
	public static void main(String args[]) {
		QrManager qr = QrManager.getInstance();
		try {
//			qr.createQr("3i7rys3e0l");
			qr.createQr("2017111310120010");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
