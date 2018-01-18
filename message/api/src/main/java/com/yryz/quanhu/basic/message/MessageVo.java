package com.yryz.quanhu.basic.message;

import java.io.Serializable;

/**
 * @author xiepeng
 * @version 1.0
 * @date 2017年9月5日15:24:16
 * @Description 消息属性
 */
public class MessageVo implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -4415964546381198562L;
	
	/**
	 * 消息ID
	 */
	private String messageId;

	/**
     * 消息类型（一级分类）
     * @see MessageType
     */
    private Integer type;

    /**
     * 消息标签（二级分类）
     * @see MessageLabel
     */
    private Integer label;
    
    /**
     * 消息枚举类型 ，唯一
     */
    private String msgEnumType;
    /**
     * 目标用户ID
     */
    private String toCust;
    
    /**
     * 展示类型
     * @see MessageViewCode
     */
    private String viewCode;

    /**
     * 行为类型
     * @see MessageActionCode
     */
    private String actionCode;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片
     */
    private String img;

    /**
     * 链接
     */
    private String link;

    /**
     * 发布时间
     */
    private String createTime;
    
    /**
     * 圈子工程名称,可选，有就填写
     */
    private String circleRoute;
    
    /**
     * 功能ID，可选，有就填写
     */
    private String moduleEnum;
    
    /**
     * 资源ID，可选，如果是资源就填写资源ID
     */
    private String resourceId;
    
    /**
     * 私圈ID，可选，如果是私圈或者私圈资源，则填写
     */
    private String coterieId;
    
    /**
     * 圈子ID
     */
    private String circleId;

    /**
     * 拓展数据：资源信息,请填入Body的实体映射表
     * @see Body
     */
    private Object body;
    
    /**
     * JpushId
     */
    private String jpId;

	/**
	 * 
	 * @exception 
	 */
	public MessageVo() {
		super();
	}
	
	
	/**
	 * @param messageId
	 * @exception 
	 */
	public MessageVo(String messageId) {
		super();
		this.messageId = messageId;
	}

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @return the label
	 */
	public Integer getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(Integer label) {
		this.label = label;
	}

	/**
	 * @return the toCust
	 */
	public String getToCust() {
		return toCust;
	}

	/**
	 * @param toCust the toCust to set
	 */
	public void setToCust(String toCust) {
		this.toCust = toCust;
	}
	
	/**
	 * @return the viewCode
	 */
	public String getViewCode() {
		return viewCode;
	}

	/**
	 * @param viewCode the viewCode to set
	 */
	public void setViewCode(String viewCode) {
		this.viewCode = viewCode;
	}


	/**
	 * @return the actionCode
	 */
	public String getActionCode() {
		return actionCode;
	}

	/**
	 * @param actionCode the actionCode to set
	 */
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
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
	 * @return the img
	 */
	public String getImg() {
		return img;
	}

	/**
	 * @param img the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * @return the circleRoute
	 */
	public String getCircleRoute() {
		return circleRoute;
	}

	/**
	 * @param circleRoute the circleRoute to set
	 */
	public void setCircleRoute(String circleRoute) {
		this.circleRoute = circleRoute;
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
	 * @return the circleId
	 */
	public String getCircleId() {
		return circleId;
	}


	/**
	 * @param circleId the circleId to set
	 */
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}


	/**
	 * @return the body
	 */
	public Object getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(Object body) {
		this.body = body;
	}
	
	/**
	 * @return the jpId
	 */
	public String getJpId() {
		return jpId;
	}


	/**
	 * @param jpId the jpId to set
	 */
	public void setJpId(String jpId) {
		this.jpId = jpId;
	}
	
	
	public String getMsgEnumType() {
		return msgEnumType;
	}


	public void setMsgEnumType(String msgEnumType) {
		this.msgEnumType = msgEnumType;
	}


	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
