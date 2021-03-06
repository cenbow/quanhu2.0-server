package com.yryz.quanhu.other.category.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.other.category.dao.CategoryDao;
import com.yryz.quanhu.other.category.entity.Category;
import com.yryz.quanhu.other.category.service.ICategoryAdminService;
import com.yryz.quanhu.other.category.vo.CategoryAdminVo;
import com.yryz.quanhu.other.category.vo.CategorySearchAdminVo;
import com.yryz.quanhu.other.category.vo.CategoryTreeAdminVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserTagApi;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chengyunfei
 * @version 2.0
 * @date 2018/01/22
 * @description
 */
@Service("categoryAdminService")
public class CategoryAdminServiceImpl implements ICategoryAdminService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private CategoryDao categoryDao;

    @Reference
    private IdAPI idAPI;

    @Reference
    private UserTagApi userTagApi;

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

    @Override
    public List<CategoryAdminVo> findAllCategory() {

        List<Category> categories = categoryDao.findAll();

        List<CategoryAdminVo> categoryList = categories.stream().map(category -> {

            CategoryAdminVo categoryAdminVo = new CategoryAdminVo();
            BeanUtils.copyProperties(category, categoryAdminVo);

            return categoryAdminVo;

        }).collect(Collectors.toList());


        return categoryList;
    }

    /**
     * 获取分类标签
     *
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

        LinkedList<Category> categories = categoryDao.selectBySearch(search);

        logger.info(JsonUtils.toFastJson(categories));


        CategorySearchAdminVo vo = new CategorySearchAdminVo();

        List<CategoryAdminVo> categoryList = categories.stream().map(category -> {

            CategoryAdminVo categoryAdminVo = new CategoryAdminVo();
            BeanUtils.copyProperties(category, categoryAdminVo);

            if (search.getParentKid() == 0L) {
                //下属分类数
                vo.setParentKid(category.getKid());
                Integer num = categoryDao.selectCountBySearch(vo);
                categoryAdminVo.setSubordinateNum(num);

                //下属达人数
                Set<String> tagIds = new HashSet<>();
                tagIds.add(category.getKid().toString());

               List<Category> suborinateList = categoryDao.selectByPidAdmin(category.getKid());

                for (Category c : suborinateList ) {
                    tagIds.add(c.getKid().toString());
                }

                Response<Map<String, Long>> rpc = userTagApi.getTagCountByUser(tagIds);
                Map<String, Long> countMap = rpc.getData();

                Long starNum = 0L;

                for (Long size : countMap.values() ) {

                    starNum += size;
                }
                categoryAdminVo.setStarNum(starNum);
            } else {

                //下属达人数
                Set<String> tagIds = new HashSet<>();
                tagIds.add(category.getKid().toString());
                Response<Map<String, Long>> rpc = userTagApi.getTagCountByUser(tagIds);
                Map<String, Long> countMap = rpc.getData();
                categoryAdminVo.setStarNum(countMap.get(category.getKid().toString()));
            }

            return categoryAdminVo;

        }).collect(Collectors.toList());


        PageList<CategoryAdminVo> pageList = new PageList<>(search.getCurrentPage(), search.getPageSize(), categoryList, Long.parseLong(String.valueOf(count)));

        return pageList;
    }

    @Override
    @Transactional
    public Integer update(CategoryAdminVo category) {
        Integer result = categoryDao.update(category);

        if (category.getCategoryStatus() != null) {

            List<Category> categories = categoryDao.selectByPidAdmin(category.getKid());
            if (CollectionUtils.isNotEmpty(categories)) {
                for (Category categoryDb : categories) {
                    category.setKid(categoryDb.getKid());
                    categoryDao.update(category);
                }
            }
        }
        return result;
    }

    @Override
    public Integer save(CategoryAdminVo category) {
        Response<Long> response = idAPI.getSnowflakeId();
        Long kid = ResponseUtils.getResponseNotNull(response);
        category.setKid(kid);
        category.setCategorySort(9999999);
        category.setCategoryStatus(10);
        category.setRecommend(20);
        category.setCreateDate(new Date());
        category.setLastUpdateDate(new Date());
        return categoryDao.insert(category);
    }
}
