package com.yryz.quanhu.support;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.quanhu.support.id.api.IdAPI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/18
 * @description
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class IdTest {
    @Reference
    IdAPI idAPI;

    @Test
    public void getIdTest() {
        Long quanhuUser = idAPI.getKid("quanhu_user");
        System.out.println("quanhuUser " + quanhuUser);
    }
}
