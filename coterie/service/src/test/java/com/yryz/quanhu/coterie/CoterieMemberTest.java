package com.yryz.quanhu.coterie;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.coterie.member.service.CoterieMemberAPI;
import com.yryz.quanhu.coterie.member.service.impl.CoterieMemberOrderNotifyServiceImpl;
import com.yryz.quanhu.order.sdk.dto.OutputOrder;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CoterieMemberTest {
    @Reference
    private CoterieMemberAPI coterieMemberAPI;

    @Autowired
    CoterieMemberOrderNotifyServiceImpl service;

    private static Long userId = 377914625728512L;
    private static Long coterieId = 367337454723072L;

    private static Long memberId = 356895487025152L;
    private static String reason_waitting = "【测试】【待审】 " + System.currentTimeMillis();
    private static String reason_join = "【测试】【不审】 " + System.currentTimeMillis();

//
//
//
//    367303078723584
//
//
//    Response [status=true, code=200, msg=success, errorMsg=, data={367305670803456=2, 367305981181952=0, 367306710990848=0, 367305872130048=0, 367306299949056=0,
//            367305167486976=3, 367305586917376=1, 367306182508544=1, 367305477865472=2, 367306375446528=0, 367303078723584=0, 367305368813568=12, 367306643881984=0, 367306568384512=0, 367305771466752=1}]
//
//
//
//    Response [status=true, code=200, msg=success, errorMsg=, data={367305167486976=3}]
//    Response [status=true, code=200, msg=success, errorMsg=, data={367305368813568=12}]
//    Response [status=true, code=200, msg=success, errorMsg=, data={367305477865472=2}]
//    Response [status=true, code=200, msg=success, errorMsg=, data={367305586917376=1}]
//    Response [status=true, code=200, msg=success, errorMsg=, data={367305670803456=2}]
//    Response [status=true, code=200, msg=success, errorMsg=, data={367305771466752=1}]
//    Response [status=true, code=200, msg=success, errorMsg=, data={367305872130048=0}]
//    Response [status=true, code=200, msg=success, errorMsg=, data={367305981181952=0}]
//    Response [status=true, code=200, msg=success, errorMsg=, data={367306073456640=1}]
//    Response [status=true, code=200, msg=success, errorMsg=, data={367306182508544=1}]
//    Response [status=true, code=200, msg=success, errorMsg=, data={367306299949056=0}]
//    Response [status=true, code=200, msg=success, errorMsg=, data={367306375446528=0}]
//    Response [status=true, code=200, msg=success, errorMsg=, data={367306568384512=0}]
//    Response [status=true, code=200, msg=success, errorMsg=, data={367306643881984=0}]
//    Response [status=true, code=200, msg=success, errorMsg=, data={367306710990848=0}]
//
//



    @Test
    public void test010_Join() {
//        Response response = coterieMemberAPI.join(730941139577331712L,5536534415L,"我要加入,不审核的");
        Response response = coterieMemberAPI.join(memberId, coterieId, null);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void test020_audit() {

        try {
            Response response = coterieMemberAPI.audit(userId, memberId, coterieId, null);
            System.out.println(JsonUtils.toFastJson(response));
        } catch (QuanhuException e) {
            e.getMsg();
            e.getErrorMsg();
            System.out.println(JsonUtils.toFastJson(e));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 订单回调执行
     */
    @Test
    public void test021_auditPay() {
        OutputOrder outputOrder = new OutputOrder();

//        {"userId":737251236811898880,"coterieId":233665981858,"reason":"我要加入,要审核的","coterieName":"think","icon":"icon","owner":"727909974996672512","amount":10000}
        outputOrder.setBizContent("{\"userId\":381098110967808,\"coterieId\":367803483840512,\"reason\":\"我要加入,要审核的\",\"coterieName\":\"think\",\"icon\":\"icon\",\"owner\":\"727909974996672512\",\"amount\":10000}");

        outputOrder.setOrderId(20180207164695L);
        outputOrder.setPayType(10);
        outputOrder.setResourceId(367803483840512L);
        outputOrder.setCost(100L);
        outputOrder.setModuleEnum(ModuleContants.COTERIE);
        outputOrder.setCreateDate(new Date());
        outputOrder.setCoterieId(367803483840512L);
        outputOrder.setCreateUserId(381098110967808L);
        service.notify(outputOrder);
    }

    @Test
    public void test030_banSpeak() {
        Response response = coterieMemberAPI.banSpeak(userId, memberId, coterieId, 1);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void test040_permission() {
        Response response = coterieMemberAPI.permission(userId, coterieId);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void test050_newMemberNum() {
        Response response = coterieMemberAPI.queryNewMemberNum(coterieId);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void test060_memberApplyList() {
        Response response = coterieMemberAPI.queryMemberApplyList(coterieId, 1, 10);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void test070_memberList() {
        Response response = coterieMemberAPI.queryMemberList(coterieId, 1, 10);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void test080_kick() {
        Response response = coterieMemberAPI.kick(userId, memberId, coterieId, "就要踢你");
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void test090_quit() {
        Response response = coterieMemberAPI.quit(memberId, coterieId);
        System.out.println(JsonUtils.toFastJson(response));
    }


    @Test
    public void test090_isBanSpeak() {
//        Response response = coterieMemberAPI.isBanSpeak(memberId,coterieId);
        Response response = coterieMemberAPI.isBanSpeak(userId, coterieId);
        System.out.println(JsonUtils.toFastJson(response));
    }
}
