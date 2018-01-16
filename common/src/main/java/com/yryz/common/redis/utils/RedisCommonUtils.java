package com.yryz.common.redis.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.yryz.common.utils.StringUtils;

public class RedisCommonUtils {

	/**
	 * 把object对象转成map
	 * 
	 * @param t
	 * @return
	 */
	public static <T> Map<String, String> objectToMap(T t) {
		Map<String, String> map = new HashMap<>();
		Field[] fields = t.getClass().getDeclaredFields();
		Field.setAccessible(fields, true);
		if (null != fields) {
			try {
				for (Field f : fields) {
					Object value = f.get(t);
					String typeName = f.getType().getName();
					if ("java.util.Date".equals(typeName) && value != null) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date = (Date) f.get(t);
						map.put(f.getName(), dateFormat.format(date));
					} else {
						map.put(f.getName(), value == null ? "" : value.toString());
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
			}
		}

		return map;
	}

	/**
	 * map转object
	 * 
	 * @param map
	 * @param t
	 */
	public static <T> void mapToObject(Map<String, String> map, T t) {
		Method[] methods = t.getClass().getDeclaredMethods();

		if (null != methods) {
			try {
				for (Method m : methods) {
					String methodName = m.getName();
	
					if (methodName.startsWith("set")) {
						methodName = methodName.substring(3);
						// 获取属性名称
						methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
	
						if (!"class".equalsIgnoreCase(methodName)) {
							String value = map.get(methodName);
							
							if ("java.lang.Long".equals(m.getParameterTypes()[0].getName())) {
								m.invoke(t, new Object[] { StringUtils.isEmpty(value) ? 0L : Long.valueOf(value) });
							} else if ("java.lang.Integer".equals(m.getParameterTypes()[0].getName())) {
								m.invoke(t, new Object[] { StringUtils.isEmpty(value) ? 0 : Integer.valueOf(value) });
							} else if ("java.lang.Double".equals(m.getParameterTypes()[0].getName())) {
								m.invoke(t, new Object[] { StringUtils.isEmpty(value) ? 0d : Double.valueOf(value) });
							} else if ("java.lang.Short".equals(m.getParameterTypes()[0].getName())) {
								m.invoke(t,
										new Object[] { StringUtils.isEmpty(value) ? (short) 0 : Short.valueOf(value) });
							} else if ("java.lang.Float".equals(m.getParameterTypes()[0].getName())) {
								m.invoke(t, new Object[] { StringUtils.isEmpty(value) ? 0f : Float.valueOf(value) });
							} else if ("java.lang.Byte".equals(m.getParameterTypes()[0].getName())) {
								m.invoke(t,
										new Object[] { StringUtils.isEmpty(value) ? (byte) 0 : Byte.valueOf(value) });
							} else if ("java.lang.String".equals(m.getParameterTypes()[0].getName())) {
								m.invoke(t, new Object[] { value });
							}else if ("java.util.Date".equals(m.getParameterTypes()[0].getName())) {
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date date = null;
								try {
									date = StringUtils.isEmpty(value)?null:dateFormat.parse(value);
								} catch (ParseException e) {
								}
								m.invoke(t, new Object[] { date });
							}
						}
					}
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			}
		}
	}

}
