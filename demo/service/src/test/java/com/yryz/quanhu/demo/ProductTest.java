package com.yryz.quanhu.demo;

import com.yryz.quanhu.demo.service.ProductService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class ProductTest {
    @Autowired
    private ProductService productService;

    @Test
    public void order10SelectAllTest() {
        assertEquals(5, productService.selectAll(1, 5).size()); // 分页5条
    }

    @Test
    public void order20DeleteTest() {
        assertEquals(1, productService.delete(1L));
    }

    @Test
    public void order30SelectByIdTest() {
        assertNull(productService.selectById(1L));// 事务正常, 查不到
    }

    @Test(expected = RuntimeException.class)    // 事务回滚, 查得到
    public void order40DeleteTest() {
        productService.delete(2L);
    }

    @Test
    public void order50SelectByIdTest() {
        assertNotNull(productService.selectById(2L));
    }
}
