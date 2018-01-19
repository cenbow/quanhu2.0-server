package com.yryz.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年10月16日 下午3:12:51
 * @Description HTTP请求访问工具类
 */
public class HTTPWeb {
	
	private static Logger logger = LoggerFactory.getLogger(HTTPWeb.class);
	
	public static String post(String url , Map<String, ? extends Object > data) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost();
		try {
			httpPost.setURI(new URI(url));
			if(data != null){
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for (String key : data.keySet()) {
					params.add(new BasicNameValuePair(key, data.get(key).toString()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
			}
			
			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(1000)
					.setConnectionRequestTimeout(2000)
					.build();
			httpPost.setConfig(config);
			CloseableHttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if(entity == null){
				return null;
			}
			BufferedReader reader = new BufferedReader((new InputStreamReader(entity.getContent(),"utf-8")));
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ( (str = reader.readLine()) != null ) {
				buffer.append(str);
			}
			response.close();
			return buffer.toString();
		} catch (URISyntaxException | IOException e) {
			return null;
		} finally {
			try {
				client.close();
			} catch (IOException e) {
			}
		}
	}
	
	public static String post(String url , JSONObject json ) {
		Map<String, String> data = new HashMap<String, String>(1);
		for (Object key : json.keySet()) {
			data.put(key.toString(), json.get(key.toString()).toString());
		}
		return post(url, data);
	}
	
	public static String post(String url , String json){
		return post(url, new JSONObject(json));
	}
	
	public static String get(String url , Map<String, ? extends Object> data){
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet();
		try {
			URIBuilder uriBuilder = new URIBuilder().setPath(url);
			if(data != null){
				for (String key : data.keySet()) {
					uriBuilder.setParameter(key, data.get(key).toString());
				}
			}
			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(1000)
					.setConnectionRequestTimeout(2000)
					.build();
			httpGet.setConfig(config);
			httpGet.setURI(uriBuilder.build());
			CloseableHttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity == null){
				return null;
			}
			BufferedReader reader = new BufferedReader((new InputStreamReader(entity.getContent(),"utf-8")));
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ( (str = reader.readLine()) != null ) {
				buffer.append(str);
			}
			response.close();
			return buffer.toString();
		} catch (URISyntaxException e) {
			logger.warn("..URL:"+url, e);
		} catch (ClientProtocolException e) {
			logger.warn("..URL:"+url, e);
		} catch (IOException e) {
			logger.warn("..URL:"+url, e);
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				logger.warn("",e);
			}
		}
		return null;
	}
	
	public static String get(String url , JSONObject json ) {
		Map<String, String> data = new HashMap<String, String>(1);
		for (Object key : json.keySet()) {
			data.put(key.toString(), json.get(key.toString()).toString());
		}
		return get(url, data);
	}
	
	public static String get(String url , String json){
		return get(url, new JSONObject(json));
	}
	
	public static String postBody(String url , String data){
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost();
		try {
			httpPost.setURI(new URI(url));
//			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setEntity(new StringEntity(data,"utf-8"));
			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(1000)
					.setConnectionRequestTimeout(2000)
					.build();
			httpPost.setConfig(config);
			CloseableHttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if(entity == null){
				return null;
			}
			BufferedReader reader = new BufferedReader((new InputStreamReader(entity.getContent(),"utf-8")));
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ( (str = reader.readLine()) != null ) {
				buffer.append(str);
			}
			response.close();
			return buffer.toString();
		} catch (URISyntaxException | IOException e) {
			logger.warn("..URL:"+url, e);
			return null;
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				logger.warn(""+e);
			}
		}
	}
	
	public static String getBody(String url){
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet();
		try {
			URIBuilder uriBuilder = new URIBuilder().setPath(url);
			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(1000)
					.setConnectionRequestTimeout(2000)
					.build();
//			httpGet.setHeader("Content-Type", "application/json");
			httpGet.setConfig(config);
			httpGet.setURI(uriBuilder.build());
			CloseableHttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity == null){
				return null;
			}
			BufferedReader reader = new BufferedReader((new InputStreamReader(entity.getContent(),"utf-8")));
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ( (str = reader.readLine()) != null ) {
				buffer.append(str);
			}
			response.close();
			return buffer.toString();
		} catch (URISyntaxException e) {
			logger.warn("..URL:"+url, e);
		} catch (ClientProtocolException e) {
			logger.warn("..URL:"+url, e);
		} catch (IOException e) {
			logger.warn("..URL:"+url, e);
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				logger.warn("",e);
			}
		}
		return null;
	}
	
	public static String putBody(String url , String data){
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut();
		try {
			httpPut.setURI(new URI(url));
//			Httpput.setHeader("Content-Type", "application/json");
			httpPut.setEntity(new StringEntity(data,"utf-8"));
			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(1000)
					.setConnectionRequestTimeout(2000)
					.build();
			httpPut.setConfig(config);
			CloseableHttpResponse response = client.execute(httpPut);
			HttpEntity entity = response.getEntity();
			if(entity == null){
				return null;
			}
			BufferedReader reader = new BufferedReader((new InputStreamReader(entity.getContent(),"utf-8")));
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ( (str = reader.readLine()) != null ) {
				buffer.append(str);
			}
			response.close();
			return buffer.toString();
		} catch (URISyntaxException | IOException e) {
			logger.warn("..URL:"+url, e);
			return null;
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				logger.warn(""+e);
			}
		}
	}
	
	public static String deleteBody(String url){
		CloseableHttpClient client = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete();
		try {
			httpDelete.setURI(new URI(url));
//			httpDelete.setHeader("Content-Type", "application/json");
			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(1000)
					.setConnectionRequestTimeout(2000)
					.build();
			httpDelete.setConfig(config);
			CloseableHttpResponse response = client.execute(httpDelete);
			HttpEntity entity = response.getEntity();
			if(entity == null){
				return null;
			}
			BufferedReader reader = new BufferedReader((new InputStreamReader(entity.getContent(),"utf-8")));
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ( (str = reader.readLine()) != null ) {
				buffer.append(str);
			}
			response.close();
			return buffer.toString();
		} catch (URISyntaxException | IOException e) {
			logger.warn("..URL:"+url, e);
			return null;
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				logger.warn(""+e);
			}
		}
	}
	
}
