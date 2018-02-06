package com.yryz.quanhu.other.category.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.other.category.api.CategoryAPI;
import com.yryz.quanhu.other.category.service.ICategoryService;
import com.yryz.quanhu.other.category.vo.CategoryCheckedVo;
import com.yryz.quanhu.other.category.vo.CategoryDiscoverVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * @author chengyunfei
 * @version 2.0
 * @date 2018/01/22
 * @description 用户标签分类服务
 */
@Service(interfaceClass = CategoryAPI.class)
public class CategoryAPIImpl implements CategoryAPI {

    private static final Logger logger = LoggerFactory.getLogger(CategoryAPIImpl.class);

    @Autowired
    @Qualifier("categoryService")
    private ICategoryService categoryService;

    /**
     * 分类列表(包含多个一二级分类用于发现页达人主页)
     * 二级分类目前最多九个, 后台控， 暂无后台配置
     *
     * @return
     */
    @Override
    public Response<List<CategoryDiscoverVo>> list() {
        try {

            List<CategoryDiscoverVo> list = categoryService.findCategoryDiscover();

            return ResponseUtils.returnObjectSuccess(list);

        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("通过分类ID查找相关分类列表发生异常!", e);
            return ResponseUtils.returnException(e);
        }
    }

    /**
     * 通过分类ID查找相关分类列表(包含一个一级分类与多个二级分类用于发现页达人子页)
     *
     * @return
     */
    @Override
    public Response<CategoryDiscoverVo> listById(Long categoryId) {
        try {


            CategoryDiscoverVo categoryDiscoverVo = categoryService.findCategoryDiscoverById(categoryId);

            return ResponseUtils.returnObjectSuccess(categoryDiscoverVo);


        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("通过分类ID查找相关分类列表发生异常!", e);
            return ResponseUtils.returnException(e);
        }


    }

    /**
     * 获取向用户推荐的标签分类(引导页)
     *
     * @return
     */
    @Override
    public Response<List<CategoryCheckedVo>> recommend() {
        try {

            List<CategoryCheckedVo> categoryVos = categoryService.findCategories();
            return ResponseUtils.returnObjectSuccess(categoryVos);

        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("获取向用户推荐的标签分类!", e);
            return ResponseUtils.returnException(e);
        }
    }
}
