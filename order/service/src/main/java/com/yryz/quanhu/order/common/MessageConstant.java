///**
// * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
// * All rights reserved.
// * 
// * Created on 2017年9月11日
// * Id: MessageConstant.java, 2017年9月11日 下午3:02:01 yehao
// */
//package com.yryz.quanhu.order.common;
//
//import com.yryz.service.api.basic.message.MessageActionCode;
//import com.yryz.service.api.basic.message.MessageLabel;
//import com.yryz.service.api.basic.message.MessageType;
//import com.yryz.service.api.basic.message.MessageViewCode;
//
///**
// * @author yehao
// * @version 1.0
// * @date 2017年9月11日 下午3:02:01
// * @Description 消息枚举
// */
//public enum MessageConstant {
//	
//	/**
//	 * 修改支付密码
//	 */
//	EDIT_PAY_PASSWORD(MessageType.SYSTEM_TYPE,MessageLabel.SYSTEM_ACCOUNT,"密码安全提醒","您已成功修改支付密码，请妥善保管。",MessageViewCode.SYSTEM_MESSAGE_1,MessageActionCode.NONE),
//	/**
//	 * 充值
//	 */
//	RECHARGE(MessageType.ORDER_TYPE,MessageLabel.ORDER_RECHARGE,"充值成功","您充值成功，到账消费账户。",MessageViewCode.ORDER_MESSAGE,MessageActionCode.ACCOUNT),
//	/**
//	 * 提现
//	 */
//	CASH(MessageType.ORDER_TYPE,MessageLabel.ORDER_CASH ,"提现申请成功","提现申请成功，申请提现金额{count}元。",MessageViewCode.ORDER_MESSAGE,MessageActionCode.INTEGRAL),
//	/**
//	 * 提现退款
//	 */
//	CASH_REFUND(MessageType.ORDER_TYPE , MessageLabel.ORDER_RETURN , "提现退款通知","提现失败，您的提现金额被退回，退回{count}元。",MessageViewCode.ORDER_MESSAGE,MessageActionCode.INTEGRAL);
//	
//	
//	private Integer type;
//	
//	private Integer label;
//	
//	private String title;
//	
//	private String content;
//	
//	private String messageViewCode;
//	
//	private String messageActionCode;
//	
//	/**
//	 * @param type
//	 * @param label
//	 * @param title
//	 * @param content
//	 * @param messageViewCode
//	 * @param messageActionCode
//	 * @exception 
//	 */
//	private MessageConstant(Integer type, Integer label, String title, String content, String messageViewCode,
//			String messageActionCode) {
//		this.type = type;
//		this.label = label;
//		this.title = title;
//		this.content = content;
//		this.messageViewCode = messageViewCode;
//		this.messageActionCode = messageActionCode;
//	}
//
//	/**
//	 * @return the type
//	 */
//	public Integer getType() {
//		return type;
//	}
//
//	/**
//	 * @return the label
//	 */
//	public Integer getLabel() {
//		return label;
//	}
//
//	/**
//	 * @return the title
//	 */
//	public String getTitle() {
//		return title;
//	}
//
//	/**
//	 * @return the content
//	 */
//	public String getContent() {
//		return content;
//	}
//	
//	/**
//	 * @return the messageViewCode
//	 */
//	public String getMessageViewCode() {
//		return messageViewCode;
//	}
//
//	/**
//	 * @return the messageActionCode
//	 */
//	public String getMessageActionCode() {
//		return messageActionCode;
//	}
//	
//}
