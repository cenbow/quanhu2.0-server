package com.yryz.quanhu.behavior.like.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.message.*;
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
import com.yryz.quanhu.behavior.like.vo.LikeVO;
import com.yryz.quanhu.dymaic.service.DymaicService;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import com.yryz.quanhu.grow.entity.GrowFlowQuery;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.quanhu.resource.hotspot.api.HotSpotApi;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author:sun
 * @version:2.0
 * @Description:点赞
 * @Date:Created in 15:03 2018/1/24
 */
@Service(interfaceClass = LikeApi.class)
public class LikeProvider implements LikeApi {

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

    @Override
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
                            UserSimpleVO userSimpleVO = userApi.getUserSimple(likeVO.getUserId()).getData();
                            if (null != userSimpleVO) {
                                map.put("userNickName", userSimpleVO.getUserNickName());
                                map.put("userImg", userSimpleVO.getUserImg());
                            }
                        } catch (Exception e) {
                            logger.info("调用用户接口失败:" + e);
                        }
                        map.put("userId", likeVO.getUserId());
                        map.put("createDate", likeVO.getCreateDate());
                    }
                    try {
                        redisTemplate.opsForValue().set("LIKE:" + like.getResourceId() + "_" + like.getUserId(), 11L);
                    } catch (Exception e) {
                        logger.info("同步点赞数据到redis出现异常:" + e);
                    }

                    try {

                        GrowFlowQuery gfq=new GrowFlowQuery();
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
                        List<GrowFlowReportVo> growFlowQueries=eventAcountApiService.getGrowFlowAll(gfq).getData();
                        int currentCount=0;
                        for(GrowFlowReportVo growFlowReportVo:growFlowQueries){
                            currentCount+=growFlowReportVo.getNewGrow();
                        }
                        if(currentCount<10){
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
                        List<GrowFlowReportVo> growFlowQueries_=eventAcountApiService.getGrowFlowAll(gfq).getData();
                        int currentCount_=0;
                        for(GrowFlowReportVo growFlowReportVo:growFlowQueries_){
                            currentCount_+=growFlowReportVo.getNewGrow();
                        }
                        if(currentCount_<10){
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
     * @return
     */
    public static long startOfTodDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date=calendar.getTime();
        return date.getTime();
    }
    /**
     * 当天的结束时间
     * @return
     */
    public static long endOfTodDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date date=calendar.getTime();
        return date.getTime();
    }

    @Override
    public Response<PageList<LikeVO>> queryLikers(LikeFrontDTO likeFrontDTO) {
        try {
            Response<Map<String, Long>> mapResponse = countApi.getCount("11", likeFrontDTO.getResourceId(), "");
            Long count = mapResponse.getData().get("likeCount");
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

    @Override
    public Response<Integer> isLike(Like like) {
        try {
            return ResponseUtils.returnObjectSuccess(likeService.isLike(like));
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }

    }

    @Override
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

    public void switchSend(Like like) {
        UserSimpleVO userSimpleVO = userApi.getUserSimple(like.getUserId()).getData();
        LikeAssemble likeAssemble = new LikeAssemble();
        String nickName = "";
        if (null != userSimpleVO) {
            nickName = userSimpleVO.getUserNickName();
        }
        if (like.getModuleEnum().equals(ModuleContants.RELEASE)) {
            this.releasePush(like.getResourceId(),like.getResourceUserId(),nickName + "点赞了您发布的内容。",like.getModuleEnum(),like.getCoterieId());
        }
        if (like.getModuleEnum().equals(ModuleContants.TOPIC_POST)) {
            this.topicPostPush(like.getResourceId(),like.getResourceUserId(),nickName + "点赞了您发布的帖子。",like.getModuleEnum(),like.getCoterieId());
        }
        if (like.getCoterieId() != 0) {
            if (like.getModuleEnum().equals(ModuleContants.ANSWER)) {
                AnswerVo answerVo = answerApi.getDetail(like.getResourceId()).getData();
                LikeAssemble likeAssembleAnswer = new LikeAssemble();
                if(null!=likeAssembleAnswer.getContent()&&likeAssembleAnswer.getContent().length()>20){
                    likeAssembleAnswer.setTitle(answerVo.getContent().substring(0,20));
                }else{
                    likeAssembleAnswer.setTitle(answerVo.getContent());
                }
                likeAssembleAnswer.setTargetUserId(answerVo.getCreateUserId());
                likeAssembleAnswer.setLink("");
                likeAssembleAnswer.setContent(nickName+"点赞了您的问答");
                likeAssembleAnswer.setModuleEnum(answerVo.getModuleEnum());
                likeAssembleAnswer.setResourceId(answerVo.getKid());
                likeAssembleAnswer.setCoterieId(answerVo.getCoterieId());
                this.sendMessage(likeAssembleAnswer);
                Question question = questionApi.queryDetail(answerVo.getQuestionId()).getData();
                LikeAssemble likeAssembleQuestion = new LikeAssemble();
                likeAssembleQuestion.setTargetUserId(question.getCreateUserId());
                likeAssembleQuestion.setContent(nickName+"点赞了您的问答");
                likeAssembleQuestion.setTitle(question.getTitle());
                likeAssembleQuestion.setLink("");
                likeAssembleQuestion.setModuleEnum(ModuleContants.QUESTION);
                likeAssembleQuestion.setResourceId(question.getKid());
                likeAssembleQuestion.setCoterieId(question.getCoterieId());
                this.sendMessage(likeAssembleQuestion);
            }
        }
        if (like.getModuleEnum().equals(ModuleContants.DYNAMIC)) {
            this.dynamicPush(like.getResourceId(),nickName + "点赞了您发布的动态。",like.getModuleEnum(),like.getCoterieId());
        }
        if (like.getModuleEnum().equals(ModuleContants.COMMENT)) {
            this.releasePush(like.getResourceId(),like.getResourceUserId(),nickName + "点赞了您的评论。",like.getModuleEnum(),like.getCoterieId());
            this.topicPostPush(like.getResourceId(),like.getResourceUserId(),nickName + "点赞了您的评论。",like.getModuleEnum(),like.getCoterieId());
            if(like.getCoterieId()==0){
                this.dynamicPush(like.getResourceId(),nickName + "点赞了您的评论。",like.getModuleEnum(),like.getCoterieId());
            }

        }

    }


    public void releasePush(long resourceId, long resourceUserId,String contentStr,String moduleEnum,long coterieId){
        LikeAssemble likeAssemble=new LikeAssemble();
        try {
            ReleaseInfoVo releaseInfoVo = releaseInfoApi.infoByKid(resourceId, resourceUserId).getData();
            likeAssemble.setTitle(releaseInfoVo.getTitle());
            likeAssemble.setTargetUserId(releaseInfoVo.getCreateUserId());
            if (!releaseInfoVo.getImgUrl().equals("")) {
                String img = getImgFirstUrl(releaseInfoVo.getImgUrl());
                likeAssemble.setImg(img);
            }
            likeAssemble.setContent(contentStr);
            likeAssemble.setLink("");
            likeAssemble.setModuleEnum(moduleEnum);
            likeAssemble.setResourceId(resourceId);
            likeAssemble.setCoterieId(coterieId);
            this.sendMessage(likeAssemble);
        } catch (Exception e) {
            logger.info("调用文章出现异常" + e);
        }
    }

    public void topicPostPush(long resourceId, long resourceUserId,String contentStr,String moduleEnum,long coterieId){
        LikeAssemble likeAssemble=new LikeAssemble();
        try {
            TopicPostVo topicPostVo = topicPostApi.quetyDetail(resourceId, resourceUserId).getData();
            likeAssemble.setTargetUserId(topicPostVo.getUser().getUserId());
            if (null!=topicPostVo.getContent()&&topicPostVo.getContent().length()>20) {
                likeAssemble.setTitle(topicPostVo.getContent().substring(0, 20));
            }else{
                likeAssemble.setTitle(topicPostVo.getContent());
            }
            if (!topicPostVo.getImgUrl().equals("")) {
                String img = getImgFirstUrl(topicPostVo.getImgUrl());
                likeAssemble.setImg(img);
            }
            likeAssemble.setContent(contentStr);
            likeAssemble.setLink("");
            likeAssemble.setModuleEnum(moduleEnum);
            likeAssemble.setResourceId(resourceId);
            likeAssemble.setCoterieId(coterieId);
            this.sendMessage(likeAssemble);
        } catch (Exception e) {
            logger.info("调用帖子出现异常" + e);
        }
    }

    public void dynamicPush(long resourceId,String contentPushStr,String moduleEnum,long coterieId){
        LikeAssemble likeAssemble=new LikeAssemble();
        try{
          /*  Dymaic dymaic = dymaicService.get(resourceId).getData();
            if (!dymaic.getExtJson().equals("")) {
                Map maps = (Map) JSONObject.parse(dymaic.getExtJson());
                String title = maps.get("title").toString();
                String image = maps.get("imgUrl").toString();
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
                likeAssemble.setContent(contentPushStr);
                likeAssemble.setLink("");
                likeAssemble.setModuleEnum(moduleEnum);
                likeAssemble.setResourceId(resourceId);
                likeAssemble.setCoterieId(coterieId);
                this.sendMessage(likeAssemble);
            }*/
        }catch (Exception e){
            logger.info("调用动态出现异常" + e);
        }
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
        messageVo.setViewCode(MessageViewCode.SYSTEM_MESSAGE_2);
        try {
            messageAPI.sendMessage(messageVo, true);
        } catch (Exception e) {
            logger.info("持久化消息失败:" + e);
        }
    }

    public static String getImgFirstUrl(String imgs) {
        String[] arries = imgs.split(",");
        return arries[0];
    }

}
