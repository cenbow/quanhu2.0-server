/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月16日
 * Id: ResourceModel.java, 2018年1月16日 下午2:00:49 yehao
 */
package com.yryz.quanhu.resource.entity;

import java.io.Serializable;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月16日 下午2:01:43
 * @Description 资源实体管理类
 */
public class ResourceModel extends Entity implements Serializable {

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
    private Integer resourceId;

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
     * 删除标记
     * 10正常，11已删除
     */
    private Integer delFlag;

    /**
     * 发布时间
     */
    private String createDate;
    
    /**
     * 热度值
     */
    private Long heat;
    
    /**
     * 达人状态：11，是；10，否
     */
    private String talentType;
    
    /**
     * 创建时间
     */
    private Long createTime;
    
    /**
     * 更新时间
     */
    private Long updateTime;
    
    /**
     * 公开状态：10不公开，11公开
     */
    private String publicState;
    
    /**
     * 推荐状态：10不推荐，11推荐
     */
    private String recommend;
    
    /**
     * 资源简介
     */
    private String summary;
    
    /**
     * 扩展字段，仅供展示使用，由前端的发布方和列表解析方解决
     */
    private String extJson;

	/**
	 * 
	 * @exception 
	 */
	public ResourceModel() {
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
	public Integer getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Integer resourceId) {
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
	 * @return the delFlag
	 */
	public Integer getDelFlag() {
		return delFlag;
	}

	/**
	 * @param delFlag the delFlag to set
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
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
	 * @return the heat
	 */
	public Long getHeat() {
		return heat;
	}

	/**
	 * @param heat the heat to set
	 */
	public void setHeat(Long heat) {
		this.heat = heat;
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
	 * @return the createTime
	 */
	public Long getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Long getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
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
	 * @return the recommend
	 */
	public String getRecommend() {
		return recommend;
	}

	/**
	 * @param recommend the recommend to set
	 */
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
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
    
}
