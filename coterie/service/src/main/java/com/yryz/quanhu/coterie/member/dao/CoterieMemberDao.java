/*
 * CoterieMemberMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2017-08-23 Created
 */
package com.yryz.quanhu.coterie.member.dao;

import com.yryz.quanhu.coterie.member.dto.CoterieMemberSearchDto;
import com.yryz.quanhu.coterie.member.entity.CoterieMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 私圈成员
 * @author chengyunfei
 *
 */
public interface CoterieMemberDao {
    int deleteByPrimaryKey(Long kid);
    
    int deleteByCustIdAndCoterieId(@Param("userId") Long userId, @Param("coterieId") Long coterieId);

    int insert(CoterieMember record);

    int insertSelective(CoterieMember record);

    CoterieMember selectByPrimaryKey(Long kid);

    int updateByPrimaryKeySelective(CoterieMember record);

    int updateByPrimaryKey(CoterieMember record);

    int updateByCustIdAndCoterieId(CoterieMember record);

    List<CoterieMember> selectPageByCoterieId(@Param("coterieId") Long coterieId, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    CoterieMember selectByCoterieIdAndCustId(@Param("coterieId") Long coterieId, @Param("custId") Long userId);
    
    int selectCountByCoterieId(Long coterieId);
    
    List<CoterieMember> selectBySearchParam(CoterieMemberSearchDto param);
    
    int selectCountBySearchParam(CoterieMemberSearchDto param);
}