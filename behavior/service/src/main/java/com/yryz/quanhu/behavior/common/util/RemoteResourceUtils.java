package com.yryz.quanhu.behavior.common.util;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import com.yryz.common.constant.ModuleContants;
import com.yryz.quanhu.behavior.common.vo.RemoteResource;
import com.yryz.quanhu.other.activity.entity.ActivityVoteDetail;
import com.yryz.quanhu.resource.questionsAnswers.vo.AnswerVo;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionAnswerVo;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionVo;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import com.yryz.quanhu.resource.topic.vo.TopicPostVo;
import com.yryz.quanhu.resource.topic.vo.TopicVo;
import com.yryz.quanhu.resource.vo.ResourceVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/1 16:38
 * Created by lifan
 */
public class RemoteResourceUtils {

    private static final Logger logger = LoggerFactory.getLogger(RemoteResourceUtils.class);

    public static RemoteResource convert(ResourceVo resourceVo) {
        if (null != resourceVo) {
            RemoteResource remoteResource = new RemoteResource();
            remoteResource.setModuleEnum(resourceVo.getModuleEnum());
            remoteResource.setResourceId(resourceVo.getResourceId() == null ? null : Long.valueOf(resourceVo.getResourceId()));
            remoteResource.setCoterieId(resourceVo.getCoterieId() == null ? null : Long.valueOf(resourceVo.getCoterieId()));
            remoteResource.setDelFlag(resourceVo.getDelFlag());
            if (ModuleContants.RELEASE.equals(resourceVo.getModuleEnum())) {
                ReleaseInfo releaseInfo = JSON.parseObject(resourceVo.getExtJson(), ReleaseInfo.class);
                if (null != releaseInfo) {
                    remoteResource.setCoverPlanUrl(releaseInfo.getCoverPlanUrl());
                    remoteResource.setTitle(releaseInfo.getTitle());
                    remoteResource.setContent(releaseInfo.getContent());
                    remoteResource.setImgUrl(releaseInfo.getImgUrl());
                    if (StringUtils.isNotBlank(releaseInfo.getImgUrl())) {
                        remoteResource.setFirstImgUrl(Splitter.on(",")
                                .omitEmptyStrings().limit(1).splitToList(releaseInfo.getImgUrl()).get(0));
                    }
                    remoteResource.setAudioUrl(releaseInfo.getAudioUrl());
                    remoteResource.setVideoUrl(releaseInfo.getVideoUrl());
                    remoteResource.setVideoThumbnailUrl(releaseInfo.getVideoThumbnailUrl());
                    remoteResource.setCreateUserId(releaseInfo.getCreateUserId());
                    remoteResource.setCreateDate(releaseInfo.getCreateDate());
                }
            } else if (ModuleContants.TOPIC.equals(resourceVo.getModuleEnum())) {
                TopicVo topicVo = JSON.parseObject(resourceVo.getExtJson(), TopicVo.class);
                if(null != topicVo) {
                    remoteResource.setContent(topicVo.getContent());
                    remoteResource.setImgUrl(topicVo.getImgUrl());
                    if(StringUtils.isNotBlank(topicVo.getImgUrl())) {
                        remoteResource.setFirstImgUrl(Splitter.on(",")
                                .omitEmptyStrings().limit(1).splitToList(topicVo.getImgUrl()).get(0));
                    }
                    remoteResource.setCreateUserId(topicVo.getUser() == null ? null : topicVo.getUser().getUserId());
                    remoteResource.setCreateDate(topicVo.getCreateDate());
                }
            } else if (ModuleContants.TOPIC_POST.equals(resourceVo.getModuleEnum())) {
                TopicPostVo topicPostVo = JSON.parseObject(resourceVo.getExtJson(), TopicPostVo.class);
                if (null != topicPostVo) {
                    remoteResource.setContent(topicPostVo.getContent());
                    remoteResource.setImgUrl(topicPostVo.getImgUrl());
                    if (StringUtils.isNotBlank(topicPostVo.getImgUrl())) {
                        remoteResource.setFirstImgUrl(Splitter.on(",")
                                .omitEmptyStrings().limit(1).splitToList(topicPostVo.getImgUrl()).get(0));
                    }
                    remoteResource.setAudioUrl(topicPostVo.getAudioUrl());
                    remoteResource.setVideoUrl(topicPostVo.getVideoUrl());
                    remoteResource.setVideoThumbnailUrl(topicPostVo.getVideoThumbnailUrl());
                    remoteResource.setCreateUserId(topicPostVo.getUser() == null ? null : topicPostVo.getUser().getUserId());
                    remoteResource.setCreateDate(topicPostVo.getCreateDate());
                }
            } else if (ModuleContants.QUESTION.equals(resourceVo.getModuleEnum())) {
                QuestionVo questionVo = JSON.parseObject(resourceVo.getExtJson(), QuestionVo.class);
                if (null != questionVo) {
                    remoteResource.setTitle(questionVo.getTitle());
                    remoteResource.setContent(questionVo.getContent());
                    remoteResource.setCreateUserId(questionVo.getUser() == null ? null : questionVo.getUser().getUserId());
                    remoteResource.setCreateDate(questionVo.getCreateDate());
                }
            } else if (ModuleContants.ANSWER.equals(resourceVo.getModuleEnum())) {
                QuestionAnswerVo questionAnswerVo = JSON.parseObject(resourceVo.getExtJson(), QuestionAnswerVo.class);
                if (null != questionAnswerVo) {
                    QuestionVo questionVo = questionAnswerVo.getQuestion();
                    AnswerVo answerVo = questionAnswerVo.getAnswer();
                    if (null != questionVo) {
                        remoteResource.getQuestion().setContent(questionVo.getContent());
                        remoteResource.getQuestion().setCreateUserId(questionVo.getUser() == null ? null : questionVo.getUser().getUserId());
                        remoteResource.getQuestion().setCreateDate(questionVo.getCreateDate());
                    }
                    if (null != answerVo) {
                        remoteResource.setContent(answerVo.getContent());
                        remoteResource.setImgUrl(answerVo.getImgUrl());
                        if (StringUtils.isNotBlank(answerVo.getImgUrl())) {
                            remoteResource.setFirstImgUrl(Splitter.on(",")
                                    .omitEmptyStrings().limit(1).splitToList(answerVo.getImgUrl()).get(0));
                        }
                        remoteResource.setAudioUrl(answerVo.getAnswerAudio());
                        remoteResource.setCreateUserId(answerVo.getCreateUserId() == null
                                ? (answerVo.getUser() == null ? null : answerVo.getUser().getUserId())
                                : answerVo.getCreateUserId());
                        remoteResource.setCreateDate(answerVo.getCreateDate());
                    }
                }
            } else if (ModuleContants.ACTIVITY_WORKS_ENUM.equals(resourceVo.getModuleEnum())) {
                ActivityVoteDetail activityVoteDetail = JSON.parseObject(resourceVo.getExtJson(), ActivityVoteDetail.class);
                if (null != activityVoteDetail) {
                    remoteResource.setCoverPlanUrl(activityVoteDetail.getCoverPlan());
                    remoteResource.setContent(activityVoteDetail.getContent());
                    remoteResource.setImgUrl(activityVoteDetail.getImgUrl());
                    if (StringUtils.isNotBlank(activityVoteDetail.getImgUrl())) {
                        remoteResource.setFirstImgUrl(Splitter.on(",")
                                .omitEmptyStrings().limit(1).splitToList(activityVoteDetail.getImgUrl()).get(0));
                    }
                    remoteResource.setVideoUrl(activityVoteDetail.getVideoUrl());
                    remoteResource.setVideoThumbnailUrl(activityVoteDetail.getVideoThumbnailUrl());
                    remoteResource.setCreateUserId(activityVoteDetail.getCreateUserId());
                    remoteResource.setCreateDate(activityVoteDetail.getCreateDate());
                }
            } else {
                logger.warn("不合法的moduleEnum={}，丢弃：{}", resourceVo.getModuleEnum(), JSON.toJSONString(resourceVo));
            }
            return remoteResource;
        }
        return null;
    }
}
