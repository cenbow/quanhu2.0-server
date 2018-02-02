/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
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
    EDIT_PAY_PASSWORD(MessageType.SYSTEM_TYPE, MessageLabel.SYSTEM_ACCOUNT, "密码安全提醒", "您已成功修改支付密码，请妥善保管。", MessageViewCode.SYSTEM_MESSAGE_1, MessageActionCode.NONE),

    /**
     * 充值
     */
    RECHARGE(MessageType.ORDER_TYPE, MessageLabel.ORDER_RECHARGE, "充值成功", "您充值成功，到账消费账户。", MessageViewCode.ORDER_MESSAGE, MessageActionCode.ACCOUNT),

    /**
     * 提现
     */
    CASH(MessageType.ORDER_TYPE, MessageLabel.ORDER_CASH, "提现成功", "提现成功，提现金额{count}元/点。", MessageViewCode.ORDER_MESSAGE, MessageActionCode.INTEGRAL),

    /**
     * 提现退款
     */
    CASH_REFUND(MessageType.ORDER_TYPE, MessageLabel.ORDER_RETURN, "提现退款通知", "由于提现不成功，您的提现金额被退回，退回{count}元。", MessageViewCode.ORDER_MESSAGE, MessageActionCode.INTEGRAL),

    /**
     * 问题待回答
     */
    QUESTION_TO_BE_ANSWERED(MessageType.INTERACTIVE_TYPE, MessageLabel.INTERACTIVE_ANSWER, "问题待回答通知", "{someone}向您提了一个问题。", MessageViewCode.INTERACTIVE_MESSAGE, MessageActionCode.COMMON_DETAIL),

    /**
     *
     */
    QUESTION_TO_BE_REJECT(MessageType.INTERACTIVE_TYPE, MessageLabel.INTERACTIVE_ANSWER, "圈主拒绝回答问题", "{someone}拒绝了您的提问。", MessageViewCode.INTERACTIVE_MESSAGE, MessageActionCode.COMMON_DETAIL),

    /**
     * 问题待回答
     */
    QUESTION_HAVE_ANSWERED(MessageType.INTERACTIVE_TYPE, MessageLabel.INTERACTIVE_ANSWER, "问题回答通知", "{someone}回答了您的提问。", MessageViewCode.INTERACTIVE_MESSAGE, MessageActionCode.COMMON_DETAIL),

    /**
     * 文章被评论
     * INTERACTIVE_TYPE=4 互动消息   INTERACTIVE_COMMENT4002   INTERACTIVE_MESSAGE3000  COMMON_DETAIL8004
     */
    ARTICLE_BE_COMMENTED(MessageType.INTERACTIVE_TYPE, MessageLabel.INTERACTIVE_COMMENT, "评论通知", "{count}评论了您发布的内容。", MessageViewCode.INTERACTIVE_MESSAGE, MessageActionCode.COMMON_DETAIL),

    /**
     * 帖子被评论
     * INTERACTIVE_TYPE=4 互动消息   INTERACTIVE_COMMENT4002   INTERACTIVE_MESSAGE3000  COMMON_DETAIL8004
     */
    POST_BE_COMMENTED(MessageType.INTERACTIVE_TYPE, MessageLabel.INTERACTIVE_COMMENT, "评论通知", "{count}评论了您发布的帖子。", MessageViewCode.INTERACTIVE_MESSAGE, MessageActionCode.COMMON_DETAIL),

    /**
     * 评论被回复
     * INTERACTIVE_TYPE=4 互动消息   INTERACTIVE_COMMENT4002   INTERACTIVE_MESSAGE3000  COMMON_DETAIL8004
     */
    COMMENTED_BE_ANSWERED(MessageType.INTERACTIVE_TYPE, MessageLabel.INTERACTIVE_COMMENT, "回复通知", "{count}回复了您的评论。", MessageViewCode.INTERACTIVE_MESSAGE, MessageActionCode.COMMON_DETAIL),

    /**
     * 评论下架
     */
    COMMENT_SHELVE(MessageType.SYSTEM_TYPE, MessageLabel.SYSTEM_REVIEW, "审核通知", "您在{count}下发布的评论因违反平台相关规定已被管理员下线。", MessageViewCode.SYSTEM_MESSAGE_2, MessageActionCode.NONE),

    /**
     * 帖子被圈主删除
     */
    POST_HAVE_DELETE(MessageType.INTERACTIVE_TYPE, MessageLabel.INTERACTIVE_COTERIE, "删除帖子通知", "{someone}删除了你的帖子。", MessageViewCode.INTERACTIVE_MESSAGE, MessageActionCode.COTERIE_HOME),

    /**
     * 话题有新帖子
     */
    TOPIC_HAVE_POST(MessageType.INTERACTIVE_TYPE, MessageLabel.INTERACTIVE_COMMENT, "回贴通知", "{someone}在您的话题下发布了新帖。", MessageViewCode.INTERACTIVE_MESSAGE, MessageActionCode.TOPIC_HOME),

    /**
     * 活动有新人报名
     */
    ACTIVITY_HAVE_SIGNUP(MessageType.INTERACTIVE_TYPE, MessageLabel.INTERACTIVE_COTERIE, "报名通知", "{count}已报名参加您发布的活动。", MessageViewCode.INTERACTIVE_MESSAGE, MessageActionCode.COMMON_DETAIL),

    /**
     * 私圈内容收费   奖励通知    XXX阅读了您的付费内容，您获得N奖励。（XXX=用户昵称）
     */
    CONTENT_READ_REMINDERS(MessageType.ORDER_TYPE, MessageLabel.ORDER_ACCOUNT, "奖励通知", "'{count}'阅读了您的付费内容，您获得{money}奖励。", MessageViewCode.ORDER_PIC_MESSAGE, MessageActionCode.INTEGRAL),

    /**
     * 私圈内付费阅读支付成功  支付成功    您在XXX私圈付费阅读内容，支付100悠然币。
     */
    CONTENT_BUY_REMINDERS(MessageType.ORDER_TYPE, MessageLabel.ORDER_PAY, "支付成功", "您在'{count}'私圈付费阅读内容，支付{money}悠然币。", MessageViewCode.ORDER_PIC_MESSAGE, MessageActionCode.ACCOUNT),

    /**
     * 问答奖励  {XXX}付费向你提问，您收到奖励{money}。
     */
    ANSWER_PAYED(MessageType.ORDER_TYPE, MessageLabel.ORDER_ACCOUNT, "问答奖励", "您回答{someone}的提问，获得{money}奖励。", MessageViewCode.ORDER_PIC_MESSAGE, MessageActionCode.INTEGRAL),

    /**
     * 提问支付  {XXX}付费向你提问，您收到奖励{money}。
     */
    QUESTION_PAYED(MessageType.ORDER_TYPE, MessageLabel.ORDER_PAY, "付费提问", "您在{coterieName}私圈付费向圈主提问，支付{money}悠然币。", MessageViewCode.ORDER_MESSAGE, MessageActionCode.ACCOUNT),

    /**
     * 提问圈主未回答,回答超时
     */
    QUESTION_INVALID(MessageType.ORDER_TYPE, MessageLabel.ORDER_RETURN, "提问超时", "您向{coterieName}圈主提问，圈主回复超时，所付金额退回{money}悠然币。", MessageViewCode.ORDER_MESSAGE, MessageActionCode.ACCOUNT),

    /**
     * 删除未回答的付费提问
     */
    QUESTION_DELETE(MessageType.ORDER_TYPE, MessageLabel.ORDER_RETURN, "删除提问", "您删除了向{coterieName}圈主提问，所付金额退回{money}悠然币。", MessageViewCode.ORDER_MESSAGE, MessageActionCode.ACCOUNT),

    /**
     * 付费加入私圈  您付费加入XXX私圈，支付99悠然币。
     */
    JOIN_COTERIE_PAYED(MessageType.ORDER_TYPE, MessageLabel.ORDER_PAY, "付费加入私圈", "您付费加入{name}私圈，支付{money}悠然币。", MessageViewCode.ORDER_MESSAGE, MessageActionCode.ACCOUNT),

    /**
     * 私圈奖励 XXX加入您的私圈，您收到奖励99.00。
     */
    JOIN_COTERIE_REWARD(MessageType.ORDER_TYPE, MessageLabel.ORDER_ACCOUNT, "私圈奖励", "{name}加入您的私圈，您获得{money}奖励。", MessageViewCode.ORDER_PIC_MESSAGE, MessageActionCode.INTEGRAL),

    /**
     *在第三方绑定手机号获得奖励券
     */
    PRIZES_HAVE_POST(MessageType.SYSTEM_TYPE,MessageLabel.SYSTEM_NOTICE,"投票活动奖励","您已通过活动获得{count1} {count2}张",MessageViewCode.SYSTEM_MESSAGE_2, MessageActionCode.MYCARD),

    /**
     * 转发
     * */
    TRANSMIT_CONTENT_POST(MessageType.INTERACTIVE_TYPE, MessageLabel.INTERACTIVE_TRANSMIT, "转发提示", "", MessageViewCode.INTERACTIVE_MESSAGE, MessageActionCode.COMMON_DETAIL),

    /**
     * 付费参加活动
     */
    ACTIVITY_JOIN_POST(MessageType.ORDER_TYPE,MessageLabel.ORDER_PAY,"参加活动","您成功参与{count}活动，支付{count1}悠然币。",MessageViewCode.ORDER_MESSAGE, MessageActionCode.ACCOUNT),

    /**
     * 打赏消费
     */
    REWARD_ACCOUNT(MessageType.ORDER_TYPE,MessageLabel.ORDER_PAY,"支付成功","您打赏了一个%s，支付%s悠然币。",MessageViewCode.ORDER_PIC_MESSAGE, MessageActionCode.ACCOUNT),

    /**
     * 被打赏收益
     */
    REWARD_INTEGRAL(MessageType.ORDER_TYPE,MessageLabel.ORDER_ACCOUNT,"奖励通知","%s打赏了一个%s，您获得%s奖励。",MessageViewCode.ORDER_PIC_MESSAGE, MessageActionCode.INTEGRAL),

    /**
     * 内容下架
     */
    RELEASE_SHELVE(MessageType.SYSTEM_TYPE, MessageLabel.SYSTEM_REVIEW, "审核通知", "您发布的'{count}'因违反平台相关规定已被管理员下线。", MessageViewCode.SYSTEM_MESSAGE_1, MessageActionCode.NONE),
    ;

    private Integer type;

    private Integer label;

    private String title;

    private String content;

    private String messageViewCode;

    private String messageActionCode;

    /**
     * @param type                     一级分类
     * @param label                    二级分类
     * @param title                    消息标题
     * @param content                  消息内容
     * @param messageViewCode          消息视图码
     * @param messageActionCode        消息跳转码
     *                                 详情参见：http://confluence.yryz.com/pages/viewpage.action?pageId=14975116
     */
    private MessageConstant(Integer type, Integer label, String title, String content, String messageViewCode, String messageActionCode) {
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
