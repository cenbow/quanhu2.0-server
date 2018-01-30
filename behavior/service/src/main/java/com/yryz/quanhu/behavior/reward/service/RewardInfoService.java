package com.yryz.quanhu.behavior.reward.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.behavior.reward.dto.RewardInfoDto;
import com.yryz.quanhu.behavior.reward.entity.RewardInfo;
import com.yryz.quanhu.behavior.reward.vo.RewardFlowVo;
import com.yryz.quanhu.behavior.reward.vo.RewardInfoVo;

import java.util.List;

/**
 * @author wangheng
 * @Description: 资源打赏
 * @date 2018年1月27日 上午11:36:41
 */
public interface RewardInfoService {

    int insertSelective(RewardInfo record);

    List<RewardInfoVo> selectByCondition(RewardInfoDto dto);

    PageList<RewardInfoVo> pageByCondition(RewardInfoDto dto, boolean isCount);

    RewardInfo selectByKid(Long id);

    int updateByKid(RewardInfo record);

    PageList<RewardFlowVo> selectRewardFlow(Long userId, Integer currentPage, Integer pageSize);
}
