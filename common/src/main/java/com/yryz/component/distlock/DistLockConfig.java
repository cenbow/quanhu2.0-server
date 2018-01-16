package com.yryz.component.distlock;

import java.io.Serializable;
import java.util.List;

public class DistLockConfig<T extends Serializable> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2090170777268483193L;

	private String prefix;

	private List<T> resources;

	/**
	 * 超时默认500毫秒
	 */
	private Long timeout = 500L;

	/**
	 * 失效默认2秒
	 */
	private Integer expire = 2;

	public DistLockConfig(String prefix, List<T> resources) {
		this.prefix = prefix;
		this.resources = resources;
	}

	public DistLockConfig(String prefix, List<T> resources, Long timeout) {
		this.prefix = prefix;
		this.resources = resources;
		this.timeout = timeout;
	}

	public DistLockConfig(String prefix, List<T> resources, Long timeout, Integer expire) {
		this.prefix = prefix;
		this.resources = resources;
		this.timeout = timeout;
		this.expire = expire;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public List<T> getResources() {
		return resources;
	}

	public void setResources(List<T> resources) {
		this.resources = resources;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	public Integer getExpire() {
		return expire;
	}

	public void setExpire(Integer expire) {
		this.expire = expire;
	}

	public class DistLockConfigEntry<E extends T> {

		private String lockKey;

		private E resource;

		/**
		 * 超时默认500毫秒
		 */
		private Long timeout;

		/**
		 * 失效默认2秒
		 */
		private Integer expire;
		
		public DistLockConfigEntry(String lockKey , E resource , Long timeout , Integer expire){
			this.lockKey = lockKey;
			this.resource = resource;
			this.timeout = timeout;
			this.expire = expire;
		}

		public String getLockKey() {
			return lockKey;
		}

		public void setLockKey(String lockKey) {
			this.lockKey = lockKey;
		}

		public T getResource() {
			return resource;
		}

		public void setResource(E resource) {
			this.resource = resource;
		}

		public Long getTimeout() {
			return timeout;
		}

		public void setTimeout(Long timeout) {
			this.timeout = timeout;
		}

		public Integer getExpire() {
			return expire;
		}

		public void setExpire(Integer expire) {
			this.expire = expire;
		}
	}

}
