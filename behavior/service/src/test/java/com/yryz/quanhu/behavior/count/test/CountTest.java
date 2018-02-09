package com.yryz.quanhu.behavior.count.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yryz.quanhu.behavior.count.api.CountApi;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.behavior.count.test
 * @Desc:
 * @Date: 2018/2/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CountTest {

    @Reference
    CountApi countApi;

    @Test
    public void getCount() throws JsonProcessingException {
        List<Long> kids = Lists.newArrayList();
        kids.add(367826804170752L);
        kids.add(367797729255424L);
        System.out.print(countApi.getCount("10,11", kids, null));
    }

    @Test
    public void getCountFlag() throws JsonProcessingException {
        List<Long> kids = Lists.newArrayList();
        kids.add(367826804170752L);
        kids.add(367797729255424L);
        System.out.print(countApi.getCountFlag("10,11", kids, null, 355010681618432L));
    }
}
