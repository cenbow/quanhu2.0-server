package com.yryz.quanhu.dymaic;

import com.yryz.common.response.Response;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.dymaic.service.DymaicService;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import com.yryz.quanhu.dymaic.vo.DymaicVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author xiepeng
 * @version 1.0
 * @data 2018/1/19 0019 32
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:test.xml"})
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DymaicTest {

    @Autowired
    private DymaicService dymaicService;

    @Test
    public void add() {
        Long[] kids = new Long[]{21L, 22L};

        for (Long kid : kids) {
            Dymaic dymaic = new Dymaic();
            dymaic.setUserId("debar");
            dymaic.setKid(kid);
            dymaic.setContent("content test " + kid);
            dymaic.setModuleEnum(1000);

            Response<Boolean> response = dymaicService.send(dymaic);
            print(response);
        }
    }

    @Test
    public void delete() {
        Response<Boolean> response = dymaicService.delete("xiepeng", 11L);
        print(response);
    }

    @Test
    public void get() {
        Response<Dymaic> response = dymaicService.get(11L);
        print(response);
    }

    @Test
    public void getBatch() {
        List<Long> kids = Arrays.asList(new Long[]{10L, 11L, 12L});
        Response<Map<Long, Dymaic>> response = dymaicService.get(kids);
        print(response);
    }

    //============  SendList ==============

    @Test
    public void getSendList() {
        Response<List<DymaicVo>> response = dymaicService
                .getSendList("xiepeng", 11L, 2L);
        print(response);
    }

    @Test
    public void rebuildSendList() {
        Response<Boolean> response = dymaicService.rebuildSendList("xiepeng");
        print(response);
    }


    //============  TimeLine ==============
    @Test
    public void getTimeLine() {
        Response<List<DymaicVo>> response =
                dymaicService.getTimeLine("debar", 100L, 10L);
        print(response);
    }

    @Test
    public void pushTimeLine() {
        Response<Boolean> response = dymaicService.pushTimeLine(null);
        print(response);
    }

    @Test
    public void rebuildTimeLine() {
        Response<Boolean> response = dymaicService.rebuildTimeLine("xiepeng", 100L);
        print(response);
    }

    @Test
    public void shuffleTimeLine() {
        Response<Boolean> response = dymaicService.shuffleTimeLine("debar", "xiepeng");
        print(response);
    }

    private void print(Object obj) {
        System.out.println("\n" + GsonUtils.parseJson(obj) + "\n");
    }
}
