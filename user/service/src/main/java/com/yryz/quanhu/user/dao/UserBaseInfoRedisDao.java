package com.yryz.quanhu.user.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.yryz.common.exception.RedisOptException;
import com.yryz.common.utils.StringUtils;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.service.UserApi;

/**
 * 用户基本信息缓存管理
 * @author danshiyu
 *
 */
@Service
public class UserBaseInfoRedisDao {
	private static final Logger logger = LoggerFactory.getLogger(UserBaseInfoRedisDao.class);
	/**
	 * 用户信息缓存过期时间/天
	 */
	private static final int USER_INFO_EXPIRE = 180;
	
	@Autowired
	private RedisTemplateBuilder templateBuilder;
	
	/**
	 * 用户信息保存
	 * @param info
	 */
	public void saveUserInfo(UserBaseInfo info){
		String key = UserApi.userInfoKey(info.getUserId());
		try {
			RedisTemplate<String, UserBaseInfo> template = templateBuilder.buildRedisTemplate(UserBaseInfo.class);
			template.opsForValue().set(key, info);
			template.expire(key, USER_INFO_EXPIRE, TimeUnit.DAYS);
		} catch (Exception e) {
			logger.error("[saveUserInfo]",e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 用户信息保存
	 * @param infos
	 */
	public void saveUserInfo(List<UserBaseInfo>infos){
		if(CollectionUtils.isEmpty(infos)){
			return;
		}
		for(int i = 0 ; i < infos.size();i++){
			UserBaseInfo info = infos.get(i);
			saveUserInfo(info);
		}
	}
	
	/**
	 * 查询用户信息
	 * @param userId
	 * @return
	 */
	public UserBaseInfo getUserInfo(Long userId){
		String key = UserApi.userInfoKey(userId);
		try {
			RedisTemplate<String, UserBaseInfo> template = templateBuilder.buildRedisTemplate(UserBaseInfo.class);
			return template.opsForValue().get(key);
		} catch (Exception e) {
			logger.error("[getUserInfo]",e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 查询用户信息
	 * @param userIds
	 * @return
	 */
	public List<UserBaseInfo> getUserInfo(Set<String> userIds){
		if(CollectionUtils.isEmpty(userIds)){
			return null;
		}
		List<UserBaseInfo> infos = new ArrayList<>(userIds.size());
		for(Iterator<String> iterator = userIds.iterator();iterator.hasNext();){
			Long userId = NumberUtils.createLong(iterator.next());
			UserBaseInfo baseInfo = getUserInfo(userId);
			if(baseInfo != null){
				infos.add(baseInfo);
			}
		}
		return infos;
	}
	
	/**
	 * 删除用户信息
	 * @param userId
	 */
	public void deleteUserInfo(Long userId){
		String key = UserApi.userInfoKey(userId);
		try {
			RedisTemplate<String, UserBaseInfo> template = templateBuilder.buildRedisTemplate(UserBaseInfo.class);
			template.delete(key);
		} catch (Exception e) {
			logger.error("[deleteUserInfo]",e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 保存用户手机号信息
	 * @param userId
	 * @param phone
	 * @param appId
	 */
	public void saveUserPhoneInfo(List<UserBaseInfo> infos){
		if(CollectionUtils.isEmpty(infos)){
			return;
		}
		for(int i = 0 ; i < infos.size();i++){
			UserBaseInfo info = infos.get(i);
			saveUserPhoneInfo(info.getUserId(), info.getUserPhone(), info.getAppId());
		}
	}
	
	/**
	 * 保存用户手机号信息
	 * @param userId
	 * @param phone
	 * @param appId
	 */
	public void saveUserPhoneInfo(Long userId,String phone,String appId){
		String key = UserApi.userPhoneKey(phone, appId);
		try {
			RedisTemplate<String, UserBaseInfo> template = templateBuilder.buildRedisTemplate(UserBaseInfo.class);
			template.opsForValue().set(key, new UserBaseInfo(userId, phone, null, null));
			template.expire(key, USER_INFO_EXPIRE, TimeUnit.DAYS);
		} catch (Exception e) {
			logger.error("[saveUserPhoneInfo]",e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 根据手机号查询用户id
	 * @param userId
	 * @param phone
	 * @param appId
	 */
	public List<UserBaseInfo> getUserPhoneInfo(Set<String> phones,String appId){
		if(CollectionUtils.isEmpty(phones) || StringUtils.isBlank(appId)){
			return null;
		}
		try {
			RedisTemplate<String, UserBaseInfo> template = templateBuilder.buildRedisTemplate(UserBaseInfo.class);
			List<UserBaseInfo> infos = new ArrayList<>(phones.size());
			for(Iterator<String> iterator = phones.iterator();iterator.hasNext();){
				String phone = iterator.next();
				String key = UserApi.userPhoneKey(phone, appId);
				UserBaseInfo info = template.opsForValue().get(key);
				if(info != null){
					infos.add(info);
				}			
			}
			return infos;
		} catch (Exception e) {
			logger.error("[getUserPhoneInfo]",e);
			throw new RedisOptException(e);
		}
	}
	
	/**
	 * 删除用户手机号信息
	 * @param phone
	 * @param appId
	 */
	public void deleteUserPhoneInfo(String phone,String appId){
		String key = UserApi.userPhoneKey(phone, appId);
		try {
			RedisTemplate<String, UserBaseInfo> template = templateBuilder.buildRedisTemplate(UserBaseInfo.class);
			template.delete(key);
		} catch (Exception e) {
			logger.error("[deleteUserPhoneInfo]",e);
			throw new RedisOptException(e);
		}
	}
}
