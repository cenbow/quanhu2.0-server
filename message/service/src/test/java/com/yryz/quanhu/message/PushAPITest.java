package com.yryz.quanhu.message;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.yryz.common.constant.AppConstants;
import com.yryz.common.context.Context;
import com.yryz.common.response.Response;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.message.push.api.PushAPI;
import com.yryz.quanhu.message.push.entity.PushConfigDTO;
import com.yryz.quanhu.message.push.entity.PushReqVo;
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
        usrIds.add("626942183000989696");
        pushReqVo.setCustIds(usrIds);
        pushReqVo.setMsg("test");
        pushReqVo.setPushType(PushReqVo.CommonPushType.BY_ALIAS);
        for (int i = 0; i < 1; i++) {
            RpcContext.getContext().setAttachment(AppConstants.APP_ID, "vebff12m1762");
            Response<Boolean> response = pushAPI.commonSendAlias(pushReqVo);
            System.out.println("fwefe" + GsonUtils.parseJson(response));
        }
//        PushConfigDTO dto = new PushConfigDTO();
//        dto.setPushKey("4ca5c25ed02f766b715f8aa1");
//        dto.setPushSecret("4ca91e07dd6912b3807e3c6f");
//        dto.setPushType(0);
//        dto.setPushEvn(false);
//        dto.setPushDesc("测试环境推送配置");
//        System.out.println("wfefe"+GsonUtils.parseJson(dto));
    }
}
