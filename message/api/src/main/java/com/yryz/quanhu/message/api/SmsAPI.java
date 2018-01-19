package com.yryz.quanhu.message.api;


import com.yryz.quanhu.message.entity.*;

import java.util.List;

/**
 * Dubbo Service(user) API
 * 短信 验证服务
 * @author Pxie
 */
public interface SmsAPI {
	
	/**
	 * 发送验证码
	 * @param phone
	 * @param code
	 * @return
	 * @throws
	 */
	@Deprecated
	public SmsVerifyCode sendVerifyCode(String phone, String code);
	
	/**
	 * 发送验证码
	 * @param reqVo 
	 * phone,code必填，channel选填
	 * @return
	 * @throws
	 */
	public SmsVerifyCode sendVerifyCode(SmsReqVo reqVo);

	/**
	 * 
	 * 发送短信自定义消息
	 * @param reqVo
	 * smsParam参数必填，templateType必填>1,channel必填
	 * @return
	 * @throws
	 */
	public boolean sendMessage(SmsReqVo reqVo);
	
	/**
	 * 验证短信、成功后删除
	 * @param phoneNo 手机号
	 * @param code 功能码
	 * @param verifyCode 验证码
	 * @return  功能码类型
	 * @throws
	 */
	public boolean checkVerifyCode(String phone, String code, String verifyCode);
	
	/**
	 * 验证短信不删除
	 * @param phoneNo 手机号
	 * @param code 功能码
	 * @param verifyCode 验证码
	 * @return  功能码类型
	 * @throws
	 */
	public boolean checkVerifyCodeNoDelete(String phone, String code, String verifyCode);
	
//	/**
//	 * 查询验证码类型
//	 * @param phone
//	 * @param verifyCode
//	 * @return
//	 * @throws
//	 */
//	public String getVerifyCode(String phone, String verifyCode) throws ;
	
	
	/**
	 *  短信通道保存
	 * @author Administrator
	 * @date 2017年10月18日
	 * @param channel
	 * @return
	 * @throws
	 * @Description 
	 */
	public int saveChannel(SmsChannelVo channel);
	
	/**
	 * 短信通道更新
	 * @author Administrator
	 * @date 2017年10月18日
	 * @param channel
	 * @return
	 * @throws
	 * @Description
	 */
	public int updateChannel(SmsChannelVo channel);
	
	/**
	 * 
	 * 删除短信通道
	 * @param id
	 * @return
	 * @throws
	 */	
	public int deleteChannel(Integer id);
	
	/**
	 * 
	 * 短信通道获取
	 * @param id
	 * @return
	 * @throws
	 */
	public SmsChannelVo getChannel(Integer id);
	
	
	/**
	 * 
	 * 短信通道查询
	 * @return
	 * @throws
	 */
	public List<SmsChannelVo> listChannel();
	
	/**
	 * 
	 * 短信模板新增
	 * @param template
	 * @return
	 */
	public int saveTemplate(SmsTemplateVo template);
	
	/**
	 * 
	 * 短信模板更新
	 * @param template
	 * @return
	 */
	public int updateTemplate(SmsTemplateVo template);
	
	/**
	 * 
	 * 删除短信模板
	 * @param id
	 * @return
	 */
	public int deleteTemplate(Integer id);
	
	/**
	 * 
	 * 获取短信模板
	 * @param id
	 * @return
	 */
	public SmsTemplateVo getTemplate(Integer id);
	
	/**
	 * 
	 * 查询短信模板
	 * @return
	 */
	public List<SmsTemplateVo> listTemplate();
	
	/**
	 * 更新某个ip发送短信验证码次数
	 * @param ip
	 * @Description 
	 */
	public void saveIpSendVerifyCodeCount(String ip);
	
	/**
	 * 检查当前ip是否可以发送短信验证码
	 * @param ip
	 * @return
	 * @Description 限制每个ip每天发送1000次，限制每个ip发送间隔时间
	 */
	public boolean checkIpSendVerifyCodeLimit(String ip);

	/**
	 * 获取图像校验码
	 * @param phone
	 * @return
	 */
	public String getSmsImgCode(String phone);
	
	/**
	 * 检查图像校验码
	 * @param phone
	 * @param code 图像校验码
	 * @return
	 */
	public boolean checkSmsImgCode(String phone, String code);

	/**
	 * 检查滑动校验码
	 * @param phone
	 * @param afsCheckReq 滑动校验码
	 * @return
	 */
	public boolean checkSmsSlipCode(String phone, AfsCheckRequest afsCheckReq);
}

