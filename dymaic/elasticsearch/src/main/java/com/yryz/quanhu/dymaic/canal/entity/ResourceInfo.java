package com.yryz.quanhu.dymaic.canal.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "quanhu-v2-resourceinfo", type = "resourceInfo", refreshInterval = "-1")
public class ResourceInfo {
	
	private TopicInfo topicInfo;
	private TopicPostInfo topicPostInfo;
	private ReleaseInfo releaseInfo;
	@Id
	private Long kid;//文章或话题或帖子的kid
	// 1话题，2帖子，3文章
	private Integer resourceType;
	private Date createDate;
	private Long lastHeat;

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public TopicInfo getTopicInfo() {
		return topicInfo;
	}

	public void setTopicInfo(TopicInfo topicInfo) {
		this.topicInfo = topicInfo;
	}

	public TopicPostInfo getTopicPostInfo() {
		return topicPostInfo;
	}

	public void setTopicPostInfo(TopicPostInfo topicPostInfo) {
		this.topicPostInfo = topicPostInfo;
	}

	public ReleaseInfo getReleaseInfo() {
		return releaseInfo;
	}

	public void setReleaseInfo(ReleaseInfo releaseInfo) {
		this.releaseInfo = releaseInfo;
	}

	public Long getKid() {
		return kid;
	}

	public void setKid(Long kid) {
		this.kid = kid;
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

}
