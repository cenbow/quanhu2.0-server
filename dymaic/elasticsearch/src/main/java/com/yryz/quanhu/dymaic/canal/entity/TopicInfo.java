package com.yryz.quanhu.dymaic.canal.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class TopicInfo implements Serializable{
	private static final long serialVersionUID = -7955581440447209892L;

//	@Id
    private Long kid;
	
	@Field(type = FieldType.text)
    private String title;
	
	@Field(type = FieldType.text)
    private String imgUrl;
	
	@Field(type = FieldType.text)
    private String content;
	
	@Field(type = FieldType.text)
    private String contentSource;
	
	@Field(type = FieldType.Integer)
    private Byte recommend;
	
	@Field(type = FieldType.Integer)
    private Byte shelveFlag;
	
	@Field(type = FieldType.Integer)
    private Byte delFlag;
	
	@Field(type = FieldType.Integer)
    private Integer sort;
	
	@Field(type = FieldType.Integer)
    private Integer replyCount;
	
	@Field(type = FieldType.Long)
    private Long createUserId;
	
	@Field(type = FieldType.Long)
    private Long lastUpdateUserId;
	
	@Field(type = FieldType.Date)
    private Date createDate;
	
	@Field(type = FieldType.Date)
    private Date lastUpdateDate;
	
	@Field(type = FieldType.Integer)
    private Integer revision;
	
	@Field(type = FieldType.Long)
    private Long coterieId;
	
	@Field(type = FieldType.text)
    private String cityCode;
	
	@Field(type = FieldType.text)
    private String gps;
	
	//最终热度
	@Field(type=FieldType.Long)
	private Long lastHeat;

	public TopicInfo(){
		
	}
	
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

}