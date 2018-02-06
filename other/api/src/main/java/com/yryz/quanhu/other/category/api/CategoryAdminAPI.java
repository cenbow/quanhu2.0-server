package com.yryz.quanhu.other.category.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.other.category.vo.*;

import java.util.List;

/**
 * Dubbo Service(category) API admin
 * @author chengyunfei
 */
public interface CategoryAdminAPI {

	/**
	 * 分类Tree列表
	 *
	 * @return
	 */
	Response<List<CategoryTreeAdminVo>> findCategoryTree(CategoryAdminVo search);

	/**
	 * 一级分类列表
	 *
	 * @return
	 */
	Response<PageList<CategoryAdminVo>> findCategoryBySearch(CategorySearchAdminVo search);


	/**
	 * 更新分类标签
	 */
	Response<Integer> update(CategoryAdminVo category);

	/**
	 * 保存分类标签
	 */
	Response<Integer> save(CategoryAdminVo category);

}
