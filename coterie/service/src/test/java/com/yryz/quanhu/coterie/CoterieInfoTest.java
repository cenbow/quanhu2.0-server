package com.yryz.quanhu.coterie;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.member.service.CoterieMemberAPI;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CoterieInfoTest {
    @Reference
    private CoterieApi coterieApi;

    private static Long memberId = 730941139577331712L;
    private static Long userId = 737251236811898880L;
    private static String reason_waitting = "【测试】【待审】 " + System.currentTimeMillis();
    private static String reason_join = "【测试】【不审】 " + System.currentTimeMillis();

    private static Long coterieId = 9382818189L;

    @Test
    public void test010_getByKids() {
        List list = new ArrayList();
        list.add(coterieId);
        Response response = coterieApi.getByKids(list);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void test020_queryPage() {
        Response response = coterieApi.queryPage(1,10);
        System.out.println(JsonUtils.toFastJson(response));
    }

//    @Test
//    public void test020_audit() {
//
//        try {
//            Response response = coterieMemberAPI.audit(userId,memberId,coterieId,null);
//            System.out.println(JsonUtils.toFastJson(response));
//        } catch (QuanhuException e) {
//            e.getMsg();
//            e.getErrorMsg();
//            System.out.println(JsonUtils.toFastJson(e));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    public void test030_banSpeak() {
//        Response response = coterieMemberAPI.banSpeak(userId,memberId,coterieId, 1);
//        System.out.println(JsonUtils.toFastJson(response));
//    }
//
//    @Test
//    public void test040_permission() {
//        Response response = coterieMemberAPI.permission(userId,coterieId);
//        System.out.println(JsonUtils.toFastJson(response));
//    }
//
//    @Test
//    public void test050_newMemberNum() {
//        Response response = coterieMemberAPI.queryNewMemberNum(coterieId);
//        System.out.println(JsonUtils.toFastJson(response));
//    }
//
//    @Test
//    public void test060_memberApplyList() {
//        Response response = coterieMemberAPI.queryMemberApplyList(coterieId,1,10);
//        System.out.println(JsonUtils.toFastJson(response));
//    }
//
//    @Test
//    public void test070_memberList() {
//        Response response = coterieMemberAPI.queryMemberList(coterieId,1,10);
//        System.out.println(JsonUtils.toFastJson(response));
//    }
//
//    @Test
//    public void test080_kick() {
//        Response response = coterieMemberAPI.kick(userId,memberId,coterieId,"就要踢你");
//        System.out.println(JsonUtils.toFastJson(response));
//    }
//
//    @Test
//    public void test090_quit() {
//        Response response = coterieMemberAPI.quit(memberId,coterieId);
//        System.out.println(JsonUtils.toFastJson(response));
//    }
//
//
//    @Test
//    public void test090_isBanSpeak() {
////        Response response = coterieMemberAPI.isBanSpeak(memberId,coterieId);
//        Response response = coterieMemberAPI.isBanSpeak(730921949663453184L,4816176744L);
//        System.out.println(JsonUtils.toFastJson(response));
//    }
}
