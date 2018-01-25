package com.yryz.quanhu.coterie;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.coterie.member.service.CoterieMemberAPI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoterieMemberTest {
    @Reference
    private CoterieMemberAPI coterieMemberAPI;

    @Test
    public void banSpeak() {
        Response response = coterieMemberAPI.banSpeak(1L,1L,1);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void kick() {
        Response response = coterieMemberAPI.kick(1L,1L,"就要踢你");
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void quit() {
        Response response = coterieMemberAPI.quit(1L,1L);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void join() {
        Response response = coterieMemberAPI.join(1L,1L,"我要加入");
        System.out.println(JsonUtils.toFastJson(response));
    }
}
