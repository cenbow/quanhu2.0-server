package com.yryz.quanhu.user.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 更多达人
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class StarListVO implements Serializable {
	/**
	 * 达人总数
	 */
	private Integer starTotal;
	/**
	 * 达人信息列表
	 */
	private List<StarInfoVO> starList;
	
	public Integer getStarTotal() {
		return starTotal;
	}
	public void setStarTotal(Integer starTotal) {
		this.starTotal = starTotal;
	}
	public List<StarInfoVO> getStarList() {
		return starList;
	}
	public void setStarList(List<StarInfoVO> starList) {
		this.starList = starList;
	}
	@Override
	public String toString() {
		return "StarListDTO [starTotal=" + starTotal + ", starList=" + starList + "]";
	}
}
