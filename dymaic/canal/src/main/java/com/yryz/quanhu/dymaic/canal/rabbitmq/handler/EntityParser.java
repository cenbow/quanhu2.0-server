package com.yryz.quanhu.dymaic.canal.rabbitmq.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.yryz.quanhu.dymaic.canal.entity.CanalChangeInfo;

public class EntityParser {
	public static <T> T parse(List<CanalChangeInfo> changeInfoList, Class<T> classs) {
		T instance;
		try {
			instance = classs.newInstance();
			Map<String, CanalChangeInfo> infoMap = CanalChangeInfo.toMap(changeInfoList);
			Method[] methods = classs.getMethods();
			for (int i = 0; i < methods.length; i++) {
				Method md = methods[i];
				if (!md.getName().startsWith("set")) {
					continue;
				}
				
				Class<?>[] classes = md.getParameterTypes();
				if (classes.length != 1) {
					continue;
				}
				
				//约定entity的属性需要和数据库字段对应
				String key = md.getName().substring(3).toLowerCase();
				CanalChangeInfo column = infoMap.get(key);
				if (column != null) {
					Object value = convert(column.getValue(), classes[0]);
					try {
						md.invoke(instance, value);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						throw new RuntimeException(classs.getName() + "."+md.getName()+",赋值异常", e);
					}
				}
			}
			return instance;
		} catch (InstantiationException | IllegalAccessException e1) {
			throw new RuntimeException(classs.getName() + ",实例化异常", e1);
		}
	}
	
	public static <T> T parse(T source, List<CanalChangeInfo> changeInfoList,Class<T> classs) {
		T instance;
		try {
			instance = classs.newInstance();
			if (source != null) {
				BeanUtils.copyProperties(source, instance);
			}

			Map<String, CanalChangeInfo> infoMap = CanalChangeInfo.toMap(changeInfoList);
			Method[] methods = classs.getMethods();
			for (int i = 0; i < methods.length; i++) {
				Method md = methods[i];
				if (!md.getName().startsWith("set")) {
					continue;
				}
				
				Class<?>[] classes = md.getParameterTypes();
				if (classes.length != 1) {
					continue;
				}
				
				String key = md.getName().substring(3).toLowerCase();
				CanalChangeInfo column = infoMap.get(key);
				if (column != null && column.getUpdate()) {
					Object value = convert(column.getValue(), classes[0]);
					try {
						md.invoke(instance, value);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						throw new RuntimeException(classs.getName() + "."+md.getName()+",赋值异常", e);
					}
				}
				
				
			}
			return instance;
		} catch (InstantiationException | IllegalAccessException e1) {
			throw new RuntimeException(classs.getName() + ",实例化异常", e1);
		}
	}

	private static Object convert(String value, Class<?> paramType) {
		if (paramType.equals(String.class)) {
			return value;
		}
		if (StringUtils.isBlank(value)) {
			return null;
		}

		if (paramType.equals(Integer.class)) {
			return Integer.valueOf(value);
		} else if (paramType.equals(Long.class)) {
			return Long.valueOf(value);
		} else if (paramType.equals(Boolean.class)) {
			return Boolean.valueOf(value);
		} else if (paramType.equals(Byte.class)) {
			return Byte.valueOf(value);
		} else if (paramType.equals(Short.class)) {
			return Short.valueOf(value);
		} else if (paramType.equals(Double.class)) {
			return Double.valueOf(value);
		} else if (paramType.equals(Float.class)) {
			return Float.valueOf(value);
		} else if (paramType.equals(Character.class)) {
			return value.charAt(0);
		} else if (paramType.equals(Date.class)){
			SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return fmt.parse(value);
			} catch (ParseException e) {
				return null;
			}
		}else{
			return null;
		}
	}
}
