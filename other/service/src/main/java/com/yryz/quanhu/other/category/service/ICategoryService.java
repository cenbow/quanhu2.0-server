package com.yryz.quanhu.other.category.service;

import com.yryz.quanhu.other.category.vo.CategoryCheckedVo;
import com.yryz.quanhu.other.category.vo.CategoryDiscoverVo;

import java.util.List;

/**
 * @author chengyunfei
 * @version 2.0
 * @date 2018/01/22
 * @description
 */
public interface ICategoryService {

	/**
	 * 分类列表(包含多个一二级分类用于发现页达人主页)
	 *
	 * @return
	 */
	List<CategoryDiscoverVo> findCategoryDiscover();


	/**
	 * 通过分类ID查找相关分类列表(包含一个一级分类与多个二级分类用于发现页达人子页)
	 *
	 * @return
	 */
	CategoryDiscoverVo findCategoryDiscoverById(Long kid);

	/**
	 * 获取向用户推荐的标签分类(引导页)
	 * @return
	 */
	List<CategoryCheckedVo> findCategories();

	/**
	 * 设置用户标签分类(引导页 button 选好了)
	 * @return
	 */
	public Integer save(String ids);

//	/**
//	 * 更新标签
//	 */
//	public Integer update(Category category);
}
