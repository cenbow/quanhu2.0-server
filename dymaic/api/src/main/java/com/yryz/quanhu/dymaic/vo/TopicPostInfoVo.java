package com.yryz.quanhu.dymaic.vo;

import java.io.Serializable;
import java.util.Date;

public class TopicPostInfoVo implements Serializable{
	private static final long serialVersionUID = 756020625791940599L;

	private Long kid;

	private Long topicId;
	private String content;
	private String contentSource;
	private String videoUrl;

	private String videoThumbnailUrl;
	private String imgUrl;
	private String imgThumbnailUrl;
	private String audioUrl;

	private Byte shelveFlag;

	private Byte delFlag;

	private Integer sort;

	private Long createUserId;

	private Long lastUpdateUserId;

	private Date createDate;

	private Date lastUpdateDate;

	private Integer revision;

	private String cityCode;

	private String gps;
	
	private Long coterieId;

	public Long getKid() {
		return kid;
	}

	public void setKid(Long kid) {
		this.kid = kid;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentSource() {
		return contentSource;
	}

	public void setContentSource(String contentSource) {
		this.contentSource = contentSource;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoThumbnailUrl() {
		return videoThumbnailUrl;
	}

	public void setVideoThumbnailUrl(String videoThumbnailUrl) {
		this.videoThumbnailUrl = videoThumbnailUrl;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgThumbnailUrl() {
		return imgThumbnailUrl;
	}

	public void setImgThumbnailUrl(String imgThumbnailUrl) {
		this.imgThumbnailUrl = imgThumbnailUrl;
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public Byte getShelveFlag() {
		return shelveFlag;
	}

	public void setShelveFlag(Byte shelveFlag) {
		this.shelveFlag = shelveFlag;
	}

	public Byte getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Byte delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public void setLastUpdateUserId(Long lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Integer getRevision() {
		return revision;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public Long getCoterieId() {
		return coterieId;
	}

	public void setCoterieId(Long coterieId) {
		this.coterieId = coterieId;
	}

	@Override
	public String toString() {
		return "TopicPostInfoVo [kid=" + kid + ", topicId=" + topicId + ", content=" + content + ", contentSource="
				+ contentSource + ", videoUrl=" + videoUrl + ", videoThumbnailUrl=" + videoThumbnailUrl + ", imgUrl="
				+ imgUrl + ", imgThumbnailUrl=" + imgThumbnailUrl + ", audioUrl=" + audioUrl + ", shelveFlag="
				+ shelveFlag + ", delFlag=" + delFlag + ", sort=" + sort + ", createUserId=" + createUserId
				+ ", lastUpdateUserId=" + lastUpdateUserId + ", createDate=" + createDate + ", lastUpdateDate="
				+ lastUpdateDate + ", revision=" + revision + ", cityCode=" + cityCode + ", gps=" + gps + ", coterieId="
				+ coterieId + "]";
	}
	
}
