/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年12月4日
 * Id: VerifyCodeAPI.java, 2017年12月4日 下午3:57:11 Administrator
 */
package com.yryz.quanhu.message.commonsafe.api;

import com.yryz.common.context.Context;
import com.yryz.common.entity.AfsCheckRequest;
import com.yryz.common.response.Response;
import com.yryz.quanhu.message.commonsafe.constants.RedisConstants;
import com.yryz.quanhu.message.commonsafe.dto.IpLimitDTO;
import com.yryz.quanhu.message.commonsafe.dto.VerifyCodeDTO;
import com.yryz.quanhu.message.commonsafe.vo.VerifyCodeVO;

/**
 * 公共安全接口
 * @author danshiyu
 * @version 1.0
 * @date 2017年12月4日 下午3:57:11
 * @Description 提供普通验证码、图形验证码、ip防护服务
 */
public interface CommonSafeApi {
	/**
	 * 获取ip限制key
	 * @param ip
	 * @param appId
	 * @param serviceType
	 * @return
	 */
	public static String getIpLimitKey(String ip,String appId,String serviceType){
		return String.format("%s:%s:%s", Context.getProperty("ip.limit.total"),ip,appId,serviceType);
	}
	/**
	 * 获取ip限制key
	 * @param ip
	 * @param appId
	 * @param serviceType
	 * @return
	 */
	public static String getIpRunTimeKey(String ip,String appId,String serviceType){
		return String.format("%s:%s:%s", Context.getProperty("ip.limit.time"),ip,appId,serviceType);
	}
	
    /**
     * 图形验证码key
     * @param key 例如手机号、邮箱
     * @param appId 
     * @return
     */
	@Deprecated
    public static String getImgCode(String key,String appId){
    	return String.format("%s:%s:%s",RedisConstants.IMG_VERIFY_CODE, key,appId);
    }
    /**
     * 图形验证码/滑动验证码不验证次数key
     * @param key 例如手机号、邮箱
     * @param appId 
     * @return
     */
    public static String getImgCodeCount(String key,String appId){
    	return String.format("%s:%s:%s",Context.getProperty("taobao.splip.code.no.check.times"), key,appId);
    }
    /**
     * 获取普通验证码key
     * @param key 例如手机号、邮箱
     * @param appId
     * @param serviceCode 接入方自定义类型
     * @return
     */
	public static String getVerifyCodeKey(String key,String appId,int serviceCode){
		return String.format("%s:%s:%s:%s", Context.getProperty("verify.code"),key,appId,serviceCode);
	}
	
	/**
	 * 获取普通验证码风控cache key
	 * @param key  例如手机号、邮箱
	 * @param appId
	 * @return
	 */
	public static String getVerifyCodeLimitTotalKey(String key,String appId){
		return String.format("%s:%s:%s", Context.getProperty("verify.code.limit.total"),key,appId);
	}
	
	/**
	 * 获取普通验证码风控cache key
	 * @param key  例如手机号、邮箱
	 * @param appId
	 * @return
	 */
	public static String getVerifyCodeLimitTimeKey(String key,String appId){
		return String.format("%s:%s:%s", Context.getProperty("verify.code.limit.lastTime"),key,appId);
	}
	
	
	/**
	 * 根据业务类型获取普通(手机、邮箱)验证码
	 * @param codeDTO {@link VerifyCodeDTO}
	 * @return 
	 */
	Response<VerifyCodeVO> getVerifyCode(VerifyCodeDTO codeDTO);

	/**
	 * 根据业务类型验证普通(手机、邮箱)验证码
	 * @param codeDTO {@link VerifyCodeDTO}
	 * @return 0:成功 1:校验失败 2:已过期
	 */
	Response<Integer> checkVerifyCode(VerifyCodeDTO codeDTO);


	/**
	 * 检查支持阿里滑动验证码
	 * @param verifyCodeDTO
	 * @param afsCheckRequest
	 * @return
	 */
	Response<Integer> checkSlipCode(VerifyCodeDTO verifyCodeDTO, AfsCheckRequest afsCheckRequest);
	
	/**
	 * 根据业务类型获取图形验证码
	 * @param codeDTO {@link VerifyCodeDTO}
	 * @return 
	 */
	Response<String> getImgVerifyCode(VerifyCodeDTO codeDTO);
	
	/**
	 * 根据业务类型验证图形验证码
	 * @param codeDTO {@link VerifyCodeDTO}
	 * @return
	 */
	Response<Boolean> checkImgVerifyCode(VerifyCodeDTO codeDTO);
	
	/**
	 * 根据业务类型记录对每个ip计数
	 * @param serviceType 业务类型接入方自定义，例如phone_login(手机号登录)、email_forget_password(邮箱找回密码)等
	 * @param ip
	 */
	Response<Boolean> saveIpCount(IpLimitDTO dto);
	
	/**
	 * 根据业务类型判断每个ip是否超过访问次数或者超过发送频率
	 * @param serviceType 业务类型接入方自定义，例如phone_login(手机号登录)、email_forget_password(邮箱找回密码)等
	 * @param ip
	 * @return 超过次数和发送频率返回false
	 */
	Response<Boolean> checkIpLimit(IpLimitDTO dto);


}
