package com.yryz.quanhu.user.imsync.yunxin;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.message.im.entity.BlackAndMuteListVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * im好友
 *
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月28日 下午2:12:45
 */
public class YunxinRelation {
    private static final Logger imLogger = LoggerFactory.getLogger("im.logger");
    private static YunxinRelation yunxinRelation = null;

    private YunxinRelation() {
    }

    public static YunxinRelation getInstance() {
        if (yunxinRelation == null)
            yunxinRelation = new YunxinRelation();

        return yunxinRelation;
    }

    /**
     * 添加好友
     *
     * @param aCustId
     * @param bCustId
     * @throws Exception
     */
    public void addFriend(String aCustId, String bCustId) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accid", aCustId);
        params.put("faccid", bCustId);
        params.put("type", "1"); // 1直接加好友，2请求加好友，3同意加好友，4拒绝加好友
        params.put("msg", ""); // 加好友对应的请求消息，第三方组装，最长256字符

        String result = null;
        try {
            result = YunxinHttpClient.getInstance().post(YunxinHttpClient.FRIEND_ADD, params);
            imLogger.info("[im_addFriend]----->params:{},result:{}", params.toString(), result);
        } catch (Exception e) {
            imLogger.info("[im_addFriend]----->params:{},result:{}", params.toString(), e.getMessage());
            throw new Exception("yunxin invoke error");
        }

        YunxinHttpClient.getInstance().checkCode(result);
    }

    /**
     * 更新好友信息
     *
     * @param aCustId
     * @param bCustId
     * @param alias
     * @throws Exception
     */
    public void updateFriend(String aCustId, String bCustId, String alias) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accid", aCustId);
        params.put("faccid", bCustId);
        params.put("alias", alias);
        //扩展设置备注名为空
        Map<String, String> exParam = new HashMap<>();
        exParam.put("aliasIsEmpty", "false");
        if (StringUtils.isBlank(alias)) {
            exParam.put("aliasIsEmpty", "true");
        }
        params.put("ex", JsonUtils.toFastJson(exParam));
        String result = null;
        try {
            result = YunxinHttpClient.getInstance().post(YunxinHttpClient.FRIEND_UPDATE, params);
            imLogger.info("[im_updateFriend]----->aCustId:{},bCustId:{},alias:{},result:{}", aCustId, bCustId, alias, result);
        } catch (Exception e) {
            imLogger.info("[im_updateFriend]----->aCustId:{},bCustId:{},alias:{},result:{}", aCustId, bCustId, alias, e.getMessage());
            throw new Exception("yunxin invoke error");
        }
        YunxinHttpClient.getInstance().checkCode(result);
    }

    /**
     * 删除好友
     *
     * @param aCustId
     * @param bCustId
     * @throws Exception
     */
    public void deleteFriend(String aCustId, String bCustId) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accid", aCustId);
        params.put("faccid", bCustId);

        String result = null;
        try {
            result = YunxinHttpClient.getInstance().post(YunxinHttpClient.FRIEND_DELETE, params);
            imLogger.info("[im_deleteFriend]----->aCustId:{},bCustId:{},result:{}", aCustId, bCustId, result);
        } catch (Exception e) {
            imLogger.info("[im_deleteFriend]----->aCustId:{},bCustId:{},result:{}", aCustId, bCustId, e.getMessage());
            throw new Exception("yunxin invoke error");
        }
        YunxinHttpClient.getInstance().checkCode(result);
    }

    /**
     * 查询好友
     *
     * @param aCustId
     * @param bCustId
     * @throws Exception
     */
    public void getFriends(String custId) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accid", custId);
        params.put("createtime", "0");

        String result = null;
        try {
            result = YunxinHttpClient.getInstance().post(YunxinHttpClient.FRIEND_GET, params);
            System.out.println(result);
            imLogger.info("[im_getFriends]----->custId:{},result:{}", custId, result);
        } catch (Exception e) {
            imLogger.info("[im_getFriends]----->custId:{},result:{}", custId, result);
            throw new Exception("yunxin invoke error");
        }
        YunxinHttpClient.getInstance().checkCode(result);
    }



    /**
     * 设置黑名单/静音
     *
     *
     参数	类型	必须	说明
     accid	String	是	用户帐号，最大长度32字符，必须保证一个APP内唯一
     targetAcc	String	是	被加黑或加静音的帐号
     relationType	int	是	本次操作的关系类型,1:黑名单操作，2:静音列表操作
     relationValue	int	是	操作值，0:取消黑名单或静音，1:加入黑名单或静音

     * @param
     */
    public void setSpecialRelation(String userId, String targetUserId, String relationType, String value) throws Exception {
        Map<String, String> params = Maps.newHashMap();
        params.put("accid", userId);
        params.put("targetAcc", targetUserId);
        params.put("relationType", relationType);
        params.put("value", value);

        String result = null;
        try {
            imLogger.info("setSpecialRelation, request params: {}", JSON.toJSONString(params));
            result = YunxinHttpClient.getInstance().post(YunxinHttpClient.SET_SPECIALRELATION, params);
            imLogger.info("setSpecialRelation, result:{}", result);
        } catch (Exception e) {
            imLogger.info("setSpecialRelation error", e);
            throw new Exception("yunxin invoke error");
        }
        YunxinHttpClient.getInstance().checkCode(result);
    }

    /**
     * 查看指定用户的黑名单和静音列表
     *
     * @param userId
     * @throws Exception
     */
    public BlackAndMuteListVo listBlackAndMuteList(String userId) throws Exception {
        BlackAndMuteListVo blackAndMuteList = new BlackAndMuteListVo();

        List<String> blackList = Lists.newArrayList();
        List<String> muteList = Lists.newArrayList();

        Map<String, String> params = Maps.newHashMap();
        params.put("accid", userId);

        String result = null;
        try {
            imLogger.info("setSpecialRelation, request params: {}", JSON.toJSONString(params));
            result = YunxinHttpClient.getInstance().post(YunxinHttpClient.LIST_BLACK_AND_MUTE_LIST, params);
            imLogger.info("setSpecialRelation, result:{}", result);
        } catch (Exception e) {
            imLogger.info("setSpecialRelation error", e);
            throw new Exception("yunxin invoke error");
        }
        YunxinHttpClient.getInstance().checkCode(result);

        JSONObject json = null;
        try {
            json = JSONObject.fromObject(result);
            //黑名单列表
            JSONArray blackJSONArray = json.getJSONArray("blacklist");
            if (blackJSONArray != null && blackJSONArray.size() > 0) {
                for (int i = 0; i < blackJSONArray.size(); i++) {
                    blackList.add((String) blackJSONArray.get(i));
                }
            }
            //静音列表
            JSONArray muteJSONArray = json.getJSONArray("mutelist");
            if (muteJSONArray != null && muteJSONArray.size() > 0) {
                for (int i = 0; i < muteJSONArray.size(); i++) {
                    muteList.add((String) muteJSONArray.get(i));
                }
            }
        } catch (QuanhuException e) {
            throw QuanhuException.busiError("", "yunxin return code error");
        }

        if (CollectionUtils.isNotEmpty(blackList)) {
            blackAndMuteList.setBlackList(blackList);
        }
        if (CollectionUtils.isNotEmpty(muteList)) {
            blackAndMuteList.setMuteList(muteList);
        }

        return blackAndMuteList;
    }


    public static void main(String args[]) {
        try {
            // YunxinRelation.getInstance().addFriend("6qcmqlen5r",
            // "1lfrxekrb6");
            // YunxinRelation.getInstance().deleteFriend("6qcmqlen5r",
            // "1lfrxekrb6");
            // YunxinRelation.getInstance().getFriends("6qcmqlen5r");
//            YunxinRelation.getInstance().updateFriend("2ukx4mrr2k", "36b5ne0f3q", "");
//            YunxinRelation.getInstance().getFriends("2ukx4mrr2k");\
            YunxinRelation.getInstance().setSpecialRelation("626942183000989696",
                    "6269421830009896989", "1", "1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
