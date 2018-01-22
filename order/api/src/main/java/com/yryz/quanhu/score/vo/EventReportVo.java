/**
* author:XXX
*/
package com.yryz.quanhu.score.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author ChengYunfei
 */
public class EventReportVo implements Serializable {

	
	private String tenantId;

	private String moduleEnum;

	private Long infoId;

	/** 用户id */
    @ApiModelProperty(value = "用户id")
	private Long id;
    
	/** 积分增减标志（0-增加，1-减少） */
    @ApiModelProperty(value = "积分增减标志（0增加，1减少）")
	private int consumeFlag;
    
	/** 用户id */
    @ApiModelProperty(value = "用户id")
    private String custId;
    
	/** 事件编码 */
    @ApiModelProperty(value = "事件编码")
    private String eventCode;
    
	/** 新增积分 */
    @ApiModelProperty(value = "新增积分")
    private Integer newScore;
    
	/** 总积分 */
    @ApiModelProperty(value = "总积分")
    private Long allScore;
    
	/** 记录时间 */
    @ApiModelProperty(value = "记录时间")
    private Date createTime;
    
	/** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    

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
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getConsumeFlag() {
		return consumeFlag;
	}

	public void setConsumeFlag(int consumeFlag) {
		this.consumeFlag = consumeFlag;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}


	public Integer getNewScore() {
		return newScore;
	}

	public void setNewScore(Integer newScore) {
		this.newScore = newScore;
	}

	public Long getAllScore() {
		return allScore;
	}

	public void setAllScore(Long allScore) {
		this.allScore = allScore;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}