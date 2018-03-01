package com.rongzhong.component.pay.alipay;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rongzhong.component.pay.alipay.util.AlipayCore;
import com.rongzhong.component.pay.alipay.util.AlipayNotify;
import com.rongzhong.component.pay.alipay.util.AlipaySubmit;
import com.rongzhong.component.pay.alipay.util.SignType;
import com.rongzhong.component.pay.entity.OrderInfo;
import com.rongzhong.component.pay.entity.PayResponse;
import com.rongzhong.component.pay.entity.Response;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年6月16日 下午3:37:36
 * @Description 阿里支付工具类
 */
public class Alipay {
	
	private static Logger logger = LoggerFactory.getLogger(Alipay.class);
	
	public static String buildAppAliPayOrder(OrderInfo orderInfo) throws IOException {
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("app_id", AlipayConfig.app_id);
		sParaTemp.put("method", "alipay.trade.app.pay");
		sParaTemp.put("charset", AlipayConfig.input_charset);
		sParaTemp.put("sign_type", AlipayConfig.sign_type);
		sParaTemp.put("timestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		sParaTemp.put("version", "1.0");
		sParaTemp.put("notify_url", AlipayConfig.notify_url);
		String bizContent = "{" +
				"\"body\":\"支付宝充值\","+
		        "\"out_trade_no\":\"" + orderInfo.getSn() + "\"," +
		        "\"product_code\":\"QUICK_MSECURITY_PAY\"," +
		        "\"subject\":\"支付宝充值\"," +
		        "\"total_amount\":" + String.valueOf(orderInfo.getOrderAmount() /100.0) +
		        "}";
		sParaTemp.put("biz_content", bizContent);

		Map<String, String> map = AlipaySubmit.buildRequestPara(sParaTemp, SignType.RSA2);
		String linkStr =  AlipayCore.createLinkString2(map);
		return linkStr;
	}

	/**
	 * 建立请求，以表单HTML形式构造（默认）
	 * 
	 * @param orderInfo
	 *            订单信息
	 * @return
	 * @throws IOException
	 */
	public static String buildPayH5Request(OrderInfo orderInfo,String returnUrl) throws IOException {
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("app_id", AlipayConfig.app_id);
		sParaTemp.put("method", "alipay.trade.wap.pay");
		if(StringUtils.isNotEmpty(returnUrl)){
			sParaTemp.put("return_url", returnUrl);
		}
		sParaTemp.put("charset", AlipayConfig.input_charset);
		sParaTemp.put("sign_type", AlipayConfig.sign_type);
		sParaTemp.put("timestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		sParaTemp.put("version", "1.0");
		sParaTemp.put("notify_url", AlipayConfig.notify_url);
		String bizContent = "{" +
				"\"body\":\"支付宝支付\","+
		        "\"out_trade_no\":\"" + orderInfo.getSn() + "\"," +
		        "\"product_code\":\"QUICK_WAP_WAY\"," +
		        "\"subject\":\"支付宝支付\"," +
		        "\"total_amount\":" + String.valueOf(orderInfo.getOrderAmount() /100.0) +
		        "}";
		sParaTemp.put("biz_content", bizContent);
		return AlipaySubmit.buildRequest(sParaTemp, "POST", "确认", AlipayConfig.sign_type);
	}
	
	/**
	 * 建立请求，以表单HTML形式构造（默认）
	 * 
	 * @param orderInfo
	 *            订单信息
	 * @param returnUrl html回调地址           
	 * @return
	 * @throws IOException
	 */
	public static String buildPayPageRequest(OrderInfo orderInfo,String returnUrl) throws IOException {
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("app_id", AlipayConfig.app_id);
		sParaTemp.put("method", "alipay.trade.page.pay");
		if(StringUtils.isNotEmpty(returnUrl)){
			sParaTemp.put("return_url", returnUrl);
		}
		sParaTemp.put("charset", AlipayConfig.input_charset);
		sParaTemp.put("sign_type", AlipayConfig.sign_type);
		sParaTemp.put("timestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		sParaTemp.put("version", "1.0");
		sParaTemp.put("notify_url", AlipayConfig.notify_url);
		String bizContent = "{" +
				"\"body\":\"支付宝充值\","+
		        "\"out_trade_no\":\"" + orderInfo.getSn() + "\"," +
		        "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
		        "\"subject\":\"支付宝充值\"," +
		        "\"total_amount\":" + String.valueOf(orderInfo.getOrderAmount() /100.0) +
		        "}";
		sParaTemp.put("biz_content", bizContent);
		
		return AlipaySubmit.buildRequest(sParaTemp, "POST", "确认", AlipayConfig.sign_type);
	}
	
	/**
	 * 解析支付宝支付结果
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static PayResponse parsePayResult(HttpServletRequest request) throws IOException {
		// 获取支付宝POST过来的反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			params.put(name, valueStr);
			
//			System.out.print(name + ":" + valueStr + " | ");
		}

		// 商户订单号
		String out_trade_no = request.getParameter("out_trade_no");

		// 支付宝交易号
		String trade_no = request.getParameter("trade_no");

		// 交易状态
		String trade_status = request.getParameter("trade_status");

		// 交易付款时间
		String paymentTime = request.getParameter("gmt_payment");

		// 商户号
		String sellerId = request.getParameter("seller_id");

		// 交易金额(单位：元)
		String totalFee = request.getParameter("total_amount");
		
		//APPId
		String appId = request.getParameter("auth_app_id");

		PayResponse response = new PayResponse();
		response.setSn(out_trade_no);
		response.setBankSn(trade_no);
		response.setPayDatetime(paymentTime);
		response.setMchId(sellerId);
		response.setEndDesc(params.toString());
		Double payAmount = Double.parseDouble(totalFee) * 100;// 转换为分
		response.setPayAmount(String.valueOf(payAmount.intValue())); // 去掉小数点
		
		if(!StringUtils.equals(appId, AlipayConfig.app_id) || !StringUtils.equals(sellerId, AlipayConfig.partner)){
			response.setResult(Response.VERIFY_FAILURE);
			response.setMessage("验证 APPID|PARTNER 失败");
			logger.error("支付宝支付，sn: " + out_trade_no + "验证 APPID|PARTNER 失败");
		} else 
		if (AlipayNotify.verify(params, AlipayConfig.sign_type)) {// 验证成功
			if (trade_status.equals("TRADE_FINISHED")) {
				response.setResult(Response.SUCCESS);
				response.setMessage("success");
			} else if (trade_status.equals("TRADE_SUCCESS")) {
				response.setResult(Response.SUCCESS);
				response.setMessage("success");
			}
		} else {// 验证失败
			response.setResult(Response.VERIFY_FAILURE);
			response.setMessage("验证签名失败");
			logger.error("支付宝支付，sn: " + out_trade_no + "验证签名失败");
		}

		return response;
	}

	public static PayResponse parsePayResult(Map<String, String> params) throws IOException {

		// 商户订单号
		String out_trade_no = params.get("out_trade_no");

		// 支付宝交易号
		String trade_no = params.get("trade_no");

		// 交易状态
		String trade_status = params.get("trade_status");

		// 交易付款时间
		String paymentTime = params.get("gmt_payment");

		// 商户号
		String sellerId = params.get("seller_id");

		// 交易金额(单位：元)
		String totalFee = params.get("total_amount");

		//APPId
		String appId = params.get("auth_app_id");

		PayResponse response = new PayResponse();
		response.setSn(out_trade_no);
		response.setBankSn(trade_no);
		response.setPayDatetime(paymentTime);
		response.setMchId(sellerId);
		response.setEndDesc(params.toString());
		Double payAmount = Double.parseDouble(totalFee) * 100;// 转换为分
		response.setPayAmount(String.valueOf(payAmount.intValue())); // 去掉小数点

		if(!StringUtils.equals(appId, AlipayConfig.app_id) || !StringUtils.equals(sellerId, AlipayConfig.partner)){
			response.setResult(Response.VERIFY_FAILURE);
			response.setMessage("验证 APPID|PARTNER 失败");
			logger.error("支付宝支付，sn: " + out_trade_no + "验证 APPID|PARTNER 失败");
		} else
		if (AlipayNotify.verify(params, AlipayConfig.sign_type)) {// 验证成功
			if (trade_status.equals("TRADE_FINISHED")) {
				response.setResult(Response.SUCCESS);
				response.setMessage("success");
			} else if (trade_status.equals("TRADE_SUCCESS")) {
				response.setResult(Response.SUCCESS);
				response.setMessage("success");
			}
		} else {// 验证失败
			response.setResult(Response.VERIFY_FAILURE);
			response.setMessage("验证签名失败");
			logger.error("支付宝支付，sn: " + out_trade_no + "验证签名失败");
		}

		return response;
	}
}
