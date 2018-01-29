package com.yryz.quanhu.behavior.reward.api;

import java.util.Map;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.reward.dto.RewardInfoDto;
import com.yryz.quanhu.behavior.reward.entity.RewardInfo;
import com.yryz.quanhu.behavior.reward.vo.RewardInfoVo;

/**
* @Description: 资源打赏
* @author wangheng
* @date 2018年1月27日 上午11:53:31
*/
public interface RewardInfoApi {

    /**  
    * @Description: 资源打赏
    * @author wangheng
    * @param @param record
    * @param @return
    * @return int
    * @throws  
    */
    Response<Map<String, Object>> reward(RewardInfo record);

    /**  
    * @Description: 打赏记录 [付款成功]
    * @author wangheng
    * @param @param dto
    * @param @param isCount
    * @param @return
    * @return PageList<RewardInfoVo>
    * @throws  
    */
    Response<PageList<RewardInfoVo>> pageByCondition(RewardInfoDto dto, boolean isCount);

    /**  
    * @Description: 更新打赏记录
    * @author wangheng
    * @param @param record
    * @param @return
    * @return int
    * @throws  
    */
    Response<Integer> updateByKid(RewardInfo record);
}
