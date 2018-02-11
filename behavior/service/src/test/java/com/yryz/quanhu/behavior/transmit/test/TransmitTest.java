package com.yryz.quanhu.behavior.transmit.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yryz.quanhu.behavior.transmit.api.TransmitApi;
import com.yryz.quanhu.behavior.transmit.dto.TransmitInfoDto;
import com.yryz.quanhu.behavior.transmit.entity.TransmitInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransmitTest {

    @Reference
    TransmitApi transmitApi;

    @Test
    public void single() {
        TransmitInfo transmitInfo = new TransmitInfo();
        transmitInfo.setContent("转发JTets");
        transmitInfo.setModuleEnum("1003");
        transmitInfo.setParentId(749172760356601856L);
        transmitInfo.setResourceId(749172760356601856L);
        transmitInfo.setTargetUserId(738839567258206208L);
        System.out.println(JSON.toJSONString(transmitApi.single(transmitInfo)));
    }

    @Test
    public void list() {
        TransmitInfoDto transmitInfoDto = new TransmitInfoDto();
        transmitInfoDto.setParentId(737325677185073152L);
        transmitInfoDto.setModuleEnum("1003");
        System.out.println(JSON.toJSONString(transmitApi.list(transmitInfoDto)));
    }

    @Test
    public void updateShelvesFlag() {
        Long transmitId = 747521482870521856L;
        Integer shelvesFlag = 10;
        System.out.println(transmitApi.updateShelvesFlag(transmitId, shelvesFlag));
    }

    @Test
    public void removeTransmit() {
        Long transmitId = 747521482870521856L;
        System.out.println(transmitApi.removeTransmit(transmitId));
    }


}
