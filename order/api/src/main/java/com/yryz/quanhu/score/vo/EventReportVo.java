/**
* author:XXX
*/
package com.yryz.quanhu.score.vo;

import java.io.Serializable;

/**
 * @author ChengYunfei
 */
public class EventReportVo implements Serializable {

	private String tenantId;

	private String moduleEnum;

	private Long infoId;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getModuleEnum() {
		return moduleEnum;
	}

	public void setModuleEnum(String moduleEnum) {
		this.moduleEnum = moduleEnum;
	}

	public Long getInfoId() {
		return infoId;
	}

	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}
}