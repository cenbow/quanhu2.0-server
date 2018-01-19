package com.yryz.quanhu.message;

import com.yryz.quanhu.message.api.PushAPI;
import com.yryz.quanhu.message.entity.PushReqVo;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/19
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PushAPITest {

    @Autowired
    PushAPI pushAPI;

    @Test
    public void commonSendAliasTest() {
        PushReqVo pushReqVo = new PushReqVo();
        List<String> usrIds = Lists.newArrayList();
        usrIds.add("123456");
        pushReqVo.setCustIds(usrIds);
        pushReqVo.setMsg("test");
        pushReqVo.setPushType(PushReqVo.CommonPushType.BY_ALIAS);

        pushAPI.commonSendAlias(pushReqVo);
    }
}
