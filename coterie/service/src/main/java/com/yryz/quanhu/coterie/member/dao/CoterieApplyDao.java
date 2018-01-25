/*
 * CoterieApplyMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2017-08-23 Created
 */
package com.yryz.quanhu.coterie.member.dao;

import com.yryz.quanhu.coterie.member.entity.CoterieMemberApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 私圈成员申请
 * @author chengyunfei
 *
 */
@Mapper
public interface CoterieApplyDao {
    int insert(CoterieMemberApply record);
    int updateByCoterieApply(CoterieMemberApply record);





    int deleteByUserIdAndCoterieId(Long userId, Long coterieId);

//    int insertSelective(CoterieMemberApply record);

    CoterieMemberApply selectByPrimaryKey(Long kid);

    int updateByPrimaryKey(CoterieMemberApply record);
    
    List<CoterieMemberApply> selectPageByCoterieId(@Param("coterieId") Long coterieId, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    List<CoterieMemberApply> selectByCoterieIdAndCustId(@Param("coterieId") Long coterieId, @Param("custId") Long userId);

    CoterieMemberApply selectWaitingByCoterieIdAndCustId(@Param("coterieId") Long coterieId, @Param("custId") Long userId);
    
    Integer selectNewMemberNum(Long coterieId);
}