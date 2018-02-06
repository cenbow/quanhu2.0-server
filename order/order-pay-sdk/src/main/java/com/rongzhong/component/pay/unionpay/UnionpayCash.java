package com.rongzhong.component.pay.unionpay;

import java.util.HashMap;
import java.util.Map;

import com.yryz.common.context.Context;

public class UnionpayCash {
	
	//默认配置的是UTF-8
	public final static String ENCODING_UTF8 = "UTF-8";
	
	public final static String encoding_GBK = "GBK";
	//全渠道固定值
	public final static String VERSION = "5.0.0";
	
	private static String noticeUrl = Context.getProperty("acpsdk_cash_notify_url");
	
	private static String merId = Context.getProperty("acpsdk_mer_id");
	
	public static boolean payCash(String txnAmt ,String orderId ,String name ,String bankCardNo ,String txnTime){
//		String txnTime = DateUtils.getDate("yyyyMMddHHmmss");
		
		Map<String, String> contentData = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		contentData.put("version", VERSION);            //版本号 全渠道默认值
		contentData.put("encoding", ENCODING_UTF8);     //字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", "01");           		 	//签名方法 目前只支持01：RSA方式证书加密
		contentData.put("txnType", "12");              		 	//交易类型 12：代付
		contentData.put("txnSubType", "00");           		 	//默认填写00
		contentData.put("bizType", "000401");          		 	//000401：代付
		contentData.put("channelType", "07");          		 	//渠道类型

		/***商户接入参数***/
		contentData.put("merId", merId);   		 				//商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
		contentData.put("accessType", "0");            		 	//接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
		contentData.put("orderId", orderId);        	 	    //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		contentData.put("txnTime", txnTime);		 		    //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		contentData.put("accType", "01");					 	//账号类型 01：银行卡02：存折03：IC卡帐号类型(卡介质)
		
		//////////如果商户号开通了  商户对敏感信息加密的权限那么，需要对 卡号accNo加密使用：
//		contentData.put("encryptCertId",AcpService.getEncryptCertId());      //上送敏感信息加密域的加密证书序列号
//		String accNo = AcpService.encryptData(bankCardNo, ENCODING_UTF8); //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
//		contentData.put("accNo", accNo);
		
		/////////商户未开通敏感信息加密的权限那么不对敏感信息加密使用：
		contentData.put("accNo",bankCardNo);                  //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		////////

		//代收交易的上送的卡验证要素为：姓名或者证件类型+证件号码
		Map<String,String> customerInfoMap = new HashMap<String,String>();
//		customerInfoMap.put("certifTp", "01");						    //证件类型
//		customerInfoMap.put("certifId", "341126197709218366");		    //证件号码
		customerInfoMap.put("customerNm", name);					//姓名
		String customerInfoStr = AcpService.getCustomerInfo(customerInfoMap,bankCardNo,ENCODING_UTF8);				
		
		contentData.put("customerInfo", customerInfoStr);
		contentData.put("txnAmt", txnAmt);						 		//交易金额 单位为分，不能带小数点
		contentData.put("currencyCode", "156");                    	    //境内商户固定 156 人民币
		//contentData.put("reqReserved", "透传字段");                      //商户自定义保留域，如需使用请启用即可；交易应答时会原样返回
		
		//后台通知地址（需设置为外网能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，【支付失败的交易银联不会发送后台通知】
		//后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
		//注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
		//    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200或302，那么银联会间隔一段时间再次发送。总共发送5次，银联后续间隔1、2、4、5 分钟后会再次通知。
		//    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
		contentData.put("backUrl", noticeUrl);
				
		/**对请求参数进行签名并发送http post请求，接收同步应答报文**/
		Map<String, String> reqData = AcpService.sign(contentData,ENCODING_UTF8);			 		 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();								 //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		
		Map<String, String> rspData = AcpService.post(reqData,requestBackUrl,ENCODING_UTF8);        //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		boolean flag = false;
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, ENCODING_UTF8)){
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode");
				System.out.println("respCode:" + respCode);
				if(("00").equals(respCode)){
					//交易已受理(不代表交易已成功），等待接收后台通知确定交易成功，也可以主动发起 查询交易确定交易状态。
					
					flag = true;
					//如果返回卡号且配置了敏感信息加密，解密卡号方法：
					//String accNo1 = resmap.get("accNo");
					//String accNo2 = AcpService.decryptPan(accNo1, "UTF-8");	//解密卡号使用的证书是商户签名私钥证书acpsdk.signCert.path
					//LogUtil.writeLog("解密后的卡号："+accNo2);
					
				}else if(("03").equals(respCode) ||
						 ("04").equals(respCode) ||
						 ("05").equals(respCode) ||
						 ("01").equals(respCode) ||
						 ("12").equals(respCode) ||
						 ("34").equals(respCode) ||
						 ("60").equals(respCode) ){
					//后续需发起交易状态查询交易确定交易状态。
				}else{
					//其他应答码为失败请排查原因
				}
			}else{
				LogUtil.writeErrorLog("验证签名失败");
				//检查验证签名失败的原因
			}	
		}else{
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
		}
		
		String reqMessage = DemoBase.genHtmlResult(reqData);
		String rspMessage = DemoBase.genHtmlResult(rspData);
		System.out.println("代付交易</br>请求报文:<br/>"+reqMessage+"<br/>" + "应答报文:</br>"+rspMessage+"");
		return flag;
	}

}
