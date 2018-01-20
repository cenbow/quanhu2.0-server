/*
 * CoterieMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2017-08-23 Created
 */
package com.yryz.quanhu.coterie.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.coterie.entity.Coterie;
import com.yryz.quanhu.coterie.entity.CoterieSearch;

/**
 * 私圈信息
 * @author jk
 *
 */
public interface CoterieMapper {
    int insert(Coterie record);

    int insertSelective(Coterie record);

    Coterie selectByCoterieId(String coterieId);
    
    List<Coterie> selectListByStatus(Integer status);
    
    List<Coterie> selectListByCoterieIdList(@Param("coterieIdList") List<String> coterieIdList);
    
    List<Coterie> selectListByCoterie(Coterie record);
    
    List<Coterie> findPageByStatus(@Param("circleId") String circleId, @Param("start") Integer start, @Param("pageSize") Integer pageSize, @Param("status") Byte status);
    
    List<Coterie> findPage(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    int updateByCoterieIdSelective(Coterie record);

    int updateByPrimaryKey(Coterie record);
    
    int updateExpert(@Param("custId") String custId, @Param("isExpert") Byte isExpert);
    
    int updateMemberNum(@Param("coterieId") String coterieId, @Param("newMemberNum") Integer newMemberNum, @Param("oldMemberNum") Integer oldMemberNum);
    
    List<Coterie> selectMyCreateCoterie(@Param("custId") String custId, @Param("circleId") String circleId);
    
    List<Coterie> selectMyCreateCoteriePage(@Param("custId") String custId, @Param("start") Integer start, @Param("pageSize") Integer pageSize, @Param("status") Integer status);
    
    Integer selectMyCreateCoterieCount(@Param("custId") String custId, @Param("status") Integer status);
    
    List<Coterie> selectMyJoinCoterie(@Param("custId") String custId, @Param("circleId") String circleId);
    
    List<Coterie> selectMyJoinCoteriePage(@Param("custId") String custId, @Param("start") Integer start, @Param("pageSize") Integer pageSize);
    
    Integer selectMyJoinCoterieCount(@Param("custId") String custId);
    
    List<Coterie> selectByName(@Param("name") String name);
    
    Coterie selectByCustIdAndCircleId(@Param("custId") String custId, @Param("circleId") String circleId);
    
    List<Coterie> selectBySearchParam(CoterieSearch param);
    
    Integer selectCountBySearchParam(CoterieSearch param);
    
    Integer selectCountByCircleId(@Param("circleId") String circleId, @Param("status") Byte status);
    
    int updateRecommend(@Param("coterieIdList") List<String> coterieIdList, @Param("recommend") Byte recommend);
    
    List<Coterie> selectRecommendList(@Param("circleId") String circleId, @Param("start") Integer start, @Param("pageSize") Integer pageSize);
    
    List<Coterie> selectHeatList(@Param("circleId") String circleId, @Param("expert") Byte expert, @Param("start") Integer start, @Param("pageSize") Integer pageSize);
    
    List<Coterie> selectHeatListByCircleId(@Param("circleId") String circleId, @Param("start") Integer start, @Param("pageSize") Integer pageSize);
    
    List<Coterie> selectLikeName(@Param("circleId") String circleId, @Param("name") String name, @Param("start") Integer start, @Param("pageSize") Integer pageSize);
    
    List<String> selectCircleIdListByOwnerId(@Param("ownerId") String ownerId);
}