/**
 * 
 */
package com.yryz.quanhu.user.service;

import com.yryz.quanhu.user.dto.SmsVerifyCodeDTO;
import com.yryz.quanhu.user.vo.SmsVerifyCodeVO;

/**
 * @author danshiyu
 * 短信验证码管理
 */
public interface SmsService {
	/**
	 * 发送验证码
	 * @param phone
	 * @param type 验证码类型
	 * @return
	 */
	public SmsVerifyCodeVO sendVerifyCode(SmsVerifyCodeDTO codeDTO);
	
	/**
	 * 验证短信、成功后删除
	 * @param phoneNo 手机号
	 * @param type 验证码类型
	 * @param verifyCode 验证码
	 * @return  功能码类型
	 */
	public boolean checkVerifyCode(String phone, String type, String verifyCode,String appId);
	
	/**
	 * 更新某个ip发送短信验证码次数
	 * @param request
	 * @Description 
	 *//*
	public void saveIpSendVerifyCodeCount(Map<String,Object> params);
	
	*//**
	 * 检查当前ip是否可以发送短信验证码
	 * @param request
	 * @return
	 * @Description 限制每个ip每天发送1000次
	 *//*
	public boolean checkIpSendVerifyCodeLimit(Map<String,Object> params);
	
	*//**
	 * 获取图像校验码
	 * @param phone
	 * @return
	 *//*
	public void getSmsImgCode(String phone,HttpServletResponse response);
	
	*//**
	 * 检查图像校验码
	 * @param phone
	 * @param code 图像校验码
	 * @return
	 *//*
	public boolean checkSmsImgCode(String phone,String code);*/
}
