package com.rongzhong.component.pay.iospay;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import com.rongzhong.component.pay.security.TrustAnyHostnameVerifier;
import com.rongzhong.component.pay.security.TrustAnyTrustManager;

public class IosVerify {
	
	/**
	 * 校验交易凭证
	 * @param receipt
	 * @param isSandbox
	 * @return
	 */
	public static String verifyReceipt(String receipt, boolean isSandbox) {
		String url = IosIapConfig.URL_VERIFY;
		if (isSandbox) {
			url = IosIapConfig.URL_SANDBOX;
		}
		HttpsURLConnection conn = null;
//		String buyCode = getBASE64(receipt);
		String buyCode = receipt;  //苹果内购的数据只能客户端用base64编码后传过来。他并不是一个正常的字符数据。服务端直接将组装好发到苹果端验证
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
			URL console = new URL(url);
			conn = (HttpsURLConnection) console.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setRequestMethod("POST");
			conn.setRequestProperty("content-type", "text/json");
			conn.setRequestProperty("Proxy-Connection", "Keep-Alive");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			BufferedOutputStream hurlBufOus = null;
			try {
				hurlBufOus = new BufferedOutputStream(conn.getOutputStream());

				String str = String.format(Locale.CHINA, "{\"receipt-data\":\"" + buyCode + "\"}");
				hurlBufOus.write(str.getBytes());
				hurlBufOus.flush();
			} finally {
				if (hurlBufOus != null)
					hurlBufOus.close();
			}
			
			BufferedReader reader = null;
			StringBuffer sb = new StringBuffer();
			try {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			} finally {
				if (reader != null)
					reader.close();
			}

			return sb.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (conn != null)
				conn.disconnect();
		}
		return null;
	}

	/**
	 * 用BASE64加密
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String getBASE64(String str) {
		byte[] b = str.getBytes();
		String s = null;
		if (b != null) {
			s = new sun.misc.BASE64Encoder().encode(b);
		}
		return s;
	}

	/**
	 * 解密BASE64字串
	 * 
	 * @param s
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String getFromBASE64(String s) {
		byte[] b = null;
		if (s != null) {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				return new String(b);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new String(b);
	}

}
