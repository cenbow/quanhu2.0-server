/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月16日
 * Id: MyInviterVO.java, 2017年11月16日 下午4:22:11 Administrator
 */
package com.yryz.quanhu.user.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 我的邀请返回实体
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月16日 下午4:22:11
 */
@SuppressWarnings("serial")
public class MyInviterVO implements Serializable {
	/**
	 * 	邀请好友总数量
	 */
	private Integer total;
	
	/**
	 * 好友详情
	 */
	private List<MyInviterDetailVO> inviterDetail;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}


	public List<MyInviterDetailVO> getInviterDetail() {
		return inviterDetail;
	}

	public void setInviterDetail(List<MyInviterDetailVO> inviterDetail) {
		this.inviterDetail = inviterDetail;
	}

	/**
	 * 
	 * @exception 
	 */
	public MyInviterVO() {
		super();
	}


	/**
	 * @param total
	 * @param inviterDetail
	 * @exception 
	 */
	public MyInviterVO(Integer total, List<MyInviterDetailVO> inviterDetail) {
		super();
		this.total = total;
		this.inviterDetail = inviterDetail;
	}

	@Override
	public String toString() {
		return "MyInviterVO [total=" + total + ", inviterDetail=" + inviterDetail + "]";
	}
}
