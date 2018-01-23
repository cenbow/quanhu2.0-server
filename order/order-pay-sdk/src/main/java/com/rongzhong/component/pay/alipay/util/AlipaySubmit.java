package com.rongzhong.component.pay.alipay.util;



import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.rongzhong.component.pay.alipay.AlipayApiException;
import com.rongzhong.component.pay.alipay.AlipayConfig;
import com.rongzhong.component.pay.alipay.sign.AlipaySignature;
import com.rongzhong.component.pay.alipay.sign.MD5;
import com.rongzhong.component.pay.alipay.sign.RSA;


/* *
 *类名：AlipaySubmit
 *功能：支付宝各接口请求提交类
 *详细：构造支付宝各接口表单HTML文本，获取远程HTTP数据
 *版本：3.3
 *日期：2012-08-13
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipaySubmit {

	/**
	 * 支付宝提供给商户的服务接入网关URL(新)
	 */
	private static final String ALIPAY_GATEWAY_NEW = AlipayConfig.gateway_url;
	
	/**
	 * 取我们私钥
	 * 
	 * @return
	 * @throws IOException
	 */
	private static String getPrivateKey() throws IOException {
		File file = new File(AlipayConfig.rsa_private_key_pkcs8_path);
		return FileUtils.readFileToString(file, "UTF-8");
	}
	
	/**
	 * 生成签名
	 * @param linkStr 用= &拼装号的参数
	 * @param signType
	 * @return
	 * @throws IOException
	 */
	public static String buildRequestSign(String linkStr, String signType) throws IOException {
		String sign = "";
		if (signType.equals(SignType.MD5)) {
			sign = MD5.sign(linkStr, AlipayConfig.md5_key, AlipayConfig.input_charset);
		}
		else if (signType.equals(SignType.RSA)) {
			String privateKey = getPrivateKey();
			sign = RSA.sign(linkStr, privateKey, AlipayConfig.input_charset);
		} else if(signType.endsWith(SignType.RSA2)){
			String privateKey = getPrivateKey();
			try {
				sign = AlipaySignature.rsa256Sign(linkStr, privateKey, AlipayConfig.input_charset);
			} catch (AlipayApiException e) {
				e.printStackTrace();
			}
		}

		return sign;
	}

	/**
	 * 生成签名结果
	 * @param sPara
	 *            要签名的数组
	 * @return 签名结果字符串
	 * @throws IOException 
	 */
	public static String buildRequestMysign(Map<String, String> sPara, String signType) throws IOException {
		String prestr = AlipayCore.createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String mysign = "";
		if (signType.equals(SignType.MD5)) {
			mysign = MD5.sign(prestr, AlipayConfig.md5_key, AlipayConfig.input_charset);
		}
		else if (signType.equals(SignType.RSA)) {
			String privateKey = getPrivateKey();
			mysign = RSA.sign(prestr, privateKey, AlipayConfig.input_charset);
		} else if(signType.endsWith(SignType.RSA2)){
			String privateKey = getPrivateKey();
			try {
				System.out.println(prestr);
				mysign = AlipaySignature.rsa256Sign(prestr, privateKey, AlipayConfig.input_charset);
				System.out.println(mysign);
			} catch (AlipayApiException e) {
				e.printStackTrace();
			}
		}

		return mysign;
	}

	/**
	 * 生成要请求给支付宝的参数数组
	 * 
	 * @param sParaTemp
	 *            请求前的参数数组
	 * @return 要请求的参数数组
	 * @throws IOException 
	 */
	public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String signType) throws IOException {
		// 除去数组中的空值和签名参数
		Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
		sPara.put("sign_type", signType);
		// 生成签名结果
		String mysign = buildRequestMysign(sPara, signType);
		// 签名结果与签名方式加入请求提交参数组中
		sPara.put("sign", mysign);
		
		return sPara;
	}

	/**
	 * 建立请求，以表单HTML形式构造（默认）
	 * 
	 * @param sParaTemp
	 *            请求参数数组
	 * @param strMethod
	 *            提交方式。两个值可选：post、get
	 * @param strButtonName
	 *            确认按钮显示文字
	 * @return 提交表单HTML文本
	 * @throws IOException 
	 */
	public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName,
			String signType) throws IOException {
		// 待请求参数数组
		Map<String, String> sPara = buildRequestPara(sParaTemp, signType);
		List<String> keys = new ArrayList<String>(sPara.keySet());

		StringBuffer sbHtml = new StringBuffer();

		sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + buildUrl(sPara)
				+ "\" method=\"" + strMethod + "\" >");

		for (int i = 0; i < keys.size(); i++) {
			String name = (String) keys.get(i);
			String value = (String) sPara.get(name);
			
			//增加页面
			value = value.replace("\"", "&quot;");
			
			sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
		}

		
		// submit按钮控件请不要含有name属性
		sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
		sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

		return sbHtml.toString();
	}
	
	/**
	 * 构建请求地址
	 * @param sPara
	 * @return
	 */
	public static String buildUrl(Map<String, String> sPara){
		StringBuffer baseUrl = new StringBuffer(ALIPAY_GATEWAY_NEW);
		List<String> keys = new ArrayList<String>(sPara.keySet());
		Collections.sort(keys);
		StringBuffer sbURL = new StringBuffer();
		for (int i = 0; i < keys.size(); i++) {
			String name = (String) keys.get(i);
				String value = (String) sPara.get(name);
				try {
					value = URLEncoder.encode(value, AlipayConfig.input_charset);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				sbURL.append("&");
				sbURL.append(name);
				sbURL.append("=");
				sbURL.append(value);
		}
		String dataStr = sbURL.toString();
		if(dataStr.length() > 1 ){
			dataStr = dataStr.substring(1);
		}
		return baseUrl.append(dataStr).toString();
	}

	/**
	 * 用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
	 * 
	 * @return 时间戳字符串
	 * @throws IOException
	 * @throws DocumentException
	 * @throws MalformedURLException
	 */
	public static String query_timestamp() throws MalformedURLException, DocumentException, IOException {

		// 构造访问query_timestamp接口的URL串
		String strUrl = ALIPAY_GATEWAY_NEW + "service=query_timestamp&partner=" + AlipayConfig.partner
				+ "&_input_charset" + AlipayConfig.input_charset;
		StringBuffer result = new StringBuffer();

		SAXReader reader = new SAXReader();
		Document doc = reader.read(new URL(strUrl).openStream());

		List<Node> nodeList = doc.selectNodes("//alipay/*");

		for (Node node : nodeList) {
			// 截取部分不需要解析的信息
			if (node.getName().equals("is_success") && node.getText().equals("T")) {
				// 判断是否有成功标示
				List<Node> nodeList1 = doc.selectNodes("//response/timestamp/*");
				for (Node node1 : nodeList1) {
					result.append(node1.getText());
				}
			}
		}

		return result.toString();
	}
}
