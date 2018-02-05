/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月11日
 * Id: EventEnum.java, 2017年9月11日 上午9:40:10 Administrator
 */
package com.yryz.quanhu.score.enums;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年9月11日 上午9:40:10
 * @Description 事件枚举
 */
public enum EventEnum {
	/** 创建私圈 */
	CREATE_COTERIE("1", "创建私圈", "每次触发（包含积分、成长、热度）"),
	/** 加入私圈 */
	JOIN_COTERIE("2", "加入私圈", "每次触发（包含积分、成长、热度）"),
	/** 发布资源 */
	CREATE_RESOURCE("3", "发布文章", "每次操作触发（每日前两次发布文章正文内容超过100字时触发，每次记20分，最多记40分）"),
	/** 点赞 */
	//LIKE("4", "点赞", "每日累计操作N次触发(包含积分、热度)"),
	/** 评论 */
	COMMENT("5", "评论", "评论一次记5分，每日最多记10分"),
	/** 分享 */
	SHARE("6", "转发（私圈外的文章、话题、贴子、动态）", "每次操作触发（每日前两次转发触发，每次记10分，最多记20分）"),
	/** 打赏*/
	COLLECTION("7", "打赏", "每次操作触发"),
	/** 充值 */
	RECHARGE("8", "充值", "充值货币A"),
	/** 付费 */
	RESOURCE_PAY("9", "付费", "资源付费"),
	/** 被点赞 */
	REWARD("10", "被点赞", "被点赞一次记1分，每日最多记10分"),
	/** 会员注册 */
	REGISTER("11", "会员注册", "一次性触发"),
	/** 资料完善 */
	USER_DATA_IMPROVE("12", "资料完善", "一次性触发"),
	/** 点赞 */
	READ_RESOURCE("13", "点赞", "(热度)"),
	/** 被评论 */
	REPLY_POST("14", "	被评论", "被评论一次记2分，每日最多记10分"),
	/** 签到 */
	SIGN_IN("15", "签到", "每日累计操作/次触发(积分)"),
	
	/** 内容被付费 */
	RESOURCE_BY_PAY("16","内容被付费","内容被付费"),
	
	/** 发布私圈话题*/
	SEND_TOPIC("17","发布私圈话题","发布私圈话题"),
	
	/** 发布私圈活动*/
	SEND_ACTIVITY("18","发布私圈活动","发布私圈活动"),
	
	/** 成为认证达人 */
	STAR_AUTH_SUCCESS("19","成为认证用户","一次性触发（增加用户成长值到500）"),
	
	/** 发帖 */
	SEND_POST("20","发帖","发帖"),
	
	/** 活动投票 */
	ACTIVITY_VOTE("-1", "活动投票", ""),
	
	/** 积分支付 */
	SCORE_PAY("-2","积分支付","积分支付"),
	
	/** 发布资源（热度计算） */
	CREATE_RESOURCE_HOT("21","发布资源(热度计算)","发布资源(热度计算)"),
	
	/** 用户发布问题  */
	CREATE_QUESTION("23","收藏","收藏"),
	
	/** 阅读  */
	READ("24","阅读","阅读"),
		
	/** 邀请好友注册 */
	INVITE_FRIENDS_TO_REGISTER("25","邀请好友注册","每次触发（可获得500积分/人）"),
	
	/** 被邀请人注册 */
	INVITEE_REGISTER("26","被邀请人注册","一次性触发（可获得500积分/人）"),
	
	/** 发布活动 */
	PUBLICATION_OF_ACTIVITY("30","发布活动","一次性触发(100,300,500)"),
	/** 主动关注人数 */
	ACTIVE_ATTENTION_NUMBER("31","主动关注人数","一次性触发(关注数达到20人，得30分,关注数达到30人，得50分)"),
	
//	/** 内容被点赞达到N次 */
//	RESOURCE_LIKE_TIMES("31", "内容被点赞达到N次", "内容被收藏达到N次(热度)"),
	/** 内容被评论达到N次 */
	RESOURCE_COMMENT_TIMES("32", "内容被评论达到N次", "内容被收藏达到N次(热度)"),
	/** 内容被分享达到N次 */
	RESOURCE_SHARED_TIMES("33", "内容被分享达到N次", "内容被收藏达到N次(热度)"),
	/** 内容被收藏达到N次 */
	RESOURCE_COLLECTION_TIMES("34", "内容被收藏达到N次", "内容被收藏达到N次(热度)"),
	/** 内容被打赏 */
	RESOURCE_REWARDED_TIMES("35","内容被打赏达N次","每次（奖励付费对应的金额数字10倍的成长值）"),
	/** 消费积分 */
	CONSUME_SCORE("36","消费积分","消费积分"),
	/** 新增积分 */
	ADD_SCORE("37","新增积分","新增积分");

	
	/** 事件编码 */
	private String code;

	/** 事件名称 */
	private String name;
	/** 事件触发提交规则 */
	private String rule;

	private EventEnum(String code, String name, String rule) {
		this.code = code;
		this.name = name;
		this.rule = rule;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getRule() {
		return rule;
	}
}
