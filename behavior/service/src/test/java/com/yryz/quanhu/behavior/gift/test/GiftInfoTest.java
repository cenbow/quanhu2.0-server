package com.yryz.quanhu.behavior.gift.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yryz.quanhu.behavior.gift.api.GiftInfoApi;
import com.yryz.quanhu.behavior.gift.dto.GiftInfoDto;

/**
* @author wangheng
* @date 2018年1月26日 下午5:10:38
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class GiftInfoTest {

    @Reference
    GiftInfoApi giftInfoApi;

    /**  
    * @Description: 礼物分页列表
    * @author wangheng
    * @param @throws JsonProcessingException
    * @return void
    * @throws  
    */
    @Test
    public void test001() throws JsonProcessingException {
        GiftInfoDto dto = new GiftInfoDto();

        System.out.println(new ObjectMapper().writeValueAsString(giftInfoApi.pageByCondition(dto, true)));
    }
}
