package com.yryz.quanhu.behavior.reward.dao;

import com.yryz.quanhu.behavior.reward.dto.RewardInfoDto;
import com.yryz.quanhu.behavior.reward.entity.RewardInfo;
import com.yryz.quanhu.behavior.reward.vo.RewardFlowVo;
import com.yryz.quanhu.behavior.reward.vo.RewardInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RewardInfoDao {

    int insertSelective(RewardInfo record);

    List<RewardInfoVo> selectByCondition(RewardInfoDto dto);

    long countByCondition(RewardInfoDto dto);

    RewardInfo selectByKid(Long id);

    int updateByKid(RewardInfo record);

    List<RewardFlowVo> selectRewardFlow(Long userId);
}