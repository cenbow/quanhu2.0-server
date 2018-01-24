package com.yryz.quanhu.coterie.coterie.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import com.yryz.quanhu.coterie.coterie.until.Context;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * OSS文件上传Client (Singleton Pattern)
 * 
 * @author 
 * @version 20161201
 */
public class OssManager {
	private static Logger logger = Logger.getLogger(OssManager.class);
	
	/* 文件分类 */
    public static final String VIDEO = "video/";
    public static final String AUDIO = "audio/";
    public static final String PIC = "pic/";
    public static final String H5 = "H5/";
    
    /* 业务分类  */
    public static final String M_QR = "qr/";
    public static final String M_HEADIMG = "headImage/";
    public static final String M_OPUS = "opus/";
    public static final String M_SHINE = "shine/";
    public static final String M_ADS = "ads/";
    public static final String M_OP = "op/";
    public static final String Coterie_QR = "coterie_qr/";

    /* OSS配置 */
    public static String endpoint;
    public static String accessKeyId;
    public static String secretAccessKey;
    public static String bucketName;
    
    private OSSClient client;
    
    private static OssManager instance = new OssManager();
    
    public static OssManager getInstance() {
		return instance;
	}
    
    public OssManager()  {
    	init();
    }
    
    private void init() {
    	endpoint = Context.getProperty("OSS_ENDPOINT");
    	accessKeyId = Context.getProperty("OSS_ACCESSKEYID");
    	secretAccessKey = Context.getProperty("OSS_SECRETACCESSKEY");
    	bucketName = Context.getProperty("OSS_BUCKETNAME");
    	
    	//建议签名鉴权
    	client = new OSSClient(endpoint, accessKeyId, secretAccessKey);
    }
	
    
    /**
     * 上传文件
     * 
     * @param fileName
     * @param file
     * @param uploadPath
     * @return URL
     */
    public String uploadFile(String fileName, File file, String uploadPath) {
    	String key = uploadPath + fileName;
    	if (logger.isDebugEnabled()) {
			logger.info("====>upload "+uploadPath+" "+  key);
		}
    	
    	try{
	    	PutObjectResult result = client.putObject(bucketName, key, file);
	   
	    	return getResultUrl(result, key);
		} catch (OSSException e) {
			logger.debug("oss上传"+uploadPath+"失败",e);
			logger.error("oss上传"+uploadPath+"失败",e);
			return null;
		} catch (ClientException e) {
			logger.debug("oss上传"+uploadPath+"失败",e);
			logger.error("oss上传"+uploadPath+"失败",e);
			return null;
		}
    	
    }
    /**
     * 上传文件
     * 
     * @param file
     * @param uploadPath
     * @return URL
     */
    public String uploadInfo(File file,String uploadPath) {
    	String url="";
    	if(file==null){
    		return url;
    	}
    	String fileName=file.getName();
    	String key = uploadPath + fileName;
    	if (logger.isDebugEnabled()) {
			logger.info("====>upload "+uploadPath+" "+  key);
		}
    	
    	try{
	    	PutObjectResult result = client.putObject(bucketName, key, file);
	   
	    	return getResultUrl(result, key);
		} catch (OSSException e) {
			logger.error("oss上传"+uploadPath+"失败",e);
			return null;
		} catch (ClientException e) {
			logger.error("oss上传"+uploadPath+"失败",e);
			return null;
		}
    	
    }
    /**
     * 上传文件地址
     * 
     * @param result
     * @param key
     * @return
     */
    private String getResultUrl(PutObjectResult result,String key)
    {
     	StringBuffer buffer = new StringBuffer();	    	
    	buffer.append("http://").append(bucketName);
    	buffer.append(".").append(endpoint);
    	buffer.append("/").append(key);
    	if(logger.isDebugEnabled())
    	{
    		logger.debug("ETag:"+result.getETag());
    		logger.debug("requestId:"+result.getRequestId());
    		logger.debug("responseBody:"+result.getCallbackResponseBody());
    		logger.debug("url:"+buffer.toString());
    	}
    	return buffer.toString();
    }
    
    /**
     * 获取静态地址
     * 通过path、fileName拼接OSS完整访问路径
     * 
     * @param   fileName  文件名称
     * @param   path      OSS存储路径
     * @return  URL
     */
    public static String getStaticUrl(String path, String fileName) {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append("http://").append(bucketName)
    		.append(".").append(endpoint)
    		.append("/")
    		.append(path)
    		.append(fileName);
    	
    	return buffer.toString();
    }
}
