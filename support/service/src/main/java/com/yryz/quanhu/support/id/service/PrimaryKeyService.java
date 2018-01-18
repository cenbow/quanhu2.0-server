package com.yryz.quanhu.support.id.service;


import com.yryz.quanhu.support.id.entity.KeyBuffer;
import com.yryz.quanhu.support.id.entity.KeyInfo;

public interface PrimaryKeyService {

	/**
	 * 异步扩展主缓冲区
	 * 
	 * @param keyInfo
	 */
	void asynExpandKey(final KeyInfo keyInfo);

	/**
	 * (同步)扩展主缓冲区
	 * 
	 * @param keyInfo
	 */
	void syncExpandKey(KeyInfo keyInfo);

	/**
	 * 扩展主缓冲区
	 * 
	 * @param keyInfo
	 */
	KeyBuffer expandCurrBuff(final KeyInfo keyInfo);

	/**
	 * 异步加载预备缓冲区
	 * 
	 * @param keyInfo
	 */
	void asynLoadPrepBuff(KeyInfo keyInfo);

	/**
	 * (同步)加载预备缓冲区
	 * 
	 * @param keyInfo
	 */
	void loadPrepBuff(final KeyInfo keyInfo);

	/**
	 * 从数据库读取一个缓冲区
	 * 
	 * @param primaryKeyName
	 * @return
	 */
	KeyBuffer loadBuff(final String primaryKeyName);

}