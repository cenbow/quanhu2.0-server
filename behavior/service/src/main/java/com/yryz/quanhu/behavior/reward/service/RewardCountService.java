package com.yryz.quanhu.behavior.reward.service;

import com.yryz.quanhu.behavior.reward.entity.RewardCount;

/**
* @Description: 资源统计
* @author wangheng
* @date 2018年1月27日 上午11:36:41
*/
public interface RewardCountService {
    /**  
    * @Description: 更新打赏 统计（使用数据库 +计算）  
    * @author wangheng
    * @param @param record
    * @param @return
    * @return int
    * @throws  
    */
    int addCountByTargetId(RewardCount record);

    /**  
    * @Description: 查询打赏
    * @author wangheng
    * @param @param targetId
    * @param @return
    * @return RewardCount
    * @throws  
    */
    RewardCount selectByTargetId(Long targetId);
}
