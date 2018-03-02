package com.rongzhong.component.pay.chinapay;

import chinapay.Base64;
import chinapay.SecureLink;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yryz.common.context.Context;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;

public class ChinapayCash {

    private static Logger LOGGER = LoggerFactory.getLogger(ChinapayCash.class);

    private static String MerKeyPath = Context.getWebRootRealPath() + Context.getProperty("chinapay.merkeypath");
    private static String pay_url = Context.getProperty("chinapay.payurl");
    private static String PubKeyPath = Context.getWebRootRealPath() + Context.getProperty("chinapay.pubkeypath");
    private static String merId = Context.getProperty("chinapay.merid");

    public static boolean payCash(String txnAmt, String orderId, String name, String bankCardNo, String bankName) {
        TransactionBean transactionBean = buildTransaction(txnAmt, orderId, name, bankCardNo, bankName);
        return sendData(transactionBean);
    }

    private static TransactionBean buildTransaction(String txnAmt, String orderId, String name, String bankCardNo, String bankName) {
        String merDate = DateFormatUtils.format(new Date(), "yyyyMMdd");
        String merSeqId = orderId;  //流水号
        String cardNo = bankCardNo;  //银行卡号
        String usrName = name; //姓名
        String openBank = bankName;//开户行
        String prov = "上海";  //省份
        String city = "上海";  //省份
        String transAmt = txnAmt;    //单位：分？
        String purpose = "用户提现";  //交易用途
        String flag = "00";  //付款标志 00：对私   01：对公
        String version = "20150304";  //接口版本
        String termType = "";  //渠道类型，选填；  07：互联网  08：移动端
        //签名数据组装
        TransactionBean pay = new TransactionBean();
        pay.setMerId(merId);
        pay.setMerDate(merDate);
        pay.setMerSeqId(merSeqId);
        pay.setCardNo(cardNo);
        pay.setUsrName(usrName);
        pay.setOpenBank(openBank);
        pay.setProv(prov);
        pay.setCity(city);
        pay.setTransAmt(transAmt);
        pay.setPurpose(purpose);
        pay.setSubBank("浦东支行");
        pay.setFlag(flag);
        pay.setVersion(version);
        pay.setTermType(termType);

        String Data = pay.toString();
        System.out.println("字符串数据拼装结果：" + Data);
        String plainData = null;
        try {
            plainData = new String(Base64.encode(Data.getBytes("GBK")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//		System.out.println("转换成后数据：" + plainData);

        //签名
        String chkValue = null;
        int KeyUsage = 0;
        ChinapayKey key = new ChinapayKey();
        boolean flage = key.buildKey(merId, KeyUsage, MerKeyPath);
        if (flage == false) {
            System.out.println("buildkey error!");
            return null;
        } else {
            SecureLink sl = new SecureLink(key);
            System.out.println("original data " + plainData);
            chkValue = sl.Sign(plainData);
            System.out.println("signed data" + chkValue);
        }
        ;
        pay.setChkValue(chkValue);        //设置签名内容
        return pay;

    }

    private static boolean sendData(TransactionBean transactionBean) {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = builder.create();
        String data = gson.toJson(transactionBean);
        System.out.println(data);
        String resMes = HTTPWeb.post(pay_url, data);
        System.out.println("交易返回报文：" + resMes);

        int dex = resMes.lastIndexOf("&");

        //拆分页面应答数据
        String str[] = resMes.split("&");
        System.out.println(str.length);

        //提取返回数据
        if (str.length == 10) {
            int Res_Code = str[0].indexOf("=");
            int Res_merId = str[1].indexOf("=");
            int Res_merDate = str[2].indexOf("=");
            int Res_merSeqId = str[3].indexOf("=");
            int Res_cpDate = str[4].indexOf("=");
            int Res_cpSeqId = str[5].indexOf("=");
            int Res_transAmt = str[6].indexOf("=");
            int Res_stat = str[7].indexOf("=");
            int Res_cardNo = str[8].indexOf("=");
            int Res_chkValue = str[9].indexOf("=");

            String responseCode = str[0].substring(Res_Code + 1);
            String MerId = str[1].substring(Res_merId + 1);
            String MerDate = str[2].substring(Res_merDate + 1);
            String MerSeqId = str[3].substring(Res_merSeqId + 1);
            String CpDate = str[4].substring(Res_cpDate + 1);
            String CpSeqId = str[5].substring(Res_cpSeqId + 1);
            String TransAmt = str[6].substring(Res_transAmt + 1);
            String Stat = str[7].substring(Res_stat + 1);
            String CardNo = str[8].substring(Res_cardNo + 1);
            String ChkValue = str[9].substring(Res_chkValue + 1);

            //		System.out.println("responseCode=" + responseCode);
            //		System.out.println("merId=" + MerId);
            //		System.out.println("merDate=" + MerDate);
            //		System.out.println("merSeqId=" + MerSeqId);
            //		System.out.println("transAmt=" + TransAmt);
            //		System.out.println("cpDate=" + CpDate);
            //		System.out.println("cpSeqId=" + CpSeqId);
            //		System.out.println("Stat=" + Stat);
            //		System.out.println("cardNo=" + CardNo);
            //		System.out.println("chkValue=" + ChkValue);

            String msg = resMes.substring(0, dex);
            String plainData = new String(Base64.encode(msg.getBytes()));
            System.out.println("需要验签的字段：" + msg);

            //传入显示页面的数据准备
            TransactionBean pay = new TransactionBean();
            pay.setResponseCode(responseCode);
            pay.setMerId(MerId);
            pay.setMerDate(MerDate);
            pay.setMerSeqId(MerSeqId);
            pay.setCpDate(CpDate);
            pay.setCpSeqId(CpSeqId);
            pay.setTransAmt(TransAmt);
            pay.setStat(Stat);
            pay.setCardNo(CardNo);
            pay.setData(resMes);

            //		request.setAttribute("payInput", pay);

            //对收到的ChinaPay应答传回的域段进行验签
            boolean buildOK = false;
            boolean res = false;
            int KeyUsage = 0;
            ChinapayKey key = new ChinapayKey();
            try {
                buildOK = key.buildKey("999999999999999", KeyUsage, PubKeyPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!buildOK) {
                LOGGER.error("chinapay build key error!");
                return false;
            }

            SecureLink sl = new SecureLink(key);
            res = sl.verifyAuthToken(plainData, ChkValue);
            if (res) {
                if ("0000".equals(responseCode)) {
                    return true;
                } else {
                    LOGGER.warn("china pay return a wrong value , the result is " + resMes);
                    return false;
                }
            }
        }

        //交易失败应答
        if (str.length == 2) {
            int Res_Code = str[0].indexOf("=");
            int Res_chkValue = str[1].indexOf("=");

            String responseCode = str[0].substring(Res_Code + 1);
            String ChkValue = str[1].substring(Res_chkValue + 1);
            System.out.println("responseCode=" + responseCode);
            System.out.println("chkValue=" + ChkValue);

            String plainData = str[0];
            String plainData1 = new String(Base64.encode(plainData.getBytes()));
            System.out.println("需要验签的字段：" + plainData);

            TransactionBean pay = new TransactionBean();
            pay.setResponseCode(responseCode);
            pay.setData(resMes);

            //对收到的ChinaPay应答传回的域段进行验签
            boolean buildOK = false;
            boolean res = false;
            int KeyUsage = 0;
            ChinapayKey key = new ChinapayKey();
            try {
                buildOK = key.buildKey("999999999999999", KeyUsage, PubKeyPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!buildOK) {
                LOGGER.error("chinapay build key error!");
                return false;
            }

            SecureLink sl = new SecureLink(key);
            res = sl.verifyAuthToken(plainData1, ChkValue);
            if (res) {
                if ("0000".equals(responseCode)) {
                    return true;
                } else {
                    LOGGER.warn("china pay return a wrong value , the result is " + resMes);
                    return false;
                }
            }
        }
        LOGGER.warn("chinapay return an error string ,the result is " + resMes);
        return false;
    }

    private static String getOrderId() {
        String date = DateFormatUtils.format(new Date(), "yyyyMMdd");
        Random random = new Random();
        int num = random.nextInt(1000000) + 1000000;
        return date + num;
    }

    private static TransactionBean buildTransaction() {
        String txnAmt = "1";
        String orderId = getOrderId();
        String name = "谢小青";
        String bankCardNo = "6215593202008646336";
        String bankName = "工商银行";
        return buildTransaction(txnAmt, orderId, name, bankCardNo, bankName);
    }

    public static void main(String[] args) {
        TransactionBean transactionBean = buildTransaction();
        sendData(transactionBean);
    }

}
