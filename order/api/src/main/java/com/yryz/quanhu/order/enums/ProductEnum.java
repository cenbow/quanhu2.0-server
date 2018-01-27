package com.yryz.quanhu.order.enums;

/**
 * 平台支付类型枚举
 * 
 * @author yehao
 *
 */
public enum ProductEnum {
	/**
	 * 打赏
	 */
	REWARD_TYPE(1000, "打赏"),
	/**
	 * 红包类型
	 */
	REDPACKAGE_TYPE(1001, "红包支付"),
	/**
	 * 红包退回
	 */
	REDPACKAGE_RETURN(1021, "红包退回"),
	/**
	 * 红包领取
	 */
	REDPACKAGE_RECEIVE(1031, "红包领取"),
	/**
	 * 积分转消费账户
	 */
	INTEGRAL_2_ACCOUNT(1002, "兑换"),
	/**
	 * 用户抽奖类型
	 */
	LOTTERY_TYPE(1003, "中奖"),
	/**
	 * 打赏
	 */
	PROFIT_TYPE(1004, "打赏"),
	/**
	 * 广告类型
	 */
	ADVERT_TYPE(1005, "广告"),
	/**
	 * 退款
	 */
	REFUND_TYPE(1006, "退款"),
	/**
	 * 退款
	 */
	CASH_REFUND(1007, "提现退款"),
	/**
	 * 被打赏
	 */
	REWARDED_TYPE(1008,"被打赏"),
	/**
	 * 用户充值
	 */
	RECHARGE_TYPE(2000, "充值"),
	/**
	 * 用户提现
	 */
	CASH_TYPE(2001, "提现"),
	/**
	 * 充值手续费
	 */
	FEE_TYPE(2002, "充值手续费"),
	/**
	 * 提现手续费
	 */
	FEE_CASH_TYPE(2003, "提现手续费"),
	/**
	 * 运营账户赠送
	 */
	ACTIVITY_REGISTER(3001, "赠送"),
	
	/** 付费加入私圈*/
	COTERIE_JOIN(4001,"加入私圈付费"),
	
	/** 私圈资源付费 */
	RESOURCE_PAY(4002,"私圈资源购买"),
	
	/** 向私圈群主提问付费 */
	COTERIE_ASK_PAY(4003,"付费向私圈主提问"),
	
	/** 向私圈主提问退款 */
	COTERIE_ASK_RETURN(4004,"付费向私圈主提问退款"),
	
	/** 私圈主回答问题 */
	COTERIE_ANSWER(4005,"私圈主回答问题"),

	/** 参与报名类活动 */
	ACTIVITY_SIGNUP(4006, "参与报名类活动");

	private int type;

	private String desc;

	ProductEnum(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
