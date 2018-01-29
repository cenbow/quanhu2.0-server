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
        /**
        {
        "giftId": 1,
        "giftNum": 1,
        "giftPrice": 100,
        "moduleEnum": "0092",
        "resourceId": 737186863841918976,
        "toUserId": 724011759597371392
        }
         */
        RewardInfo info = new RewardInfo();
        info.setCreateUserId(727447149320273920L);
        //        info.setCoterieId(9382818189L);
        info.setRewardPrice(80L);
        info.setGiftId(1L);
        info.setGiftNum(1);
        info.setGiftPrice(100L);
        info.setModuleEnum("0092");
        info.setResourceId(737186863841918976L);
        info.setToUserId(724011759597371392L);
        System.out.println(new ObjectMapper().writeValueAsString(rewardInfoApi.reward(info)));
    }

    /**  
    * @Description: 分页条件查询(我打赏的)
    * @author wangheng
    * @param @throws JsonProcessingException
    * @return void
    * @throws  
    */
    @Test
    public void test002() throws JsonProcessingException {
        RewardInfoDto dto = new RewardInfoDto();
        // 我打赏的-人-列表
        // dto.setQueryType(QueryType.my_reward_user_list);

        // 我打赏的-资源-列表
        dto.setQueryType(QueryType.my_reward_resource_list);
        dto.setCreateUserId(727447149320273920L);
        System.out.println(new ObjectMapper().writeValueAsString(rewardInfoApi.pageByCondition(dto, true)));
    }
}
