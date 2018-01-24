package com.yryz.quanhu.resource.release.config.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yryz.quanhu.resource.release.config.api.ReleaseConfigApi;

/**
* @author wangheng
* @date 2018年1月24日 下午12:52:40
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReleaseConfigTest {

    @Reference
    private ReleaseConfigApi eleaseConfigApi;

    @Test
    public void test001() throws JsonProcessingException {
        System.out.println(new ObjectMapper().writeValueAsString(eleaseConfigApi.getTemplate(-1L)));
    }

    @Test
    public void test002() {
    }
}
