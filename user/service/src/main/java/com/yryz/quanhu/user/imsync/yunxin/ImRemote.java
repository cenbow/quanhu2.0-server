package com.yryz.quanhu.user.imsync.yunxin;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImRemote {
	 private static Logger logger = LoggerFactory.getLogger(ImRemote.class);
	 /**
     * 添加用戶
     *
     * @param userId
     * @param nick
     */
    public static void add(String userId, String nick, String iconUrl){
        try {
            YunxinUser.getInstance().getUser(userId);
            logger.info("查询用户：{}", userId);
            return;
        } catch (Exception e) {
            logger.error("[Im addUser]", e);
        }
        try {
            YunxinUser.getInstance().addUser(userId, nick, iconUrl);
            logger.info("同步用户：" + userId + ", " + nick + ", " + iconUrl);
        } catch (Exception e) {
            logger.error("[Im addUser]", e);
        }
    }
    
    public static void addFirend(String aCustId, String bCustId, String nameNotes){
        if (StringUtils.isEmpty(aCustId) || StringUtils.isEmpty(bCustId)) {
            return;
        }

        try {
            YunxinRelation.getInstance().addFriend(aCustId, bCustId);
            logger.info("同步好友：" + aCustId + ", " + bCustId);            
        } catch (Exception e) {
            logger.error("[Im addUser]", e);
        }

        try {
            if (StringUtils.isNotEmpty(nameNotes)) {
                logger.info("同步好友备注：" + aCustId + ", " + bCustId + ", " + nameNotes);
                YunxinRelation.getInstance().updateFriend(aCustId, bCustId, nameNotes);
            }
        } catch (Exception e) {
            logger.error("[Im updateFriend]", e);
        }

    }
}
