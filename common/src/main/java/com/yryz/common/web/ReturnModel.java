package com.yryz.common.web;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import com.alibaba.fastjson.JSONObject;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.BaseException;

public class ReturnModel {
	/**
	 * bean to result
	 * 
	 * @param object
	 * @return
	 * @throws BaseException
	 */
	public static JSONObject beanToString(Object object) {
		ReturnCode result = new ReturnCode();
		try {
			if (object != null) {
				result.setData(object);
			}
			if (object == null) {
				result.setData(new HashMap<>());
			}
		} catch (Exception e) {
			result = new ReturnCode(ExceptionEnum.Exception.getCode(), ExceptionEnum.Exception.getShowMsg(),
					ExceptionEnum.Exception.getErrorMsg());
		}
		return getJsonObject(result);
	}

	/**
	 * List to result
	 * 
	 * @param object
	 * @return
	 * @throws BaseException
	 */
	public static JSONObject listToString(List<? extends Object> list) {
		ReturnCode result = new ReturnCode();
		try {
			if (list != null) {
				result.setData(list);
			} else {
				list = new ArrayList<Object>();
				result.setData(list);
			}
		} catch (Exception e) {
			result = new ReturnCode(ExceptionEnum.Exception.getCode(), ExceptionEnum.Exception.getShowMsg(),
					ExceptionEnum.Exception.getErrorMsg());
		}
		return getJsonObject(result);
	}

	/**
	 * string to result
	 * 
	 * @param msg
	 * @return
	 */
	public static JSONObject returnSuccess() {
		ReturnCode result = new ReturnCode();
		return getJsonObject(result);
	}

	public static JSONObject returnException() {
		ReturnCode result = new ReturnCode();
		result.setShowmsg(ExceptionEnum.Exception.getShowMsg());
		result.setErrormsg(ExceptionEnum.Exception.getErrorMsg());
		result.setRstcode(ExceptionEnum.Exception.getCode());
		return getJsonObject(result);
	}
	
	public static JSONObject returnException(String showmsg) {
		ReturnCode result = new ReturnCode();
		result.setShowmsg(showmsg);
		result.setErrormsg(ExceptionEnum.Exception.getErrorMsg());
		result.setRstcode(ExceptionEnum.Exception.getCode());
		return getJsonObject(result);
	}
	
	public static JSONObject returnException(String code, String showmsg, String errormsg) {
		ReturnCode result = new ReturnCode();
		result.setShowmsg(showmsg);
		result.setRstcode(code);
		result.setErrormsg(errormsg);
		return getJsonObject(result);
	}

	private static JSONObject getJsonObject(ReturnCode code) {
		JSONObject jsonObject = new JSONObject();
		try {
			Field[] fields = code.getClass().getDeclaredFields();
			if (ArrayUtils.isNotEmpty(fields)) {
				Field.setAccessible(fields, true);
				for (int i = 0; i < fields.length; i++) {
					jsonObject.put(fields[i].getName(), fields[i].get(code));
				}
			}
		} catch (IllegalArgumentException e) {
			jsonObject.put("rstcode", ExceptionEnum.Exception.getCode());
			jsonObject.put("errormsg", ExceptionEnum.Exception.getErrorMsg());
			jsonObject.put("showmsg", ExceptionEnum.Exception.getShowMsg());
		} catch (IllegalAccessException e) {
			jsonObject.put("rstcode", ExceptionEnum.Exception.getCode());
			jsonObject.put("errormsg", ExceptionEnum.Exception.getErrorMsg());
			jsonObject.put("showmsg", ExceptionEnum.Exception.getShowMsg());
		}
		return jsonObject;
	}
}
