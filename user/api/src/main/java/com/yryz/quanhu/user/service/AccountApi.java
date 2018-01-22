package com.yryz.quanhu.user.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yryz.common.entity.RequestHeader;
import com.yryz.common.response.Response;
import com.yryz.quanhu.user.contants.RedisConstants;
import com.yryz.quanhu.user.dto.BindPhoneDTO;
import com.yryz.quanhu.user.dto.BindThirdDTO;
import com.yryz.quanhu.user.dto.ForgotPasswordDTO;
import com.yryz.quanhu.user.dto.LoginDTO;
import com.yryz.quanhu.user.dto.RegisterDTO;
import com.yryz.quanhu.user.dto.SmsVerifyCodeDTO;
import com.yryz.quanhu.user.dto.ThirdLoginDTO;
import com.yryz.quanhu.user.dto.UnBindThirdDTO;
import com.yryz.quanhu.user.vo.LoginMethodVO;
import com.yryz.quanhu.user.vo.RegisterLoginVO;
import com.yryz.quanhu.user.vo.SmsVerifyCodeVO;
/**
 * 用户账户操作
 * @author danshiyu
 *
 */
public interface AccountApi {
	/**
	 * 用户id账户缓存key
	 * @param userId
	 * @return
	 */
	static String userCacheKey(String userId){
		return String.format("%s.%s", RedisConstants.ACCOUNT_USER,userId);
	}
	/**
	 * 手机号账户缓存key
	 * @param phone
	 * @param appId
	 * @return
	 */
	static String phoneAccountKey(String phone,String appId){
		return String.format("%s.p.%s", RedisConstants.ACCOUNT_USER,phone,appId);
	}
	/**
	 * 第三方账户缓存key
	 * @param thirdId
	 * @param appId
	 * @return
	 */
	static String thirdAccountKey(String thirdId,String appId){
		return String.format("%s.third.%s", RedisConstants.ACCOUNT_USER,thirdId,appId);
	}
	/**
	 * 发送短信验证码
	 * @param codeDTO
	 * @return
	 */
	public Response<SmsVerifyCodeVO> sendVerifyCode(SmsVerifyCodeDTO codeDTO);
	
	/**
	 * 手机号注册
	 * @param registerDTO
	 * @param header
	 * @return
	 */
	public Response<RegisterLoginVO> register(RegisterDTO registerDTO, RequestHeader header);

	/**
	 * 手机号密码登录
	 * 
	 * @param loginDTO
	 * @param header
	 * @return
	 * @Description
	 */
	public Response<RegisterLoginVO> login(LoginDTO loginDTO, RequestHeader header);

	/**
	 * 手机短信登录
	 * 
	 * @param loginDTO
	 * @param header
	 * @return
	 * @Description
	 */
	public Response<RegisterLoginVO> loginByVerifyCode(LoginDTO loginDTO, RequestHeader header);

	/**
	 * (app)第三方登录
	 * 
	 * @param loginDTO
	 * @param header
	 * @return
	 * @Description
	 */
	public Response<RegisterLoginVO> loginThird(ThirdLoginDTO loginDTO,RequestHeader header);

	/**
	 * (app)第三方登录绑定手机号
	 * 
	 * @param header
	 * @param loginDTO
	 * @return
	 * @Description
	 */
	public Response<RegisterLoginVO> loginThirdBindPhone(ThirdLoginDTO loginDTO,RequestHeader header);

	/**
	 * (web)第三方登录
	 * 
	 * @param loginType
	 *            类型 sina:微博 weixin:微信
	 * @param returnUrl
	 *            web端登录成功返回地址
	 * @return
	 * @Description
	 */
	public Response<String> webLoginThird(String loginType, String returnUrl);

	/**
	 * (web)第三方登录回调
	 * 
	 * @return
	 * @throws IOException
	 * @Description 第三方回调成功后跳转到web端登录时传的地址
	 */
	public void webThirdLoginNotify(HttpServletRequest request, HttpServletResponse response) throws IOException;

	/**
	 * 获取登录方式
	 * 
	 * @param userId
	 * @return
	 * @Description 根据header信息获取登录方式
	 */
	public Response<List<LoginMethodVO>> getLoginMethod(String userId);
	
	/**
	 * 查询用户最后登录时间
	 * @param userIds
	 * @return
	 */
	public Response<Map<String,Date>> getLastLoginTime(List<String> userIds);
	/**
	 * 退出登录
	 * 
	 * @param request
	 * @return
	 *//*
	public JSONObject loginOut(Map<String, Object> params) {
		return ReturnModel.returnSuccess();
	}*/

	/**
	 * 绑定手机号
	 * 
	 * @param phoneDTO
	 * @return
	 */
	public Response<Boolean> bindPhone(BindPhoneDTO phoneDTO);

	/**
	 * 绑定 第三方账户
	 * 
	 * @param thirdDTO
	 * @return 
	 */
	public Response<Boolean> bindThird(BindThirdDTO thirdDTO);

	/**
	 * 解绑 第三方账户
	 * 
	 * @param thirdDTO
	 * @return 
	 */
	public Response<Boolean> unbindThird(UnBindThirdDTO thirdDTO);

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param newPassword
	 *            新密码
	 * @param oldPassword
	 *            旧密码
	 * @return
	 * @Description 需要在header获取用户id
	 */
	public Response<Boolean> editPassword(String userId,String oldPassword,String newPassword);

	/**
	 * 手机短信重置密码
	 * 
	 * @param passwordDTO
	 */
	public Response<Boolean> forgotPassword(ForgotPasswordDTO passwordDTO);
	/**
	 * 检查用户是否被禁言
	 * @param userId
	 * @return
	 */
	public Response<Boolean> checkUserDisTalk(String userId);
}
