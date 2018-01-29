package com.yryz.quanhu.behavior.reward.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yryz.quanhu.behavior.reward.api.RewardInfoApi;
import com.yryz.quanhu.behavior.reward.constants.RewardConstants.QueryType;
import com.yryz.quanhu.behavior.reward.dto.RewardInfoDto;
import com.yryz.quanhu.behavior.reward.entity.RewardInfo;

/**
* @author wangheng
* @date 2018年1月26日 下午5:10:38
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RewardTest {

    @Reference
    RewardInfoApi rewardInfoApi;

    /**  
    * @Description: 打赏发起
    * @author wangheng
    * @param @throws JsonProcessingException
    * @return void
    * @throws  
    */
    @Test
    public void test001() throws JsonProcessingException {
        RewardInfo info = new RewardInfo();
        info.setCreateUserId(729671306726400000L);
        info.setGiftId(1L);
        info.setGiftNum(1);
        info.setGiftPrice(100L);
        info.setModuleEnum("0091");
        info.setResourceId(728714439552114688L);
        info.setRewardPrice(100L);
        info.setToUserId(724007310011252736L);
        System.out.println(new ObjectMapper().writeValueAsString(rewardInfoApi.reward(info)));
    }

    /**  
    * @Description: 分页条件查询
    * @author wangheng
    * @param @throws JsonProcessingException
    * @return void
    * @throws  
    */
    @Test
    public void test002() throws JsonProcessingException {
        RewardInfoDto dto = new RewardInfoDto();
        dto.setQueryType(QueryType.my_reward_resource_list);
        dto.setCreateUserId(729671306726400000L);
        System.out.println(new ObjectMapper().writeValueAsString(rewardInfoApi.pageByCondition(dto, true)));
    }
}
