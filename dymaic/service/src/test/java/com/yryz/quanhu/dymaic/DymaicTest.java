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

    private final static Long USER_DEMO = 100L;
    private final Long USER_DEBAR = 200L;


    @Autowired
    private DymaicService dymaicService;

    @Test
    public void add() {
        Long[] kids = new Long[]{101L, 102L, 103L};

        for (Long kid : kids) {
            Dymaic dymaic = new Dymaic();
            dymaic.setUserId(USER_DEBAR);
            dymaic.setKid(kid);
            dymaic.setContent("content test " + kid);
            dymaic.setModuleEnum(1000);

            Response<Boolean> response = dymaicService.send(dymaic);
            print(response);
        }
    }

    @Test
    public void delete() {
        Response<Boolean> response = dymaicService.delete(USER_DEMO, 1L);
        print(response);
    }

    @Test
    public void get() {
        Response<Dymaic> response = dymaicService.get(1L);
        print(response);
    }

    @Test
    public void getBatch() {
        List<Long> kids = Arrays.asList(new Long[]{1L, 2L, 101L});
        Response<Map<Long, Dymaic>> response = dymaicService.get(kids);
        print(response);
    }

    //============  SendList ==============

    @Test
    public void getSendList() {
        Response<List<DymaicVo>> response = dymaicService
                .getSendList(USER_DEMO, 100L, 5L);
        print(response);
    }

    @Test
    public void rebuildSendList() {
        Response<Boolean> response = dymaicService.rebuildSendList(USER_DEMO);
        print(response);
    }


    //============  TimeLine ==============
    @Test
    public void getTimeLine() {
        Response<List<DymaicVo>> response =
                dymaicService.getTimeLine(USER_DEBAR, 1000L, 10L);
        print(response);
    }

    @Test
    public void pushTimeLine() {
        Response<Boolean> response = dymaicService.pushTimeLine(null);
        print(response);
    }

    @Test
    public void rebuildTimeLine() {
        Response<Boolean> response = dymaicService.rebuildTimeLine(USER_DEBAR, USER_DEMO);
        print(response);
    }

    @Test
    public void shuffleTimeLine() {
        Response<Boolean> response = dymaicService.shuffleTimeLine(USER_DEBAR, USER_DEMO);
        print(response);
    }

    private void print(Object obj) {
        System.out.println("\n" + GsonUtils.parseJson(obj) + "\n");
    }
}
