/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月11日
 * Id: MessageConstant.java, 2017年9月11日 下午3:02:01 yehao
 */
package com.yryz.common.message;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年9月11日 下午3:02:01
 * @Description 消息枚举
 */
public enum MessageConstant {
	
	/**
	 * 修改支付密码
	 */
	EDIT_PAY_PASSWORD(MessageType.SYSTEM_TYPE,MessageLabel.SYSTEM_ACCOUNT,"密码安全提醒","您已成功修改支付密码，请妥善保管。",MessageViewCode.SYSTEM_MESSAGE_1,MessageActionCode.NONE),

	/**
	 *在第三方绑定手机号获得奖励券
	 */
	PRIZES_HAVE_POST(MessageType.SYSTEM_TYPE,MessageLabel.SYSTEM_NOTICE,"投票活动奖励","您已通过活动获得{count1} {count2}张",MessageViewCode.SYSTEM_MESSAGE_2, MessageActionCode.MYCARD) ,
	/**
	 * 付费参加活动
	 */
	ACTIVITY_JOIN_POST(MessageType.ORDER_TYPE,MessageLabel.ORDER_PAY,"参加活动","您成功参与{count}活动，支付{count1}悠然币。",MessageViewCode.ORDER_MESSAGE, MessageActionCode.ACCOUNT) ;


	private Integer type;
	
	private Integer label;
	
	private String title;
	
	private String content;
	
	private String messageViewCode;
	
	private String messageActionCode;
	
	/**
	 * @param type
	 * @param label
	 * @param title
	 * @param content
	 * @param messageViewCode
	 * @param messageActionCode
	 * @exception 
	 */
	private MessageConstant(Integer type, Integer label, String title, String content, String messageViewCode,
                            String messageActionCode) {
		this.type = type;
		this.label = label;
		this.title = title;
		this.content = content;
		this.messageViewCode = messageViewCode;
		this.messageActionCode = messageActionCode;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @return the label
	 */
	public Integer getLabel() {
		return label;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * @return the messageViewCode
	 */
	public String getMessageViewCode() {
		return messageViewCode;
	}

	/**
	 * @return the messageActionCode
	 */
	public String getMessageActionCode() {
		return messageActionCode;
	}
	
}
