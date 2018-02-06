/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月26日
 * Id: ResourceTotal.java, 2018年1月26日 上午10:03:00 yehao
 */
package com.yryz.quanhu.resource.vo;

import java.io.Serializable;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月26日 上午10:03:00
 * @Description 资源动态全局字段
 */
public class ResourceTotal implements Serializable {	
	
    /**
     * 动态ID
     */
    private Long kid;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 资源类型模块ID
     * 1000私圈,1001用户,1002转发,1003文章,1004话题,1005帖子,1006问题,1007答案
     */
    private Integer moduleEnum;

    /**
     * 资源ID
     */
    private Long resourceId;

    /**
     * 文章分类ID
     */
    private Integer classifyId;

    /**
     * 私圈ID
     */
    private String coterieId;

    /**
     * 动态标题
     */
    private String title;

    /**
     * 动态正文
     */
    private String content;

    /**
     * 发布时间
     */
    private String createDate;
    
    /**
     * 达人状态：11，是；10，否
     */
    private String talentType;
    
    /**
     * 公开状态：10不公开，11公开
     */
    private String publicState;
    
    /**
     * 扩展字段，仅供展示使用，由前端的发布方和列表解析方解决
     */
    private String extJson;
    
    /**
     * 转发理由
     */
    private String transmitNote;

    /**
     * 转发资源类型模块ID
     * 1000私圈,1001用户,1002转发,1003文章,1004话题,1005帖子,1006问题,1007答案
     */
    private Integer transmitType;

	/**
	 * 转发记录ID
	 */
	private Long transmitId;

	/**
	 * 
	 * @exception 
	 */
	public ResourceTotal() {
		super();
	}

	/**
	 * @return the kid
	 */
	public Long getKid() {
		return kid;
	}

	/**
	 * @param kid the kid to set
	 */
	public void setKid(Long kid) {
		this.kid = kid;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the moduleEnum
	 */
	public Integer getModuleEnum() {
		return moduleEnum;
	}

	/**
	 * @param moduleEnum the moduleEnum to set
	 */
	public void setModuleEnum(Integer moduleEnum) {
		this.moduleEnum = moduleEnum;
	}

	/**
	 * @return the resourceId
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the classifyId
	 */
	public Integer getClassifyId() {
		return classifyId;
	}

	/**
	 * @param classifyId the classifyId to set
	 */
	public void setClassifyId(Integer classifyId) {
		this.classifyId = classifyId;
	}

	/**
	 * @return the coterieId
	 */
	public String getCoterieId() {
		return coterieId;
	}

	/**
	 * @param coterieId the coterieId to set
	 */
	public void setCoterieId(String coterieId) {
		this.coterieId = coterieId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the talentType
	 */
	public String getTalentType() {
		return talentType;
	}

	/**
	 * @param talentType the talentType to set
	 */
	public void setTalentType(String talentType) {
		this.talentType = talentType;
	}

	/**
	 * @return the publicState
	 */
	public String getPublicState() {
		return publicState;
	}

	/**
	 * @param publicState the publicState to set
	 */
	public void setPublicState(String publicState) {
		this.publicState = publicState;
	}

	/**
	 * @return the extJson
	 */
	public String getExtJson() {
		return extJson;
	}

	/**
	 * @param extJson the extJson to set
	 */
	public void setExtJson(String extJson) {
		this.extJson = extJson;
	}

	/**
	 * @return the transmitNote
	 */
	public String getTransmitNote() {
		return transmitNote;
	}

	/**
	 * @param transmitNote the transmitNote to set
	 */
	public void setTransmitNote(String transmitNote) {
		this.transmitNote = transmitNote;
	}

	/**
	 * @return the transmitType
	 */
	public Integer getTransmitType() {
		return transmitType;
	}

	/**
	 * @param transmitType the transmitType to set
	 */
	public void setTransmitType(Integer transmitType) {
		this.transmitType = transmitType;
	}

	public Long getTransmitId() {
		return transmitId;
	}

	public void setTransmitId(Long transmitId) {
		this.transmitId = transmitId;
	}
}
