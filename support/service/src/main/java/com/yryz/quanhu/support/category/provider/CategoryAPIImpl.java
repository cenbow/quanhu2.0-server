package com.yryz.quanhu.support.category.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.support.category.api.CategoryAPI;
import com.yryz.quanhu.support.category.service.ICategoryService;
import com.yryz.quanhu.support.category.vo.CategoryCheckedVo;
import com.yryz.quanhu.support.category.vo.CategoryDiscoverVo;
import com.yryz.quanhu.support.category.vo.CategoryVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.support.id.service.DefaultUidService;
import com.yryz.quanhu.support.id.service.IIdService;
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
     * @return
     */
    @Override
    public Response<List<CategoryDiscoverVo>> list() {
        List<CategoryDiscoverVo> listDisVo = null;

        List<CategoryDiscoverVo> list = categoryService.findCategoryDiscover();

        return new Response<>(list);
    }

    /**
     * 通过分类ID查找相关分类列表(包含一个一级分类与多个二级分类用于发现页达人子页)
     *
     * @return
     */
    @Override
    public Response<CategoryDiscoverVo> listById(Long categoryId) {

        CategoryDiscoverVo categoryDiscoverVo = categoryService.findCategoryDiscoverById(categoryId);

        return new Response<>(categoryDiscoverVo);
    }

    /**
     * 获取向用户推荐的标签分类(引导页)
     * @return
     */
    @Override
    public Response<List<CategoryCheckedVo>> recommend() {
        List<CategoryCheckedVo> categoryVos = categoryService.findCategories();
        return new Response<>(categoryVos);
    }

    /**
     * 设置用户标签分类(引导页 button 选好了)
     * @return
     */
    @Override
    public Response<String> save(String ids) {
        Integer result = categoryService.save(ids);

        if (result > 0 ) {
            return new Response<>();
        } else {
          throw new QuanhuException(ExceptionEnum.SysException);
        }
    }
}
