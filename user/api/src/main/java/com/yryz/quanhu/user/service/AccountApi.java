package com.yryz.quanhu.user.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yryz.common.entity.RequestHeader;
import com.yryz.common.response.Response;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.user.contants.RedisConstants;
import com.yryz.quanhu.user.contants.RegType;
import com.yryz.quanhu.user.dto.AgentRegisterDTO;
import com.yryz.quanhu.user.dto.BindPhoneDTO;
import com.yryz.quanhu.user.dto.BindThirdDTO;
import com.yryz.quanhu.user.dto.ForgotPasswordDTO;
import com.yryz.quanhu.user.dto.LoginDTO;
import com.yryz.quanhu.user.dto.RegisterDTO;
import com.yryz.quanhu.user.dto.SmsVerifyCodeDTO;
import com.yryz.quanhu.user.dto.ThirdLoginDTO;
import com.yryz.quanhu.user.dto.UnBindThirdDTO;
import com.yryz.quanhu.user.dto.WebThirdLoginDTO;
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
	static String userCacheKey(Long userId){
		if(userId == null || userId == 0l){
			return null;
		}
		return String.format("%s.%s", RedisConstants.ACCOUNT_USER,userId);
	}
	/**
	 * 手机号账户缓存key
	 * @param phone
	 * @param appId
	 * @return
	 */
	static String phoneAccountKey(String phone,String appId){
		if(StringUtils.isBlank(phone) || StringUtils.isBlank(appId)){
			return null;
		}
		return String.format("%s.p.%s", RedisConstants.ACCOUNT_USER,phone,appId);
	}
	/**
	 * 第三方账户缓存key
	 * @param thirdId
	 * @param appId
	 * @param type {@link RegType}
	 * @return
	 */
	static String thirdAccountKey(String thirdId,String appId,int type){
		if(StringUtils.isBlank(thirdId) || StringUtils.isBlank(appId)){
			return null;
		}
		return String.format("%s.third.%s.%s", RedisConstants.ACCOUNT_USER,thirdId,appId,type);
	}
	/**
	 * 用户登录方式
	 * @param userId
	 * @return
	 */
	static String thirdLoginMethodKey(Long userId){
		if(userId == null || userId == 0l){
			return null;
		}
		return String.format("%s.%s", RedisConstants.USER_LOGIN_METHOD,userId);
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
	 * 管理后台代理注册
	 * 
	 * @param registerDTO
	 * @return
	 * @Description
	 */
	public Response<Boolean> agentResiter(AgentRegisterDTO registerDTO);
	

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
	public Response<RegisterLoginVO> loginByVerifyCode(RegisterDTO registerDTO, RequestHeader header);

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
	 * (app)第三方登录绑定手机号<br/>
	 * 1.校验第三方账户是否存在
	 * 1.查询没有手机号的老用户是否存在，存在即进行绑定手机号的操作，然后返回<br/>
	 * 2.查询活动参与者是否存在，存在就用参与者信息注册用户信息，删除参与者信息，创建第三方账号信息<br/>
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
	public Response<String> webLoginThird(String loginType, String returnUrl,String appId);
	
	/**
	 * (web)第三方登录回调
	 * 
	 * @return
	 * @throws IOException
	 * @Description 第三方回调成功后跳转到web端登录时传的地址
	 */
	public Response<String> webThirdLoginNotify(String code,String state,String appId);

	/**
	 * 微信授权登录返回授权地址
	 * @param loginDTO
	 * @return
	 */
	public Response<String> wxOauthLogin(WebThirdLoginDTO loginDTO);
	/**
	 * 微信授权登录回调返回成功的地址
	 * @param loginDTO
	 * @return
	 */
	public Response<String> wxOauthLoginNotify(WebThirdLoginDTO loginDTO);
	/**
	 * 获取登录方式
	 * 
	 * @param userId
	 * @return
	 * @Description 根据header信息获取登录方式
	 */
	public Response<List<LoginMethodVO>> getLoginMethod(Long userId);
	
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
	 * 活动检查手机号<br/>
	 * 1.存在参与者就生成正常用户，存在正常用户就只校验短信验证码<br/>
	 * 1.根据用户id查询老用户，兼容老用户不存在手机号的情况,执行绑定手机号<br/>
	 * 2.查询观察者信息，存在就根据手机号创建用户，删除观察者信息，写入第三方账户信息表，否则提示错误<br/>
	 * @param phoneDTO
	 * @return
	 */
	public Response<Boolean> activityCheckPhone(BindPhoneDTO phoneDTO);
	
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
	public Response<Boolean> editPassword(Long userId,String oldPassword,String newPassword);

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
	public Response<Boolean> checkUserDisTalk(Long userId);
	/**
	 * 根据手机号查询用户是否注册
	 * @param phones
	 * @param appId
	 * @return
	 */
	public Response<List<Map<String,String>>> getUserAccountByPhone(Set<String>phones,String appId);
}
