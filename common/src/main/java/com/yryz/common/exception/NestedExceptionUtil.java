/*
 * NestedExceptionUtil.java
 * Copyright (c) 2011,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2011-3-8 Created
 */
package com.yryz.common.exception;

/**
 * <p>
 * 异常基础类负责拼装消息
 * </p>
 * 
 * @author kinjoYang
 * @version 1.0 2011-3-8
 * @since 1.0
 */
public abstract class NestedExceptionUtil {

	public static String buildMessage(String message, Throwable cause) {
		if (cause != null) {
			StringBuffer buf = new StringBuffer();
			if (message != null) {
				buf.append(message).append(";");
			}
			buf.append("Exception is:").append(cause);
			return buf.toString();
		} else {
			return message;
		}
	}

	public static String buildMessage(String message, int type, Throwable cause) {
		if (cause != null) {
			StringBuffer buf = new StringBuffer();
			if (message != null) {
				buf.append(message).append(",");
			}
			try {
				switch (type) {
				case ExceptionDescriptor.EXCEPTION_DEF:
					buf.append("PangoNestedRuntimeException");
				case ExceptionDescriptor.EXCEPTION_SQL:
					buf.append("PangoSQLException,SQL is:" + ((DataBaseAccessException) cause).getSql());
				case ExceptionDescriptor.EXCEPTION_CCE:
					buf.append("PangoClassCastException");
				case ExceptionDescriptor.EXCEPTION_IOB:
					buf.append("PangoIndexOutOfBandsException");
				case ExceptionDescriptor.EXCEPTION_NCF:
					buf.append("PangoNoClassDefFoundException");
				case ExceptionDescriptor.EXCEPTION_SEC:
					buf.append("PangoSeccurityException");
				case ExceptionDescriptor.EXCEPTION_NPE:
					buf.append("PangoNullPointerException");
				case ExceptionDescriptor.EXCEPTION_MOG:
					buf.append("PangoNestedRuntimeException");
				default:
					buf.append("PangoNestedRuntimeException");
				}
			} catch (Exception e) {
				buf.append("PangoNestedRuntimeException");
			}
			// buf.append(" caused by :").append(cause);
			buf.append(" caused by :").append(cause.getMessage());
			return buf.toString();
		} else {
			return message;
		}
	}
}
