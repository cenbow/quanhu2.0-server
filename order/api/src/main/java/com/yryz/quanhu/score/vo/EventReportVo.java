/**
* author:XXX
*/
package com.yryz.quanhu.score.vo;

import java.io.Serializable;




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
    
    private Long userId;
    
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
    private String createTime;
    
	/** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    private String updateTime;

	/** 成长值 */
    @ApiModelProperty(value = "成长值")
	private Long grow = 0L;
	
    /** 成长级别 */
    @ApiModelProperty(value = "成长级别")
	private String growLevel;
    
    
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

    public Long getGrow() {
		return grow;
	}

	public void setGrow(Long grow) {
		this.grow = grow;
	}

	public String getGrowLevel() {
		return growLevel;
	}

	public void setGrowLevel(String growLevel) {
		this.growLevel = growLevel;
	}

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}