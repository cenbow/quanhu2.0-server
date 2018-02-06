package com.yryz.quanhu.behavior.comment.provider;

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
import com.yryz.quanhu.behavior.comment.dto.CommentAssemble;
import com.yryz.quanhu.behavior.comment.dto.CommentDTO;
import com.yryz.quanhu.behavior.comment.dto.CommentFrontDTO;
import com.yryz.quanhu.behavior.comment.dto.CommentSubDTO;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import com.yryz.quanhu.behavior.comment.service.CommentApi;
import com.yryz.quanhu.behavior.comment.service.CommentService;
import com.yryz.quanhu.behavior.comment.vo.CommentInfoVO;
import com.yryz.quanhu.behavior.comment.vo.CommentVO;
import com.yryz.quanhu.behavior.comment.vo.CommentVOForAdmin;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.behavior.like.dto.LikeAssemble;
import com.yryz.quanhu.dymaic.service.DymaicService;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.quanhu.resource.questionsAnswers.api.AnswerApi;
import com.yryz.quanhu.resource.questionsAnswers.api.QuestionApi;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.vo.AnswerVo;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoApi;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;
import com.yryz.quanhu.resource.topic.api.TopicPostApi;
import com.yryz.quanhu.resource.topic.vo.TopicPostVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;

/**
 * @Author:sun
 * @version:2.0
 * @Description:评论
 * @Date:Created in 19:13 2018/1/23
 */
@Service(interfaceClass = CommentApi.class)
public class CommentProvider implements CommentApi {

    private static final Logger logger = LoggerFactory.getLogger(CommentProvider.class);

    @Autowired
    private CommentService commentService;

    @Reference(check = false)
    private IdAPI idAPI;

    @Reference(check = false)
    private UserApi userApi;

    @Reference(check = false)
    private CountApi countApi;

    @Reference(check = false)
    private MessageAPI messageAPI;

    @Reference(check = false)
    private ReleaseInfoApi releaseInfoApi;

    @Reference(check = false)
    private TopicPostApi topicPostApi;

  /*  @Reference(check = false)
    private DymaicService dymaicService;*/

    @Reference(check = false)
    private AnswerApi answerApi;

    @Reference(check = false)
    private QuestionApi questionApi;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Response<Comment> accretion(Comment comment) {
        try {
            comment.setKid(idAPI.getSnowflakeId().getData());
            Map<String, Integer> map = new HashMap<String, Integer>();
            UserSimpleVO userSimpleVO = userApi.getUserSimple(comment.getCreateUserId()).getData();
            if (null != userSimpleVO) {
                comment.setNickName(userSimpleVO.getUserNickName());
                comment.setUserImg(userSimpleVO.getUserImg());
            }
            comment.setShelveFlag((byte) 10);
            comment.setDelFlag((byte) 10);
            int count = commentService.accretion(comment);
            if (count > 0) {
                map.put("result", 1);
                if (comment.getTopId() == 0) {
                    try {
                        countApi.commitCount(BehaviorEnum.Comment, comment.getResourceId(), "", 1L);
                    } catch (Exception e) {
                        logger.info("进入统计系统失败" + e);
                    }
                }
            } else {
                map.put("result", 0);
            }

            return ResponseUtils.returnObjectSuccess(comment);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Map<String, Integer>> delComment(Comment comment) {
        try {
            Map<String, Integer> map = new HashMap<String, Integer>();
            int count = commentService.delComment(comment);
            if (count > 0) {
                map.put("result", 1);
                Comment comments = new Comment();
                comments.setKid(comment.getKid());
                Comment commentStr = commentService.querySingleComment(comments);
                try {
                    stringRedisTemplate.delete("COMMENT:" + commentStr.getModuleEnum() + ":" + commentStr.getKid() + "_" + commentStr.getTopId() + "_" + commentStr.getResourceId());
                } catch (Exception e) {
                    logger.info("从redis中移除评论数据失败" + e);
                }

            } else {
                map.put("result", 0);
            }
            return ResponseUtils.returnObjectSuccess(map);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<CommentVO>> queryComments(CommentFrontDTO commentFrontDTO) {
        try {
            return ResponseUtils.returnObjectSuccess(commentService.queryComments(commentFrontDTO));
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> updownBatch(List<Comment> comments) {
        try {
            int count = commentService.updownBatch(comments);
            if(count>0){
                String strKeys="";
                for(Comment comment:comments){
                    Comment commentSingle = commentService.querySingleComment(comment);
                    strKeys+="COMMENT:"+commentSingle.getModuleEnum()+":"+commentSingle.getKid()+ "_" + commentSingle.getTopId() + "_" + commentSingle.getResourceId()+",";
                }
                if(null!=strKeys&&!strKeys.equals("")){
                    String[] strArray=strKeys.split(",");
                    for (int i=0;i<strArray.length;i++){
                        stringRedisTemplate.delete(strArray[i]);
                    }
                }
            }
            return ResponseUtils.returnObjectSuccess(count);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<CommentVOForAdmin>> queryCommentForAdmin(CommentDTO commentDTO) {
        try {
            return ResponseUtils.returnObjectSuccess(commentService.queryCommentForAdmin(commentDTO));
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<CommentInfoVO> querySingleCommentInfo(CommentSubDTO commentSubDTO) {
        try {
            CommentInfoVO commentInfoVO = commentService.querySingleCommentInfo(commentSubDTO);
            commentInfoVO.setCommentEnties(commentService.querySubCommentsInfo(commentSubDTO));
            return ResponseUtils.returnObjectSuccess(commentInfoVO);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> updownSingle(Comment comment) {
        try {
            return ResponseUtils.returnObjectSuccess(commentService.updownSingle(comment));
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }


    public void switchSend(Comment comment) {
        UserSimpleVO userSimpleVO = userApi.getUserSimple(comment.getCreateUserId()).getData();
        String nickName = "";
        if (null != userSimpleVO) {
            nickName = userSimpleVO.getUserNickName();
        }
        String pingContent = "";
        if (comment.getModuleEnum().equals(ModuleContants.RELEASE)) {
            String contentStr = "";
            long contentType = 0;
            if (comment.getTopId() == 0) {
                contentType = 0;
                contentStr = nickName + "评论了您发布的内容。";
            } else {
                contentType = 1;
                contentStr = nickName + "回复了您的评论。";
                pingContent=this.getBePingComment(comment.getParentId());
            }
            this.releasePush(comment.getResourceId(), comment.getTargetUserId(), contentStr, contentType, pingContent);
        }

        if (comment.getModuleEnum().equals(ModuleContants.TOPIC_POST)) {
            String contentStr = "";
            long contentType = 0;
            if (comment.getTopId() == 0) {
                contentType = 0;
                contentStr = nickName + "评论了您发布的帖子。";
            } else {
                contentType = 1;
                contentStr = nickName + "回复了您的评论。";
                pingContent=this.getBePingComment(comment.getParentId());
            }
            this.topicPostPush(comment.getResourceId(), comment.getTargetUserId(), contentStr, contentType,pingContent);
        }

        if (comment.getModuleEnum().equals(ModuleContants.DYNAMIC)) {
            String contentStr = "";
            long contentType = 0;
            if (comment.getTopId() == 0) {
                contentType = 0;
                contentStr = nickName + "评论了您的动态。";
            } else {
                contentType = 1;
                contentStr = nickName + "回复了您的评论。";
                pingContent=this.getBePingComment(comment.getParentId());
            }
            this.dynamicPush(comment.getResourceId(), contentStr, contentType,pingContent);
        }

        if (comment.getModuleEnum().equals(ModuleContants.ANSWER)) {
            AnswerVo answerVo = answerApi.getDetail(comment.getResourceId()).getData();
            CommentAssemble commentAssembleAnswer = new CommentAssemble();
            if(null!=answerVo.getContent()&&answerVo.getContent().length()>20){
                commentAssembleAnswer.setTitle(answerVo.getContent().substring(0, 20));
            }else{
                commentAssembleAnswer.setTitle(answerVo.getContent());
            }
            commentAssembleAnswer.setTargetUserId(answerVo.getCreateUserId());
            commentAssembleAnswer.setLink("");
            commentAssembleAnswer.setContent(nickName + "评论了您的问题。");
            this.sendMessage(commentAssembleAnswer);
            Question question = questionApi.queryDetail(answerVo.getQuestionId()).getData();
            CommentAssemble commentAssembleQuestion = new CommentAssemble();
            commentAssembleQuestion.setTargetUserId(question.getCreateUserId());
            commentAssembleQuestion.setContent(nickName + "评论了您的问题。");
            commentAssembleQuestion.setTitle(question.getTitle());
            commentAssembleQuestion.setLink("");
            this.sendMessage(commentAssembleQuestion);
        }


    }

    public String getBePingComment(Long kid) {
        Comment comentSub = new Comment();
        comentSub.setKid(kid);
        Comment commentSingle = commentService.querySingleComment(comentSub);
        String pingContent = "";
        if (null != commentSingle) {
            if (null!=commentSingle.getContentComment()&&commentSingle.getContentComment().length() > 20) {
                pingContent = commentSingle.getContentComment().substring(0, 20);
            } else {
                pingContent = commentSingle.getContentComment();
            }

        }
        return pingContent;
    }

    public void topicPostPush(long resourceId, long resourceUserId, String contentStr, long contentType, String bePingContent) {
        CommentAssemble commentAssemble = new CommentAssemble();
        try {
            TopicPostVo topicPostVo = topicPostApi.quetyDetail(resourceId, resourceUserId).getData();
            if (contentType == 0) {
                if (!topicPostVo.getImgUrl().equals("")) {
                    String img = getImgFirstUrl(topicPostVo.getImgUrl());
                    commentAssemble.setImg(img);
                }
                if(null!=topicPostVo.getContent()&&topicPostVo.getContent().length()>20){
                    commentAssemble.setTitle(topicPostVo.getContent().substring(0, 20));
                }else{
                    commentAssemble.setTitle(topicPostVo.getContent());
                }

            }
            if (contentType != 0) {
                commentAssemble.setViewCode((byte) 1);
                //截取被回复的评论内容
                commentAssemble.setTitle(bePingContent);
            }
            commentAssemble.setContent(contentStr);
            commentAssemble.setLink("");
            this.sendMessage(commentAssemble);
        } catch (Exception e) {
            logger.error("调用文章出现异常", e);
        }
    }

    public void releasePush(long resourceId, long resourceUserId, String contentStr, long contentType, String bePingContent) {
        CommentAssemble commentAssemble = new CommentAssemble();
        try {
            ReleaseInfoVo releaseInfoVo = releaseInfoApi.infoByKid(resourceId, resourceUserId).getData();
            commentAssemble.setTargetUserId(releaseInfoVo.getCreateUserId());
            if (contentType == 0) {
                if (!releaseInfoVo.getImgUrl().equals("")) {
                    String img = getImgFirstUrl(releaseInfoVo.getImgUrl());
                    commentAssemble.setImg(img);
                }
                commentAssemble.setTitle(releaseInfoVo.getTitle());
                commentAssemble.setViewCode((byte) 2);
            }
            if (contentType != 0) {
                commentAssemble.setViewCode((byte) 1);
                //截取被回复的评论内容
                commentAssemble.setTitle(bePingContent);
            }
            commentAssemble.setContent(contentStr);
            commentAssemble.setLink("");
            this.sendMessage(commentAssemble);
        } catch (Exception e) {
            logger.error("调用文章出现异常", e);
        }
    }

    public void dynamicPush(long resourceId, String contentPushStr, long contentType, String bePingContent) {
        CommentAssemble commentAssemble = new CommentAssemble();
        try {
           /* Dymaic dymaic = dymaicService.get(resourceId).getData();
            if (!dymaic.getExtJson().equals("")) {
                Map maps = (Map) JSONObject.parse(dymaic.getExtJson());
                String title = maps.get("title").toString();
                String image = maps.get("imgUrl").toString();
                if (contentType == 0) {
                    String imgUrl = "";
                    if (!image.equals("")) {
                        imgUrl = getImgFirstUrl(image);
                        commentAssemble.setImg(imgUrl);
                    }
                    if (!title.equals("")) {
                        commentAssemble.setTitle(title);
                    }
                    String content = maps.get("content").toString();
                    if (title.equals("") && !content.equals("")) {
                        commentAssemble.setTitle(content.substring(0, 20));
                    }
                    commentAssemble.setViewCode((byte) 2);
                }
                if (contentType != 0) {
                    commentAssemble.setViewCode((byte) 1);
                    //截取被回复的评论内容
                    commentAssemble.setTitle(bePingContent);
                }
                commentAssemble.setContent(contentPushStr);
                commentAssemble.setLink("");
                this.sendMessage(commentAssemble);
            }*/
        } catch (Exception e) {
            logger.info("调用动态出现异常" + e);
        }
    }

    public void sendMessage(CommentAssemble commentAssemble) {
        MessageVo messageVo = new MessageVo();
        messageVo.setContent(commentAssemble.getContent());
        messageVo.setMessageId(IdGen.uuid());
        messageVo.setImg(commentAssemble.getImg());
        messageVo.setTitle(commentAssemble.getTitle());
        messageVo.setLink(commentAssemble.getLink());
        messageVo.setActionCode(MessageActionCode.COMMON_DETAIL);
        messageVo.setLabel(MessageLabel.INTERACTIVE_COMMENT);
        messageVo.setToCust(String.valueOf(commentAssemble.getTargetUserId()));
        messageVo.setType(MessageType.SYSTEM_TYPE);
        messageVo.setCreateTime(DateUtils.getDateTime());
        if (commentAssemble.getViewCode() == 2) {
            messageVo.setViewCode(MessageViewCode.SYSTEM_MESSAGE_2);
        } else {
            messageVo.setViewCode(MessageViewCode.SYSTEM_MESSAGE_1);
        }
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
