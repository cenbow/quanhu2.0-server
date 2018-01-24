/**
 * 
 */
package com.yryz.common.message;

import java.io.Serializable;

/**
 * @author xx
 * @date 2017年9月21日
 */
public class JumpDetails implements	Serializable{
	
	private static final long serialVersionUID = -3634597873342821996L;
	
	/*圈子名称*/
	private	String	circleRoute;
	
	/*私圈id*/
	private	String	coterieId;
	
	/*资源id*/
	private	String	resourceId;
	
	/*功能id*/
	private	String	moduleEnum;

	public JumpDetails() {
		super();
	}

	public JumpDetails(String circleRoute, String coterieId, String resourceId, String moduleEnum) {
		super();
		this.circleRoute = circleRoute;
		this.coterieId = coterieId;
		this.resourceId = resourceId;
		this.moduleEnum = moduleEnum;
	}

	public String getCircleRoute() {
		return circleRoute;
	}

	public void setCircleRoute(String circleRoute) {
		this.circleRoute = circleRoute;
	}

	public String getCoterieId() {
		return coterieId;
	}

	public void setCoterieId(String coterieId) {
		this.coterieId = coterieId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getModuleEnum() {
		return moduleEnum;
	}

	public void setModuleEnum(String moduleEnum) {
		this.moduleEnum = moduleEnum;
	}
	
	
}
