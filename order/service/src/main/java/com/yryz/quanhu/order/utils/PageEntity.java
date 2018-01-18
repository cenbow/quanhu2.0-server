package com.yryz.quanhu.order.utils;

/**
 * 分页对象积累。需要做查询分类的数据实体都要继承该对象
 * @author Administrator
 *
 */
public class PageEntity {
	
	private long start = 0L;
	
	private long limit = 50L;
	
	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
		if(limit > 0){
			this.limit = limit;
		}
	}

}
