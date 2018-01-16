/*
 * ExceptionDescriptor.java
 * Copyright (c) 2011,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2011-3-8 Created
 */
package com.yryz.common.exception;

/**
 * <p>
 * 异常描叙
 * </p>
 * 
 * @author kinjoYang
 * @version 1.0 2011-3-8
 * @since 1.0
 * 
 */
public final class ExceptionDescriptor {

	/**default Exception*/
	public static final int EXCEPTION_DEF = -1;
	/** SQLException*/
	public static final int EXCEPTION_SQL = 0;

	/**IndexOutOfBandsException*/
	public static final int EXCEPTION_IOB = 1;

	/**ClassCastException*/
	public static final int EXCEPTION_CCE = 2;

	/**NoClassDefFoundException*/
	public static final int EXCEPTION_NCF = 3;

	/**SeccurityException*/
	public static final int EXCEPTION_SEC = 4;

	/**NullPointerException*/
	public static final int EXCEPTION_NPE = 5;

	/**mongodb Exception*/
	public static final int EXCEPTION_MOG = 6;

}
