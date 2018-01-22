package com.yryz.common.aliyun.jaq;

import com.aliyun.oss.ServiceException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.jaq.model.v20161123.AfsCheckRequest;
import com.aliyuncs.jaq.model.v20161123.AfsCheckResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.log4j.Logger;

public class AfsCheckManager {
	private static Logger logger = Logger.getLogger(AfsCheckManager.class);
	private IAcsClient client;
	
	public AfsCheckManager(String accessKey, String accessSecret){
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey, accessSecret);
        client = new DefaultAcsClient(profile);
	}
	
	public boolean afsCheck(AfsCheckRequest request){
		AfsCheckResponse response;
		try {
			response = client.getAcsResponse(request);
			if(response.getErrorCode() == 0 && response.getData() == true) {
	            return true;
	        } else {
	        	logger.error("AfsCheckResponse:"+response.getErrorCode()+","+response.getErrorMsg());
	            return false;
	        }
		} catch (ServerException e) {
			e.printStackTrace();
			throw new ServiceException("afsCheck ServerException",e);
		} catch (ClientException e) {
			e.printStackTrace();
			throw new ServiceException("afsCheck ClientException",e);
		}
	}
}
