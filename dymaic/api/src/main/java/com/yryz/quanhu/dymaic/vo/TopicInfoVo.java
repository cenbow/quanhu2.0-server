package com.yryz.quanhu.dymaic.vo;

import java.io.Serializable;
import java.util.Date;

public class TopicInfoVo implements Serializable {
	private static final long serialVersionUID = -7714943393916036981L;

	private Long kid;

	private String title;

	private String imgUrl;

	private String content;

	private String contentSource;

	private Byte recommend;

	private Byte shelveFlag;

	private Byte delFlag;

	private Integer sort;

	private Integer replyCount;

	private Long createUserId;

	private Long lastUpdateUserId;

	private Date createDate;

	private Date lastUpdateDate;

	private Integer revision;

	private Long coterieId;

	private String cityCode;

	private String gps;
	
	public Long getKid() {
		return kid;
	}

	public void setKid(Long kid) {
		this.kid = kid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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

	public Byte getRecommend() {
		return recommend;
	}

	public void setRecommend(Byte recommend) {
		this.recommend = recommend;
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

	public Integer getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
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

	public Long getCoterieId() {
		return coterieId;
	}

	public void setCoterieId(Long coterieId) {
		this.coterieId = coterieId;
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

	@Override
	public String toString() {
		return "TopicInfoVo [kid=" + kid + ", title=" + title + ", imgUrl=" + imgUrl + ", content=" + content
				+ ", contentSource=" + contentSource + ", recommend=" + recommend + ", shelveFlag=" + shelveFlag
				+ ", delFlag=" + delFlag + ", sort=" + sort + ", replyCount=" + replyCount + ", createUserId="
				+ createUserId + ", lastUpdateUserId=" + lastUpdateUserId + ", createDate=" + createDate
				+ ", lastUpdateDate=" + lastUpdateDate + ", revision=" + revision + ", coterieId=" + coterieId
				+ ", cityCode=" + cityCode + ", gps=" + gps + "]";
	}

}