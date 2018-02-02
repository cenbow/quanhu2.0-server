package com.yryz.quanhu.resource.release.info.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoAdminApi;

/**
* @Description: 平台文章
* @author wangheng
* @date 2018年1月24日 下午8:00:29
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReleaseAdminInfoTest {

    @Reference
    ReleaseInfoAdminApi releaseInfoAdminApi;

    /**  
    * @Description: 文章下架
    * @author wangheng
    * @param @throws JsonProcessingException
    * @return void
    * @throws  
    */
    @Test
    public void test001() throws JsonProcessingException {
        System.out.println(new ObjectMapper().writeValueAsString(
                releaseInfoAdminApi.batchShelve(new Long[] { 741242051704225792L }, (byte) 11, 14046L)));
    }
}
