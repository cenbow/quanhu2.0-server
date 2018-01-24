package com.yryz.quanhu.support.category.service.impl;

import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.support.category.constants.CategoryConstant;
import com.yryz.quanhu.support.category.dao.CategoryDao;
import com.yryz.quanhu.support.category.entity.Category;
import com.yryz.quanhu.support.category.service.ICategoryService;
import com.yryz.quanhu.support.category.vo.CategoryCheckedVo;
import com.yryz.quanhu.support.category.vo.CategoryDiscoverVo;
import com.yryz.quanhu.support.category.vo.CategoryVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author chengyunfei
 * @version 2.0
 * @date 2018/01/22
 * @description
 */
@Service("categoryService")
public class CategoryServiceImpl implements ICategoryService {

    @Resource
    private CategoryDao categoryDao;

    @Override
    public List<CategoryDiscoverVo> findCategoryDiscover() {
        //获取一级分类
        List<Category> categories = categoryDao.selectByPid(0L, 0);

        List<CategoryDiscoverVo> categoryDiscovers = null;

        categoryDiscovers = categories.stream().map(category -> {
            CategoryDiscoverVo categoryDiscoverVo = new CategoryDiscoverVo();
            categoryDiscoverVo.setCategoryId(category.getKid());
            categoryDiscoverVo.setCategoryName(category.getCategoryName());

            //获取二级分类
            List<Category> subCategories = categoryDao.selectByPid(category.getKid(), CategoryConstant.CategoryViewCount);

            for (Category c : subCategories) {
                CategoryVo categoryVo = new CategoryVo();
                categoryVo.setCategoryId(c.getKid());
                categoryVo.setCategoryName(c.getCategoryName());

                categoryDiscoverVo.getSubordinate().add(categoryVo);
            }

            return categoryDiscoverVo;

        }).collect(Collectors.toList());

        return categoryDiscovers;
    }


    @Override
    public CategoryDiscoverVo findCategoryDiscoverById(Long kid) {
        //获取分类详情
        Category category = categoryDao.selectByKid(kid);

        CategoryDiscoverVo categoryDiscoverVo = new CategoryDiscoverVo();
        if (null != category) {
            if (category.getParentKid() == CategoryConstant.CategoryRootPID) {
                //构建一级分类
                categoryDiscoverVo.setCategoryId(category.getKid());
                categoryDiscoverVo.setCategoryName(category.getCategoryName());

                //构建所属二级分类
                List<Category> subCategories = categoryDao.selectByPid(category.getKid(), 0);

                for (Category c : subCategories) {
                    CategoryVo categoryVo = new CategoryVo();
                    categoryVo.setCategoryId(c.getKid());
                    categoryVo.setCategoryName(c.getCategoryName());
                    categoryDiscoverVo.getSubordinate().add(categoryVo);
                }
            } else {
                //构建一级分类
                Category parentCategory = categoryDao.selectByKid(category.getParentKid());
                categoryDiscoverVo.setCategoryId(parentCategory.getKid());
                categoryDiscoverVo.setCategoryName(parentCategory.getCategoryName());

                //构建所属二级分类
                List<Category> subCategories = categoryDao.selectByPid(category.getParentKid(), 0);

                for (Category c : subCategories) {
                    CategoryVo categoryVo = new CategoryVo();
                    categoryVo.setCategoryId(c.getKid());
                    categoryVo.setCategoryName(c.getCategoryName());
                    categoryDiscoverVo.getSubordinate().add(categoryVo);
                }
            }
        }
        return categoryDiscoverVo;
    }

    @Override
    public List<CategoryCheckedVo> findCategories() {
        List<Category> categories = categoryDao.selectByRecommend();

        Boolean checkAll = false;
        HashSet<Integer> checkSet = null;

        if (categories.size() < CategoryConstant.CategoryCheckedCount) {
            checkAll = true;
        } else {
            checkSet = randomChecked(categories.size(), CategoryConstant.CategoryCheckedCount);
        }

        List<CategoryCheckedVo> categoryVos = new ArrayList<>();


        for (Category category : categories) {
            CategoryCheckedVo categoryVo = new CategoryCheckedVo();
            categoryVo.setCategoryId(category.getKid());
            categoryVo.setCategoryName(category.getCategoryName());

            if (checkAll) {
                categoryVo.setChecked(1);
            } {
                int index = categories.indexOf(category);

                if (checkSet.contains(index)) {
                    categoryVo.setChecked(1);
                } else {
                    categoryVo.setChecked(0);
                }
            }

            categoryVos.add(categoryVo);
        }

        return categoryVos;
    }

    private HashSet<Integer> randomChecked(Integer number, Integer checkedNum) {

        HashSet<Integer> checkedSet = new HashSet<Integer>();

        Random random = new Random(); // 定义随机类

        while (true) {
            int result = random.nextInt(number);

            checkedSet.add(result);

            if (checkedSet.size() == checkedNum) {
                break;
            }
        }

        return checkedSet;
    }

//    public static void main(String[] args) {
//        randomChecked(10, 5);
//    }


    @Override
    public Integer save(String ids) {

        String[] idList = StringUtils.split(ids, ",");

        //todo  调用用户接口保存用户的分类标签

        return 1;
    }

    /**********************************************************************************************************/

//
//    public Integer saveWithUser(String ids) {
//
//        Category category = new Category();
//
//        Integer result = categoryDao.save(category);
//
//        return null;
//    }
//
//    @Override
//    public Integer update(Category category) {
//        return categoryDao.update(category);
//    }


}
