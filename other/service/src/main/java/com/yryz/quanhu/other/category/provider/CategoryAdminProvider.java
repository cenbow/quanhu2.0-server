package com.yryz.quanhu.other.category.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieSearchParam;
import com.yryz.quanhu.other.category.api.CategoryAPI;
import com.yryz.quanhu.other.category.api.CategoryAdminAPI;
import com.yryz.quanhu.other.category.service.ICategoryAdminService;
import com.yryz.quanhu.other.category.service.ICategoryService;
import com.yryz.quanhu.other.category.vo.*;
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
@Service(interfaceClass = CategoryAdminAPI.class)
public class CategoryAdminProvider implements CategoryAdminAPI{

    private static final Logger logger = LoggerFactory.getLogger(CategoryAdminProvider.class);

    @Autowired
    @Qualifier("categoryAdminService")
    private ICategoryAdminService categoryAdminService;

    @Override
    public Response<List<CategoryTreeAdminVo>> findCategoryTree(CategoryAdminVo search) {
        return ResponseUtils.returnObjectSuccess(categoryAdminService.findCategoryTree(search));
    }

    @Override
    public Response<List<CategoryAdminVo>> findAllCategory() {
        return ResponseUtils.returnObjectSuccess(categoryAdminService.findAllCategory());
    }

    @Override
    public Response<PageList<CategoryAdminVo>> findCategoryBySearch(CategorySearchAdminVo search) {
        return ResponseUtils.returnObjectSuccess(categoryAdminService.findCategoryBySearch(search));
    }

    @Override
    public Response<Integer> update(CategoryAdminVo category) {
        return ResponseUtils.returnObjectSuccess(categoryAdminService.update(category));
    }

    @Override
    public Response<Integer> save(CategoryAdminVo category) {
        return ResponseUtils.returnObjectSuccess(categoryAdminService.save(category));
    }
}
