package com.yryz.quanhu.behavior.reward.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.reward.dto.RewardInfoDto;
import com.yryz.quanhu.behavior.reward.entity.RewardInfo;
import com.yryz.quanhu.behavior.reward.vo.RewardFlowVo;
import com.yryz.quanhu.behavior.reward.vo.RewardInfoVo;

import java.util.Map;

/**
 * @author wangheng
 * @Description: 资源打赏
 * @date 2018年1月27日 上午11:53:31
 */
public interface RewardInfoApi {

    /**
     * @param @param  record
     * @param @return
     * @return int
     * @Description: 资源打赏
     * @author wangheng
     */
    Response<Map<String, Object>> reward(RewardInfo record);

    /**
     * @param @param  dto
     * @param @param  isCount
     * @param @return
     * @return PageList<RewardInfoVo>
     * @Description: 打赏记录 [付款成功]
     * @author wangheng
     */
    Response<PageList<RewardInfoVo>> pageByCondition(RewardInfoDto dto, boolean isCount);

    /**
     * @param @param  record
     * @param @return
     * @return int
     * @Description: 更新打赏记录
     * @author wangheng
     */
    Response<Integer> updateByKid(RewardInfo record);

    /**
     * 查询打赏明细流水
     *
     * @param userId
     * @param currentPage
     * @param pageSize
     * @return PageList<RewardFlowVo>
     */
    Response<PageList<RewardFlowVo>> selectRewardFlow(Long userId, Integer currentPage, Integer pageSize);
}
