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
import com.yryz.quanhu.behavior.comment.vo.CommentDetailVO;
import com.yryz.quanhu.behavior.comment.vo.CommentInfoVO;
import com.yryz.quanhu.behavior.comment.vo.CommentListInfoVO;
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

    /*@Reference(check = false)
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
            Comment commentSuccess = commentService.accretion(comment);
            if (null != commentSuccess) {
                UserSimpleVO userBase = userApi.getUserSimple(comment.getTargetUserId()).getData();
                map.put("result", 1);
                try {
                    countApi.commitCount(BehaviorEnum.Comment, comment.getResourceId(), "", 1L);
                } catch (Exception e) {
                    logger.info("进入统计系统失败" + e);
                }
                if (null != userBase) {
                    commentSuccess.setTargetUserNickName(userBase.getUserNickName());
                }
            } else {
                map.put("result", 0);
            }

            return ResponseUtils.returnObjectSuccess(commentSuccess);
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
                if (comment.getTopId() == 0) {
                    this.delBatch(comment);
                } else {
                    try {
                        countApi.commitCount(BehaviorEnum.Comment, comment.getResourceId(), "", -1L);
                    } catch (Exception e) {
                        logger.info("进入统计系统失败" + e);
                    }
                }
                Comment comments = new Comment();
                comments.setKid(comment.getKid());
                Comment commentStr = commentService.querySingleComment(comments);
                try {
                    if (null != commentStr) {
                        stringRedisTemplate.delete("COMMENT:" + commentStr.getModuleEnum() + ":" + commentStr.getKid() + "_" + commentStr.getTopId() + "_" + commentStr.getResourceId());
                    }
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
            if (count > 0) {
                String strKeys = "";
                for (Comment comment : comments) {
                    Comment commentSingle = commentService.querySingleComment(comment);
                    strKeys += "COMMENT:" + commentSingle.getModuleEnum() + ":" + commentSingle.getKid() + "_" + commentSingle.getTopId() + "_" + commentSingle.getResourceId() + ",";
                }
                if (null != strKeys && !strKeys.equals("")) {
                    String[] strArray = strKeys.split(",");
                    for (int i = 0; i < strArray.length; i++) {
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
            PageList<CommentVO> pageList = commentService.querySubCommentsInfo(commentSubDTO);
            if (null != commentInfoVO && null != pageList) {
                commentInfoVO.setCommentEnties(pageList);
            }
            return ResponseUtils.returnObjectSuccess(commentInfoVO);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> updownSingle(Comment comment) {
        try {

            int count = commentService.updownSingle(comment);
            if (count > 0) {
                if (comment.getTopId() == 0) {
                    this.delBatch(comment);
                }
                Comment comments = new Comment();
                comments.setKid(comment.getKid());
                Comment commentStr = commentService.querySingleComment(comments);
                try {
                    if (null != commentStr) {
                        stringRedisTemplate.delete("COMMENT:" + commentStr.getModuleEnum() + ":" + commentStr.getKid() + "_" + commentStr.getTopId() + "_" + commentStr.getResourceId());
                    }
                } catch (Exception e) {
                    logger.info("从redis中移除评论数据失败" + e);
                }
            }
            return ResponseUtils.returnObjectSuccess(count);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    public void delBatch(Comment comment) {
        Comment comments = new Comment();
        comments.setKid(comment.getKid());
        Comment commentStr = commentService.querySingleComment(comments);
        if (comment.getTopId() == 0) {
            CommentFrontDTO commenFront = new CommentFrontDTO();
            commenFront.setTopId(commentStr.getKid());
            commenFront.setResourceId(commentStr.getResourceId());
            commenFront.setModuleEnum(commentStr.getModuleEnum());
            commenFront.setCheckType(1L);
            List<CommentVO> commentVOS = commentService.queryComments(commenFront).getEntities();
            List<Comment> commentsBatch = null;
            if (null != commentVOS && commentVOS.size() > 0) {
                commentsBatch = new ArrayList<Comment>();
                for (CommentVO commentVO : commentVOS) {
                    Comment commentSingle = new Comment();
                    commentSingle.setKid(commentVO.getKid());
                    commentSingle.setResourceId(commentVO.getResourceId());
                    commentSingle.setCreateUserId(commentVO.getCreateUserId());
                    commentSingle.setShelveFlag((byte) 11);
                    commentSingle.setLastUpdateDate(new Date());
                    commentSingle.setLastUpdateUserId(commentVO.getCreateUserId());
                    List<Comment> commentsChildren = commentVO.getChildrenComments();
                    if (null != commentsChildren && commentsChildren.size() > 0) {
                        for (Comment commentChild : commentsChildren) {
                            Comment commentChilds = new Comment();
                            commentChilds.setKid(commentChild.getKid());
                            commentChilds.setResourceId(commentChild.getResourceId());
                            commentChilds.setCreateUserId(commentChild.getCreateUserId());
                            commentChilds.setShelveFlag((byte) 11);
                            commentChilds.setLastUpdateDate(new Date());
                            commentChilds.setLastUpdateUserId(commentChild.getCreateUserId());
                            commentsBatch.add(commentChilds);
                            try {
                                stringRedisTemplate.delete("COMMENT:" + commentChild.getModuleEnum() + ":" + commentChild.getKid() + "_" + commentChild.getTopId() + "_" + commentChild.getResourceId());
                            } catch (Exception e) {
                                logger.info("批量删除Redis子评论失败");
                            }
                        }
                    }

                    commentsBatch.add(commentSingle);
                    try {
                        stringRedisTemplate.delete("COMMENT:" + commentVO.getModuleEnum() + ":" + commentVO.getKid() + "_" + commentVO.getTopId() + "_" + commentVO.getResourceId());
                    } catch (Exception e) {
                        logger.info("删除Redis一级评论失败");
                    }
                }
                try {
                    int batchCount = commentService.updownBatch(commentsBatch);
                    if (batchCount > 0) {
                        try {
                            countApi.commitCount(BehaviorEnum.Comment, comment.getResourceId(), "", Long.valueOf(-commentsBatch.size()));
                        } catch (Exception e) {
                            logger.info("进入统计系统失败" + e);
                        }
                        logger.info("批量删除评论成功");
                    }
                } catch (Exception e) {
                    logger.info("批量删除评论失败" + e);
                }
            }
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
                pingContent = this.getBePingComment(comment.getParentId());
            }
            CommentAssemble commentAssemble = getCommentAssemble(comment, contentStr);
            this.releasePush(commentAssemble, contentType, pingContent);
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
                pingContent = this.getBePingComment(comment.getParentId());
            }
            CommentAssemble commentAssemble = getCommentAssemble(comment, contentStr);
            this.topicPostPush(commentAssemble, contentType, pingContent);
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
                pingContent = this.getBePingComment(comment.getParentId());
            }
            CommentAssemble commentAssemble = getCommentAssemble(comment, contentStr);
            commentAssemble.setTargetUserId(0);
            this.dynamicPush(commentAssemble, contentType, pingContent);
        }

        if (comment.getModuleEnum().equals(ModuleContants.ANSWER)) {
            AnswerVo answerVo = answerApi.getDetail(comment.getResourceId()).getData();
            CommentAssemble commentAssembleAnswer = new CommentAssemble();
            if (null != answerVo.getContent() && answerVo.getContent().length() > 20) {
                commentAssembleAnswer.setTitle(answerVo.getContent().substring(0, 20));
            } else {
                commentAssembleAnswer.setTitle(answerVo.getContent());
            }
            commentAssembleAnswer.setTargetUserId(answerVo.getCreateUserId());
            commentAssembleAnswer.setLink("");
            commentAssembleAnswer.setResourceId(answerVo.getKid());
            commentAssembleAnswer.setModuleEnum(answerVo.getModuleEnum());
            commentAssembleAnswer.setCoterieId(answerVo.getCoterieId());
            commentAssembleAnswer.setContent(nickName + "评论了您的问题。");
            commentAssembleAnswer.setUserImg(comment.getUserImg());
            commentAssembleAnswer.setUserNickName(comment.getNickName());
            if (commentAssembleAnswer.getCoterieId() != 0) {
                if (null != answerVo.getContent() && answerVo.getContent().length() > 7) {
                    commentAssembleAnswer.setCoterieName(answerVo.getContent().substring(0, 7));
                } else {
                    commentAssembleAnswer.setCoterieName(answerVo.getContent());
                }
                if (!answerVo.getImgUrl().equals("")) {
                    String img = getImgFirstUrl(answerVo.getImgUrl());
                    commentAssembleAnswer.setBodyImg(img);
                }
                if (null != answerVo.getContent() && answerVo.getContent().length() > 20) {
                    commentAssembleAnswer.setBodyTitle(answerVo.getContent().substring(0, 20));
                } else {
                    commentAssembleAnswer.setBodyTitle(answerVo.getContent());
                }
            }
            this.sendMessage(commentAssembleAnswer);
            Question question = questionApi.queryDetail(answerVo.getQuestionId()).getData();
            CommentAssemble commentAssembleQuestion = new CommentAssemble();
            commentAssembleQuestion.setTargetUserId(question.getCreateUserId());
            commentAssembleQuestion.setContent(nickName + "评论了您的问题。");
            commentAssembleQuestion.setTitle(question.getTitle());
            commentAssembleQuestion.setLink("");
            commentAssembleQuestion.setResourceId(question.getKid());
            commentAssembleQuestion.setModuleEnum(ModuleContants.QUESTION);
            commentAssembleQuestion.setCoterieId(question.getCoterieId());
            commentAssembleQuestion.setUserImg(comment.getUserImg());
            commentAssembleQuestion.setUserNickName(comment.getNickName());
            if (commentAssembleQuestion.getCoterieId() != 0) {
                if (null != question.getContent() && question.getContent().length() > 7) {
                    commentAssembleQuestion.setCoterieName(question.getContent().substring(0, 7));
                } else {
                    commentAssembleQuestion.setCoterieName(question.getContent());
                }
                commentAssembleQuestion.setBodyImg("");
                if (null != question.getContent() && question.getContent().length() > 20) {
                    commentAssembleQuestion.setBodyTitle(question.getContent().substring(0, 20));
                } else {
                    commentAssembleQuestion.setBodyTitle(question.getContent());
                }
            }
            this.sendMessage(commentAssembleQuestion);
        }
    }

    public static CommentAssemble getCommentAssemble(Comment comment, String contentStr) {
        CommentAssemble commentAssemble = new CommentAssemble();
        commentAssemble.setResourceId(comment.getResourceId());
        commentAssemble.setModuleEnum(comment.getModuleEnum());
        commentAssemble.setCoterieId(comment.getCoterieId());
        commentAssemble.setContent(contentStr);
        commentAssemble.setTargetUserId(comment.getTargetUserId());
        commentAssemble.setUserImg(comment.getUserImg());
        commentAssemble.setUserNickName(comment.getNickName());
        return commentAssemble;
    }

    public String getBePingComment(Long kid) {
        Comment comentSub = new Comment();
        comentSub.setKid(kid);
        Comment commentSingle = commentService.querySingleComment(comentSub);
        String pingContent = "";
        if (null != commentSingle) {
            if (null != commentSingle.getContentComment() && commentSingle.getContentComment().length() > 20) {
                pingContent = commentSingle.getContentComment().substring(0, 20);
            } else {
                pingContent = commentSingle.getContentComment();
            }

        }
        return pingContent;
    }

    public void topicPostPush(CommentAssemble commentAssemble, long contentType, String bePingContent) {

        try {
            TopicPostVo topicPostVo = topicPostApi.quetyDetail(commentAssemble.getResourceId(), commentAssemble.getResourceUserId()).getData();
            if (contentType == 0) {
                if (!topicPostVo.getImgUrl().equals("")) {
                    String img = getImgFirstUrl(topicPostVo.getImgUrl());
                    commentAssemble.setImg(img);
                }
                if (null != topicPostVo.getContent() && topicPostVo.getContent().length() > 20) {
                    commentAssemble.setTitle(topicPostVo.getContent().substring(0, 20));
                } else {
                    commentAssemble.setTitle(topicPostVo.getContent());
                }
            }
            if (contentType != 0) {
                commentAssemble.setViewCode((byte) 1);
                //截取被回复的评论内容
                commentAssemble.setTitle(bePingContent);
            }
            if (commentAssemble.getCoterieId() != 0 && null != topicPostVo.getContent()) {
                if (topicPostVo.getContent().length() > 7) {
                    commentAssemble.setCoterieName(topicPostVo.getContent().substring(0, 7));
                } else {
                    commentAssemble.setCoterieName(topicPostVo.getContent());
                }
                if (!topicPostVo.getImgUrl().equals("")) {
                    String img = getImgFirstUrl(topicPostVo.getImgUrl());
                    commentAssemble.setBodyImg(img);
                }
                commentAssemble.setBodyTitle(topicPostVo.getContent());
            }
            this.sendMessage(commentAssemble);
        } catch (Exception e) {
            logger.error("调用文章出现异常", e);
        }
    }

    public void releasePush(CommentAssemble commentAssemble, long contentType, String bePingContent) {
        try {
            ReleaseInfoVo releaseInfoVo = releaseInfoApi.infoByKid(commentAssemble.getResourceId(), commentAssemble.getResourceUserId()).getData();
            commentAssemble.setTargetUserId(releaseInfoVo.getCreateUserId());
            String img = "";
            if (contentType == 0) {
                if (!releaseInfoVo.getImgUrl().equals("")) {
                    img = getImgFirstUrl(releaseInfoVo.getImgUrl());
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
            if (commentAssemble.getCoterieId() != 0 && null != releaseInfoVo.getTitle()) {
                if (releaseInfoVo.getTitle().length() > 7) {
                    commentAssemble.setCoterieName(releaseInfoVo.getTitle().substring(0, 7));
                } else {
                    commentAssemble.setCoterieName(releaseInfoVo.getTitle());
                }
                if (!releaseInfoVo.getImgUrl().equals("")) {
                    img = getImgFirstUrl(releaseInfoVo.getImgUrl());
                    commentAssemble.setBodyImg(img);
                }
                if (releaseInfoVo.getTitle().length() > 20) {
                    commentAssemble.setBodyTitle(releaseInfoVo.getTitle().substring(0, 20));
                } else {
                    commentAssemble.setBodyTitle(releaseInfoVo.getTitle());
                }
            }
            this.sendMessage(commentAssemble);
        } catch (Exception e) {
            logger.error("调用文章出现异常", e);
        }
    }

    public void dynamicPush(CommentAssemble commentAssemble, long contentType, String bePingContent) {
       /* try {
            Dymaic dymaic = dymaicService.get(commentAssemble.getResourceId()).getData();
            if (!dymaic.getExtJson().equals("")) {
                Map maps = (Map) JSONObject.parse(dymaic.getExtJson());
                String title = maps.get("title").toString();
                String image = maps.get("imgUrl").toString();
                String content = "";
                if (contentType == 0) {
                    String imgUrl = "";
                    if (!image.equals("")) {
                        imgUrl = getImgFirstUrl(image);
                        commentAssemble.setImg(imgUrl);
                    }
                    if (!title.equals("")) {
                        commentAssemble.setTitle(title);
                    }
                    content = maps.get("content").toString();
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
                if (commentAssemble.getCoterieId() != 0) {
                    if (!title.equals("") && title.length() > 7) {
                        commentAssemble.setCoterieName(title.substring(0, 7));
                    } else {
                        commentAssemble.setCoterieName(title);
                    }
                    if(!title.equals("") && title.length() > 20){
                        commentAssemble.setBodyTitle(content.substring(0,20));
                    }else{
                        commentAssemble.setBodyTitle(title);
                    }
                    if (title.equals("") && !content.equals("")) {
                        if (content.length() > 7) {
                            commentAssemble.setCoterieName(content.substring(0, 7));
                        } else {
                            commentAssemble.setCoterieName(content);
                        }
                        if(content.length()>20){
                            commentAssemble.setBodyTitle(content.substring(0,20));
                        }else{
                            commentAssemble.setBodyTitle(content);
                        }
                    }
                    if (!image.equals("")) {
                        String imgUrl = getImgFirstUrl(image);
                        commentAssemble.setBodyImg(imgUrl);
                    }
                }
                this.sendMessage(commentAssemble);
            }
        } catch (Exception e) {
            logger.info("调用动态出现异常" + e);
        }*/
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
        messageVo.setType(MessageType.INTERACTIVE_TYPE);
        messageVo.setModuleEnum(commentAssemble.getModuleEnum());
        messageVo.setCoterieId(String.valueOf(commentAssemble.getCoterieId()));
        messageVo.setResourceId(String.valueOf(commentAssemble.getResourceId()));
        messageVo.setCreateTime(DateUtils.getDateTime());
        messageVo.setViewCode(MessageViewCode.INTERACTIVE_MESSAGE);
        InteractiveBody body = new InteractiveBody();
        body.setUserImg(commentAssemble.getUserImg());
        body.setUserNickName(commentAssemble.getUserNickName());
        body.setCoterieId(String.valueOf(commentAssemble.getCoterieId()));
        body.setCoterieName(commentAssemble.getCoterieName());
        body.setBodyImg(commentAssemble.getBodyImg());
        body.setBodyTitle(commentAssemble.getBodyTitle());
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

	@Override
	public Response<PageList<CommentListInfoVO>> listComments(CommentFrontDTO commentFrontDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<CommentDetailVO> queryCommentDetail(CommentSubDTO commentSubDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
