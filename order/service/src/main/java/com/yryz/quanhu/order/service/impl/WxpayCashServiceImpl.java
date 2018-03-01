package com.yryz.quanhu.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.rongzhong.component.pay.util.MD5Util;
import com.rongzhong.component.pay.wxpay.WxpayConfig;
import com.rongzhong.component.pay.wxpay.util.CodeGenerator;
import com.rongzhong.component.pay.wxpay.util.XMLUtil;
import com.yryz.quanhu.order.entity.RrzOrderPayInfo;
import com.yryz.quanhu.order.enums.OrderPayConstants;
import com.yryz.quanhu.order.service.OrderService;
import com.yryz.quanhu.order.service.WxpayCashService;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/26 14:51
 * Created by lifan
 */
@Service
public class WxpayCashServiceImpl implements WxpayCashService {

    private static final Logger logger = LoggerFactory.getLogger(WxpayCashServiceImpl.class);

    private static final String GET_PUBLIC_KEY_URL = "https://fraud.mch.weixin.qq.com/risk/getpublickey";
    private static final String PAY_BANK_URL = "https://api.mch.weixin.qq.com/mmpaysptrans/pay_bank";
    private static final String QUERY_BANK_URL = "https://api.mch.weixin.qq.com/mmpaysptrans/query_bank";

    //微信提现公钥Cipher
    private Cipher cipher;
    //SSL连接工厂
    private SSLConnectionSocketFactory sslConnectionSocketFactory;

    @Autowired
    private OrderService orderService;

    /**
     * 获取微信提现加密RSA公钥
     *
     * @return
     */
    public Map<String, String> wxpayCashPublicKey() {
        try {
            SortedMap<String, String> parameters = new TreeMap<String, String>();
            parameters.put("mch_id", WxpayConfig.MCH_ID);
            parameters.put("nonce_str", CodeGenerator.generate(false, 32));
            parameters.put("sign_type", "MD5");
            parameters.put("sign", createSign(parameters));
            String requestXML = getRequestXml(parameters);
            logger.info("获取提现公钥请求：{}", requestXML);
            String reqResult = httpsRequest(GET_PUBLIC_KEY_URL, requestXML);
            Map<String, String> map = XMLUtil.doXMLParse(reqResult);
            logger.info("获取提现公钥响应：{}", JSON.toJSONString(map));
            return map;
        } catch (Exception e) {
            logger.error("获取微信提现公钥失败", e);
        }
        return null;
    }

    /**
     * 微信提现到银行卡
     *
     * @param orderId
     * @param userName
     * @param bankCardNo
     * @param bankId
     * @param amount
     * @return
     */
    public boolean wxpayCash(String orderId, String userName, String bankCardNo, String bankId, String amount) {
        try {
            RrzOrderPayInfo rrzOrderPayInfo = orderService.getPayInfo(orderId);
            if (null == rrzOrderPayInfo || !OrderPayConstants.OrderState.CREATE.equals(rrzOrderPayInfo.getOrderState())) {
                logger.error("不合法的提现请求，已拒绝。orderId:{}", orderId);
                return false;
            }
            SortedMap<String, String> parameters = new TreeMap<String, String>();
            parameters.put("mch_id", WxpayConfig.MCH_ID);
            parameters.put("partner_trade_no", orderId);
            parameters.put("nonce_str", CodeGenerator.generate(false, 32));
            parameters.put("enc_bank_no", encrypt(bankCardNo));
            parameters.put("enc_true_name", encrypt(userName));
            parameters.put("bank_code", bankId);
            parameters.put("amount", amount);
//            parameters.put("desc", URLEncoder.encode("提现", "UTF-8"));
            parameters.put("sign", createSign(parameters));
            String requestXML = getRequestXml(parameters);
            logger.info("执行提现请求：{}", requestXML);
            String reqResult = httpsRequest(PAY_BANK_URL, requestXML);
            Map<String, String> map = XMLUtil.doXMLParse(reqResult);
            logger.info("执行提现响应：{}", JSON.toJSONString(map));
            if (null != map && "SUCCESS".equals(map.get("return_code")) && "SUCCESS".equals(map.get("result_code"))) {
                return true;
            }
        } catch (Exception e) {
            logger.error("执行微信提现到银行卡失败", e);
        }
        return false;
    }

    /**
     * 查询微信提现到银行卡的结果
     *
     * @param orderId
     * @return
     */
    public Map<String, String> wxpayCashQuery(String orderId) {
        try {
            SortedMap<String, String> parameters = new TreeMap<String, String>();
            parameters.put("mch_id", WxpayConfig.MCH_ID);
            parameters.put("partner_trade_no", orderId);
            parameters.put("nonce_str", CodeGenerator.generate(false, 32));
            parameters.put("sign", createSign(parameters));
            String requestXML = getRequestXml(parameters);
            logger.info("查询微信提现订单状态请求：{}", requestXML);
            String reqResult = httpsRequest(QUERY_BANK_URL, requestXML);
            Map<String, String> map = XMLUtil.doXMLParse(reqResult);
            logger.info("查询微信提现订单状态响应：{}", JSON.toJSONString(map));
            return map;
        } catch (Exception e) {
            logger.error("查询微信提现订单状态失败", e);
        }
        return null;
    }

    /**
     * 微信公钥加密
     *
     * @param content
     * @return
     * @throws Exception
     */
    private String encrypt(String content) throws Exception {
        if (null == cipher) {
            ClassPathResource classPathResource = new ClassPathResource(WxpayConfig.PUBLIC_KEY_PATH);
            String publicKey = IOUtils.toString(classPathResource.getInputStream(), "UTF-8");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(com.rongzhong.component.pay.alipay.sign.Base64.decode(publicKey));
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey pk = factory.generatePublic(x509EncodedKeySpec);
            cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-1ANDMGF1PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, pk);
        }
        byte[] result = cipher.doFinal(content.getBytes("UTF-8"));
        return com.rongzhong.component.pay.alipay.sign.Base64.encode(result);
    }

    /**
     * 创建签名
     *
     * @param parameters
     * @return
     */
    private String createSign(SortedMap<String, String> parameters) {
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
        sb.append("key=" + WxpayConfig.MD5_KEY);
        String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
        logger.info("微信支付创建签名结束：" + System.currentTimeMillis());
        return sign;
    }

    /**
     * 将请求参数转换为xml格式的string
     *
     * @param parameters
     * @return
     */
    private String getRequestXml(SortedMap<String, String> parameters) {
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
     *
     * @param requestUrl
     * @param outputStr
     * @return
     */
    private String httpsRequest(String requestUrl, String outputStr) {
        String result = "";
        if (null == sslConnectionSocketFactory) {
            //指定读取证书格式为PKCS12
            //读取本机存放的PKCS12证书文件
            InputStream instream = null;
            try {
                ClassPathResource classPathResource = new ClassPathResource(WxpayConfig.MERCHANT_KEY_PATH);
                instream = classPathResource.getInputStream();
                KeyStore keyStore = KeyStore.getInstance("PKCS12");
                //指定PKCS12的密码(商户ID)
                keyStore.load(instream, WxpayConfig.MCH_ID.toCharArray());
                SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, WxpayConfig.MCH_ID.toCharArray()).build();
                //指定TLS版本
                sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"},
                        null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            } catch (Exception e) {
                logger.error("获取微信商务证书失败", e);
            } finally {
                if (null != instream) {
                    try {
                        instream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            //设置httpclient的SSLSocketFactory
            httpclient = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
            HttpPost httpPost = new HttpPost(requestUrl);
            httpPost.setEntity(new StringEntity(outputStr));
            System.out.println("executing request" + httpPost.getRequestLine());
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            if (entity != null) {
                System.out.println("Response content length: " + entity.getContentLength());
                // 从输入流读取返回内容
                inputStreamReader = new InputStreamReader(entity.getContent(), "utf-8");
                bufferedReader = new BufferedReader(inputStreamReader);
                String str = null;
                StringBuffer buffer = new StringBuffer();
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }
                result = buffer.toString();
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            logger.error("执行微信提现失败", e);
        } finally {
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inputStreamReader) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpclient) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
