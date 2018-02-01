package com.yryz.quanhu.user.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yryz.common.exception.RedisOptException;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.user.contants.RegType;
import com.yryz.quanhu.user.entity.UserAccount;
import com.yryz.quanhu.user.entity.UserThirdLogin;
import com.yryz.quanhu.user.service.AccountApi;

/**
 * 用户账户缓存管理
 * @author danshiyu
 *
 */
@Service
public class UserAccountRedisDao {
	private static final Logger logger = LoggerFactory.getLogger(UserAccountRedisDao.class);
	/**
	 * 账户缓存过期时间/天
	 */
	private static final int ACCOUNT_EXPIRE = 7;
	
	@Autowired
	private RedisTemplateBuilder templateBuilder;
	
	/**
	 * 账户缓存保存
	 * @param account
	 */
	public void saveAccount(UserAccount account){
		String userIdKey = AccountApi.userCacheKey(account.getKid());
		String phoneKey = AccountApi.phoneAccountKey(account.getUserPhone(), account.getAppId());		 
		try {
			RedisTemplate<String, UserAccount> template = templateBuilder.buildRedisTemplate(UserAccount.class);
			template.opsForValue().set(userIdKey, account);
			template.expire(userIdKey, ACCOUNT_EXPIRE, TimeUnit.DAYS);
			//兼容老数据
			if(StringUtils.isNotBlank(phoneKey)){
				template.opsForValue().set(phoneKey, account);
				template.expire(phoneKey, ACCOUNT_EXPIRE, TimeUnit.DAYS);
			}
		} catch (Exception e) {
			logger.error("[saveAccount]",e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 根据手机号或者用户id查询账户
	 * @param userId
	 * @param phone
	 * @param appId
	 * @return
	 */
	public UserAccount getAccount(Long userId,String phone,String appId){
		String key = AccountApi.userCacheKey(userId);
		if(StringUtils.isBlank(key)){
			key = AccountApi.phoneAccountKey(phone, appId);
		}
		try {
			RedisTemplate<String, UserAccount> template = templateBuilder.buildRedisTemplate(UserAccount.class);
			return template.opsForValue().get(key);
		} catch (Exception e) {
			logger.error("[getAccount]",e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 根据手机号查询用户账号
	 * @param phones
	 * @param appId
	 * @return
	 */
	public List<UserAccount> getAccount(Set<String>phones,String appId){
		if(CollectionUtils.isEmpty(phones) || StringUtils.isBlank(appId)){
			return null;
		}
		List<UserAccount> accounts = new ArrayList<>(phones.size());
		for(Iterator<String>iterator= phones.iterator();iterator.hasNext();){
			String phone = iterator.next();
			UserAccount account = getAccount(null, phone, appId);
			accounts.add(account);
		}
		return accounts;
	}
	
	/**
	 * 删清除用户账户缓存
	 * @param userId
	 * @param phone
	 * @param appId
	 */
	public void deleteAccount(Long userId,String phone,String appId){
		String userIdKey = AccountApi.userCacheKey(userId);
		String phoneKey = AccountApi.phoneAccountKey(phone, appId);
		try {
			RedisTemplate<String, UserAccount> template = templateBuilder.buildRedisTemplate(UserAccount.class);
			template.delete(userIdKey);
			if(StringUtils.isNotBlank(phoneKey)){
				template.delete(phoneKey);
			}
		} catch (Exception e) {
			logger.error("[deleteAccount]",e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 保存用户第三方信息
	 * @param login
	 */
	public void saveUserThird(UserThirdLogin login){
		String key = AccountApi.thirdAccountKey(login.getThirdId(), login.getAppId(), login.getLoginType().intValue());
		try {
			RedisTemplate<String, UserThirdLogin> template = templateBuilder.buildRedisTemplate(UserThirdLogin.class);
			template.opsForValue().set(key, login);
			template.expire(key, ACCOUNT_EXPIRE, TimeUnit.DAYS);
		} catch (Exception e) {
			logger.error("[saveUserThird]",e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 获取用户第三方信息
	 * @param thirdId
	 * @param appId
	 * @param type {@link RegType}
	 * @return
	 */
	public UserThirdLogin getUserThird(String thirdId,String appId,int type){
		String key = AccountApi.thirdAccountKey(thirdId, appId, type);
		try {
			RedisTemplate<String, UserThirdLogin> template = templateBuilder.buildRedisTemplate(UserThirdLogin.class);
			return template.opsForValue().get(key);
		} catch (Exception e) {
			logger.error("[getUserThird]",e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 获取用户第三方信息
	 * @param thirdId
	 * @param appId
	 * @param type {@link RegType}
	 * @return
	 */
	public void deleteUserThird(String thirdId,String appId,int type){
		String key = AccountApi.thirdAccountKey(thirdId, appId, type);
		try {
			RedisTemplate<String, UserThirdLogin> template = templateBuilder.buildRedisTemplate(UserThirdLogin.class);
			template.delete(key);
		} catch (Exception e) {
			logger.error("[deleteUserThird]",e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 保存用户登录方式
	 * @param userId
	 * @param thirdLogins
	 */
	public void saveLoginMethod(Long userId,List<UserThirdLogin> thirdLogins){
		String key = AccountApi.thirdLoginMethodKey(userId);
		try {
			RedisTemplate<String,String> template = templateBuilder.buildRedisTemplate(String.class);
			template.opsForValue().set(key, JsonUtils.toFastJson(thirdLogins));
			template.expire(key, ACCOUNT_EXPIRE, TimeUnit.DAYS);
		} catch (Exception e) {
			logger.error("[saveLoginMethod]",e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 获取用户登录方式
	 * @param userId
	 * @param thirdLogins
	 */
	public List<UserThirdLogin> getLoginMethod(Long userId){
		String key = AccountApi.thirdLoginMethodKey(userId);
		try {
			RedisTemplate<String,String> template = templateBuilder.buildRedisTemplate(String.class);
			String logins = template.opsForValue().get(key);
			if(StringUtils.isNotBlank(logins)){
				return JsonUtils.fromJson(logins,new TypeReference<List<UserThirdLogin>>() {
				});
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("[getLoginMethod]",e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 清理用户登录方式
	 * @param userId
	 * @param thirdLogins
	 */
	public void deleteLoginMethod(Long userId){
		String key = AccountApi.thirdLoginMethodKey(userId);
		try {
			RedisTemplate<String,String> template = templateBuilder.buildRedisTemplate(String.class);
			template.delete(key);
		} catch (Exception e) {
			logger.error("[deleteLoginMethod]",e);
			throw new RedisOptException(e);
		}
	}
}
