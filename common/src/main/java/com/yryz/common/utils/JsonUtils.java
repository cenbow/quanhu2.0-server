package com.yryz.common.utils;


import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Enumeration;  
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;  
import com.google.gson.GsonBuilder;  
import com.google.gson.reflect.TypeToken;
  
public class JsonUtils {  
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
    public static final String EMPTY = "";  
    /** 空的 {@code JSON} 数据 - <code>"{}"</code>。 */  
    public static final String EMPTY_JSON = "{}";  
    /** 空的 {@code JSON} 数组(集合)数据 - {@code "[]"}。 */  
    public static final String EMPTY_JSON_ARRAY = "[]";  
    /** 默认的 {@code JSON} 日期/时间字段的格式化模式。 */  
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";  
    /** {@code Google Gson} 的 {@literal @Since} 注解常用的版本号常量 - {@code 1.0}。 */  
    public static final Double SINCE_VERSION_10 = 1.0d;  
    /** {@code Google Gson} 的 {@literal @Since} 注解常用的版本号常量 - {@code 1.1}。 */  
    public static final Double SINCE_VERSION_11 = 1.1d;  
    /** {@code Google Gson} 的 {@literal @Since} 注解常用的版本号常量 - {@code 1.2}。 */  
    public static final Double SINCE_VERSION_12 = 1.2d;  
  
    /** 
     * 将给定的目标对象根据指定的条件参数转换成 {@code JSON} 格式的字符串。 
     * <p /> 
     * <strong>该方法转换发生错误时，不会抛出任何异常。若发生错误时，曾通对象返回 <code>"{}"</code>； 集合或数组对象返回 
     * <code>"[]"</code></strong> 
     *  
     * @param target 
     *            目标对象。 
     * @param targetType 
     *            目标对象的类型。 
     * @param isSerializeNulls 
     *            是否序列化 {@code null} 值字段。 
     * @param version 
     *            字段的版本号注解。 
     * @param datePattern 
     *            日期字段的格式化模式。 
     * @param excludesFieldsWithoutExpose 
     *            是否排除未标注 {@literal @Expose} 注解的字段。 
     * @return 目标对象的 {@code JSON} 格式的字符串。 
     */  
    public static String toJson(Object target, Type targetType,  
            boolean isSerializeNulls, Double version, String datePattern,  
            boolean excludesFieldsWithoutExpose) {  
        if (target == null) {
            return EMPTY_JSON;
        }
        GsonBuilder builder = new GsonBuilder();  
        if (isSerializeNulls) {
            builder.serializeNulls();
        }
        if (version != null) {
            builder.setVersion(version.doubleValue());
        }
        if (isEmpty(datePattern)) {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        builder.setDateFormat(datePattern);  
        if (excludesFieldsWithoutExpose) {
            builder.excludeFieldsWithoutExposeAnnotation();
        }
        String result = EMPTY;  
        Gson gson = builder.create();  
        try {  
            if (targetType != null) {  
                result = gson.toJson(target, targetType);  
            } else {  
                result = gson.toJson(target);  
            }  
        } catch (Exception ex) {  
            log.warn("目标对象 " + target.getClass().getName()  
                    + " 转换 JSON 字符串时，发生异常！", ex);  
            if (target instanceof Collection || target instanceof Iterator  
                    || target instanceof Enumeration  
                    || target.getClass().isArray()) {  
                result = EMPTY_JSON_ARRAY;  
            } else {
                result = EMPTY_JSON;
            }
        }  
        return result;  
    }  
  
    /** 
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean} 
     * 对象。</strong> 
     * <ul> 
     * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li> 
     * <li>该方法不会转换 {@code null} 值字段；</li> 
     * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li> 
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li> 
     * </ul> 
     *  
     * @param target 
     *            要转换成 {@code JSON} 的目标对象。 
     * @return 目标对象的 {@code JSON} 格式的字符串。 
     */  
    public static String toJson(Object target) {  
        return toJson(target, null, false, null, null, true);  
    }  
  
    /** 
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean} 
     * 对象。</strong> 
     * <ul> 
     * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li> 
     * <li>该方法不会转换 {@code null} 值字段；</li> 
     * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li> 
     * </ul> 
     *  
     * @param target 
     *            要转换成 {@code JSON} 的目标对象。 
     * @param datePattern 
     *            日期字段的格式化模式。 
     * @return 目标对象的 {@code JSON} 格式的字符串。 
     */  
    public static String toJson(Object target, String datePattern) {  
        return toJson(target, null, false, null, datePattern, true);  
    }  
  
    /** 
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean} 
     * 对象。</strong> 
     * <ul> 
     * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li> 
     * <li>该方法不会转换 {@code null} 值字段；</li> 
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li> 
     * </ul> 
     *  
     * @param target 
     *            要转换成 {@code JSON} 的目标对象。 
     * @param version 
     *            字段的版本号注解({@literal @Since})。 
     * @return 目标对象的 {@code JSON} 格式的字符串。 
     */  
    public static String toJson(Object target, Double version) {  
        return toJson(target, null, false, version, null, true);  
    }  
  
    /** 
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean} 
     * 对象。</strong> 
     * <ul> 
     * <li>该方法不会转换 {@code null} 值字段；</li> 
     * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li> 
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li> 
     * </ul> 
     *  
     * @param target 
     *            要转换成 {@code JSON} 的目标对象。 
     * @param excludesFieldsWithoutExpose 
     *            是否排除未标注 {@literal @Expose} 注解的字段。 
     * @return 目标对象的 {@code JSON} 格式的字符串。 
     */  
    public static String toJson(Object target,  
            boolean excludesFieldsWithoutExpose) {  
        return toJson(target, null, false, null, null,  
                excludesFieldsWithoutExpose);  
    }  
  
    /** 
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean} 
     * 对象。</strong> 
     * <ul> 
     * <li>该方法不会转换 {@code null} 值字段；</li> 
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li> 
     * </ul> 
     *  
     * @param target 
     *            要转换成 {@code JSON} 的目标对象。 
     * @param version 
     *            字段的版本号注解({@literal @Since})。 
     * @param excludesFieldsWithoutExpose 
     *            是否排除未标注 {@literal @Expose} 注解的字段。 
     * @return 目标对象的 {@code JSON} 格式的字符串。 
     */  
    public static String toJson(Object target, Double version,  
            boolean excludesFieldsWithoutExpose) {  
        return toJson(target, null, false, version, null,  
                excludesFieldsWithoutExpose);  
    }  
  
    /** 
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong> 
     * <ul> 
     * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li> 
     * <li>该方法不会转换 {@code null} 值字段；</li> 
     * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li> 
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSSS}；</li> 
     * </ul> 
     *  
     * @param target 
     *            要转换成 {@code JSON} 的目标对象。 
     * @param targetType 
     *            目标对象的类型。 
     * @return 目标对象的 {@code JSON} 格式的字符串。 
     */  
    public static String toJson(Object target, Type targetType) {  
        return toJson(target, targetType, false, null, null, true);  
    }  
  
    /** 
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong> 
     * <ul> 
     * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li> 
     * <li>该方法不会转换 {@code null} 值字段；</li> 
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSSS}；</li> 
     * </ul> 
     *  
     * @param target 
     *            要转换成 {@code JSON} 的目标对象。 
     * @param targetType 
     *            目标对象的类型。 
     * @param version 
     *            字段的版本号注解({@literal @Since})。 
     * @return 目标对象的 {@code JSON} 格式的字符串。 
     */  
    public static String toJson(Object target, Type targetType, Double version) {  
        return toJson(target, targetType, false, version, null, true);  
    }  
  
    /** 
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong> 
     * <ul> 
     * <li>该方法不会转换 {@code null} 值字段；</li> 
     * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li> 
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li> 
     * </ul> 
     *  
     * @param target 
     *            要转换成 {@code JSON} 的目标对象。 
     * @param targetType 
     *            目标对象的类型。 
     * @param excludesFieldsWithoutExpose 
     *            是否排除未标注 {@literal @Expose} 注解的字段。 
     * @return 目标对象的 {@code JSON} 格式的字符串。 
     */  
    public static String toJson(Object target, Type targetType,  
            boolean excludesFieldsWithoutExpose) {  
        return toJson(target, targetType, false, null, null,  
                excludesFieldsWithoutExpose);  
    }  
  
    /** 
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong> 
     * <ul> 
     * <li>该方法不会转换 {@code null} 值字段；</li> 
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li> 
     * </ul> 
     *  
     * @param target 
     *            要转换成 {@code JSON} 的目标对象。 
     * @param targetType 
     *            目标对象的类型。 
     * @param version 
     *            字段的版本号注解({@literal @Since})。 
     * @param excludesFieldsWithoutExpose 
     *            是否排除未标注 {@literal @Expose} 注解的字段。 
     * @return 目标对象的 {@code JSON} 格式的字符串。 
     */  
    public static String toJson(Object target, Type targetType, Double version,  
            boolean excludesFieldsWithoutExpose) {  
        return toJson(target, targetType, false, version, null,  
                excludesFieldsWithoutExpose);  
    }  
  
    /** 
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。 
     *  
     * @param <T> 
     *            要转换的目标类型。 
     * @param json 
     *            给定的 {@code JSON} 字符串。 
     * @param token 
     *            {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。 
     * @param datePattern 
     *            日期格式模式。 
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。 
     */  
    public static <T> T fromJson(String json, TypeToken<T> token,  
            String datePattern) {  
        if (isEmpty(json)) {  
            return null;  
        }  
        GsonBuilder builder = new GsonBuilder();  
        if (isEmpty(datePattern)) {  
            datePattern = DEFAULT_DATE_PATTERN;  
        }  
        Gson gson = builder.create();  
        try {  
            return gson.fromJson(json, token.getType());  
        } catch (Exception ex) {  
            log.error(json + " 无法转换为 " + token.getRawType().getName() + " 对象!",  
                    ex);  
            return null;  
        }  
    }  
  
    /** 
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。 
     *  
     * @param <T> 
     *            要转换的目标类型。 
     * @param json 
     *            给定的 {@code JSON} 字符串。 
     * @param token 
     *            {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。 
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。 
     */  
    public static <T> T fromJson(String json, TypeToken<T> token) {  
        return fromJson(json, token, null);  
    }  
  
    /** 
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean} 
     * 对象。</strong> 
     *  
     * @param <T> 
     *            要转换的目标类型。 
     * @param json 
     *            给定的 {@code JSON} 字符串。 
     * @param clazz 
     *            要转换的目标类。 
     * @param datePattern 
     *            日期格式模式。 
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。 
     */  
    public static <T> T fromJson(String json, Class<T> clazz, String datePattern) {  
        if (isEmpty(json)) {  
            return null;  
        }  
        GsonBuilder builder = new GsonBuilder();  
        if (isEmpty(datePattern)) {  
            datePattern = DEFAULT_DATE_PATTERN;  
        }  
        Gson gson = builder.create();  
        try {  
            return gson.fromJson(json, clazz);  
        } catch (Exception ex) {  
            log.error(json + " 无法转换为 " + clazz.getName() + " 对象!", ex);  
            return null;  
        }  
    }  
  
    /** 
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean} 
     * 对象。</strong> 
     *  
     * @param <T> 
     *            要转换的目标类型。 
     * @param json 
     *            给定的 {@code JSON} 字符串。 
     * @param clazz 
     *            要转换的目标类。 
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。 
     */  
    public static <T> T fromJson(String json, Class<T> clazz) {  
        return fromJson(json, clazz, null);  
    }  
  
    public static boolean isEmpty(String inStr) {  
        boolean reTag = false;  
        if (inStr == null || "".equals(inStr)) {  
            reTag = true;  
        }  
        return reTag;  
    }  
    
    /**
     * 将对象转成json
     * <strong>此方法可以转换任意对象,注意对象成员有Date类型 可以加上@JsonFormate(pattern="format")注解</strong>
     * @param obj
     * 
     * @return
     */
    public static String toFastJson(Object obj)
    {    	
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			 log.warn("目标对象 " + obj.getClass().getName()  
	                    + " 转换 JSON 字符串时，发生异常！", e); 
			return null;
		}
    }
    
    /**
     * 将对象转成json
     * <strong>此方法可以转换任意对象,pattern默认yyyy-MM-dd HH:mm:ss</strong>
     * @param obj
     * @param pattern
     * @return
     */
    public static String toFastJson(Object obj,String pattern)
    {    	
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			if(StringUtils.isNotEmpty(pattern)) {
                pattern = "yyyy-MM-dd HH:mm:ss";
            }
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			mapper.setDateFormat(dateFormat);
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			 log.warn("目标对象 " + obj.getClass().getName()  
	                    + " 转换 JSON 字符串时，发生异常！", e); 
			return null;
		}
    }
    
    /**
     * 讲给的的JSON 字符串 转化成指定的类型对象
     * <strong>此方法可以转换任意对象,注意对象成员有Date类型 可以加上@JsonFormate(pattern="format")注解</strong>
     * @param json
     * @param reference
     * @return
     */
    public static <T> T fromJson(String json,TypeReference<T> reference)
    {
    	try {
    		ObjectMapper mapper = new ObjectMapper();
    		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return mapper.readValue(json, reference);
		} catch (JsonParseException e) {
			log.error(json + " 无法转换为 " + reference.getType().getTypeName() + " 对象!", e);  
			return null;
		} catch (JsonMappingException e) {
			log.error(json + " 无法转换为 " + reference.getType().getTypeName() + " 对象!", e); 
			return null;
		} catch (IOException e) {
			log.error(json + " 无法转换为 " + reference.getType().getTypeName() + " 对象!", e); 
			return null;
		}
    }
    
    /**
     * 将JSON 字符串 转化成指定的类型对象
     * <strong>此方法可以转换任意对象,pattern默认yyyy-MM-dd HH:mm:ss</strong>
     * @param json
     * @param reference
     * @param pattern 指定日期格式
     * @return
     */
    public static <T> T fromJson(String json,TypeReference<T> reference,String pattern)
    {
    	try {
    		ObjectMapper mapper = new ObjectMapper();
    		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    		if(StringUtils.isNotEmpty(pattern)) {
                pattern = "yyyy-MM-dd HH:mm:ss";
            }
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			mapper.setDateFormat(dateFormat);
    		return mapper.readValue(json, reference);
		} catch (JsonParseException e) {
			log.error(json + " 无法转换为 " + reference.getType().getTypeName() + " 对象!", e);  
			return null;
		} catch (JsonMappingException e) {
			log.error(json + " 无法转换为 " + reference.getType().getTypeName() + " 对象!", e); 
			return null;
		} catch (IOException e) {
			log.error(json + " 无法转换为 " + reference.getType().getTypeName() + " 对象!", e); 
			return null;
		}
    }
    
}  

