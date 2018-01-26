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
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 私圈成员
 * @author chengyunfei
 *
 */
@Mapper
public interface CoterieMemberDao {

    int insert(CoterieMember record);

    int updateByCoterieMember(CoterieMember record);

    CoterieMember selectByCoterieIdAndUserId(@Param("coterieId") Long coterieId, @Param("userId") Long userId);




    int deleteByPrimaryKey(Long kid);
    
    int deleteByUserIdAndCoterieId(@Param("userId") Long userId, @Param("coterieId") Long coterieId, @Param("reason") String reason);

//    int insertSelective(CoterieMember record);

    CoterieMember selectByPrimaryKey(Long kid);

    int updateByPrimaryKeySelective(CoterieMember record);

    int updateByPrimaryKey(CoterieMember record);

    List<CoterieMember> selectPageByCoterieId(@Param("coterieId") Long coterieId, @Param("start") Integer start, @Param("pageSize") Integer pageSize);


    int selectCountByCoterieId(Long coterieId);
    
    List<CoterieMember> selectBySearchParam(CoterieMemberSearchDto param);
    
    int selectCountBySearchParam(CoterieMemberSearchDto param);
}