package com.yryz.quanhu.dymaic.canal;

import com.yryz.quanhu.dymaic.canal.job.UserDiffHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/2/1
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DiffTest {

    @Autowired
    UserDiffHandler userDiffHandler;

    @Test
    public void handleTest() {
        userDiffHandler.handler();
    }


}
