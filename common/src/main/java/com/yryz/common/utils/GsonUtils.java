package com.yryz.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
	public static Map<String,?> parsetMap(Map<String,?>map,Class clazz)
	{
		if(map!=null)
		{
			Map newmap = new HashMap<>();
			for(Map.Entry<String, ?> entry:map.entrySet())
			{
				Object obj = parseObj(entry.getValue(), clazz);
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
			return json.get(propertyName);
		} 
		return null;
	}
	
	/**
	 * @author admin
	 * @date 2014年7月22日
	 * @param fromJson
	 * @param toJson
	 * @return
	 * @Description 操作原json到目标json
	 */
	public static JSONObject addJson(JSONObject fromJson , JSONObject toJson){
		Set<String> keys = toJson.keySet();
		for (String key : keys) {
			fromJson.put(key, toJson.get(key));
		}
		return fromJson;
	}

}
