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

    private static Long coterieId = 233665981858L;
    private static Long memberId = 730941139577331712L;
    private static Long userId = 730941139577331712L;

    @Test
    public void join() {
//        Response response = coterieMemberAPI.join(730941139577331712L,5536534415L,"我要加入,不审核的");
        Response response = coterieMemberAPI.join(111111111111111L,233665981858L,"我要加入,要审核的");
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void audit() {
        Response response = coterieMemberAPI.audit(1L,730941139577331712L,233665981858L,null);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void banSpeak() {
        Response response = coterieMemberAPI.banSpeak(1L,730941139577331712L,233665981858L, 1);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void kick() {
        Response response = coterieMemberAPI.kick(1L,730941139577331712L,233665981858L,"就要踢你");
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void quit() {
        Response response = coterieMemberAPI.quit(730941139577331712L,233665981858L);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void permission() {
        Response response = coterieMemberAPI.permission(730941139577331712L,233665981858L);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void newMemberNum() {
        Response response = coterieMemberAPI.queryNewMemberNum(233665981858L);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void memberApplyList() {
        Response response = coterieMemberAPI.queryMemberApplyList(233665981858L,1,10);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void memberList() {
        Response response = coterieMemberAPI.queryMemberList(233665981858L,1,10);
        System.out.println(JsonUtils.toFastJson(response));
    }
}
