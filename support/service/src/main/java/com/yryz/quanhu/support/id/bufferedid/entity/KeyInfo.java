package com.yryz.quanhu.support.id.bufferedid.entity;

import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KeyInfo implements Serializable {

	private static final long serialVersionUID = -3642287947953099108L;

	public KeyInfo() {
	}

	public KeyInfo(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
		this.currBuff = new KeyBuffer();
	}

	public KeyInfo(String primaryKeyName, KeyBuffer currBuff) {
		this.primaryKeyName = primaryKeyName;
		this.currBuff = currBuff;
	}

	/**
	 * 主键名称
	 */
	private String primaryKeyName;

	/**
	 * 当前主键缓冲区
	 */
	private KeyBuffer currBuff;

	/**
	 * 预备主键缓冲区
	 */
	private Queue<KeyBuffer> prepBuffs = new ConcurrentLinkedQueue<KeyBuffer>();

	/**
	 * 当前主键缓冲区锁
	 */
	public final byte[] currBuffLock = new byte[0];

	/**
	 * 预备缓冲区锁
	 */
	public final byte[] prepBuffLock = new byte[0];

	public String getPrimaryKeyName() {
		return primaryKeyName;
	}

	public KeyBuffer getCurrBuff() {
		return currBuff;
	}

	public Queue<KeyBuffer> getPrepBuffs() {
		return prepBuffs;
	}

}
