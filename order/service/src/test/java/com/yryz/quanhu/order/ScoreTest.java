/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月19日
 * Id: OrderTest.java, 2018年1月19日 下午2:10:59 yehao
 */
package com.yryz.quanhu.order;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.vo.EventInfo;

/**
 * @author syc
 * @version 2.0
 * @date 2018年1月19日 下午2:10:59
 * @Description 积分单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ScoreTest {

	
	@Reference
	private static EventAPI eventAPI;
	

	
	@Test
    public void commitTest() {
        EventInfo info = new EventInfo();
        info.setCustId("21b32tixua");
        info.setCircleId("62jqe12bhauv");
        info.setCoterieId("ctf8gkwjvo1q");
        info.setEventCode("1");
        info.setEventNum(1);
        info.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        eventAPI.commit(info);

        System.out.println("commit event result");
    }
	
	
}
