package com.yryz.quanhu.behavior.gift.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yryz.quanhu.behavior.gift.dto.GiftInfoDto;
import com.yryz.quanhu.behavior.gift.entity.GiftInfo;
import com.yryz.quanhu.behavior.gift.service.GiftInfoService;

/**
* @author wangheng
* @date 2018年1月26日 下午5:10:38
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class GiftInfoTest {

    @Autowired
    GiftInfoService giftInfoService;
    
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

        System.out.println(new ObjectMapper().writeValueAsString(giftInfoService.pageByCondition(dto, true)));
    }
    
    /**  
    * @Description: selectByKids
    * @author wangheng
    * @param @throws JsonProcessingException
    * @return void
    * @throws  
    */
    @Test
    public void test002() throws JsonProcessingException {
        Set<Long> kids = new HashSet<>();
        kids.add(3L);
        kids.add(2L);
        System.out.println(new ObjectMapper().writeValueAsString(giftInfoService.selectByKids(kids)));
    }
    
    /**  
    * @Description: 更新礼物
    * @author wangheng
    * @param @throws JsonProcessingException
    * @return void
    * @throws  
    */
    @Test
    public void test003() throws JsonProcessingException {
        GiftInfo record = new GiftInfo();
        record.setKid(3L);
        record.setGiftName("豪车");
        System.out.println(new ObjectMapper().writeValueAsString(giftInfoService.updateByKid(record)));
    }
    
    /**  
    * @Description: 禮物詳情
    * @author wangheng
    * @param @throws JsonProcessingException
    * @return void
    * @throws  
    */
    @Test
    public void test004() throws JsonProcessingException {
        System.out.println(new ObjectMapper().writeValueAsString(giftInfoService.selectByKid(1L)));
    }
}
