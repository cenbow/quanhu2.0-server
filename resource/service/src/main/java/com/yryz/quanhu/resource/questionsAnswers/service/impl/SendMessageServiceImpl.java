package com.yryz.quanhu.resource.questionsAnswers.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.message.InteractiveBody;
import com.yryz.common.message.MessageConstant;
import com.yryz.common.message.MessageVo;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.IdGen;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.quanhu.order.sdk.constant.FeeDetail;
import com.yryz.quanhu.resource.questionsAnswers.constants.QuestionAnswerConstants;
import com.yryz.quanhu.resource.questionsAnswers.service.APIservice;
import com.yryz.quanhu.resource.questionsAnswers.service.SendMessageService;
import com.yryz.quanhu.resource.questionsAnswers.vo.MessageBusinessVo;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.UUID;

@Service
public class SendMessageServiceImpl implements SendMessageService {


    private static final Logger logger = LoggerFactory.getLogger(SendMessageServiceImpl.class);

    @Autowired
    private APIservice apIservice;

    @Reference
    private MessageAPI messageAPI;

    /**
     * 发送问答的通知消息
     *
     * @param
     * @param messageConstant
     * @return
     */
    @Override
    public Boolean sendNotify4Question(MessageBusinessVo messageBusinessVo, MessageConstant messageConstant,Boolean persistent) {
        Long kid=messageBusinessVo.getKid();
        Long tosendUserId=messageBusinessVo.getTosendUserId();
        long fromUserId=messageBusinessVo.getFromUserId();
        String title=messageBusinessVo.getTitle();
        title=StringUtils.length(title) > 20 ? title.substring(0, 19) : title;
        Byte isAnonymity=messageBusinessVo.getIsAnonymity();
        String coterieId=messageBusinessVo.getCoterieId();
        Long amount=messageBusinessVo.getAmount();
        String imgUrl=messageBusinessVo.getImgUrl();
        String moduleEnum=messageBusinessVo.getModuleEnum();

        UserSimpleVO fromUser = this.apIservice.getUser(fromUserId);
        if (null == fromUser){
            return false;
        }

        CoterieInfo coterieInfo=null;
        if(coterieId!=null){
            coterieInfo = apIservice.getCoterieinfo(Long.valueOf(coterieId));
        }

        /**
         * 组装InteractiveBody对象
         */
        InteractiveBody interactiveBody = new InteractiveBody();
        interactiveBody.setBodyTitle(title);
        interactiveBody.setBodyImg(imgUrl==null?"":imgUrl);
        interactiveBody.setCircleId(String.valueOf(coterieId));
        if(coterieInfo!=null) {
            interactiveBody.setCoterieName(coterieInfo.getName());
        }
        interactiveBody.setCustImg(QuestionAnswerConstants.anonymityType.YES.equals(isAnonymity) ? null : fromUser.getUserImg());
        interactiveBody.setCustName(QuestionAnswerConstants.anonymityType.YES.equals(isAnonymity)
                ? QuestionAnswerConstants.ANONYMOUS_USER_NAME : fromUser.getUserNickName());
        interactiveBody.setCustId(String.valueOf(fromUser.getUserId()));
        interactiveBody.setCoterieId(coterieId);

        /**
         * 组装MessageVo对象
         */
        MessageVo messageVo = new MessageVo();
        messageVo.setMessageId(IdGen.uuid());
        messageVo.setResourceId(String.valueOf(kid));
        messageVo.setCoterieId(String.valueOf(coterieId));
        messageVo.setActionCode(messageConstant.getMessageActionCode());
        messageVo.setCreateTime(DateUtils.getDateTime());
        messageVo.setLabel(messageConstant.getLabel());
        messageVo.setType(messageConstant.getType());
        messageVo.setTitle(messageConstant.getTitle());

        /**
         * 处理消息的content
         */
        String content = messageConstant.getContent();
        if (messageConstant == MessageConstant.QUESTION_TO_BE_ANSWERED || messageConstant == MessageConstant.ANSWER_PAYED) {
            if (StringUtils.isNotEmpty(fromUser.getUserNickName())) {
                content = content.replaceAll("\\{someone\\}", fromUser.getUserNickName());
            }
        }

        if(StringUtils.isNotBlank(title)) {
            content = content.replaceAll("\\{title\\}", title);
        }

        if(coterieInfo!=null) {
            content = content.replaceAll("\\{coterieName\\}", coterieInfo.getName());
        }

        if (amount != null) {
            DecimalFormat df = new DecimalFormat("#0.00");
            if (messageConstant == MessageConstant.ANSWER_PAYED) {
                logger.info("处理推送消息里的抽成后的金额开始");
                //FeeDetail feeDetail = BranchFeesConstant.ANSWER.getFee().get(0);
                FeeDetail feeDetail = new FeeDetail("", 90L, 1, "");
                logger.info("原始金额是 : " + amount + ", 抽成规则是 : " + feeDetail.getFee());
                long cost = amount * (feeDetail.getFee()/100L);
                logger.info("抽成后金额是 : " + String.valueOf(cost));
                String money = df.format(cost);
                content = content.replaceAll("\\{money\\}", money);
            } else {
                content = content.replaceAll("\\{money\\}", df.format(amount));
            }
        }
        messageVo.setContent(content);
        messageVo.setToCust(String.valueOf(tosendUserId));
        messageVo.setViewCode(messageConstant.getMessageViewCode());
        messageVo.setBody(interactiveBody);
        messageVo.setModuleEnum(moduleEnum);
        Response<Boolean> data = messageAPI.sendMessage(messageVo, persistent);
        if (ResponseConstant.SUCCESS.getCode().equals(data.getCode())) {
            return data.getData();
        }
        return false;
    }

}
