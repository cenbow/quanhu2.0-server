package com.yryz.quanhu.resource.questionsAnswers.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.message.InteractiveBody;
import com.yryz.common.message.MessageConstant;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.common.utils.DateUtils;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.member.constants.MemberConstant;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.quanhu.message.message.vo.MessageVo;
import com.yryz.quanhu.order.sdk.constant.FeeDetail;
import com.yryz.quanhu.resource.enums.ResourceTypeEnum;
import com.yryz.quanhu.resource.questionsAnswers.constants.QuestionAnswerConstants;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.service.APIservice;
import com.yryz.quanhu.resource.questionsAnswers.service.QuestionMessageService;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.UUID;

@Service
public class QuestionMessageServiceImpl  implements QuestionMessageService {


    private static final Logger logger= LoggerFactory.getLogger(QuestionMessageServiceImpl.class);

    @Autowired
    private APIservice apIservice;

    @Reference
    private MessageAPI messageAPI;

    /**
     * 发送问答的通知消息
     * @param question
     * @param messageConstant
     * @return
     */
    @Override
    public Boolean sendNotify4Question(Question question, MessageConstant messageConstant) {
        UserSimpleVO createUser = this.apIservice.getUser(question.getCreateUserId());
        UserSimpleVO targetUser = this.apIservice.getUser(Long.valueOf(question.getTargetId()));
        InteractiveBody interactiveBody = new InteractiveBody();
        interactiveBody.setBodyTitle(StringUtils.length(question.getContent()) > 20 ? question.getContent().substring(0, 19) : question.getContent());
        interactiveBody.setBodyImg("");
        interactiveBody.setCircleId("");
        interactiveBody.setCircleName("");//圈子工程名
        interactiveBody.setCustImg(QuestionAnswerConstants.anonymityType.YES.equals(question.getIsAnonymity()) ? null : createUser.getUserImg());
        interactiveBody.setCustName(QuestionAnswerConstants.anonymityType.YES.equals(question.getIsAnonymity())
                ? QuestionAnswerConstants.ANONYMOUS_USER_NAME : createUser.getUserNickName());
        interactiveBody.setCustId(String.valueOf(createUser.getUserId()));
        interactiveBody.setCoterieId(String.valueOf(question.getCoterieId()));
        CoterieInfo coterieInfo = apIservice.getCoterieinfo(question.getCoterieId());
        if (null !=coterieInfo) {
            interactiveBody.setCoterieName(coterieInfo.getName());
        }
        MessageVo messageVo = null;
        if (messageConstant == MessageConstant.QUESTION_TO_BE_ANSWERED || messageConstant == MessageConstant.ANSWER_PAYED) {
            messageVo = buildMessageVo(messageConstant, targetUser.getUserId(), interactiveBody.getCustName(), interactiveBody, question.getChargeAmount(),
                    question);
        } else if (messageConstant == MessageConstant.QUESTION_INVALID || messageConstant == MessageConstant.QUESTION_PAYED
                || messageConstant == MessageConstant.QUESTION_DELETE) {
            messageVo = buildMessageVo(messageConstant, createUser.getUserId(), interactiveBody.getCoterieName(), interactiveBody, question.getChargeAmount(),
                    question);
        }

        Response<Boolean> data=messageAPI.sendMessage(messageVo, true);
        if(ResponseConstant.SUCCESS.getCode().equals(data.getCode())){
            return  data.getData();
        }
        return false;
    }

    private MessageVo buildMessageVo(MessageConstant constant, Long custId, String msg, Object body, Long count, Question question) {
        MessageVo messageVo = new MessageVo();
        messageVo.setMessageId(UUID.randomUUID().toString());
        messageVo.setActionCode(constant.getMessageActionCode());
        String content = constant.getContent();
        if (StringUtils.isNotEmpty(msg)) {
            String regex = "\\{count\\}";
            try {
                content = content.replaceAll(regex, msg);
            } catch (Exception e) {
                logger.error(content + " replaceAll Exception", e);
                regex = "\\{count\\}";
                if (content.indexOf(regex) > -1) {
                    content = org.apache.commons.lang3.StringUtils.substringBefore(content, regex) + msg
                            + org.apache.commons.lang3.StringUtils.substringAfter(content, regex);
                }
            }
        }
        messageVo.setContent(content);
        // TODO: 2018/1/26 0026
        messageVo.setCreateTime(DateUtils.getTime());
        messageVo.setLabel(constant.getLabel());
        messageVo.setType(constant.getType());
        messageVo.setTitle(constant.getTitle());
        messageVo.setToCust(String.valueOf(custId));
        messageVo.setViewCode(constant.getMessageViewCode());
        messageVo.setBody(body);
        messageVo.setModuleEnum(ResourceTypeEnum.QUESTION);
        if (StringUtils.isNotEmpty(msg)) {
            content = content.replaceAll("\\{someone\\}", msg);
        }
        if (count != null) {
            DecimalFormat df = new DecimalFormat("#0.00");
            if (constant == MessageConstant.ANSWER_PAYED) {
                logger.info("处理推送消息里的抽成后的金额开始");
                //FeeDetail feeDetail = BranchFeesConstant.ANSWER.getFee().get(0);
                FeeDetail feeDetail = new FeeDetail("", 1, 1);
                logger.info("原始金额是 : " + count + ", 抽成规则是 : " + feeDetail.getFee());
                double cost = count * feeDetail.getFee();
                logger.info("抽成后金额是 : " + String.valueOf(cost));
                String money = df.format(cost);
                content = content.replaceAll("\\{money\\}", money);
            } else {
                content = content.replaceAll("\\{money\\}", df.format(count));
            }
        }
        messageVo.setResourceId(String.valueOf(question.getKid()));
        messageVo.setCoterieId(String.valueOf(question.getCoterieId()));
        messageVo.setContent(content);
        return messageVo;
    }
}
