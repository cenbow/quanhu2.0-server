package com.yryz.quanhu.dymaic;

import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.dymaic.service.DymaicTopServiceImpl;
import com.yryz.quanhu.dymaic.service.SortIdHelper;
import com.yryz.quanhu.dymaic.vo.DymaicVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Administrator
 * @version 1.0
 * @data 2018/2/1 0001 43
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DymaicTop {

    @Autowired
    DymaicTopServiceImpl dymaicTopService;

    private static final Long userId = 724011759597371392L;
    private static final Long kid = 109844L;

    @Test
    public void add() {
        dymaicTopService.add(userId, kid);
    }

    @Test
    public void get() {
        DymaicVo vo = dymaicTopService.get(userId);
        print(vo);
    }

    @Test
    public void delete() {
        dymaicTopService.delete(userId, kid);
    }

    private void print(Object obj) {
        System.out.println("\n" + GsonUtils.parseJson(obj) + "\n");
    }

}
