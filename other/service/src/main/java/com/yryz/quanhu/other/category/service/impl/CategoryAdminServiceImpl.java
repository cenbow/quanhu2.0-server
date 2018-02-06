package com.yryz.quanhu.other.category.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.other.category.dao.CategoryDao;
import com.yryz.quanhu.other.category.entity.Category;
import com.yryz.quanhu.other.category.service.ICategoryAdminService;
import com.yryz.quanhu.other.category.vo.CategoryAdminVo;
import com.yryz.quanhu.other.category.vo.CategorySearchAdminVo;
import com.yryz.quanhu.other.category.vo.CategoryTreeAdminVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chengyunfei
 * @version 2.0
 * @date 2018/01/22
 * @description
 */
@Service("categoryAdminService")
public class CategoryAdminServiceImpl implements ICategoryAdminService {

    @Resource
    private CategoryDao categoryDao;

    @Reference
    private IdAPI idAPI;

    @Override
    public List<CategoryTreeAdminVo> findCategoryTree(CategoryAdminVo search) {
        //获取一级分类
        CategorySearchAdminVo searchAdminVo = new CategorySearchAdminVo();

        BeanUtils.copyProperties(search, searchAdminVo);

        List<Category> categories = categoryDao.selectBySearch(searchAdminVo);

        List<CategoryTreeAdminVo> categoryTrees = null;

        categoryTrees = categories.stream().map(category -> {

            CategoryTreeAdminVo categoryTreeAdminVo = new CategoryTreeAdminVo();
            BeanUtils.copyProperties(category, categoryTreeAdminVo);


            //获取二级分类
            searchAdminVo.setParentKid(category.getKid());
            List<Category> secCategories = categoryDao.selectBySearch(searchAdminVo);

            for (Category secCategory : secCategories) {
                CategoryAdminVo secCategoryVo = new CategoryAdminVo();
                BeanUtils.copyProperties(secCategory, secCategoryVo);

                categoryTreeAdminVo.getSubordinate().add(secCategoryVo);
            }

            return categoryTreeAdminVo;

        }).collect(Collectors.toList());

        return categoryTrees;
    }

    /**
     * 获取分类标签
     * @param search
     * @return
     */
    @Override
    public PageList<CategoryAdminVo> findCategoryBySearch(CategorySearchAdminVo search) {

        if (null != search.getCurrentPage() && null != search.getPageSize()) {

        search.setStart((search.getCurrentPage() - 1) * search.getPageSize());
        search.setLimit(search.getPageSize());
        }

        Integer count = categoryDao.selectCountBySearch(search);

        List<Category> categories = categoryDao.selectBySearch(search);

        List<CategoryAdminVo> categoryList = categories.stream().map(category -> {

            CategoryAdminVo categoryAdminVo = new CategoryAdminVo();
            BeanUtils.copyProperties(category, categoryAdminVo);

            return categoryAdminVo;

        }).collect(Collectors.toList());


        PageList<CategoryAdminVo> pageList = new PageList<>(search.getCurrentPage(), search.getPageSize(), categoryList, Long.parseLong(String.valueOf(count)));

        return pageList;
    }

    @Override
    public Integer update(CategoryAdminVo category) {
        return categoryDao.update(category);
    }

    @Override
    public Integer save(CategoryAdminVo category) {
        Response<Long> response = idAPI.getSnowflakeId();
        Long kid = ResponseUtils.getResponseNotNull(response);
        category.setKid(kid);
        category.setCategorySort(0);
        category.setCategoryStatus(10);
        category.setRecommend(20);
        category.setCreateDate(new Date());
        category.setLastUpdateDate(new Date());
        return categoryDao.insert(category);
    }
}
