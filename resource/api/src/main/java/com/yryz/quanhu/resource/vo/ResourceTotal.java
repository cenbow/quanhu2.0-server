/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月26日
 * Id: ResourceTotal.java, 2018年1月26日 上午10:03:00 yehao
 */
package com.yryz.quanhu.resource.vo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月26日 上午10:03:00
 * @Description 资源动态全局字段
 */
public class ResourceTotal {	
	
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
     * 发布时间
     */
    private String createDate;
    
    /**
     * 达人状态：11，是；10，否
     */
    private String talentType;
    
    /**
     * 公开状态：10不公开，11公开
     */
    private String publicState;
    
    /**
     * 扩展字段，仅供展示使用，由前端的发布方和列表解析方解决
     */
    private String extJson;
    
    /**
     * 转发理由
     */
    private String transmitNote;

    /**
     * 转发资源类型模块ID
     * 1000私圈,1001用户,1002转发,1003文章,1004话题,1005帖子,1006问题,1007答案
     */
    private Integer transmitType;

    /**
     * 发布说明
     */
    private String releaseNote;

}
