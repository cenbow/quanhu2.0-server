package com.yryz.quanhu.order.utils;

import java.util.Collection;

import org.json.JSONArray;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年10月16日 下午3:11:50
 * @Description 集合工具类
 */
public class CollectionUtils {
	
	/**
	 * 
	 * @param collections
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collections){
		if(collections == null || collections.isEmpty()){
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(JSONArray array){
		if(array == null || array.length() == 0){
			return true;
		}
		return false;
	}
	
	public static boolean isNotEmpty(Collection<?> collections){
		return !isEmpty(collections);
	}
	
	public static boolean isNotEmpty(JSONArray array){
		return !isEmpty(array);
	}

}
