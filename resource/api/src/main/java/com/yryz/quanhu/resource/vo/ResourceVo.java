/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月16日
 * Id: ResourceVo.java, 2018年1月16日 下午2:13:23 yehao
 */
package com.yryz.quanhu.resource.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;



import com.yryz.quanhu.coterie.coterie.vo.Coterie;
import com.yryz.quanhu.coterie.coterie.vo.CoterieBasicInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.resource.enums.ResourceModuleEnum;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月16日 下午2:13:23
 * @Description 资源状态的VO类
 */
public class ResourceVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8234067721074858571L;
	
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
     * @see ResourceModuleEnum
     */
    private String moduleEnum;

    /**
     * 资源ID
     */
    private String resourceId;

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
    private Date createDate;
    
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
     * 推荐时间
     */
    private Long recommendTime;
    
    /**
     * 公开状态：10不公开，11公开
     */
    private String publicState;
    
    /**
     * 推荐状态：10不推荐，11推荐
     */
    private String recommend;
    
    /**
     * 私密状态：10非私密，11私密状态
     */
    private String intimate;
    
    /**
     * 扩展字段，仅供展示使用，由前端的发布方和列表解析方解决
     */
    private String extJson;
    
    /**
     * 排序字段，管理使用
     */
    private Long sort;
    
    /**
     * 排序字段(私圈首页排序)
     */
    private Long coterieSort;
    
    /**
     * 阅读数状态
     */
    private Map<String, Long> statistics;
	
    /**
     * 用户基础信息
     */
    private UserSimpleVO user;
    
    private Coterie coterie;

	/**
	 * 
	 * @exception 
	 */
	public ResourceVo() {
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
	public String getModuleEnum() {
		return moduleEnum;
	}

	/**
	 * @param moduleEnum the moduleEnum to set
	 */
	public void setModuleEnum(String moduleEnum) {
		this.moduleEnum = moduleEnum;
	}

	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(String resourceId) {
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
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
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
	 * @return the recommendTime
	 */
	public Long getRecommendTime() {
		return recommendTime;
	}

	/**
	 * @param recommendTime the recommendTime to set
	 */
	public void setRecommendTime(Long recommendTime) {
		this.recommendTime = recommendTime;
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
	 * @return the intimate
	 */
	public String getIntimate() {
		return intimate;
	}

	/**
	 * @param intimate the intimate to set
	 */
	public void setIntimate(String intimate) {
		this.intimate = intimate;
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
	 * @return the sort
	 */
	public Long getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	/**
	 * @return the coterieSort
	 */
	public Long getCoterieSort() {
		return coterieSort;
	}

	/**
	 * @param coterieSort the coterieSort to set
	 */
	public void setCoterieSort(Long coterieSort) {
		this.coterieSort = coterieSort;
	}
	
	/**
	 * @return the statistics
	 */
	public Map<String, Long> getStatistics() {
		return statistics;
	}

	/**
	 * @param statistics the statistics to set
	 */
	public void setStatistics(Map<String, Long> statistics) {
		this.statistics = statistics;
	}

	/**
	 * @return the user
	 */
	public UserSimpleVO getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserSimpleVO user) {
		this.user = user;
	}

	/**
	 * @return the coterie
	 */
	public Coterie getCoterie() {
		return coterie;
	}

	/**
	 * @param coterie the coterie to set
	 */
	public void setCoterie(Coterie coterie) {
		this.coterie = coterie;
	}
	
}
