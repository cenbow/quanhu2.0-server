package com.yryz.quanhu.resource.coterie.release.info.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yryz.common.message.MessageConstant;
import com.yryz.common.message.MessageVo;
import com.yryz.common.message.SystemBody;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.MessageUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.quanhu.order.sdk.IOrderNotifyService;
import com.yryz.quanhu.order.sdk.constant.BranchFeesEnum;
import com.yryz.quanhu.order.sdk.constant.FeeDetail;
import com.yryz.quanhu.order.sdk.dto.OutputOrder;
import com.yryz.quanhu.resource.release.buyrecord.constants.BuyTypeEnum;
import com.yryz.quanhu.resource.release.buyrecord.entity.ReleaseBuyRecord;
import com.yryz.quanhu.resource.release.buyrecord.service.ReleaseBuyRecordService;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
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
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * @author wangheng
 * @Description: 付费阅读文章，订单回调
 * @date 2018年1月24日 下午9:06:17
 */
@Service
public class CoterieReleaseOrderNotifyService implements IOrderNotifyService {

    private Logger logger = LoggerFactory.getLogger(CoterieReleaseOrderNotifyService.class);

    @Reference(lazy = true, check = false, timeout = 10000)
    private MessageAPI messageAPI;

    @Reference(lazy = true, check = false, timeout = 10000)
    private CoterieApi coterieApi;

    @Reference(lazy = true, check = false, timeout = 10000)
    private UserApi userApi;

    @Reference(lazy = true, check = false, timeout = 10000)
    private EventAPI eventAPI;

    @Autowired
    private ReleaseBuyRecordService releaseBuyRecordService;

    @Override
    public String getModuleEnum() {
        return BranchFeesEnum.READ.toString();
    }

    @Override
    public void notify(OutputOrder outputOrder) {
        logger.info("付费阅读订单回调，outputOrder==>>" + JsonUtils.toFastJson(outputOrder));
        Assert.notNull(outputOrder, "outputOrder is null !");
        Assert.notNull(outputOrder.getBizContent(), "bizContent is null !");
        Assert.notNull(outputOrder.getCoterieId(), "coterieId is null !");
        ReleaseInfo releaseInfo = JSON.parseObject(outputOrder.getBizContent(), ReleaseInfo.class);

        Assert.notNull(releaseInfo, "fromJson releaseInfo is null !");

        CoterieInfo coterieInfo = ResponseUtils
                .getResponseData(coterieApi.queryCoterieInfo(releaseInfo.getCoterieId()));
        Assert.notNull(coterieInfo, "私圈信息为NULL，coterieId : " + releaseInfo.getCoterieId());
        // 提交资源购买记录
        ReleaseBuyRecord releaseBuyRecord = new ReleaseBuyRecord();
        releaseBuyRecord.setOrderId(outputOrder.getOrderId());
        releaseBuyRecord.setCoterieId(outputOrder.getCoterieId() == null ? 0 : outputOrder.getCoterieId());
        releaseBuyRecord.setResourceId(outputOrder.getResourceId());
        releaseBuyRecord.setAmount(outputOrder.getCost());
        releaseBuyRecord.setPayType(outputOrder.getPayType());
        releaseBuyRecord.setBuyType(BuyTypeEnum.RELEASE.getBuyType());
        releaseBuyRecord.setRemark(BuyTypeEnum.RELEASE.getRemark());
        releaseBuyRecord.setCreateUserId(outputOrder.getCreateUserId());
        releaseBuyRecord.setLastUpdateUserId(outputOrder.getCreateUserId());
        releaseBuyRecordService.insert(releaseBuyRecord);

        // 文章阅读者，文章作者
        UserSimpleVO sponsorUser = null;
        try {
            sponsorUser = ResponseUtils.getResponseData(userApi.getUserSimple(outputOrder.getCreateUserId()));
        } catch (Exception e) {
            logger.error("notify ==>> , 付费阅读成功，获取用户信息 异常!", e);
            return;
        }

        // 构建消息体
        SystemBody systemBody = new SystemBody();
        try {
            if (StringUtils.isNotBlank(releaseInfo.getImgUrl())) {
                systemBody.setBodyImg(StringUtils.split(releaseInfo.getImgUrl(), ",")[0]);
            }
            systemBody.setBodyTitle(releaseInfo.getTitle());
            systemBody.setCoterieId(String.valueOf(releaseInfo.getCoterieId()));
            systemBody.setCoterieName(coterieInfo.getName());
        } catch (Exception e) {
            logger.error("notify ==>> , 付费阅读成功，构建 SystemBody 异常!", e);
            return;
        }

        try {
            // 付费阅读成功 给文章作者发送奖励消息
            this.payMessageToAuthor(releaseInfo, sponsorUser, systemBody);
        } catch (Exception e) {
            logger.error("payMessageToAuthor ==>> , 付费阅读成功 给文章作者发送奖励消息失败 !", e);
        }

        try {
            // 付费阅读成功 给阅读者发送扣费消息
            MessageVo messageVo = MessageUtils.buildMessage(MessageConstant.CONTENT_BUY_REMINDERS,
                    String.valueOf(sponsorUser.getUserId()), coterieInfo.getName(), systemBody);
            messageVo.setContent(messageVo.getContent().replaceAll("\\{money\\}",
                    String.valueOf(releaseInfo.getContentPrice() / 100)));
            messageAPI.sendMessage(messageVo, true);
        } catch (Exception e) {
            logger.error("payMessageToSponsor ==>> , 付费阅读成功 给文章阅读者发送消息失败 !", e);
        }

        // 积分、事件 触发[资源付费    每次操作触发]
        try {
            EventInfo event = new EventInfo();
            if (null != releaseInfo.getCoterieId() && 0L != releaseInfo.getCoterieId()) {
                event.setCoterieId(String.valueOf(releaseInfo.getCoterieId()));
            }
            event.setCreateTime(DateUtils.formatDateTime(Calendar.getInstance().getTime()));
            event.setEventCode(EventEnum.RESOURCE_PAY.getCode());
            event.setOwnerId(String.valueOf(releaseInfo.getCreateUserId()));
            event.setResourceId(String.valueOf(releaseInfo.getKid()));
            event.setUserId(String.valueOf(outputOrder.getCreateUserId()));
            eventAPI.commit(event);

        } catch (Exception e) {
            logger.error("发布资源文章，对接 积分事件异常！", e);
        }
    }

    /**
     * 付款成功后给作者推送 奖励消息
     */
    private void payMessageToAuthor(ReleaseInfo info, UserSimpleVO sponsorUser, SystemBody systemBody) {
        logger.debug("付款成功后给作者推送 奖励消息，付费资源ID:" + info.getKid());

        MessageVo messageVo = MessageUtils.buildMessage(MessageConstant.CONTENT_READ_REMINDERS,
                String.valueOf(info.getCreateUserId()), sponsorUser.getUserNickName(), systemBody);

        // 获取提成 规则
        FeeDetail fee = BranchFeesEnum.READ.getFee().get(1);
        DecimalFormat df = new DecimalFormat("#0.00");
        String money;
        if (null == fee) {
            money = df.format(new BigDecimal(info.getContentPrice()).divide(new BigDecimal(100)));
        } else {
            money = df.format(new BigDecimal(info.getContentPrice()).divide(new BigDecimal(100))
                    .multiply(new BigDecimal(fee.getFee()).divide(new BigDecimal(100))));
        }
        messageVo.setContent(messageVo.getContent().replaceAll("\\{money\\}", money));
        messageAPI.sendMessage(messageVo, true);
    }
}
