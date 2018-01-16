/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月16日
 * Id: ResourceModel.java, 2018年1月16日 下午2:00:49 yehao
 */
package com.yryz.quanhu.resource.entity;

import java.io.Serializable;

import com.yryz.quanhu.resource.enums.ResourceTypeEnum;

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
	 * @see ResourceTypeEnum
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

}
