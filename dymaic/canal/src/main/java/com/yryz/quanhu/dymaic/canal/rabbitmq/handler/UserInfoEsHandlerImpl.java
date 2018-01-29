package com.yryz.quanhu.dymaic.canal.rabbitmq.handler;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.yryz.quanhu.dymaic.canal.entity.TagInfo;
import com.yryz.quanhu.dymaic.canal.entity.UserTagInfo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import com.yryz.common.entity.CanalMsgContent;
import com.yryz.common.utils.CanalEntityParser;
import com.yryz.quanhu.dymaic.canal.constant.CommonConstant;
import com.yryz.quanhu.dymaic.canal.dao.UserRepository;
import com.yryz.quanhu.dymaic.canal.entity.UserBaseInfo;
import com.yryz.quanhu.dymaic.canal.entity.UserInfo;

/**
 * 利用MQ处理canal消息处理
 *
 * @author jk
 */
@Component
public class UserInfoEsHandlerImpl implements SyncHandler {
    private static Logger logger = LoggerFactory.getLogger(UserInfoEsHandlerImpl.class);
    @Resource
    private SyncExecutor syncExecutor;

    @Resource
    private UserRepository userRepository;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @PostConstruct
    private void register() {
        syncExecutor.register(this);
    }

    @Override
    public Boolean watch(CanalMsgContent msg) {
        if (CommonConstant.QuanHuDb.DB_NAME.equals(msg.getDbName())
                && (CommonConstant.QuanHuDb.TABLE_USER.equals(msg.getTableName()) || (CommonConstant.QuanHuDb.TABLE_USER_TAG.equals(msg.getTableName())))) {
            return true;
        }
        return false;
    }

    @Override
    public void handler(CanalMsgContent msg) {
//		boolean exists=elasticsearchTemplate.indexExists(UserInfo.class);
//		if(!exists){
//			elasticsearchTemplate.createIndex(UserInfo.class);
//		}

        if (CommonConstant.QuanHuDb.DB_NAME.equals(msg.getDbName())
                && CommonConstant.QuanHuDb.TABLE_USER.equals(msg.getTableName())) {
            doUserBaseInfo(msg);
        }
        if (CommonConstant.QuanHuDb.DB_NAME.equals(msg.getDbName())
                && CommonConstant.QuanHuDb.TABLE_USER_TAG.equals(msg.getTableName())) {
            logger.info("user tag table get change");
            doUserTagInfo(msg);
        }


    }

    private void doUserTagInfo(CanalMsgContent msg) {
        TagInfo tagInfoBefore = CanalEntityParser.parse(msg.getDataBefore(), TagInfo.class);
        TagInfo tagInfoAfter = CanalEntityParser.parse(msg.getDataAfter(), TagInfo.class);

        if (CommonConstant.EventType.OPT_UPDATE.equals(msg.getEventType())) {
            Optional<UserInfo> uinfo = userRepository.findById(tagInfoBefore.getUserId());
            if (uinfo.isPresent()) {
                UserInfo userInfo = uinfo.get();
                UserTagInfo userTagInfo = userInfo.getUserTagInfo();//CanalEntityParser.parse(userInfo.getUserTagInfo().getUserTagInfo(), msg.getDataAfter(), TagInfo.class);
                if (userTagInfo != null) {
                    updateUserTagInfo(userTagInfo, tagInfoAfter);
                }
                userRepository.save(userInfo);
            }
        } else if (CommonConstant.EventType.OPT_DELETE.equals(msg.getEventType())) {
            //删除标签
            Optional<UserInfo> uinfo = userRepository.findById(tagInfoBefore.getUserId());
            if (uinfo.isPresent()) {
                UserInfo userInfo = uinfo.get();
                UserTagInfo userTagInfo = userInfo.getUserTagInfo();
                if (userTagInfo != null) {
                    deleteUserTagInfo(userTagInfo, tagInfoBefore);
                }
                userRepository.save(userInfo);
            }

        } else if (CommonConstant.EventType.OPT_INSERT.equals(msg.getEventType())) {
            // 新增标签
            Optional<UserInfo> uinfo = userRepository.findById(tagInfoAfter.getUserId());
            logger.info("user tag tables insert ops");
            if (uinfo.isPresent()) {
                UserInfo userInfo = uinfo.get();
                addUserTagInfo(userInfo, tagInfoAfter);
                userRepository.save(userInfo);
            }
        }

    }

    private void addUserTagInfo(UserInfo userInfo, TagInfo tagInfo) {
        UserTagInfo userTagInfo = userInfo.getUserTagInfo();
        if (userTagInfo == null) {
            UserTagInfo newUserTagInfo = new UserTagInfo();
            newUserTagInfo.setUserTagInfoList(Lists.newArrayList(tagInfo));
            userInfo.setUserTagInfo(newUserTagInfo);
        } else {
            userTagInfo.getUserTagInfoList().add(tagInfo);
        }
    }

    private void deleteUserTagInfo(UserTagInfo userTagInfo, TagInfo tagInfoBefore) {
        if (CollectionUtils.isNotEmpty(userTagInfo.getUserTagInfoList()) && tagInfoBefore != null) {
            List<TagInfo> after = Lists.newArrayList();
            for (TagInfo tagInfo : userTagInfo.getUserTagInfoList()) {
                if (!tagInfoBefore.getUserId().equals(tagInfo.getUserId())) {
                    after.add(tagInfo);
                }
            }
            userTagInfo.setUserTagInfoList(after);
        }
    }

    private void updateUserTagInfo(UserTagInfo userTagInfo, TagInfo tagInfoAfter) {
        if (CollectionUtils.isNotEmpty(userTagInfo.getUserTagInfoList()) && tagInfoAfter != null) {
            for (TagInfo tagInfo : userTagInfo.getUserTagInfoList()) {
                if (tagInfoAfter.getUserId().equals(tagInfo.getUserId())) {
                    tagInfo.setTagId(tagInfoAfter.getTagId());
                    tagInfo.setTagType(tagInfoAfter.getTagType());
                }
            }
        }
    }

    private void doUserBaseInfo(CanalMsgContent msg) {
        UserBaseInfo uinfoBefore = CanalEntityParser.parse(msg.getDataBefore(), UserBaseInfo.class);
        UserBaseInfo uinfoAfter = CanalEntityParser.parse(msg.getDataAfter(), UserBaseInfo.class);
        if (CommonConstant.EventType.OPT_UPDATE.equals(msg.getEventType())) {
            Optional<UserInfo> uinfo = userRepository.findById(uinfoBefore.getUserId());
            if (uinfo.isPresent()) {
                UserInfo userInfo = uinfo.get();
                UserBaseInfo userBaseInfo = CanalEntityParser.parse(userInfo.getUserBaseInfo(), msg.getDataAfter(), UserBaseInfo.class);
                userInfo.setUserBaseInfo(userBaseInfo);
                userRepository.save(userInfo);
            } else {
                UserInfo userInfo = new UserInfo();
                userInfo.setUserBaseInfo(uinfoAfter);
                userInfo.setUserId(uinfoAfter.getUserId());
                userRepository.save(userInfo);
            }
        } else if (CommonConstant.EventType.OPT_DELETE.equals(msg.getEventType())) {
            userRepository.deleteById(uinfoBefore.getUserId());
        } else if (CommonConstant.EventType.OPT_INSERT.equals(msg.getEventType())) {
            // 先执行了update则不执行insert
            Optional<UserInfo> uinfo = userRepository.findById(uinfoAfter.getUserId());
            if (!uinfo.isPresent()) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUserBaseInfo(uinfoAfter);
                userInfo.setUserId(uinfoAfter.getUserId());
                userRepository.save(userInfo);
            }
        }
    }

}
