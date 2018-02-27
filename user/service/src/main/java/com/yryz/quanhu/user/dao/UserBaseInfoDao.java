/*
 * UserBaseinfoMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-12 Created
 */
package com.yryz.quanhu.user.dao;

import java.util.List;
import java.util.Map;

import com.yryz.quanhu.user.entity.UserRegInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.entity.UserBaseInfo;
@Mapper
public interface UserBaseInfoDao {

    int insert(UserBaseInfo record);

    int update(UserBaseInfo record);
    
    UserBaseInfo checkUserByNname(@Param("appId")String appId,@Param("nickName")String nickName);
    
    UserBaseInfo selectByUserId(@Param("userId")String userId);
    
    List<UserBaseInfo> getUserByParams(Map<String,Object> params);
    
    List<UserBaseInfo> getByUserIds(@Param("userIds")List<String> userIds);
    
    List<UserBaseInfo> getByPhones(@Param("phones")List<String> phones,@Param("appId")String appId);
    
    List<String> getDevIdByUserIds(@Param("userIds")List<String> userIds);
    
    List<UserBaseInfo> getAdminList(AdminUserInfoDTO custInfoDTO);
    
    List<String> getUserIdList(AdminUserInfoDTO custInfoDTO);
    
    List<String> getUserIdByExactParam(Map<String,Object> params);
    
    List<Long> getUserIdByCreateDate(@Param("startDate")String startDate,@Param("endDate")String endDate);

    List<UserBaseInfo> getUserListByCreateDate(@Param("startDate")String startDate,@Param("endDate")String endDate);

    List<UserBaseInfo> getAllByUserIds(@Param("userIds")List<Long> userIds);

    List<UserRegInfo> listMsgUserInfo(AdminUserInfoDTO custInfoDTO);

}