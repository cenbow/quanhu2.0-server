/*
 * UserStarAuthMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2018-01-12 Created
 */
package com.yryz.quanhu.user.dao;

import java.util.List;

import com.yryz.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.user.dto.StarAuthParamDTO;
import com.yryz.quanhu.user.entity.UserStarAuth;

@Mapper
public interface UserStarAuthDao extends BaseDao{
	/**
	 * 保存达人认证信息
	 * @param record
	 * @return
	 */
    int save(UserStarAuth record);
    
    /**
     * 查询单条达人认证信息
     * @param 
     * @return
     */
    UserStarAuth get(@Param("userId")String userId,@Param("idCard")String idCard,@Param("appId")String appId);
    
    /**
     * 批量查询用户认证信息
     * @param userIds
     * @return
     */
    List<UserStarAuth> getByUserIds(@Param("userIds")List<String> userIds);
    
    /**
     * 更新达人认证信息
     * @param record
     * @return
     */
    int update(UserStarAuth record);

    /**
     * 后台达人认证信息列表
     * @param paramDTO
     * @return
     */
    List<UserStarAuth> listByParams(StarAuthParamDTO paramDTO);
    
    /**
     * app端达人列表
     * @param paramDTO
     * @return
     */
    List<UserStarAuth> starList(StarAuthParamDTO paramDTO);

    /**
     * 标签达人列表
     */
    List<UserStarAuth> labelStarList(StarAuthParamDTO paramDTO);

    /**
     * 获取达人推荐最大权重
     * @return
     */
    Integer getStarMaxWeight();
    
    /**
     * 统计达人总数
     * @return
     */
    Integer countStar();

    /**
     * 后台更新达人审核记录
     * @param starAuth
     * @return
     */
    int updateAuditStatus(UserStarAuth starAuth);

    /**
     * 后台推荐更新达人记录
     * @param starAuth
     * @return
     */
    int updateRecommendStatus(UserStarAuth starAuth);

}