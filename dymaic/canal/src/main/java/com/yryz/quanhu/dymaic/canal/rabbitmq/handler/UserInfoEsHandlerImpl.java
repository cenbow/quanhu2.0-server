package com.yryz.quanhu.dymaic.canal.rabbitmq.handler;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.yryz.common.utils.BeanUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.dymaic.canal.entity.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import com.yryz.common.entity.CanalMsgContent;
import com.yryz.common.utils.CanalEntityParser;
import com.yryz.quanhu.dymaic.canal.constant.CommonConstant;
import com.yryz.quanhu.dymaic.canal.dao.UserRepository;

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
                && (CommonConstant.QuanHuDb.TABLE_USER.equals(msg.getTableName())
                || (CommonConstant.QuanHuDb.TABLE_USER_TAG.equals(msg.getTableName()))
                || (CommonConstant.QuanHuDb.TABLE_USER_STAR_AUTH.equals(msg.getTableName())))) {
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
            logger.info("user tag table get change: {}", GsonUtils.parseJson(msg));
            doUserTagInfo(msg);
        }

        if (CommonConstant.QuanHuDb.DB_NAME.equals(msg.getDbName())
                && CommonConstant.QuanHuDb.TABLE_USER_STAR_AUTH.equals(msg.getTableName())) {
            logger.info("star table get change: {}", GsonUtils.parseJson(msg));
            doStarInfo(msg);
        }


    }

    private void doStarInfo(CanalMsgContent msg) {
        UserStarInfo starInfoBefore = CanalEntityParser.parse(msg.getDataBefore(), UserStarInfo.class);
        UserStarInfo starInfoAfter = CanalEntityParser.parse(msg.getDataAfter(), UserStarInfo.class);

        if (CommonConstant.EventType.OPT_UPDATE.equals(msg.getEventType())) {
            Optional<UserInfo> uinfo = userRepository.findById(starInfoBefore.getUserId());
            if (uinfo.isPresent()) {
                UserInfo userInfo = uinfo.get();
                //UserStarInfo userStarInfo = userInfo.getUserStarInfo();
                //if (userStarInfo != null && starInfoAfter.getKid().equals(userStarInfo.getKid())) {
                userInfo.setUserStarInfo(starInfoAfter);
                userRepository.save(userInfo);
                //}
            }
        } else if (CommonConstant.EventType.OPT_DELETE.equals(msg.getEventType())) {
            //删除
            Optional<UserInfo> uinfo = userRepository.findById(starInfoBefore.getUserId());
            if (uinfo.isPresent()) {
                UserInfo userInfo = uinfo.get();
                UserStarInfo userStarInfo = userInfo.getUserStarInfo();
                if (userStarInfo != null && starInfoBefore.getKid().equals(userStarInfo.getKid())) {
                    userInfo.setUserStarInfo(null);
                    userRepository.save(userInfo);
                }
            }

        } else if (CommonConstant.EventType.OPT_INSERT.equals(msg.getEventType())) {
            // 新增
            Optional<UserInfo> uinfo = userRepository.findById(starInfoAfter.getUserId());
            if (uinfo.isPresent()) {
                UserInfo userInfo = uinfo.get();
                if (userInfo != null) {
                    userInfo.setUserStarInfo(starInfoAfter);
                    userRepository.save(userInfo);
                }
            }
        }
    }


    private void doUserTagInfo(CanalMsgContent msg) {
        TagInfo tagInfoBefore = CanalEntityParser.parse(msg.getDataBefore(), TagInfo.class);
        TagInfo tagInfoAfter = CanalEntityParser.parse(msg.getDataAfter(), TagInfo.class);

        if (CommonConstant.EventType.OPT_UPDATE.equals(msg.getEventType())) {
            Optional<UserInfo> uinfo = userRepository.findById(tagInfoBefore.getUserId());
            if (uinfo.isPresent()) {
                UserInfo userInfo = uinfo.get();
                UserTagInfo userTagInfo = userInfo.getUserTagInfo();
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
        if (userTagInfo == null || CollectionUtils.isEmpty(userTagInfo.getUserTagInfoList())) {
            UserTagInfo newUserTagInfo = new UserTagInfo();
            newUserTagInfo.setUserTagInfoList(Lists.newArrayList(tagInfo));
            userInfo.setUserTagInfo(newUserTagInfo);
        } else {
            userTagInfo.getUserTagInfoList().add(tagInfo);
        }
    }

    private void deleteUserTagInfo(UserTagInfo userTagInfo, TagInfo before) {
        if (CollectionUtils.isNotEmpty(userTagInfo.getUserTagInfoList()) && before != null) {
            List<TagInfo> after = Lists.newArrayList();
            for (TagInfo tagInfo : userTagInfo.getUserTagInfoList()) {
                if (!(
                        before.getKid().equals(tagInfo.getKid())
                        //before.getTagType().equals(tagInfo.getTagType()) && before.getTagId().equals(tagInfo.getTagId())
                )) {
                    after.add(tagInfo);
                }
            }
            userTagInfo.setUserTagInfoList(after);
        }
    }

    private void updateUserTagInfo(UserTagInfo userTagInfo, TagInfo after) {
        if (CollectionUtils.isNotEmpty(userTagInfo.getUserTagInfoList()) && after != null) {
            for (TagInfo tagInfo : userTagInfo.getUserTagInfoList()) {
                if (after.getKid().equals(tagInfo.getKid())) {
                    tagInfo.setTagType(after.getTagType());
                    tagInfo.setTagId(after.getTagId());
                    tagInfo.setDelFlag(after.getDelFlag());
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
