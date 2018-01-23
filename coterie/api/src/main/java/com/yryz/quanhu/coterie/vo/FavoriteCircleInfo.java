package com.yryz.quanhu.coterie.vo;

import java.io.Serializable;

/**
 * 
 * @author jiangkun
 * @version 1.0
 * @date 2017年10月18日 上午9:48:39
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
@SuppressWarnings("serial")
public class FavoriteCircleInfo implements Serializable {
	private static final long serialVersionUID = -8693543366634932651L;
	/**
	 * 圈子ID
	 */
	private String appId;
	/**
	 * 用户ID
	 */
	private String custId;

	/** 圈子名称 */
	private String circleName;

	/** 圈子图标 */
	private String circleIcon;

	/** 圈子首页地址 */
	private String circleUrl;

	/** 圈子路由 */
	private String circleRoute;

	/** 1:用户选择的喜欢圈子，2：系统计算推荐的 */
	private Integer type;

	/** 圈子简介 */
	private String circleIntro;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getCircleIcon() {
		return circleIcon;
	}

	public void setCircleIcon(String circleIcon) {
		this.circleIcon = circleIcon;
	}

	public String getCircleUrl() {
		return circleUrl;
	}

	public void setCircleUrl(String circleUrl) {
		this.circleUrl = circleUrl;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCircleRoute() {
		return circleRoute;
	}

	public void setCircleRoute(String circleRoute) {
		this.circleRoute = circleRoute;
	}

	public String getCircleIntro() {
		return circleIntro;
	}

	public void setCircleIntro(String circleIntro) {
		this.circleIntro = circleIntro;
	}

}
