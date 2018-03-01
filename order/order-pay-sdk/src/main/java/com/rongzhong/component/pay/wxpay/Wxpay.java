package com.rongzhong.component.pay.wxpay;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rongzhong.component.pay.entity.OrderInfo;
import com.rongzhong.component.pay.entity.PayResponse;
import com.rongzhong.component.pay.entity.Response;
import com.rongzhong.component.pay.util.MD5Util;
import com.rongzhong.component.pay.wxpay.entity.WxQRpay;
import com.rongzhong.component.pay.wxpay.entity.WxWapPay;
import com.rongzhong.component.pay.wxpay.util.CodeGenerator;
import com.rongzhong.component.pay.wxpay.util.PayException;
import com.rongzhong.component.pay.wxpay.util.TrustAnyTrustManager;
import com.rongzhong.component.pay.wxpay.util.XMLUtil;


public class Wxpay {
	protected static Logger logger = LoggerFactory.getLogger(Wxpay.class);

	/**
	 * 创建签名
	 * @param characterEncoding
	 * @param parameters url参数
	 * @return
	 */
	private static String createSign(String characterEncoding, SortedMap<String, String> parameters, String md5Key) {
		logger.info("微信支付创建签名开始：" + System.currentTimeMillis());
		StringBuffer sb = new StringBuffer();
		Set<Map.Entry<String, String>> es = parameters.entrySet();
		Iterator<Map.Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if (null != v
					&& !"".equals(v)
					&& !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + md5Key);
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		logger.info("微信支付创建签名结束：" + System.currentTimeMillis());
		return sign;
	}

	/**
	 * 将请求参数转换为xml格式的string
	 * @param parameters
	 * @return
	 */
	private static String getRequestXml(SortedMap<String, String> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set<Map.Entry<String, String>> es = parameters.entrySet();
		Iterator<Map.Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 发送https请求
	 * @param requestUrl
	 * @param requestMethod
	 * @param outputStr
	 * @return
	 */
	private static String httpsRequest(String requestUrl,
									String requestMethod,
									String outputStr) throws ConnectException, Exception {
		HttpsURLConnection conn = null;
		String result = "";
		try {
			TrustManager[] tm = { new TrustAnyTrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				try {
					// 注意编码格式
					outputStream.write(outputStr.getBytes("UTF-8"));
				} finally {
					if (outputStream != null) {
						outputStream.close();
					}
				}
			}

			InputStream inputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;
			try {
				// 从输入流读取返回内容
				inputStream = conn.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream, "utf-8");
				bufferedReader = new BufferedReader(inputStreamReader);
				String str = null;
				StringBuffer buffer = new StringBuffer();
				while ((str = bufferedReader.readLine()) != null) {
					buffer.append(str);
				}
				result = buffer.toString();
			} finally {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
			}
		} finally {
			// 释放资源
			if (conn != null)
				conn.disconnect();
		}

		return result;
	}

	/**
	 * 获取微信手机支付请求参数列表
	 * @param orderInfo
	 * @param ip
	 * @return
	 * @throws ConnectException
	 * @throws Exception
	 */
	public static SortedMap<String, String> getAppPayReqMap(OrderInfo orderInfo, String ip) throws ConnectException, Exception {
		SortedMap<String, String> parameters = new TreeMap<String, String>();
		parameters.put("appid", WxpayConfig.APP_ID);
		parameters.put("mch_id", WxpayConfig.MCH_ID);
		parameters.put("nonce_str", CodeGenerator.generate(false, 32));
		parameters.put("body", orderInfo.getProductName());
		parameters.put("out_trade_no", orderInfo.getSn());
		parameters.put("total_fee", String.valueOf(orderInfo.getOrderAmount()));
		parameters.put("spbill_create_ip", ip);
		parameters.put("notify_url", WxpayConfig.NOTIFY_URL);
		parameters.put("trade_type", "APP");
		parameters.put("sign", createSign("UTF-8", parameters, WxpayConfig.MD5_KEY));
		String requestXML = getRequestXml(parameters);
		String reqResult = httpsRequest(WxpayConfig.UNIFIED_ORDER_URL, "POST", requestXML);
		Map<String, String> map = XMLUtil.doXMLParse(reqResult);

		SortedMap<String, String> resultMap = new TreeMap<String, String>();
		if (map.get("return_code") != null &&
				"SUCCESS".equals(map.get("return_code")) &&
				map.get("result_code") != null &&
				"SUCCESS".equals(map.get("result_code"))) {
			// 验证签名
			SortedMap<String, String> sortedMap = new TreeMap<String, String>(map);
			String sign = createSign("UTF-8", sortedMap, WxpayConfig.MD5_KEY);
			if (!sign.equals(map.get("sign"))) {
				logger.error("调用微信手机支付统一下单接口，验证签名失败！sn: " + map.get("out_trade_no") + ", prepay_id: " + map.get("prepay_id"));
				throw new PayException("100038");
			}

			// 创建微信app支付请求参数Map
			String prepayId = map.get("prepay_id");
			resultMap.put("appid", WxpayConfig.APP_ID);
			resultMap.put("partnerid", WxpayConfig.MCH_ID);
			resultMap.put("prepayid", prepayId);
			resultMap.put("package", "Sign=WXPay");
			resultMap.put("noncestr", CodeGenerator.generate(false, 32));
			String timestamp = String.valueOf(System.currentTimeMillis());
			resultMap.put("timestamp", timestamp.substring(0, 10));
			sign = createSign("UTF-8", resultMap, WxpayConfig.MD5_KEY);
			resultMap.put("sign", sign);
		}

		return resultMap;
	}

	/**
	 * 获取wap微信手机支付请求参数列表
	 * @param orderInfo
	 * @param ip
	 * @return
	 * @throws ConnectException
	 * @throws Exception
	 */
	public static SortedMap<String, String> getWAPWXPayReqMap(OrderInfo orderInfo, String ip,String openId) throws ConnectException, Exception {
		SortedMap<String, String> parameters = new TreeMap<String, String>();
		parameters.put("appid", WxpayConfig.APP_ID);
		parameters.put("openid", openId);
		parameters.put("mch_id", WxpayConfig.MCH_ID);
		parameters.put("nonce_str", CodeGenerator.generate(false, 32));
		parameters.put("body", orderInfo.getProductName());
		parameters.put("out_trade_no", orderInfo.getSn());
		parameters.put("total_fee", String.valueOf(orderInfo.getOrderAmount()));
		parameters.put("spbill_create_ip", ip);
		parameters.put("notify_url", WxpayConfig.NOTIFY_URL);
		parameters.put("trade_type", "JSAPI");
		parameters.put("sign", createSign("UTF-8", parameters, WxpayConfig.MD5_KEY));
		String requestXML = getRequestXml(parameters);
		String reqResult = httpsRequest(WxpayConfig.UNIFIED_ORDER_URL, "POST", requestXML);
		logger.info("getWAPWXPayReqMap reqResult:"+reqResult);
		Map<String, String> map = XMLUtil.doXMLParse(reqResult);

		SortedMap<String, String> resultMap = new TreeMap<String, String>();
		if (map.get("return_code") != null &&
				"SUCCESS".equals(map.get("return_code")) &&
				map.get("result_code") != null &&
				"SUCCESS".equals(map.get("result_code"))) {
			// 验证签名
			SortedMap<String, String> sortedMap = new TreeMap<String, String>(map);
			String sign = createSign("UTF-8", sortedMap, WxpayConfig.MD5_KEY);
			if (!sign.equals(map.get("sign"))) {
				logger.error("调用微信手机支付统一下单接口，验证签名失败！sn: " + map.get("out_trade_no") + ", prepay_id: " + map.get("prepay_id"));
				throw new PayException("100038");
			}

			// 创建微信app支付请求参数Map
			String prepayId = map.get("prepay_id");
			resultMap.put("appid", WxpayConfig.APP_ID);
			resultMap.put("partnerid", WxpayConfig.MCH_ID);
			resultMap.put("prepayid", prepayId);
			resultMap.put("package", "prepay_id="+prepayId);
			resultMap.put("noncestr", CodeGenerator.generate(false, 32));
			String timestamp = String.valueOf(System.currentTimeMillis());
			resultMap.put("timestamp", timestamp.substring(0, 10));
			sign = createSign("UTF-8", resultMap, WxpayConfig.MD5_KEY);
			resultMap.put("sign", sign);
		}

		return resultMap;
	}

	/**
	 * 获取微信h5支付请求参数列表
	 * @param orderInfo
	 * @param ip
	 * @return
	 * @throws ConnectException
	 * @throws Exception
	 */
	public static SortedMap<String, String> getH5PayReqMap(OrderInfo orderInfo, String ip) throws ConnectException, Exception {
		SortedMap<String, String> parameters = new TreeMap<String, String>();
		parameters.put("appid", WxpayConfig.APP_ID);
		parameters.put("mch_id", WxpayConfig.MCH_ID);
		parameters.put("nonce_str", CodeGenerator.generate(false, 32));
		parameters.put("body", orderInfo.getProductName());
		parameters.put("out_trade_no", orderInfo.getSn());
		parameters.put("total_fee", String.valueOf(orderInfo.getOrderAmount()));
		parameters.put("spbill_create_ip", ip);
		parameters.put("notify_url", WxpayConfig.NOTIFY_URL);
		parameters.put("trade_type", "MWEB");
		parameters.put("sign", createSign("UTF-8", parameters, WxpayConfig.MD5_KEY));
		String requestXML = getRequestXml(parameters);
		String reqResult = httpsRequest(WxpayConfig.UNIFIED_ORDER_URL, "POST", requestXML);
		Map<String, String> map = XMLUtil.doXMLParse(reqResult);

		SortedMap<String, String> resultMap = new TreeMap<String, String>();
		if (map.get("return_code") != null &&
				"SUCCESS".equals(map.get("return_code")) &&
				map.get("result_code") != null &&
				"SUCCESS".equals(map.get("result_code"))) {
			// 验证签名
			SortedMap<String, String> sortedMap = new TreeMap<String, String>(map);
			String sign = createSign("UTF-8", sortedMap, WxpayConfig.MD5_KEY);
			if (!sign.equals(map.get("sign"))) {
				logger.error("调用微信手机支付统一下单接口，验证签名失败！sn: " + map.get("out_trade_no") + ", prepay_id: " + map.get("prepay_id"));
				throw new PayException("100038");
			}

			// 创建微信app支付请求参数Map
			String prepayId = map.get("prepay_id");
			resultMap.put("appid", WxpayConfig.APP_ID);
			resultMap.put("partnerid", WxpayConfig.MCH_ID);
			resultMap.put("prepayid", prepayId);
			resultMap.put("package", "Sign=WXPay");
			resultMap.put("mweburl", map.get("mweb_url"));
			resultMap.put("noncestr", CodeGenerator.generate(false, 32));
			String timestamp = String.valueOf(System.currentTimeMillis());
			resultMap.put("timestamp", timestamp.substring(0, 10));
			sign = createSign("UTF-8", resultMap, WxpayConfig.MD5_KEY);
			resultMap.put("sign", sign);
		}

		return resultMap;
	}

	/**
	 * 获取微信扫码支付相关信息
	 * @param orderInfo
	 * @param ip
	 * @return
	 */
	public static WxQRpay getWxQRpay(OrderInfo orderInfo, String ip) throws ConnectException, Exception {
		SortedMap<String, String> parameters = new TreeMap<String, String>();
		parameters.put("appid", WxpayConfig.APP_ID);
		parameters.put("mch_id", WxpayConfig.MCH_ID);
		parameters.put("nonce_str", CodeGenerator.generate(false, 32));
		parameters.put("body", orderInfo.getProductName());
		parameters.put("out_trade_no", orderInfo.getSn());
		parameters.put("total_fee", String.valueOf(orderInfo.getOrderAmount()));
		parameters.put("spbill_create_ip", ip);
		parameters.put("notify_url", WxpayConfig.NOTIFY_URL);
		parameters.put("trade_type", "NATIVE");
		parameters.put("product_id", orderInfo.getSn());
		parameters.put("sign", createSign("UTF-8", parameters, WxpayConfig.MD5_KEY));
		String requestXML = getRequestXml(parameters);

		logger.info("请求微信统一下单接口开始：" + System.currentTimeMillis());
		String result = httpsRequest(WxpayConfig.UNIFIED_ORDER_URL, "POST", requestXML);
		logger.info("请求微信统一下单接口结束：" + System.currentTimeMillis());

		Map<String, String> map = XMLUtil.doXMLParse(result);
		WxQRpay qrpay = new WxQRpay();
		if (map.get("return_code") != null &&
				"SUCCESS".equals(map.get("return_code")) &&
				map.get("result_code") != null &&
				"SUCCESS".equals(map.get("result_code"))) {
			// 验证签名
			SortedMap<String, String> sortedMap = new TreeMap<String, String>(map);
			String sign = createSign("UTF-8", sortedMap, WxpayConfig.MD5_KEY);
			if (!sign.equals(map.get("sign"))) {
				logger.error("调用微信扫码支付统一下单接口，验证签名失败！sn: " + map.get("out_trade_no") + ", prepay_id: " + map.get("prepay_id"));
				throw new PayException("100038");
			}

			qrpay.setTradeType(map.get("trade_type"));
			qrpay.setPrepayId(map.get("prepay_id"));
			qrpay.setCodeUrl(map.get("code_url"));
		}
		else
			return null;

		return qrpay;
	}

	/**
	 * 获取微信扫码支付相关信息
	 * @param orderInfo
	 * @param ip
	 * @return
	 */
	public static WxWapPay getWxWapPay(OrderInfo orderInfo, String ip) throws ConnectException, Exception {
		SortedMap<String, String> parameters = new TreeMap<String, String>();
		parameters.put("appid", WxpayConfig.APP_ID);
		parameters.put("mch_id", WxpayConfig.MCH_ID);
		parameters.put("nonce_str", CodeGenerator.generate(false, 32));
		parameters.put("body", orderInfo.getProductName());
		parameters.put("out_trade_no", orderInfo.getSn());
		parameters.put("total_fee", String.valueOf(orderInfo.getOrderAmount()));
		parameters.put("spbill_create_ip", ip);
		parameters.put("notify_url", WxpayConfig.NOTIFY_URL);
		parameters.put("trade_type", "MWEB");
		parameters.put("product_id", orderInfo.getSn());
		parameters.put("sign", createSign("UTF-8", parameters, WxpayConfig.MD5_KEY));
		String requestXML = getRequestXml(parameters);

		logger.info("请求微信统一下单接口开始：" + System.currentTimeMillis());
		String result = httpsRequest(WxpayConfig.UNIFIED_ORDER_URL, "POST", requestXML);
		logger.info("请求微信统一下单接口结束：" + System.currentTimeMillis());

		Map<String, String> map = XMLUtil.doXMLParse(result);
		WxWapPay wxWapPay = new WxWapPay();
		if (map.get("return_code") != null &&
				"SUCCESS".equals(map.get("return_code")) &&
				map.get("result_code") != null &&
				"SUCCESS".equals(map.get("result_code"))) {
			// 验证签名
			SortedMap<String, String> sortedMap = new TreeMap<String, String>(map);
			String sign = createSign("UTF-8", sortedMap, WxpayConfig.MD5_KEY);
			if (!sign.equals(map.get("sign"))) {
				logger.error("调用微信扫码支付统一下单接口，验证签名失败！sn: " + map.get("out_trade_no") + ", prepay_id: " + map.get("prepay_id"));
				throw new PayException("100038");
			}

			wxWapPay.setTradeType(map.get("trade_type"));
			wxWapPay.setPrepayId(map.get("prepay_id"));
			wxWapPay.setMwebUrl(map.get("mweb_url"));
			wxWapPay.setPackageId(map.get("package"));
		}
		else
			return null;

		return wxWapPay;
	}

	/**
	 * 构建返回给微信结果的xml
	 * @param return_code
	 * @param return_msg
	 * @return
	 */
	public static String buildReturnXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code +
				"]]></return_code><return_msg><![CDATA[" + return_msg +
				"]]></return_msg></xml>";
	}

	/**
	 * 解析微信支付结果
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static PayResponse parsePayResult(HttpServletRequest request) throws Exception {
		InputStream inStream = null;
		ByteArrayOutputStream outStream = null;

		try {
			inStream = request.getInputStream();
			outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
		} finally {
			if (outStream != null)
				outStream.close();
			if (inStream != null)
				inStream.close();
		}

		String result = new String(outStream.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息
		Map<String, String> map = XMLUtil.doXMLParse(result);
		for (Object keyValue : map.keySet()) {
			System.out.println(keyValue + "=" + map.get(keyValue));
		}

		PayResponse response = new PayResponse();
		response.setSn(map.get("out_trade_no"));
		response.setBankSn(map.get("transaction_id"));
		response.setPayDatetime(map.get("time_end"));
		response.setMchId(map.get("mch_id"));
		response.setPayAmount(map.get("total_fee"));
		response.setEndDesc(map.toString());

		SortedMap<String, String> sortedMap = new TreeMap<String, String>(map);
		String md5Key = WxpayConfig.MD5_KEY;
		if (map.get("mch_id").equals(WxpayConfig.GZH_MCH_ID)) {
			md5Key = WxpayConfig.GZH_MD5_KEY;
		}

		String sign = createSign("UTF-8", sortedMap, md5Key); // 验证签名
		if (!sign.equals(map.get("sign"))) {
			response.setResult(Response.VERIFY_FAILURE);
			response.setMessage("验证签名失败");
			logger.error("微信支付，sn: " + map.get("out_trade_no") + "验证签名失败");
		}
		else if (map.get("result_code").equalsIgnoreCase("SUCCESS")) {
			response.setResult(Response.SUCCESS);
			response.setMessage("success");
		}
		else {
			response.setResult(Response.FAILURE);
			response.setMessage(map.get("err_code"));
		}

		return response;
	}

	/**
	 * 解析微信支付结果
	 * @param paramStr
	 * @return
	 * @throws Exception
	 */
	public static PayResponse parsePayResult(String paramStr) throws Exception {
		Map<String,String> params = XMLUtil.doXMLParse(paramStr);
		for (Object keyValue : params.keySet()) {
			System.out.println(keyValue + "=" + params.get(keyValue));
		}

		PayResponse response = new PayResponse();
		response.setSn(params.get("out_trade_no"));
		response.setBankSn(params.get("transaction_id"));
		response.setPayDatetime(params.get("time_end"));
		response.setMchId(params.get("mch_id"));
		response.setPayAmount(params.get("total_fee"));
		response.setEndDesc(params.toString());

		SortedMap<String, String> sortedMap = new TreeMap<String, String>(params);
		String md5Key = WxpayConfig.MD5_KEY;
		if (params.get("mch_id").equals(WxpayConfig.GZH_MCH_ID)) {
			md5Key = WxpayConfig.GZH_MD5_KEY;
		}

		String sign = createSign("UTF-8", sortedMap, md5Key); // 验证签名
		if (!sign.equals(params.get("sign"))) {
			response.setResult(Response.VERIFY_FAILURE);
			response.setMessage("验证签名失败");
			logger.error("微信支付，sn: " + params.get("out_trade_no") + "验证签名失败");
		}
		else if (params.get("result_code").equalsIgnoreCase("SUCCESS")) {
			response.setResult(Response.SUCCESS);
			response.setMessage("success");
		}
		else {
			response.setResult(Response.FAILURE);
			response.setMessage(params.get("err_code"));
		}
		return response;
	}

	public static void main(String[] args) throws ConnectException, Exception {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setSn("201604151541002x");
		orderInfo.setOrderAmount(100L);
		orderInfo.setProductName("test");
		orderInfo.setProductId("1256894365862062");

		WxQRpay qrPay = getWxQRpay(orderInfo, "127.0.0.1");
	}
}
