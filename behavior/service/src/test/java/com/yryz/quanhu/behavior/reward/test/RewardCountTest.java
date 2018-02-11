package com.yryz.quanhu.behavior.reward.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yryz.quanhu.behavior.reward.entity.RewardCount;
import com.yryz.quanhu.behavior.reward.service.RewardCountService;

/**
* @author wangheng
* @date 2018年1月26日 下午5:10:38
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RewardCountTest {

    @Autowired
    RewardCountService rewardCountService;

    @Test
    public void test001() throws JsonProcessingException {
        System.out
                .println(new ObjectMapper().writeValueAsString(rewardCountService.selectByTargetId(356367633866752L)));
    }

    @Test
    public void test002() throws JsonProcessingException {
        // 更新被打赏者 统计
        RewardCount uBeCount = new RewardCount();
        uBeCount.setTargetId(356367633866752L);
        uBeCount.setTotalRewardedCount(0);
        System.out.println(new ObjectMapper().writeValueAsString(rewardCountService.addCountByTargetId(uBeCount)));
    }

}
