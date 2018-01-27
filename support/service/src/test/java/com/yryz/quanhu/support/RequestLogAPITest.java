package com.yryz.quanhu.support;

import com.yryz.common.utils.DateUtils;
import com.yryz.quanhu.support.requestlog.api.RequestLogAPI;
import com.yryz.quanhu.support.requestlog.entity.RequestLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/27
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestLogAPITest {
    @Autowired
    RequestLogAPI requestLogAPI;

    @Test
    public void test() {
        RequestLog requestLog = new RequestLog();
        requestLog.setHeader("test header");
        requestLog.setErrorMessage("again");
        requestLog.setRequestTime(DateUtils.getDateTime());
        requestLogAPI.execute(requestLog);
    }
}
