package com.yryz.quanhu.behavior.like.provider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.message.InteractiveBody;
import com.yryz.common.message.MessageActionCode;
import com.yryz.common.message.MessageLabel;
import com.yryz.common.message.MessageType;
import com.yryz.common.message.MessageViewCode;
import com.yryz.common.message.MessageVo;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.IdGen;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.behavior.like.Service.LikeApi;
import com.yryz.quanhu.behavior.like.dto.LikeAssemble;
import com.yryz.quanhu.behavior.like.dto.LikeFrontDTO;
import com.yryz.quanhu.behavior.like.entity.Like;
import com.yryz.quanhu.behavior.like.service.LikeService;
import com.yryz.quanhu.behavior.like.vo.LikeInfoVO;
import com.yryz.quanhu.behavior.like.vo.LikeVO;
import com.yryz.quanhu.grow.entity.GrowFlowQuery;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.quanhu.resource.questionsAnswers.api.AnswerApi;
import com.yryz.quanhu.resource.questionsAnswers.api.QuestionApi;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.vo.AnswerVo;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoApi;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;
import com.yryz.quanhu.resource.topic.api.TopicPostApi;
import com.yryz.quanhu.resource.topic.vo.TopicPostVo;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.service.EventAcountApiService;
import com.yryz.quanhu.score.vo.EventInfo;
import com.yryz.quanhu.score.vo.GrowFlowReportVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
 * @Author:sun
 * @version:2.0
 * @Description:点赞
 * @Date:Created in 15:03 2018/1/24
 */
//@Service(interfaceClass = LikeApi.class)
public class LikeProvider{

    private static final Logger logger = LoggerFactory.getLogger(LikeProvider.class);

    @Autowired
    private LikeService likeService;

    @Reference(check = false)
    private IdAPI idAPI;

    @Reference(check = false)
    private CountApi countApi;

    @Reference(check = false)
    private UserApi userApi;

    @Autowired
    private RedisTemplateBuilder redisTemplateBuilder;

    @Reference(check = false)
    private EventAPI eventAPI;

    @Reference(check = false)
    private MessageAPI messageAPI;

    @Reference(check = false)
    private ReleaseInfoApi releaseInfoApi;

    @Reference(check = false)
    private TopicPostApi topicPostApi;

    /*@Reference(check = false)
    private DymaicService dymaicService;*/

    @Reference(check = false)
    private AnswerApi answerApi;

    @Reference(check = false)
    private QuestionApi questionApi;

    @Reference(check = false)
    private EventAcountApiService eventAcountApiService;

    @Transactional
    public Response<Map<String, Object>> dian(Like like) {
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            like.setKid(idAPI.getSnowflakeId().getData());
            int isLikeCount = likeService.isLike(like);
            int tongCount = 0;
            if (isLikeCount <= 0) {
                int dianCount = likeService.accretion(like);
                if (dianCount > 0) {
                    tongCount = 1;
                    LikeVO likeVO = likeService.querySingleLiker(like);
                    if (null != likeVO) {
                        try {
                            UserSimpleVO userSimpleVO = ResponseUtils.getResponseData(userApi.getUserSimple(likeVO.getUserId()));
                            if (null != userSimpleVO) {
                                map.put("userNickName", userSimpleVO.getUserNickName());
                                map.put("userImg", userSimpleVO.getUserImg());
                            }
                        } catch (Exception e) {
                            logger.info("调用用户接口失败:" + e);
                        }
                        map.put("userId", String.valueOf(likeVO.getUserId()));
                        map.put("createDate", likeVO.getCreateDate());
                    }
                    try {
                        redisTemplate.opsForValue().set("LIKE:" + like.getResourceId() + "_" + like.getUserId(), 11L);
                        redisTemplate.expire("LIKE:" + like.getResourceId() + "_" + like.getUserId(),24, TimeUnit.HOURS);
                    } catch (Exception e) {
                        logger.info("同步点赞数据到redis出现异常:" + e);
                    }

                    try {

                        GrowFlowQuery gfq = new GrowFlowQuery();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        gfq.setStartTime(sdf.format(startOfTodDay()));
                        gfq.setEndTime(sdf.format(endOfTodDay()));
                        //对接积分系统
                        List<EventInfo> eventInfos = new ArrayList<EventInfo>();
                        //点赞
                        EventInfo eventInfo = new EventInfo();
                        eventInfo.setOwnerId(String.valueOf(like.getResourceUserId()));
                        eventInfo.setUserId(String.valueOf(like.getUserId()));
                        eventInfo.setEventGrow(String.valueOf(1));
                        eventInfo.setCreateTime(String.valueOf(new Date()));
                        eventInfo.setResourceId(String.valueOf(like.getResourceId()));
                        eventInfo.setEventCode(String.valueOf(13));
                        eventInfo.setEventNum(1);
                        eventInfo.setAmount(0.0);
                        eventInfo.setCoterieId(String.valueOf(""));
                        eventInfo.setCircleId("");
                        gfq.setEventCode("13");
                        gfq.setUserId(String.valueOf(like.getUserId()));
                        List<GrowFlowReportVo> growFlowQueries = eventAcountApiService.getGrowFlowAll(gfq).getData();
                        int currentCount = 0;
                        for (GrowFlowReportVo growFlowReportVo : growFlowQueries) {
                            currentCount += growFlowReportVo.getNewGrow();
                        }
                        if (currentCount < 10) {
                            eventInfos.add(eventInfo);
                        }
                        //被点赞
                        EventInfo eventInfo_ = new EventInfo();
                        eventInfo_.setOwnerId(String.valueOf(like.getResourceUserId()));
                        eventInfo_.setUserId(String.valueOf(like.getResourceUserId()));
                        eventInfo_.setEventGrow(String.valueOf(1));
                        eventInfo_.setCreateTime(String.valueOf(new Date()));
                        eventInfo_.setResourceId(String.valueOf(like.getResourceId()));
                        eventInfo_.setEventCode(String.valueOf(10));
                        eventInfo_.setEventNum(1);
                        eventInfo_.setAmount(0.0);
                        eventInfo_.setCoterieId(String.valueOf(""));
                        eventInfo_.setCircleId("");
                        gfq.setEventCode("10");
                        gfq.setUserId(String.valueOf(like.getResourceUserId()));
                        List<GrowFlowReportVo> growFlowQueries_ = eventAcountApiService.getGrowFlowAll(gfq).getData();
                        int currentCount_ = 0;
                        for (GrowFlowReportVo growFlowReportVo : growFlowQueries_) {
                            currentCount_ += growFlowReportVo.getNewGrow();
                        }
                        if (currentCount_ < 10) {
                            eventInfos.add(eventInfo_);
                        }
                        eventAPI.commit(eventInfos);
                    } catch (Exception e) {
                        logger.info("对接积分系统出现异常:" + e);
                    }
                    //点赞推送消息
                    this.switchSend(like);
                }
            } else {
                int count = likeService.cleanLike(like);
                if (count > 0) {
                    tongCount = -1;
                    map.put("result", 1);
                    try {
                        redisTemplate.delete("LIKE:" + like.getResourceId() + "_" + like.getUserId());
                    } catch (Exception e) {
                        logger.info("从redis中清掉点赞数据出现异常:" + e);
                    }

                } else {
                    map.put("result", 0);
                }
            }
            try {
                if (tongCount != 0) {
                    Response<Object> response = countApi.commitCount(BehaviorEnum.Like, like.getResourceId(), "", Long.valueOf(tongCount));
                    if (response.getCode().equals("200")) {
                        logger.info("进入统计系统成功");
                    }
                }
            } catch (Exception e) {
                logger.info("进入统计系统失败:" + e);
            }
            return ResponseUtils.returnObjectSuccess(map);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    /**
     * 当天的开始时间
     *
     * @return
     */
    public static long startOfTodDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        return date.getTime();
    }

    /**
     * 当天的结束时间
     *
     * @return
     */
    public static long endOfTodDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date date = calendar.getTime();
        return date.getTime();
    }


    public Response<PageList<LikeVO>> queryLikers(LikeFrontDTO likeFrontDTO) {
        try {
            Map<String, Long> map = ResponseUtils.getResponseData(countApi.getCount("11", likeFrontDTO.getResourceId(), ""));
            Long count = map.get("likeCount");
            PageList<LikeVO> likeVOS = likeService.queryLikers(likeFrontDTO);
            for (LikeVO likeVO : likeVOS.getEntities()) {
                UserSimpleVO userSimpleVO = userApi.getUserSimple(likeVO.getUserId()).getData();
                if (null != userSimpleVO) {
                    likeVO.setUserNickName(userSimpleVO.getUserNickName());
                    likeVO.setUserImg(userSimpleVO.getUserImg());
                } else {
                    break;
                }
            }
            likeVOS.setCount(count);
            return ResponseUtils.returnObjectSuccess(likeVOS);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }


    public Response<Integer> isLike(Like like) {
        try {
            return ResponseUtils.returnObjectSuccess(likeService.isLike(like));
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }

    }


    public Response<Long> getLikeFlag(Map<String, Object> map) {
        try {
            RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
            Long likeFlag = 0L;
            if (null != redisTemplate) {
                likeFlag = redisTemplate.opsForValue().get("LIKE:" + map.get("resourceId") + "_" + map.get("userId"));
                if (null == likeFlag) {
                    likeFlag = 10L;
                }
            } else {
                Like like = new Like();
                if (null != map || map.size() > 0) {
                    like.setResourceId(Long.valueOf(map.get("resourceId").toString()));
                    like.setUserId(Long.valueOf(map.get("userId").toString()));
                }
                likeFlag = Long.valueOf(likeService.isLike(like));
            }
            return ResponseUtils.returnObjectSuccess(likeFlag);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }

    }


    public Response<Map<String,Integer>> getLikeFlagBatch(List<Long> resourceIds, long userId) {
        try{
            return ResponseUtils.returnObjectSuccess(likeService.getLikeFlagBatch(resourceIds,userId));
        }catch (Exception e){
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }

    }

    public void switchSend(Like like) {
        UserSimpleVO userSimpleVO = ResponseUtils.getResponseData(userApi.getUserSimple(like.getUserId()));
        String nickName = "";
        if (null != userSimpleVO) {
            nickName = userSimpleVO.getUserNickName();
        }
        if (like.getModuleEnum().equals(ModuleContants.RELEASE)) {
            String contentStr = nickName + "点赞了您发布的内容。";
            LikeAssemble likeAssemble = getLikeAssemble(like, contentStr);
            likeAssemble.setUserImg(userSimpleVO.getUserImg());
            likeAssemble.setUserNickName(userSimpleVO.getUserNickName());
            this.releasePush(likeAssemble);
        }
        if (like.getModuleEnum().equals(ModuleContants.TOPIC_POST)) {
            String contentStr = nickName + "点赞了您发布的帖子。";
            LikeAssemble likeAssemble = getLikeAssemble(like, contentStr);
            likeAssemble.setUserImg(userSimpleVO.getUserImg());
            likeAssemble.setUserNickName(userSimpleVO.getUserNickName());
            this.topicPostPush(likeAssemble);
        }
        if (like.getModuleEnum().equals(ModuleContants.ANSWER)) {
            AnswerVo answerVo = ResponseUtils.getResponseData(answerApi.getDetail(like.getResourceId()));
            if(answerVo == null){
            	throw QuanhuException.busiShowError("答案不存在", "答案不存在"); 
            }
            LikeAssemble likeAssembleAnswer = new LikeAssemble();
            if (null != likeAssembleAnswer.getContent() && likeAssembleAnswer.getContent().length() > 20) {
                likeAssembleAnswer.setTitle(answerVo.getContent().substring(0, 20));
            } else {
                likeAssembleAnswer.setTitle(answerVo.getContent());
            }
            likeAssembleAnswer.setTargetUserId(answerVo.getCreateUserId());
            likeAssembleAnswer.setLink("");
            likeAssembleAnswer.setContent(nickName + "点赞了您的问答");
            likeAssembleAnswer.setModuleEnum(answerVo.getModuleEnum());
            likeAssembleAnswer.setResourceId(answerVo.getKid());
            likeAssembleAnswer.setCoterieId(answerVo.getCoterieId());
            likeAssembleAnswer.setUserNickName(userSimpleVO.getUserNickName());
            likeAssembleAnswer.setUserImg(userSimpleVO.getUserImg());
            if (answerVo.getCoterieId() != 0) {
                if (null != answerVo.getContent() && answerVo.getContent().length() > 7) {
                    likeAssembleAnswer.setCoterieName(answerVo.getContent().substring(0, 7));
                } else {
                    likeAssembleAnswer.setCoterieName(answerVo.getContent());
                }
                if (!answerVo.getImgUrl().equals("")) {
                    String img = getImgFirstUrl(answerVo.getImgUrl());
                    likeAssembleAnswer.setBodyImg(img);
                }
                if (null != answerVo.getContent() && answerVo.getContent().length() > 20) {
                    likeAssembleAnswer.setBodyTitle(answerVo.getContent().substring(0, 20));
                } else {
                    likeAssembleAnswer.setBodyTitle(answerVo.getContent());
                }
            }
            this.sendMessage(likeAssembleAnswer);
            Question question = ResponseUtils.getResponseData(questionApi.queryDetail(answerVo.getQuestionId()));
            if(question == null){
            	throw QuanhuException.busiShowError("问题不存在", "问题不存在"); 
            }
            LikeAssemble likeAssembleQuestion = new LikeAssemble();
            likeAssembleQuestion.setTargetUserId(question.getCreateUserId());
            likeAssembleQuestion.setContent(nickName + "点赞了您的问答");
            likeAssembleQuestion.setTitle(question.getTitle());
            likeAssembleQuestion.setLink("");
            likeAssembleQuestion.setModuleEnum(ModuleContants.QUESTION);
            likeAssembleQuestion.setResourceId(question.getKid());
            likeAssembleQuestion.setCoterieId(question.getCoterieId());
            likeAssembleQuestion.setUserImg(userSimpleVO.getUserImg());
            likeAssembleQuestion.setUserNickName(userSimpleVO.getUserNickName());
            if (likeAssembleQuestion.getCoterieId() != 0) {
                if (null != question.getContent() && question.getContent().length() > 7) {
                    likeAssembleQuestion.setCoterieName(question.getContent().substring(0, 7));
                } else {
                    likeAssembleQuestion.setCoterieName(question.getContent());
                }
                likeAssembleQuestion.setBodyImg("");
                if (null != question.getContent() && question.getContent().length() > 20) {
                    likeAssembleQuestion.setBodyTitle(question.getContent().substring(0, 20));
                } else {
                    likeAssembleQuestion.setBodyTitle(question.getContent());
                }
            }

            this.sendMessage(likeAssembleQuestion);
        }
        if (like.getModuleEnum().equals(ModuleContants.DYNAMIC)) {
            String contentStr = nickName + "点赞了您发布的动态。";
            LikeAssemble likeAssemble = getLikeAssemble(like, contentStr);
            likeAssemble.setUserImg(userSimpleVO.getUserImg());
            likeAssemble.setUserNickName(userSimpleVO.getUserNickName());
            like.setResourceUserId(0);
            this.dynamicPush(likeAssemble);
        }
        if (like.getModuleEnum().equals(ModuleContants.COMMENT)) {
            String contentStr = nickName + "点赞了您的评论。";
            LikeAssemble likeAssemble = getLikeAssemble(like, contentStr);
            likeAssemble.setUserImg(userSimpleVO.getUserImg());
            likeAssemble.setUserNickName(userSimpleVO.getUserNickName());
            this.releasePush(likeAssemble);
            this.topicPostPush(likeAssemble);
            if (like.getCoterieId() == 0) {
                like.setResourceUserId(0);
                this.dynamicPush(likeAssemble);
            }

        }

    }

    public static LikeAssemble getLikeAssemble(Like like, String contentStr) {
        LikeAssemble likeAssemble = new LikeAssemble();
        likeAssemble.setResourceId(like.getResourceId());
        likeAssemble.setModuleEnum(like.getModuleEnum());
        likeAssemble.setResourceUserId(like.getResourceUserId());
        likeAssemble.setContent(contentStr);
        return likeAssemble;
    }

    public void releasePush(LikeAssemble likeAssemble) {
        try {
            ReleaseInfoVo releaseInfoVo = ResponseUtils.getResponseData(releaseInfoApi.infoByKid(likeAssemble.getResourceId(), likeAssemble.getResourceUserId()));
            likeAssemble.setTitle(releaseInfoVo.getTitle());
            likeAssemble.setTargetUserId(releaseInfoVo.getCreateUserId());
            if (!releaseInfoVo.getImgUrl().equals("")) {
                String img = getImgFirstUrl(releaseInfoVo.getImgUrl());
                likeAssemble.setImg(img);
            }
            if (releaseInfoVo.getCoterieId() != 0 && null != releaseInfoVo.getTitle()) {
                if (releaseInfoVo.getTitle().length() > 7) {
                    likeAssemble.setCoterieName(releaseInfoVo.getTitle().substring(0, 7));
                } else {
                    likeAssemble.setCoterieName(releaseInfoVo.getTitle());
                }
                if (!releaseInfoVo.getImgUrl().equals("")) {
                    String img = getImgFirstUrl(releaseInfoVo.getImgUrl());
                    likeAssemble.setBodyImg(img);
                }
                if (releaseInfoVo.getTitle().length() > 20) {
                    likeAssemble.setBodyTitle(releaseInfoVo.getTitle().substring(0, 20));
                } else {
                    likeAssemble.setBodyTitle(releaseInfoVo.getTitle());
                }
                likeAssemble.setCoterieId(releaseInfoVo.getCoterieId());
            }
            this.sendMessage(likeAssemble);
        } catch (Exception e) {
            logger.info("调用文章出现异常" + e);
        }
    }

    public void topicPostPush(LikeAssemble likeAssemble) {
        try {
            TopicPostVo topicPostVo = ResponseUtils.getResponseData(topicPostApi.quetyDetail(likeAssemble.getResourceId(), likeAssemble.getResourceUserId()));
            likeAssemble.setTargetUserId(topicPostVo.getUser().getUserId());
            if (null != topicPostVo.getContent() && topicPostVo.getContent().length() > 20) {
                likeAssemble.setTitle(topicPostVo.getContent().substring(0, 20));
            } else {
                likeAssemble.setTitle(topicPostVo.getContent());
            }
            if (!topicPostVo.getImgUrl().equals("")) {
                String img = getImgFirstUrl(topicPostVo.getImgUrl());
                likeAssemble.setImg(img);
            }
            if (topicPostVo.getCoterieId() != 0 && null != topicPostVo.getContent()) {
                if (topicPostVo.getContent().length() > 7) {
                    likeAssemble.setCoterieName(topicPostVo.getContent().substring(0, 7));
                } else {
                    likeAssemble.setCoterieName(topicPostVo.getContent());
                }
                if (!topicPostVo.getImgUrl().equals("")) {
                    String img = getImgFirstUrl(topicPostVo.getImgUrl());
                    likeAssemble.setBodyImg(img);
                }
                likeAssemble.setBodyTitle(topicPostVo.getContent());
                likeAssemble.setCoterieId(topicPostVo.getCoterieId());
            }
            this.sendMessage(likeAssemble);
        } catch (Exception e) {
            logger.info("调用帖子出现异常" + e);
        }
    }

    public void dynamicPush(LikeAssemble likeAssemble) {
       /* try {
            Dymaic dymaic = dymaicService.get(likeAssemble.getResourceId()).getData();
            if (!dymaic.getExtJson().equals("")) {
                Map maps = (Map) JSONObject.parse(dymaic.getExtJson());
                String title = maps.get("title").toString();
                String image = maps.get("imgUrl").toString();
                String coterieId= maps.get("coterieId").toString();
                String imgUrl = "";
                if (!image.equals("")) {
                    imgUrl = getImgFirstUrl(image);
                    likeAssemble.setImg(imgUrl);
                }
                if (!title.equals("")) {
                    likeAssemble.setTitle(title);
                }
                String content = maps.get("content").toString();
                if (title.equals("") && !content.equals("")) {
                    likeAssemble.setTitle(content.substring(0, 20));
                }
                if (!coterieId.equals("0")) {
                    if (!title.equals("") && title.length() > 7) {
                        likeAssemble.setCoterieName(title.substring(0, 7));
                    } else {
                        likeAssemble.setCoterieName(title);
                    }
                    if(!title.equals("") && title.length() > 20){
                        likeAssemble.setBodyTitle(content.substring(0,20));
                    }else{
                        likeAssemble.setBodyTitle(title);
                    }
                    if (title.equals("") && !content.equals("")) {
                        if (content.length() > 7) {
                            likeAssemble.setCoterieName(content.substring(0, 7));
                        } else {
                            likeAssemble.setCoterieName(content);
                        }
                        if(content.length()>20){
                            likeAssemble.setBodyTitle(content.substring(0,20));
                        }else{
                            likeAssemble.setBodyTitle(content);
                        }
                    }
                    if (!image.equals("")) {
                        String imgUrls = getImgFirstUrl(image);
                        likeAssemble.setBodyImg(imgUrls);
                    }
                    likeAssemble.setCoterieId(Long.valueOf(coterieId));
                }
                this.sendMessage(likeAssemble);
            }
        } catch (Exception e) {
            logger.info("调用动态出现异常" + e);
        }*/
    }


    public void sendMessage(LikeAssemble likeAssemble) {
        MessageVo messageVo = new MessageVo();
        messageVo.setContent(likeAssemble.getContent());
        messageVo.setMessageId(IdGen.uuid());
        messageVo.setImg(likeAssemble.getImg());
        messageVo.setTitle(likeAssemble.getTitle());
        messageVo.setLink(likeAssemble.getLink());
        messageVo.setActionCode(MessageActionCode.COMMON_DETAIL);
        messageVo.setLabel(MessageLabel.INTERACTIVE_LIKE);
        messageVo.setToCust(String.valueOf(likeAssemble.getTargetUserId()));
        messageVo.setType(MessageType.INTERACTIVE_TYPE);
        messageVo.setCreateTime(DateUtils.getDateTime());
        messageVo.setModuleEnum(likeAssemble.getModuleEnum());
        messageVo.setCoterieId(String.valueOf(likeAssemble.getCoterieId()));
        messageVo.setResourceId(String.valueOf(likeAssemble.getResourceId()));
        messageVo.setViewCode(MessageViewCode.INTERACTIVE_MESSAGE);
        InteractiveBody body = new InteractiveBody();
        body.setUserImg(likeAssemble.getUserImg());
        body.setUserNickName(likeAssemble.getUserNickName());
        body.setCoterieId(String.valueOf(likeAssemble.getCoterieId()));
        body.setCoterieName(likeAssemble.getCoterieName());
        body.setBodyImg(likeAssemble.getBodyImg());
        body.setBodyTitle(likeAssemble.getBodyTitle());
        try {
            messageAPI.sendMessage(messageVo, false);
        } catch (Exception e) {
            logger.info("持久化消息失败:" + e);
        }
    }

    public static String getImgFirstUrl(String imgs) {
        String[] arries = imgs.split(",");
        return arries[0];
    }

	public Response<PageList<LikeInfoVO>> listLike(LikeFrontDTO likeFrontDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
