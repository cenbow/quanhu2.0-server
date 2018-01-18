package com.yryz.quanhu.support.id.entity;


import com.yryz.quanhu.support.id.common.GenIdConstant;

import java.io.Serializable;


public class KeyBuffer implements Serializable {

	private static final long serialVersionUID = 5084385423087752764L;
	/**
	 * 最小值
	 */
	private long min = GenIdConstant.UNDER_LIMIT;
	/**
	 * 最大值
	 */
	private long max = GenIdConstant.UNDER_LIMIT;
	/**
	 * 当前值
	 */
	private long current = GenIdConstant.UNDER_LIMIT;

	/** 步长 */
	private Integer inc = GenIdConstant.INC_DEFAULT;

	public long getMin() {
		return min;
	}

	public void setMin(long min) {
		this.min = min;
	}

	public long getMax() {
		return max;
	}

	public void setMax(long max) {
		this.max = max;
	}

	public long getCurrent() {
		return current;
	}

	public void setCurrent(long current) {
		this.current = current;
	}

	public boolean hasNext() {
		return current + inc - 1 < max;
	}

	public long nextValue() {
		current = current + inc;
		return current;
	}

	public void copy(KeyBuffer buff) {
		this.max = buff.getMax();
		this.min = buff.getMin();
		this.current = buff.getCurrent();
	}

	public Integer getInc() {
		return inc;
	}

	public void setInc(Integer inc) {
		this.inc = inc;
	}
}
