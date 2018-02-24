package com.yryz.quanhu.dymaic.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class ResourceInfoVo implements Serializable {
	private static final long serialVersionUID = -3670262531273122386L;
	// 1话题，2帖子，3文章
	private Integer resourceType;
	private TopicInfoVo topicInfo;
	private TopicPostInfoVo topicPostInfo;
	private ReleaseInfoVo releaseInfo;
	private Date createDate;
	private Long lastHeat;
	private UserSimpleVo createUserInfo;
	private String moduleEnum;
	/**
     * 计数、状态
     */
    private Map<String, Long> statistics;
	
	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public TopicInfoVo getTopicInfo() {
		return topicInfo;
	}

	public void setTopicInfo(TopicInfoVo topicInfo) {
		this.topicInfo = topicInfo;
	}

	public TopicPostInfoVo getTopicPostInfo() {
		return topicPostInfo;
	}

	public void setTopicPostInfo(TopicPostInfoVo topicPostInfo) {
		this.topicPostInfo = topicPostInfo;
	}

	public ReleaseInfoVo getReleaseInfo() {
		return releaseInfo;
	}

	public void setReleaseInfo(ReleaseInfoVo releaseInfo) {
		this.releaseInfo = releaseInfo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getLastHeat() {
		return lastHeat;
	}

	public void setLastHeat(Long lastHeat) {
		this.lastHeat = lastHeat;
	}

	public UserSimpleVo getCreateUserInfo() {
		return createUserInfo;
	}

	public void setCreateUserInfo(UserSimpleVo createUserInfo) {
		this.createUserInfo = createUserInfo;
	}

	public Map<String, Long> getStatistics() {
        return statistics;
    }

    public void setStatistics(Map<String, Long> statistics) {
        this.statistics = statistics;
    }
    
    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    @Override
    public String toString() {
        return "ResourceInfoVo [resourceType=" + resourceType + ", topicInfo=" + topicInfo + ", topicPostInfo="
                + topicPostInfo + ", releaseInfo=" + releaseInfo + ", createDate=" + createDate + ", lastHeat="
                + lastHeat + ", createUserInfo=" + createUserInfo + ", statistics=" + statistics + "]";
    }
}
