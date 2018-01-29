package com.yryz.quanhu.behavior.reward.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.behavior.reward.entity.RewardCount;

@Mapper
public interface RewardCountDao {

    int addCountByTargetId(RewardCount record);

    int insertSelective(RewardCount record);

    RewardCount selectByTargetId(Long targetId);
}