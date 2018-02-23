package com.yryz.quanhu.behavior.reward.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yryz.quanhu.behavior.reward.api.RewardInfoApi;
import com.yryz.quanhu.behavior.reward.constants.RewardConstants;
import com.yryz.quanhu.behavior.reward.constants.RewardConstants.QueryType;
import com.yryz.quanhu.behavior.reward.dto.RewardInfoDto;
import com.yryz.quanhu.behavior.reward.entity.RewardInfo;
import com.yryz.quanhu.behavior.reward.service.RewardInfoService;
import com.yryz.quanhu.behavior.reward.service.impl.RewardOrderNotifyService;
import com.yryz.quanhu.order.sdk.dto.OutputOrder;

/**
* @author wangheng
* @date 2018年1月26日 下午5:10:38
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RewardTest {

    @Reference
    RewardInfoApi rewardInfoApi;

    @Autowired
    RewardOrderNotifyService rewardOrderNotifyService;
    
    @Autowired
    private RewardInfoService rewardInfoService;
    
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

        /*// 我打赏的-资源-列表
        dto.setQueryType(QueryType.my_reward_resource_list);
        dto.setCreateUserId(360712374173696L);*/
        
        // 我打赏的-资源-列表
        dto.setQueryType(QueryType.reward_my_resource_list);
        dto.setToUserId(356393596608512L);
        System.out.println(new ObjectMapper().writeValueAsString(rewardInfoApi.pageByCondition(dto, true)));
    }
    
    /**  
    * @Description: 打賞訂單回調
    * @author wangheng
    * @param @throws JsonProcessingException
    * @return void
    * @throws  
    */
    @Test
    public void test003() throws JsonProcessingException {
        OutputOrder outputOrder = new OutputOrder();
        outputOrder.setBizContent(
                "{\"createUserId\":730921949663453184,\"createDate\":null,\"lastUpdateUserId\":null,\"lastUpdateDate\":null,\"id\":null,\"kid\":null,\"rewardPrice\":null,\"rewardStatus\":null,\"giftId\":6,\"giftNum\":1,\"giftPrice\":100,\"orderId\":null,\"moduleEnum\":\"1005\",\"resourceId\":750261431486922752,\"coterieId\":0,\"toUserId\":747592865226702848,\"appId\":null,\"revision\":null}");
        outputOrder.setCoterieId(0L);
        outputOrder.setCreateDate(new Date());
        outputOrder.setCreateUserId(730921949663453184L);
        outputOrder.setModuleEnum("1005");
        outputOrder.setResourceId(750261431486922752L);
        outputOrder.setOrderId(20180207164694L);
        outputOrder.setCost(100L);
        outputOrder.setPayType(10);
        rewardOrderNotifyService.notify(outputOrder);
    }
    
    /**  
     * @Description: 打赏、收到打赏流水
     * @author wangheng
     * @param @throws JsonProcessingException
     * @return void
     * @throws  
     */
     @Test
     public void test004() throws JsonProcessingException {
         System.out.println(new ObjectMapper().writeValueAsString(rewardInfoService.selectRewardFlow(356393596608512L, 1, 30)));
     }
     
     /**  
      * @Description: 打赏成功 更新狀態
      * @author wangheng
      * @param @throws JsonProcessingException
      * @return void
      * @throws  
      */
      @Test
    public void test005() throws JsonProcessingException {
        RewardInfo upInfo = new RewardInfo();
        upInfo.setKid(378033735573504L);
        //upInfo.setRewardPrice(rewardedPrice);
        upInfo.setRewardStatus(RewardConstants.reward_status_pay_success);
        // 为用户打赏/收益明细 缓存预留用户参数，DB实际不更新
        upInfo.setCreateUserId(360812374769664L);
        upInfo.setToUserId(368619989000192L);
        upInfo.setResourceId(377959471226880L);
        
        System.out.println(new ObjectMapper().writeValueAsString(rewardInfoService.updateByKid(upInfo)));
    }
}
