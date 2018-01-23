package com.yryz.quanhu.support.category.api;

import com.yryz.common.response.Response;
import com.yryz.quanhu.support.category.vo.CategoryCheckedVo;
import com.yryz.quanhu.support.category.vo.CategoryDiscoverVo;
import com.yryz.quanhu.support.category.vo.CategoryVo;

import java.util.List;

/**
 * Dubbo Service(category) API
 * @author chengyunfei
 */
public interface CategoryAPI {
	
	/**
	 * 分类列表(包含多个一二级分类用于发现页达人主页)
	 * 
	 * @return
	 */
	Response<List<CategoryDiscoverVo>> list();

	/**
	 * 通过分类ID查找相关分类列表(包含一个一级分类与多个二级分类用于发现页达人子页)
	 *
	 * @return
	 */
	Response<CategoryDiscoverVo> listById(Long categoryId);

	/**
     * 获取向用户推荐的标签分类(引导页)
	 * @return
	 */
	Response<List<CategoryCheckedVo>> recommend();

	/**
     * 设置用户标签分类(引导页 button 选好了)
	 * @return
	 */
	Response<String> save(String ids);

}
