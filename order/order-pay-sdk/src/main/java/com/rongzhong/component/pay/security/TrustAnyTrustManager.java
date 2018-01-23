package com.rongzhong.component.pay.security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/*
 * 证书过滤器
 * @author: Robin 
 * @date : 2011-03-10
 */
public class TrustAnyTrustManager implements X509TrustManager
{

	/**
	 * 该方法体为空时信任所有客户端证书
	 * 
	 * @param chain
	 * @param authType
	 * @throws CertificateException
	 */
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}
	/**
	 * 该方法体为空时信任所有服务器证书
	 * 
	 * @param chain
	 * @param authType
	 * @throws CertificateException
	 */

	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}

	/**
	 * 返回信任的证书
	 * @return
	 */
	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[]{};
	}
}