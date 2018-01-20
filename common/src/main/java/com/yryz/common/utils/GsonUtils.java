/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月19日
 * Id: GsonUtils.java, 2018年1月19日 上午9:15:17 yehao
 */
package com.yryz.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月19日 上午9:15:17
 * @Description Gson序列化工具
 */
public class GsonUtils {
	
	/**
	 * 将对象转为Json字符串
	 * @param obj
	 * @return
	 */
	public static String parseJson(Object obj){
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = builder.create();
		return gson.toJson(obj);
	}
	
	/**
	 * json字符串转对象
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T json2Obj(String json , Class<T> clazz){
		if(json == null || "".equals(json.trim())){
			return null;
		}
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = builder.create();
		return gson.fromJson(json, clazz);
	}
	
	/**
	 * 将对象转化成一个新对象
	 * @param src
	 * @param clazz
	 * @return
	 */
	public static <T> T parseObj(Object src , Class<T> clazz){
		if(src == null) {
			return null;
		}
		String json = parseJson(src);
		return json2Obj(json, clazz);
	}
	
	/**
	 * 转换map
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static <T> Map<String,T> parseMap(Map<String,?>map,Class<T> clazz)
	{
		if(map!=null)
		{
			Map<String,T> newmap = new HashMap<>();
			for(Map.Entry<String, ?> entry:map.entrySet())
			{
				T obj = parseObj(entry.getValue(), clazz);
				newmap.put(entry.getKey(), obj);
			}
			return newmap;
		}else
		{
			return null;
		}
	}
	
	/**
	 * 转化列表
	 * @param list
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> parseList(List<?> list , Class<T> clazz){
		if(list != null){
			List<T> newList = new ArrayList<>();
			for (Object object : list) {
				T obj = parseObj(object , clazz);
				newList.add(obj);
			}
			return newList;
		} else {
			return null;
		}
	}
	
	/**
	 * @author admin
	 * @date 2014年7月22日
	 * @param json
	 * @param propertyName
	 * @return
	 * @Description 获取参数的值
	 */
	public static Object get(JSONObject json , String propertyName){
		if(json.has(propertyName)){
			try {
				return json.get(propertyName);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} 
		return null;
	}
	
}
