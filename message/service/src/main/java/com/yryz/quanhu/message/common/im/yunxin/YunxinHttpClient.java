package com.yryz.quanhu.message.common.im.yunxin;

import com.yryz.common.appinfo.AppInfo;
import com.yryz.common.context.Context;
import com.yryz.common.exception.QuanhuException;
import com.yryz.quanhu.message.common.utils.YunxinConfigUtils;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * 云信请求封装
 * @author Pxie
 */
public class YunxinHttpClient {

	public static final String USER_CREATE = "https://api.netease.im/nimserver/user/create.action";
	public static final String USER_UPDATE = "https://api.netease.im/nimserver/user/updateUinfo.action";
	public static final String USER_GET = "https://api.netease.im/nimserver/user/getUinfos.action";
	public static final String USER_BLOCK = "https://api.netease.im/nimserver/user/block.action";
	public static final String USER_UNBLOCK = "https://api.netease.im/nimserver/user/unblock.action";

	public static final String FRIEND_ADD = "https://api.netease.im/nimserver/friend/add.action";
	public static final String FRIEND_UPDATE = "https://api.netease.im/nimserver/friend/update.action";
	public static final String FRIEND_DELETE = "https://api.netease.im/nimserver/friend/delete.action";
	public static final String FRIEND_GET = "https://api.netease.im/nimserver/friend/get.action";

	public static final String SET_SPECIALRELATION = "https://api.netease.im/nimserver/user/setSpecialRelation.action";
	public static final String LIST_BLACK_AND_MUTE_LIST = "https://api.netease.im/nimserver/user/listBlackAndMuteList.action";



	public static final String TEAM_ADD = "https://api.netease.im/nimserver/team/create.action";
	public static final String TEAM_QUERY = "https://api.netease.im/nimserver/team/query.action";
	public static final String TEAM_UPDATE = "https://api.netease.im/nimserver/team/update.action";
	public static final String TEAM_DELETE = "https://api.netease.im/nimserver/team/remove.action";
	public static final String TEAM_ADD_MEMBER = "https://api.netease.im/nimserver/team/add.action";
	public static final String TEAM_CHANGEOWNER = "https://api.netease.im/nimserver/team/changeOwner.action";
	public static final String TEAM_ADDMANAGER = "https://api.netease.im/nimserver/team/addManager.action";
	public static final String TEAM_REMOVEMANAGER = "https://api.netease.im/nimserver/team/removeManager.action";
	public static final String TEAM_MUTETLIST = "https://api.netease.im/nimserver/team/muteTlist.action";
	public static final String TEAM_MUTETLISTALL = "https://api.netease.im/nimserver/team/muteTlistAll.action";



	private static Logger logger = LoggerFactory.getLogger(YunxinHttpClient.class);

	private static String appKey;
	private static String appSecret;
    private static YunxinHttpClient client = null;

    private YunxinHttpClient(YunxinConfig config){
    	if (config != null) {
    		appKey = config.getAppKey();
    		appSecret = config.getAppSecret();
		} else {
			appKey = Context.getProperty("YUNXIN_APPKEY");
			appSecret  = Context.getProperty("YUNXIN_APPSECRET");
		}

//		appKey = "6392d34d5e184ff729bd159c657634e6";
//		appSecret = "941f7f877b46";

//online
//		appKey = "6206141b1378b670588546f5822cfb51";
//		appSecret = "011351144ee1";
	}

    public static YunxinHttpClient getInstance() {
        YunxinConfig config = YunxinConfigUtils.getYunxinConfig();
        client = new YunxinHttpClient(config);

        return client;
    }

	/**
	 * 请求头
	 * @param post
	 */
	private void addHeader(HttpPost post) {
        String nonce =  "12345";
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

		post.addHeader("appKey", appKey);
		post.addHeader("Nonce", nonce);
		post.addHeader("CurTime", curTime);
		post.addHeader("checkSum", checkSum);
		post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	}

	/**
	 * 返回状态判断
	 * @param json
	 * @throws Exception
	 */
	public JSONObject checkCode(String result) {
		if (result == null) {
			throw QuanhuException.busiError("yunxin return code error");
		}

		int code = 0;
		JSONObject json = null;
		try {
			 json = JSONObject.fromObject(result);
			code = json.getInt("code");
		} catch (QuanhuException e) {
			throw QuanhuException.busiError("","yunxin return code error");
		}

		if (code != 200) {
			throw QuanhuException.busiError("",json.getString("desc"));
		}

		return json;
	}

	public String post(String url , Map<String, ? extends Object > data) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost();
		try {
			httpPost.setURI(new URI(url));
			if(data != null){
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for (String key : data.keySet()) {
					if(data.get(key)!=null) {
						params.add(new BasicNameValuePair(key, data.get(key).toString()));
					}
				}
				httpPost.setEntity(new UrlEncodedFormEntity(params,"utf-8"));
			}

			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(1000)
					.setConnectionRequestTimeout(2000)
					.build();
			httpPost.setConfig(config);
			addHeader(httpPost);
			CloseableHttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if(entity == null) return null;
			BufferedReader reader = new BufferedReader((new InputStreamReader(entity.getContent(),"utf-8")));
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ( (str = reader.readLine()) != null ) {
				buffer.append(str);
			}
			response.close();
			return buffer.toString();
		} catch (URISyntaxException | IOException e) {
			logger.error("Yunxin Client Post", e);
			return null;
		} finally {
			try {
				client.close();
			} catch (IOException e) {
			}
		}
	}

	public String post(String url, JSONObject json ) {
		Map<String, String> data = new HashMap<String, String>();
		for (Object key : json.keySet()) {
			data.put(key.toString(), json.get(key.toString()).toString());
		}
		return post(url, data);
	}


	public static void main(String args[]) {
		YunxinUser user = YunxinUser.getInstance();
		try {
			user.addUser("testPxie", "pxie", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
