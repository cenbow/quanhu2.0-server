package com.yryz.common.mongodb;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zhongying
 * @version 1.0
 * @param <T>
 * @date 2017年4月7日 下午3:18:20
 * @Description 分页方法执行对象
 */
public class Page<T> {

	private int currentPage;

	private int pageSize;

	/**
	 * 查询的实体对象集合
	 */
	private Collection<T> entities;

	/*
     * 实体对象数量
     */
	private int count;

	public Page() {

	}

	public Page(int pageSize) {
		this.pageSize = pageSize;
	}

	public Page(int currentPage, int pageSize, Collection<T> entities, int count) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.entities = entities;
		this.count = count;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Collection<T> getEntities() {
		return entities;
	}

	public void setEntities(Collection<T> entities) {
		this.entities = entities;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}

