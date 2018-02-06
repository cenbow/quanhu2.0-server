/*
 * CoterieMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2017-08-23 Created
 */
package com.yryz.quanhu.coterie.coterie.dao;

import com.yryz.quanhu.coterie.coterie.entity.Coterie;
import com.yryz.quanhu.coterie.coterie.entity.CoterieSearch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 私圈信息
 * @author jk
 *
 */
@Mapper
public interface CoterieMapper {
    int insertSelective(Coterie record);

    Coterie selectByCoterieId(Long coterieId);
    
    List<Coterie> selectListByCoterieIdList(@Param("coterieIdList") List<Long> coterieIdList);
    
    List<Coterie> selectListByCoterie(Coterie record);
    
    List<Coterie> findPageByStatus(@Param("start") Integer start, @Param("pageSize") Integer pageSize, @Param("status") Byte status);

    List<Coterie> findPage(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    int updateByCoterieIdSelective(Coterie record);

    int updateExpert(@Param("custId") String custId, @Param("isExpert") Byte isExpert);

    int updateMemberNum(@Param("coterieId") Long coterieId, @Param("newMemberNum") Integer newMemberNum, @Param("oldMemberNum") Integer oldMemberNum);

    List<Coterie> selectMyCreateCoterie(@Param("custId") String custId);

    /**
     * 创建的私圈数量(已上架或审核中)
     * @param custId
     * @return
     */
    Integer selectMyCreateCoterieCount(@Param("custId") String custId);
    
    /**
     * 个人主页创建的私圈
     * @return
     */
    List<Coterie> selectCreateCoterie(@Param("custId") String custId,@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    List<Coterie> selectMyJoinCoterie(@Param("custId") String custId);
    
    List<Coterie> selectMyJoinCoteriePage(@Param("custId") String custId, @Param("start") Integer start, @Param("pageSize") Integer pageSize);
    
    Integer selectMyJoinCoterieCount(@Param("custId") String custId);
    
    List<Coterie> selectByName(@Param("name") String name);
    
    int updateRecommend(@Param("coterieIdList") List<Long> coterieIdList, @Param("recommend") Byte recommend);
    
    List<Long> selectKidByCreateDate(@Param("startDate") String startDate, @Param("endDate") String endDate);
    
    List<Coterie> selectByKids(@Param("kidList") List<Long> kidList);
    
    /**
     * 获取推荐的私圈
     * @return
     */
    List<Coterie> selectRecommendList();
    List<Coterie> selectOrderByMemberNum();
}