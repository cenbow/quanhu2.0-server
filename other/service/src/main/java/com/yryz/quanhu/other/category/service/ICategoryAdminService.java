package com.yryz.quanhu.other.category.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.other.category.vo.*;

import java.util.List;

/**
 * @author chengyunfei
 * @version 2.0
 * @date 2018/02/06
 * @description
 */
public interface ICategoryAdminService {

	/**
	 * 分类Tree列表
	 *
	 * @return
	 */
	List<CategoryTreeAdminVo> findCategoryTree(CategoryAdminVo search);

	List<CategoryAdminVo> findAllCategory();

	/**
	 *  分类标签列表
	 *
	 * @return
	 */
	PageList<CategoryAdminVo> findCategoryBySearch(CategorySearchAdminVo search);


	/**
	 * 更新分类标签
	 */
	Integer update(CategoryAdminVo category);

	/**
	 * 保存分类标签
	 */
	Integer save(CategoryAdminVo category);

}
