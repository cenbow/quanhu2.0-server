package com.yryz.quanhu.support.id.common;

public class GenIdConstant {
	
	public static final Long UNDER_LIMIT = -1L;

	/**
	 * 数据库重试次数
	 */
	public static final int RETRY_COUNT = 5;
	
	/**
	 * 扩充预备缓冲区线程数限制
	 */
	public static final int EXPAND_PREPBUFF_THREAD_LIMIT = 3;
	
	public static final int UPDATE_1 = 1;
	
	/**默认步长*/
	public static final int INC_DEFAULT = 1;
	
	/** 步长除数默认值 */
	public static final Long MOD_INC_DEFAULT = 2L;


	/**初始化默认起始值*/
	public static final int DEFAULT_CODE_LENGTH = 6;
	/**初始化默认步长**/
	public static final Long DEFAULT_PRIMARYKEY_STEP = 100L;
}
