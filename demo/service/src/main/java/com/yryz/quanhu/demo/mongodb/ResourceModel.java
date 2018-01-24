/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月16日
 * Id: ResourceModel.java, 2018年1月16日 下午2:00:49 yehao
 */
package com.yryz.quanhu.demo.mongodb;

import java.io.Serializable;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月16日 下午2:01:43
 * @Description 资源实体管理类
 */
public class ResourceModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3185737813541083029L;
	
	/** 
	 * 数据id
	 */
	private String resourceId;

	/** 
	 * 发布人ID
	 */
	private String custId;
	
	/** 
	 * 是否达人:否(0)，是(1)
	 */
	private String talentType;
	
	/**
	 * 私圈ID，如果有私圈ID，则表示此资源是私圈资源
	 */
	private String coterieId;

	/**
	 * 功能ID 前端跳转专用 
	 */
	private String moduleEnum;
	
	/** 
	 * 资源类型,枚举：文章(1000)、话题(1001)、帖子(1002)、问题(1003)、答案(1004)、活动(1005)
	 */
	private String resourceType;
	
	/** 
	 * 资源标签
	 */
	private String resourceTag;
	
	/** 
	 * 地区城市，二级城市，地区编码 
	 */
	private String cityCode;

	/** 
	 * 信息标题 
	 */
	private String title;
	
	/** 
	 * 简介 
	 */ 
	private String summary;

	/** 
	 * 信息简介 150字以内
	 */
	private String content;

	/** 
	 * 信息缩略图
	 */
	private String thumbnail;

	/** 
	 * 图片相册数据 
	 */
	private String pics;

	/** 
	 * 视频地址
	 */
	private String video;

	/** 
	 * 视频预览图 
	 */
	private String videoPic;

	/** 
	 * 音频地址
	 */
	private String audio;
	
	/** 
	 * 热度值 
	 */
	private Long heat;
	
	/** 
	 * 阅读数 
	 */
	private Long readNum;
	
	/** 
	 * 参与数
	 */
	private Long partNum;

	/** 
	 * 创建时间
	 */
	private Long createTime;
	
	/** 
	 * 结束时间 
	 */
	private Long completeTime;
	
	/** 
	 * 更新时间
	 */
	private Long updateTime;
	
	/** 
	 * 排序字段 
	 */
	private Long orderby;
	
	/** 
	 * 用户GPS信息 
	 */
	private String gps;
	
	/** 
	 * 资源扩展展示信息
	 */
	private String extjson;
	
	/**
	 * 资源价格
	 */
	private Long price;
	
	/**
	 * 公开状态
	 */
	private Integer publicState;
	
	/**
	 * 推荐类型，0不推荐，1推荐
	 */
	private String recommendType;
	
	/**
	 * 删除状态，0未删除，1已删除
	 */
	private String delFlag;

	/**
	 * 
	 * @exception 
	 */
	public ResourceModel() {
		super();
	}

	/**
	 * @param resourceId
	 * @param custId
	 * @param talentType
	 * @param coterieId
	 * @param moduleEnum
	 * @param resourceType
	 * @param resourceTag
	 * @param cityCode
	 * @param title
	 * @param summary
	 * @param content
	 * @param thumbnail
	 * @param pics
	 * @param video
	 * @param videoPic
	 * @param audio
	 * @param heat
	 * @param readNum
	 * @param partNum
	 * @param createTime
	 * @param completeTime
	 * @param updateTime
	 * @param orderby
	 * @param gps
	 * @param extjson
	 * @param price
	 * @param publicState
	 * @param recommendType
	 * @param delFlag
	 * @exception 
	 */
	public ResourceModel(String resourceId, String custId, String talentType, String coterieId, String moduleEnum,
			String resourceType, String resourceTag, String cityCode, String title, String summary, String content,
			String thumbnail, String pics, String video, String videoPic, String audio, Long heat, Long readNum,
			Long partNum, Long createTime, Long completeTime, Long updateTime, Long orderby, String gps, String extjson,
			Long price, Integer publicState, String recommendType, String delFlag) {
		super();
		this.resourceId = resourceId;
		this.custId = custId;
		this.talentType = talentType;
		this.coterieId = coterieId;
		this.moduleEnum = moduleEnum;
		this.resourceType = resourceType;
		this.resourceTag = resourceTag;
		this.cityCode = cityCode;
		this.title = title;
		this.summary = summary;
		this.content = content;
		this.thumbnail = thumbnail;
		this.pics = pics;
		this.video = video;
		this.videoPic = videoPic;
		this.audio = audio;
		this.heat = heat;
		this.readNum = readNum;
		this.partNum = partNum;
		this.createTime = createTime;
		this.completeTime = completeTime;
		this.updateTime = updateTime;
		this.orderby = orderby;
		this.gps = gps;
		this.extjson = extjson;
		this.price = price;
		this.publicState = publicState;
		this.recommendType = recommendType;
		this.delFlag = delFlag;
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
	 * @return the custId
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param custId the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
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
	 * @return the resourceType
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * @param resourceType the resourceType to set
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * @return the resourceTag
	 */
	public String getResourceTag() {
		return resourceTag;
	}

	/**
	 * @param resourceTag the resourceTag to set
	 */
	public void setResourceTag(String resourceTag) {
		this.resourceTag = resourceTag;
	}

	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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
	 * @return the thumbnail
	 */
	public String getThumbnail() {
		return thumbnail;
	}

	/**
	 * @param thumbnail the thumbnail to set
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * @return the pics
	 */
	public String getPics() {
		return pics;
	}

	/**
	 * @param pics the pics to set
	 */
	public void setPics(String pics) {
		this.pics = pics;
	}

	/**
	 * @return the video
	 */
	public String getVideo() {
		return video;
	}

	/**
	 * @param video the video to set
	 */
	public void setVideo(String video) {
		this.video = video;
	}

	/**
	 * @return the videoPic
	 */
	public String getVideoPic() {
		return videoPic;
	}

	/**
	 * @param videoPic the videoPic to set
	 */
	public void setVideoPic(String videoPic) {
		this.videoPic = videoPic;
	}

	/**
	 * @return the audio
	 */
	public String getAudio() {
		return audio;
	}

	/**
	 * @param audio the audio to set
	 */
	public void setAudio(String audio) {
		this.audio = audio;
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
	 * @return the readNum
	 */
	public Long getReadNum() {
		return readNum;
	}

	/**
	 * @param readNum the readNum to set
	 */
	public void setReadNum(Long readNum) {
		this.readNum = readNum;
	}

	/**
	 * @return the partNum
	 */
	public Long getPartNum() {
		return partNum;
	}

	/**
	 * @param partNum the partNum to set
	 */
	public void setPartNum(Long partNum) {
		this.partNum = partNum;
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
	 * @return the completeTime
	 */
	public Long getCompleteTime() {
		return completeTime;
	}

	/**
	 * @param completeTime the completeTime to set
	 */
	public void setCompleteTime(Long completeTime) {
		this.completeTime = completeTime;
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
	 * @return the orderby
	 */
	public Long getOrderby() {
		return orderby;
	}

	/**
	 * @param orderby the orderby to set
	 */
	public void setOrderby(Long orderby) {
		this.orderby = orderby;
	}

	/**
	 * @return the gps
	 */
	public String getGps() {
		return gps;
	}

	/**
	 * @param gps the gps to set
	 */
	public void setGps(String gps) {
		this.gps = gps;
	}

	/**
	 * @return the extjson
	 */
	public String getExtjson() {
		return extjson;
	}

	/**
	 * @param extjson the extjson to set
	 */
	public void setExtjson(String extjson) {
		this.extjson = extjson;
	}

	/**
	 * @return the price
	 */
	public Long getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Long price) {
		this.price = price;
	}

	/**
	 * @return the publicState
	 */
	public Integer getPublicState() {
		return publicState;
	}

	/**
	 * @param publicState the publicState to set
	 */
	public void setPublicState(Integer publicState) {
		this.publicState = publicState;
	}

	/**
	 * @return the recommendType
	 */
	public String getRecommendType() {
		return recommendType;
	}

	/**
	 * @param recommendType the recommendType to set
	 */
	public void setRecommendType(String recommendType) {
		this.recommendType = recommendType;
	}

	/**
	 * @return the delFlag
	 */
	public String getDelFlag() {
		return delFlag;
	}

	/**
	 * @param delFlag the delFlag to set
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}
