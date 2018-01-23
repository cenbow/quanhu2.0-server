/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月22日
 * Id: PayMsgConstant.java, 2018年1月22日 下午4:03:47 yehao
 */
package com.yryz.quanhu.support.category.constants;

import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

/**
 * @author chengyunfei
 * @version 2.0
 * @date 2018-01-23
 * @Description 标签分类枚举
 */
public class CategoryConstant {

	//标签分类一级分类根ID
	public static final Long CategoryRootPID = 0L;

	//发现页二级标签分类最多显示个数
	public static final Integer CategoryViewCount = 9;

	//发现页默认勾选的个数
	public static final Integer CategoryCheckedCount = 3;

	//标签所属类型
	public static final Integer CategoryTypePeople = 10;
	public static final Integer CategoryTypeContent = 20;

}
