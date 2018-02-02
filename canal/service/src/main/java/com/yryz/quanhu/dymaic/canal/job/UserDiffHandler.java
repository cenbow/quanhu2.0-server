package com.yryz.quanhu.dymaic.canal.job;

import java.util.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yryz.common.utils.BeanUtils;
import com.yryz.quanhu.dymaic.canal.entity.*;
import com.yryz.quanhu.score.service.EventAcountAPI;
import com.yryz.quanhu.score.service.EventAcountApiService;
import com.yryz.quanhu.score.vo.EventAcount;
import com.yryz.quanhu.user.dto.StarAuthInfo;
import com.yryz.quanhu.user.service.UserOperateApi;
import com.yryz.quanhu.user.service.UserStarApi;
import com.yryz.quanhu.user.service.UserTagApi;
import com.yryz.quanhu.user.vo.UserRegLogVO;
import com.yryz.quanhu.user.vo.UserTagVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.dymaic.canal.dao.UserRepository;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;

@Component
public class UserDiffHandler implements DiffHandler {
    private static final Log logger = LogFactory.getLog(UserDiffHandler.class);
    @Resource
    private UserRepository userRepository;

    @Reference(check = false)
    private UserApi userApi;

    @Reference(check = false)
    private UserStarApi userStarApi;

    @Reference(check = false)
    private UserTagApi userTagApi;

    @Reference(check = false)
    private EventAcountApiService acountApiService;
    @Reference(check = false)
    private UserOperateApi userOperateApi;

    @Resource
    private DiffExecutor diffExecutor;

    private static final Function<Long, String> LONG_TO_STRING_FUNCTION = new Function<Long, String>() {
        @Override
        public String apply(Long input) {
            return input.toString();
        }
    };

    @PostConstruct
    public void register() {
        diffExecutor.register(this);
    }

    @Override
    public void handler() {
        try {
            String yesterday = DateUtils.getYesterDay();
            Response<List<Long>> res = userApi.getUserIdByCreateDate(yesterday + " 00:00:00", yesterday + " 23:59:59");
            if (!res.success()) {
                logger.error("diff user error:" + res.getErrorMsg());
                return;
            }

            List<Long> diffList = new ArrayList<>();
            List<Long> userIdList = res.getData();
            for (int i = 0; i < userIdList.size(); i++) {
                long userId = userIdList.get(i);
                Optional<UserInfo> info = userRepository.findById(userId);
                if (!info.isPresent()) {
                    diffList.add(userId);
                }
            }

            if (!diffList.isEmpty()) {
                Response<List<UserBaseInfoVO>> resList = userApi.getAllByUserIds(diffList);
                Set<String> stringIds = Sets.newHashSet(FluentIterable.from(diffList).transform(LONG_TO_STRING_FUNCTION).toSet());
                Response<Map<String, StarAuthInfo>> starResponse = userStarApi.get(stringIds);
                Response<Map<Long, List<UserTagVO>>> userTagInfoResponse = userTagApi.getUserTags(diffList);
                Response<Map<Long, EventAcount>> eventAcountResponse = acountApiService.getEventAcountBatch(Sets.newHashSet(diffList));
                Response<Map<Long, UserRegLogVO>> regLogResponse = userOperateApi.listByUserId(diffList);
                if (resList.success()) {
                    List<UserBaseInfoVO> volist = resList.getData();
                    List<UserInfo> list = new ArrayList<>();
                    for (int i = 0; i < volist.size(); i++) {
                        try {
                            UserBaseInfo baseInfo = GsonUtils.parseObj(volist.get(i), UserBaseInfo.class);
                            UserInfo userInfo = new UserInfo();
                            userInfo.setUserId(baseInfo.getUserId());
                            //用户基础数据
                            userInfo.setUserBaseInfo(baseInfo);
                            //达人数据
                            setStartInfo(userInfo, starResponse);
                            //标签数据
                            setTagInfo(userInfo, userTagInfoResponse);
                            //积分数据
                            setEventInfo(userInfo, eventAcountResponse);
                            //注册记录数据
                            setRegLogInfo(userInfo, regLogResponse);

                            list.add(userInfo);
                        } catch (Exception e) {
                            logger.error("process userInfo error", e);
                        }
                    }
                    userRepository.saveAll(list);
                }
            }

        } catch (Exception e) {
            logger.error("UserDiffHandler handle error", e);
        }
    }

    private void setRegLogInfo(UserInfo userInfo, Response<Map<Long, UserRegLogVO>> regLogResponse) {
        if (regLogResponse.success() && MapUtils.isNotEmpty(regLogResponse.getData())) {
            Map<Long, UserRegLogVO> regLogVOMap = regLogResponse.getData();
            UserRegLogVO regLogVO = regLogVOMap.get(userInfo.getUserId());
            if (regLogVO != null) {
                UserRegLog userRegLog = new UserRegLog();
                BeanUtils.copyProperties(userRegLog, regLogVO);
                userInfo.setUserRegLog(userRegLog);
            }
        }
    }

    private void setEventInfo(UserInfo userInfo, Response<Map<Long, EventAcount>> eventAcountResponse) {
        if (eventAcountResponse.success() && MapUtils.isNotEmpty(eventAcountResponse.getData())) {
            Map<Long, EventAcount> eventAcountMap = eventAcountResponse.getData();
            EventAcount eventAcount = eventAcountMap.get(userInfo.getUserId());
            if (eventAcount != null) {
                EventAccountInfo eventAccountInfo = new EventAccountInfo();
                BeanUtils.copyProperties(eventAccountInfo, eventAcount);
                userInfo.setEventAccountInfo(eventAccountInfo);
            }
        }
    }

    private void setTagInfo(UserInfo userInfo, Response<Map<Long, List<UserTagVO>>> userTagInfoResponse) {
        if (userTagInfoResponse.success() && MapUtils.isNotEmpty(userTagInfoResponse.getData())) {
            Map<Long, List<UserTagVO>> listMap = userTagInfoResponse.getData();
            List<UserTagVO> userTagVOS = listMap.get(userInfo.getUserId());
            if (CollectionUtils.isNotEmpty(userTagVOS)) {
                UserTagInfo userTagInfo = new UserTagInfo();
                List<TagInfo> tagInfoList = Lists.newArrayList();
                for (UserTagVO userTagVO : userTagVOS) {
                    TagInfo tagInfo = new TagInfo();
                    BeanUtils.copyProperties(tagInfo, userTagVO);
                    tagInfoList.add(tagInfo);
                }
                userTagInfo.setUserTagInfoList(tagInfoList);

                userInfo.setUserTagInfo(userTagInfo);
            }
        }
    }

    private void setStartInfo(UserInfo userInfo, Response<Map<String, StarAuthInfo>> starResponse) {
        if (starResponse.success() && MapUtils.isNotEmpty(starResponse.getData())) {
            Map<String, StarAuthInfo> authInfoMap = starResponse.getData();
            StarAuthInfo starAuthInfo = authInfoMap.get(userInfo.getUserId().toString());
            if (starAuthInfo != null) {
                UserStarInfo userStarInfo = new UserStarInfo();
                BeanUtils.copyProperties(userStarInfo, starAuthInfo);
                userInfo.setUserStarInfo(userStarInfo);
            }
        }
    }

}
