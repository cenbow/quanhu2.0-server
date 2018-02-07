package com.yryz.quanhu.other;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.other.category.api.CategoryAPI;
import com.yryz.quanhu.other.category.api.CategoryAdminAPI;
import com.yryz.quanhu.other.category.vo.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryAdminTest {

    @Reference
    private CategoryAdminAPI categoryAdminAPI;

    @Test
    public void findAll() {
        Response<List<CategoryAdminVo>> response = categoryAdminAPI.findAllCategory();
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void findCategoryTree() {
        CategoryAdminVo category = new CategoryAdminVo();
        category.setParentKid(0L);
        category.setCategoryStatus(10);
//        category.setRecommend();
//        category.setCategoryType();

        Response<List<CategoryTreeAdminVo>> response = categoryAdminAPI.findCategoryTree(category);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void findCategoryBySearch() {
        CategorySearchAdminVo search = new CategorySearchAdminVo();
        search.setCurrentPage(1);
        search.setPageSize(20);
        search.setParentKid(0L);
        search.setCategoryStatus(20);

        Response<PageList<CategoryAdminVo>> response = categoryAdminAPI.findCategoryBySearch(search);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void update() {
        CategoryAdminVo category = new CategoryAdminVo();
        category.setKid(1L);
        category.setCategoryStatus(11);
        category.setRecommend(10);
        category.setLastUpdateUserId(10L);
        category.setLastUpdateDate(new Date());

        Response<Integer> response = categoryAdminAPI.update(category);
        System.out.println(JsonUtils.toFastJson(response));
    }

    @Test
    public void save() {
        CategoryAdminVo category = new CategoryAdminVo();
        category.setParentKid(0L);
        category.setCategoryType(10);
        category.setCategoryName("IT");
        category.setCreateUserId(1L);
        category.setLastUpdateUserId(1L);

        Response<Integer> response = categoryAdminAPI.save(category);
        System.out.println(JsonUtils.toFastJson(response));
    }
}
