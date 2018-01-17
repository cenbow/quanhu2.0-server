package com.yryz.quanhu.user.oss;

import java.io.IOException;
import java.io.InputStream;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;

public class OssObjectService {
	private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String picture_path;
    private String doc_path;
    private OSSClient client;
    
	private static OssObjectService instance = new OssObjectService();
	
	public OssObjectService(){
		endpoint = "oss-cn-shanghai.aliyuncs.com";
		accessKeyId = "4DzUFgoxE8XqSQz2";
		accessKeySecret = "4Np7yLrgG2XKKX6aG3WH1BWYLznHMx";
		bucketName = "bkt-whrzw-01";
		picture_path = "file_picture";
		doc_path = "file_doc";
		client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	}
	
	public static OssObjectService getInstance(){
		return instance;
	}
	
	
	
	public OssPicture uploadPicture(String fileName,InputStream input){
		OssPicture ossPicture = new OssPicture();
		try {
			String fullPath = picture_path + "/" + fileName;//windows系统和linux系统都固定为/
			System.out.println("文件名:" + fullPath);
			PutObjectResult result = client.putObject(bucketName, fullPath, input);
			System.out.println(result.getETag());
			System.out.println(result.getRequestId());
			System.out.println(result.getCallbackResponseBody());
			System.out.println("http://bkt-whrzw-01.oss-cn-shanghai.aliyuncs.com/" + fullPath);
			ossPicture.setPictureFileName(fileName);
			ossPicture.setKey(fullPath);
			ossPicture.setUrl("http://bkt-whrzw-01.oss-cn-shanghai.aliyuncs.com/" + fullPath);
			ossPicture.setThumbnailUrl("http://bkt-whrzw-01.img-cn-shanghai.aliyuncs.com/" + fullPath + "@!style-01");
		} catch (OSSException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		} finally{
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ossPicture;
	}
}
