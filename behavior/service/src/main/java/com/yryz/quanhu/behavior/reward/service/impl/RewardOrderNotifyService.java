package com.yryz.quanhu.behavior.reward.service.impl;

import com.alibaba.dubbo.common.utils.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.message.MessageConstant;
import com.yryz.common.message.MessageViewCode;
import com.yryz.common.message.MessageVo;
import com.yryz.common.message.SystemBody;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.MessageUtils;
import com.yryz.quanhu.behavior.gift.api.GiftInfoApi;
import com.yryz.quanhu.behavior.gift.entity.GiftInfo;
import com.yryz.quanhu.behavior.reward.constants.RewardConstants;
import com.yryz.quanhu.behavior.reward.entity.RewardCount;
import com.yryz.quanhu.behavior.reward.entity.RewardInfo;
import com.yryz.quanhu.behavior.reward.service.RewardCountService;
import com.yryz.quanhu.behavior.reward.service.RewardInfoService;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.quanhu.message.push.api.PushAPI;
import com.yryz.quanhu.message.push.entity.PushReqVo;
import com.yryz.quanhu.order.sdk.IOrderNotifyService;
import com.yryz.quanhu.order.sdk.constant.BranchFeesEnum;
import com.yryz.quanhu.order.sdk.dto.OutputOrder;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionAnswerVo;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import com.yryz.quanhu.resource.topic.vo.TopicPostVo;
import com.yryz.quanhu.resource.vo.ResourceVo;
import com.yryz.quanhu.score.enums.EventEnum;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.vo.EventInfo;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangheng
 * @Description: 打赏 订单回调
 * @date 2018年1月28日 下午10:48:42
 */
@Service
public class RewardOrderNotifyService implements IOrderNotifyService {

    private Logger logger = LoggerFactory.getLogger(RewardOrderNotifyService.class);

    //消息执行线程池
    private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Autowired
    private RewardInfoService rewardInfoService;

    @Autowired
    private RewardCountService rewardCountService;

    @Reference(check = false, lazy = true, timeout = 1000)
    private ResourceApi resourceApi;

    @Reference
    private MessageAPI messageAPI;

    @Reference
    private PushAPI pushAPI;

    @Reference
    private CoterieApi coterieApi;

    @Reference
    private GiftInfoApi giftInfoApi;

    @Reference
    private UserApi userApi;

    @Reference
    private EventAPI eventAPI;

    @Override
    public String getModuleEnum() {
        return BranchFeesEnum.REWARD.toString();
    }

    @Override
    public void notify(OutputOrder outputOrder) {
        RewardInfo info = JsonUtils.fromJson(outputOrder.getBizContent(), RewardInfo.class);
        logger.debug("资源打赏 订单回调，RewardInfo==>>" + outputOrder.getBizContent());
        Assert.notNull(info, "回调订单 RewardInfo is null ！");

        // 更新 打赏记录
        RewardInfo upInfo = new RewardInfo();
        // 受赏 金额【存入 分费后的金额】
        Long rewardedPrice = info.getGiftNum() * info.getGiftPrice() * BranchFeesEnum.REWARD.getFee().get(1).getFee() / 100L;
        upInfo.setRewardPrice(rewardedPrice);
        upInfo.setRewardStatus(RewardConstants.reward_status_pay_success);

        rewardInfoService.updateByKid(upInfo);

        // 更新打赏者 统计
        RewardCount uCount = new RewardCount();
        uCount.setTargetId(info.getCreateUserId());
        uCount.setTargetType(RewardConstants.target_type_user);
        uCount.setTotalRewardAmount(info.getGiftNum() * info.getGiftPrice());
        uCount.setTotalRewardCount(1);
        rewardCountService.addCountByTargetId(uCount);

        // 更新被打赏者 统计
        RewardCount uBeCount = new RewardCount();
        uBeCount.setTargetId(info.getToUserId());
        uBeCount.setTargetType(RewardConstants.target_type_user);
        uBeCount.setTotalRewardedAmount(rewardedPrice);
        uBeCount.setTotalRewardedCount(1);
        rewardCountService.addCountByTargetId(uCount);

        // 更新资源被打赏 统计
        RewardCount rCount = new RewardCount();
        rCount.setTargetId(info.getResourceId());
        rCount.setTargetType(RewardConstants.target_type_resource);
        rCount.setTotalRewardedAmount(info.getGiftNum() * info.getGiftPrice());
        rCount.setTotalRewardedCount(1);
        rewardCountService.addCountByTargetId(uCount);

        // 给打赏者、被打赏者 发送消息
        sendMessage(info, rewardedPrice);

        //发送打赏积分事件
        sendEvent(info);
    }

    /**
     * 给打赏者、被打赏者 发送消息
     *
     * @param info
     * @param rewardedPrice
     */
    private void sendMessage(RewardInfo info, Long rewardedPrice) {
        try {
            //查询资源信息的title和img
            ResourceVo resourceVo = ResponseUtils.getResponseData(resourceApi.getResourcesById(String.valueOf(info.getResourceId())));
            String bodyTitle = "";
            String bodyImg = "";
            if (ModuleContants.RELEASE.equals(resourceVo.getModuleEnum())) {
                ReleaseInfo releaseInfo = JSON.parseObject(resourceVo.getExtJson(), ReleaseInfo.class);
                if (null != releaseInfo) {
                    bodyTitle = releaseInfo.getTitle();
                    String imgUrl = releaseInfo.getImgUrl();
                    if (StringUtils.isNotBlank(imgUrl)) {
                        bodyImg = Splitter.on(",").omitEmptyStrings().limit(1).splitToList(imgUrl).get(0);
                    }
                }
            } else if (ModuleContants.TOPIC_POST.equals(resourceVo.getModuleEnum())) {
                TopicPostVo topicPostVo = JSON.parseObject(resourceVo.getExtJson(), TopicPostVo.class);
                if (null != topicPostVo) {
                    if (StringUtils.isNotBlank(topicPostVo.getContent())) {
                        bodyTitle = topicPostVo.getContent().length() > 20 ?
                                topicPostVo.getContent().substring(0, 20) : topicPostVo.getContent();
                    }
                    String imgUrl = topicPostVo.getImgUrl();
                    if (StringUtils.isNotBlank(imgUrl)) {
                        bodyImg = Splitter.on(",").omitEmptyStrings().limit(1).splitToList(imgUrl).get(0);
                    }
                }
            } else if (ModuleContants.ANSWER.equals(resourceVo.getModuleEnum())) {
                QuestionAnswerVo questionAnswerVo = JSON.parseObject(resourceVo.getExtJson(), QuestionAnswerVo.class);
                if (null != questionAnswerVo) {
                    if (null != questionAnswerVo.getQuestion() && StringUtils.isNotBlank(questionAnswerVo.getQuestion().getContent())) {
                        bodyTitle = questionAnswerVo.getQuestion().getContent().length() > 20 ?
                                questionAnswerVo.getQuestion().getContent().substring(0, 20) :
                                questionAnswerVo.getQuestion().getContent();
                    }
                }
            }
            // 查询私圈信息
            Long coterieId = info.getCoterieId();
            CoterieInfo coterieInfo = ResponseUtils.getResponseData(coterieApi.queryCoterieInfo(coterieId));
            //查询礼物信息
            Long giftId = info.getGiftId();
            GiftInfo giftInfo = ResponseUtils.getResponseData(giftInfoApi.selectByKid(giftId));
            // 构建消息体
            SystemBody systemBody = new SystemBody();
            systemBody.setBodyImg(bodyImg);
            systemBody.setBodyTitle(bodyTitle);
            systemBody.setCoterieId(String.valueOf(coterieId));
            systemBody.setCoterieName(coterieInfo.getName());

            //给打赏者发持久化消息
            MessageVo messageVo = MessageUtils.buildMessage(MessageConstant.REWARD_ACCOUNT,
                    String.valueOf(info.getCreateUserId()), null, systemBody);
            messageVo.setContent(String.format(MessageConstant.REWARD_ACCOUNT.getContent(), giftInfo.getGiftName(),
                    formatMoney(info.getGiftNum() * info.getGiftPrice())));
            if (StringUtils.isBlank(bodyTitle)) {
                messageVo.setViewCode(MessageViewCode.ORDER_MESSAGE);
            }
            commitMessage(messageVo, false);
            logger.info("send reward message, data:{}", JsonUtils.toFastJson(messageVo));
            //给打赏者推送极光消息
            PushReqVo reqVo = new PushReqVo();
            reqVo.setCustIds(Lists.newArrayList(String.valueOf(info.getCreateUserId())));
            reqVo.setMsg(JsonUtils.toFastJson(messageVo));
            reqVo.setNotification(MessageConstant.REWARD_ACCOUNT.getTitle());
            reqVo.setNofiticationBody(String.format("打赏成功，支付%s悠然币。",
                    formatMoney(info.getGiftNum() * info.getGiftPrice())));
            reqVo.setPushType(PushReqVo.CommonPushType.BY_ALIAS);
            pushAPI.commonSendAlias(reqVo);

            //给被打赏者发持久化消息
            messageVo = MessageUtils.buildMessage(MessageConstant.REWARD_INTEGRAL,
                    String.valueOf(info.getToUserId()), null, systemBody);
            UserSimpleVO userSimpleVO = ResponseUtils.getResponseData(userApi.getUserSimple(info.getCreateUserId()));
            messageVo.setContent(String.format(MessageConstant.REWARD_INTEGRAL.getContent(),
                    userSimpleVO.getUserNickName(), giftInfo.getGiftName(), formatMoney(rewardedPrice)));
            if (StringUtils.isBlank(bodyTitle)) {
                messageVo.setViewCode(MessageViewCode.ORDER_MESSAGE);
            }
            commitMessage(messageVo, false);
            logger.info("send rewarded message, data:{}", JsonUtils.toFastJson(messageVo));
            //给被打赏者推送极光消息
            reqVo = new PushReqVo();
            reqVo.setCustIds(Lists.newArrayList(String.valueOf(info.getToUserId())));
            reqVo.setMsg(JsonUtils.toFastJson(messageVo));
            reqVo.setNotification(MessageConstant.REWARD_INTEGRAL.getTitle());
            reqVo.setNofiticationBody(String.format("%s打赏了一个%s，您获得%s奖励。",
                    userSimpleVO.getUserNickName(), giftInfo.getGiftName(), formatMoney(rewardedPrice)));
            reqVo.setPushType(PushReqVo.CommonPushType.BY_ALIAS);
            pushAPI.commonSendAlias(reqVo);
        } catch (Exception e) {
            logger.error("打赏发送消息失败", e);
        }
    }

    /**
     * 发送打赏积分事件
     *
     * @param rewardInfo
     */
    private void sendEvent(RewardInfo rewardInfo) {
        EventInfo eventInfo = new EventInfo();
        try {
            eventInfo.setCoterieId(String.valueOf(rewardInfo.getCoterieId()));
            eventInfo.setAmount((double) (rewardInfo.getGiftNum() * rewardInfo.getGiftPrice()));
            eventInfo.setUserId(String.valueOf(rewardInfo.getCreateUserId()));
            eventInfo.setEventCode(EventEnum.COLLECTION.getCode());
            eventInfo.setEventNum(1);
            eventInfo.setOwnerId(String.valueOf(rewardInfo.getCreateUserId()));
            eventInfo.setResourceId(String.valueOf(rewardInfo.getResourceId()));
            eventInfo.setCreateTime(DateUtils.getString(new Date()));
            eventAPI.commit(eventInfo);
            logger.info("reward event commit success eventInfo:{}", JSON.toJSONString(eventInfo));
        } catch (Exception e) {
            logger.info("reward event commit error rewardInfo:{}", JSON.toJSONString(eventInfo));
            logger.error("[reward event commit failure]", e);
        }
    }

    /**
     * 格式化金额
     *
     * @param amount
     * @return
     */
    private String formatMoney(long amount) {
        return new BigDecimal(amount).divide(new BigDecimal(100), 2, BigDecimal.ROUND_FLOOR).toPlainString();
    }

    /**
     * 发送消息
     *
     * @param messageVo
     * @param flag
     */
    private void commitMessage(MessageVo messageVo, boolean flag) {
        executorService.execute(() -> {
            try {
                messageAPI.sendMessage(messageVo, flag);
            } catch (Exception e) {
                logger.error("[commit reward message failure]", e);
            }
        });
    }
}
