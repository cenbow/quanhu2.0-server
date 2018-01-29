package com.yryz.quanhu.behavior.gift.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.behavior.gift.entity.GiftInfo;

@Mapper
public interface GiftInfoDao {

    int insertSelective(GiftInfo record);

    List<GiftInfo> selectByCondition(GiftInfo dto);

    long countByCondition(GiftInfo dto);

    GiftInfo selectByKid(Long id);

    int updateByKid(GiftInfo record);
}