//package com.yryz.quanhu.order.common;
//
//import java.math.RoundingMode;
//import java.text.DecimalFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.dubbo.rpc.RpcContext;
//import com.rongzhong.component.common.utils.DateUtils;
//import com.rongzhong.component.common.utils.JsonUtils;
//import com.yryz.service.api.api.exception.DatasOptException;
//import com.yryz.service.api.basic.api.PushAPI;
//import com.yryz.service.order.commom.push.PushConstant.ColumnType;
//import com.yryz.service.order.commom.push.PushConstant.MessageType;
//import com.yryz.service.order.modules.order.entity.RrzOrderUserAccount;
//import com.yryz.service.order.modules.order.service.UserAccountService;
//
///**
// * 推送管理
// * 
// * @author danshiyu
// *
// */
//@Service
//public class PushManager {
//	private static Logger logger = LoggerFactory.getLogger(PushManager.class);
//
//	@Autowired
//	private PushAPI pushAPI;
//
//	@Autowired
//	private UserAccountService userAccountService;
//
//	/**
//	 * 充值消息推送
//	 * 
//	 * @param custId
//	 * @param chargeAmount
//	 * @param balance
//	 */
//	public void charge(String custId, long chargeAmount) {
//		int messageType = MessageType.CHARGE.getValue();
//		int colType = ColumnType.ACCOUNT.getType();
//		
//		RrzOrderUserAccount account = getAccount(custId);
//		if (account == null){
//			return;
//		}
//		String msgTitle = MessageType.CHARGE.getTitle();
//		StringBuffer content = new StringBuffer();
//		content.append("您的消费账户充值");
//		content.append(getTwoPointDouble((float) chargeAmount / 100));
//		content.append("悠然币,账户余额");
//		content.append(getTwoPointDouble((float) account.getAccountSum() / 100));
//		content.append("悠然币。");
//		PushBody bodyVo = PushUtils.getPushbody(null, custId, messageType, msgTitle, colType);
//		Map<String, String> map = new HashMap<>(2);
//		map.put("content", content.toString());
//		map.put("sendTime", DateUtils.formatDateTime(new Date()));
//		bodyVo.setData(map);
//		try {
//			String appId = RpcContext.getContext().getAttachment("appId");
//			RpcContext.getContext().setAttachment("appId", appId);
//			pushAPI.sendByAlias(custId, null, JsonUtils.toFastJson(bodyVo));
//			if (logger.isDebugEnabled()) {
//				logger.debug("充值消息推送 custId:" + custId);
//				logger.debug("充值消息推送 type:" + messageType);
//				logger.debug("充值消息推送 message :" + JsonUtils.toFastJson(bodyVo));
//			}
//		} catch (Exception e) {
//
//		}
//	}
//
//	/**
//	 * 提现消息推送
//	 * 
//	 * @param custId
//	 * @param cashAmount
//	 * @param balance
//	 */
//	public void cash(String custId, long cashAmount) {
//		int messageType = MessageType.CASH.getValue();
//		int colType = ColumnType.ACCOUNT.getType();
//		try {
//			RrzOrderUserAccount account = getAccount(custId);
//			if (account == null){
//				return;
//			}
//			String msgTitle = MessageType.CASH.getTitle();
//			StringBuffer content = new StringBuffer();
//			content.append("您从悠然账户提现");
//			content.append(getTwoPointDouble((float) cashAmount / 100));
//			content.append("悠然币,账户余额");
//			content.append(getTwoPointDouble((float) account.getIntegralSum() / 100));
//			content.append("悠然币。");
//			PushBody bodyVo = PushUtils.getPushbody(null, custId, messageType, msgTitle, colType);
//			Map<String, String> map = new HashMap<>(2);
//			map.put("content", content.toString());
//			map.put("sendTime", DateUtils.formatDateTime(new Date()));
//			bodyVo.setData(map);
//			String appId = RpcContext.getContext().getAttachment("appId");
//			RpcContext.getContext().setAttachment("appId", appId);
//			pushAPI.sendByAlias(custId, null, JsonUtils.toFastJson(bodyVo));
//			if (logger.isDebugEnabled()) {
//				logger.debug("提现消息推送 custId:" + custId);
//				logger.debug("提现消息推送 type:" + messageType);
//				logger.debug("提现消息推送 message :" + JsonUtils.toFastJson(bodyVo));
//			}
//		} catch (Exception e) {
//
//		}
//	}
//
//	/**
//	 * 积分转消费消息推送
//	 * 
//	 * @param custId
//	 * @param cashAmount
//	 * @param balance
//	 */
//	public void pointToAccount(String custId, long cashAmount, long balance) {
//		int messageType = MessageType.POINTS_ACCOUNT.getValue();
//		int colType = ColumnType.ACCOUNT.getType();
//		try {
//			String msgTitle = MessageType.POINTS_ACCOUNT.getTitle();
//			StringBuffer content = new StringBuffer();
//			content.append("您已兑换");
//			content.append(getTwoPointDouble((double) cashAmount / 100));
//			content.append("悠然币到消费账户,悠然账户余额");
//			content.append(getTwoPointDouble((double) balance / 100));
//			content.append("悠然币。");
//			PushBody bodyVo = PushUtils.getPushbody(null, custId, messageType, msgTitle, colType);
//			Map<String, String> map = new HashMap<>(2);
//			map.put("content", content.toString());
//			map.put("sendTime", DateUtils.formatDateTime(new Date()));
//			bodyVo.setData(map);
//			String appId = RpcContext.getContext().getAttachment("appId");
//			RpcContext.getContext().setAttachment("appId", appId);
//			pushAPI.sendByAlias(custId, null, JsonUtils.toFastJson(bodyVo));
//			System.out.println(JsonUtils.toFastJson(bodyVo));
//			if (logger.isDebugEnabled()) {
//				logger.debug("积分转消费消息推送 custId:" + custId);
//				logger.debug("积分转消费消息推送 type:" + messageType);
//				logger.debug("积分转消费消息推送 message :" + JsonUtils.toFastJson(bodyVo));
//			}
//		} catch (Exception e) {
//
//		}
//	}
//
//	/**
//	 * 支付密码修改消息推送
//	 * 
//	 * @param custId
//	 */
//	public void updatePwd(String custId) {
//		int colType = ColumnType.ACCOUNT.getType();
//		int messageType = MessageType.PAY_PWD_UPDATE.getValue();
//		String title = MessageType.PAY_PWD_UPDATE.getTitle();
//		try {
//			PushBody bodyVo = PushUtils.getPushbody(null, custId, messageType, title, colType);
//
//			Map<String, String> content = new HashMap<String, String>(2);
//			content.put("content", "您已成功修改支付密码，请妥善保管。");
//			content.put("sendTime", DateUtils.formatDateTime(new Date()));
//
//			bodyVo.setData(content);
//			String appId = RpcContext.getContext().getAttachment("appId");
//			RpcContext.getContext().setAttachment("appId", appId);
//			pushAPI.sendByAlias(custId, null, JsonUtils.toFastJson(bodyVo));
//
//			if (logger.isDebugEnabled()) {
//				logger.info("密码修改 type:" + messageType);
//				logger.info("密码修改 message :" + JsonUtils.toFastJson(bodyVo));
//			}
//		} catch (Exception e) {
//			// ignore error
//		}
//	}
//
//	/**
//	 * 获取账户信息
//	 * 
//	 * @param custId
//	 * @return
//	 */
//	private RrzOrderUserAccount getAccount(String custId) {
//		RrzOrderUserAccount account = new RrzOrderUserAccount();
//		account.setCustId(custId);
//		try {
//			account = userAccountService.getUserAccount(account);
//			return account;
//		} catch (DatasOptException e) {
//			return null;
//		} catch (Exception e) {
//			return null;
//		}
//
//	}
//
//	/**
//	 * 得到保留2位有效数字的浮点数字符串
//	 * 
//	 * @param money
//	 * @return
//	 */
//	public static String getTwoPointDouble(double money) {
//		DecimalFormat formater = new DecimalFormat();
//		formater.setMaximumFractionDigits(2);
//		formater.setGroupingSize(0);
//		formater.setRoundingMode(RoundingMode.FLOOR);
//		return formater.format(money);
//	}
//
//}
