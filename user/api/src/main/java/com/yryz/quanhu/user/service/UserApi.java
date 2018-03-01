package com.yryz.quanhu.user.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yryz.common.context.Context;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.dto.UpdateBaseInfoDTO;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import com.yryz.quanhu.user.vo.UserLoginSimpleVO;
import com.yryz.quanhu.user.vo.UserRegInfoVO;
import com.yryz.quanhu.user.vo.UserSimpleNoneOtherVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;
/**
 * 用户基础信息服务
 * @author danshiyu
 *
 */
public interface UserApi {
	/**
	 * 用户信息key
	 * @param userId
	 * @return
	 */
	static String userInfoKey(Long userId){
		return String.format("%s:%s", Context.getProperty("user.baseinfo"),userId);
	}
	/**
	 * 用户手机号缓存key
	 * @param phone
	 * @param appId
	 * @return
	 */
	static String userPhoneKey(String phone,String appId){
		return String.format("%s:%s:%s", Context.getProperty("user.phone.userId"),phone,appId);
	}
	/**
	 * 查询用户简要信息
	 * @param userId
	 * @return
	 */
	Response<UserSimpleVO> getUserSimple(Long userId);
	/**
	 * 查询用户简要信息并聚合关系信息<br/>
	 * userId不为空表示会聚合关系信息
	 * @param userId
	 * @param friendId 好友id
	 * @return
	 */
	Response<UserSimpleVO> getUserSimple(Long userId,Long friendId);
	
	/**
	 * 查询用户简要信息（没有聚合其他数据）
	 * @param userIds
	 * @return
	 */
	Response<Map<String,UserSimpleNoneOtherVO>> getUserSimpleNoneOtherInfo(Set<String> userIds);
	
	/**
	 * 查询用户简要信息
	 * @param userIds 用户id
	 * @return
	 */
	Response<Map<String,UserSimpleVO>> getUserSimple(Set<String> userIds);
	/**
	 * 查询用户简要信息<br/>
	 * userId不为空表示查询好友的信息会聚合好友信息
	 * @param firendIds 好友id
	 * @param userId 用户id
	 * @return
	 */
	Response<Map<String,UserSimpleVO>> getUserSimple(Long userId,Set<String> firendIds);
	/**
	 * 用户登录返回的用户信息
	 * @param userId
	 * @return
	 */
	Response<UserLoginSimpleVO> getUserLoginSimpleVO(Long userId);
	/**
	 * 用户登录返回的用户信息<br/>
	 * friendId不为空表示会聚合关系信息
	 * @param userId
	 * @param friendId 好友id
	 * @return
	 */
	Response<UserLoginSimpleVO> getUserLoginSimpleVO(Long userId,Long friendId);
	
	/**
	 * 根据电话检索用户
	 * 
	 * @param phone
	 * @param appId 应用id
	 * @return
	 */
	Response<String> getUserIdByPhone(String phone,String appId);

	
	/**
	 * 根据电话检索用户
	 * 
	 * @param phones
	 * @param appId 应用id
	 * @return
	 */
	Response<Map<String, String>> getUserIdByPhone(Set<String> phones,String appId);
	
	/**
	 * 查询用户基本信息
	 * @param userIds
	 * @return
	 */
	Response<Map<String,UserBaseInfoVO>> getUser(Set<String> userIds);
	
	/**
	 * 根据手机号、昵称、注册时间模糊查询用户id
	 * @param custInfoDTO
	 * @return 昵称需要加特殊字符转义
	 */
	Response<List<String>> getUserIdByParams(AdminUserInfoDTO custInfoDTO);

	/**
	 * 获取用户推送设备号
	 * @param userId
	 * @return
	 */
	Response<String> getDeviceIdByUserId(Long userId);
	
	/**
	 * 获取用户推送设备号
	 * @param userId
	 * @return
	 */
	Response<List<String>> getDeviceIdByUserId(List<String> userId);
	/**
	 * 用户信息更新
	 * @param infoDTO
	 * @return
	 */
	Response<Boolean> updateUserInfo(UpdateBaseInfoDTO infoDTO);
	
	/**
	 * 管理后台查询用户列表
	 * @param pageNo
	 * @param pageSize
	 * @param custInfoDTO
	 * @return
	 * @Description 昵称需要加特殊字符转义
	 */
	Response<PageList<UserBaseInfoVO>> listUserInfo(int pageNo,int pageSize,AdminUserInfoDTO custInfoDTO);

	/**
	 * 查询一段时间的全部用户ID，不分状态
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Response<List<Long>> getUserIdByCreateDate(String startDate,String endDate);

	/**
	 * 查询一段时间的全部用户信息，不分状态
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Response<List<UserBaseInfoVO>> getUserListByCreateDate(String startDate, String endDate);
    
	/**
	 * 根据用户ID查询全部用户，不分状态
	 * @param userIds
	 * @return
	 */
	Response<List<UserBaseInfoVO>> getAllByUserIds(List<Long> userIds);

	/**
	 * 管理后台message模块查询用户列表
	 * @param pageNo
	 * @param pageSize
	 * @param custInfoDTO
	 * @return
	 * @Description 昵称需要加特殊字符转义
	 */
	Response<PageList<UserRegInfoVO>> listMsgUserInfo(int pageNo, int pageSize, AdminUserInfoDTO custInfoDTO);
}
