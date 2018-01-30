package com.yryz.quanhu.other;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.other.category.api.CategoryAPI;
import com.yryz.quanhu.other.category.vo.CategoryCheckedVo;
import com.yryz.quanhu.other.category.vo.CategoryDiscoverVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryTest {
    @Reference
    private CategoryAPI categoryAPI;

    @Test
    public void list() {
        Response<List<CategoryDiscoverVo>> response = categoryAPI.list();
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void listById() {
        Response<CategoryDiscoverVo> response = categoryAPI.listById(1L);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void recommend() {
        Response<List<CategoryCheckedVo>> response = categoryAPI.recommend();
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void save() {
        Response response = categoryAPI.save("3,4,5");
        System.out.println(JsonUtils.toFastJson(response));
    }
}
