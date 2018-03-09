package com.yryz.quanhu.behavior.common.manager;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.message.InteractiveBody;
import com.yryz.common.message.MessageActionCode;
import com.yryz.common.message.MessageLabel;
import com.yryz.common.message.MessageType;
import com.yryz.common.message.MessageViewCode;
import com.yryz.common.message.MessageVo;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.IdGen;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.behavior.comment.dto.CommentAssemble;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import com.yryz.quanhu.behavior.comment.service.CommentService;
import com.yryz.quanhu.behavior.common.service.RemoteResourceService;
import com.yryz.quanhu.behavior.common.util.ThreadPoolUtil;
import com.yryz.quanhu.behavior.common.vo.RemoteResource;
import com.yryz.quanhu.behavior.like.dto.LikeAssemble;
import com.yryz.quanhu.behavior.like.entity.Like;
import com.yryz.quanhu.dymaic.service.DymaicService;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.quanhu.message.push.api.PushAPI;
import com.yryz.quanhu.message.push.entity.PushReqVo;
import com.yryz.quanhu.resource.questionsAnswers.api.AnswerApi;
import com.yryz.quanhu.resource.questionsAnswers.api.QuestionApi;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.vo.AnswerVo;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoApi;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;
import com.yryz.quanhu.resource.topic.api.TopicPostApi;
import com.yryz.quanhu.resource.topic.vo.TopicPostVo;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
 * 消息代理
 * 
 * @author danshiyu
 *
 */
@Service
public class MessageManager {
	private static final Logger logger = LoggerFactory.getLogger(MessageManager.class);

	@Reference(check = false)
	private MessageAPI messageAPI;
	@Reference(check = false)
	private ReleaseInfoApi releaseInfoApi;
	@Autowired
	private CommentService commentService;
	@Reference(check = false)
	private PushAPI pushAPI;

	@Reference(check = false)
	private TopicPostApi topicPostApi;
	@Reference(check = false)
	private DymaicService dymaicService;
	@Autowired
	private UserRemote userRemote;
	@Autowired
	private RemoteResourceService resourceService;
	@Reference(check = false)
	private AnswerApi answerApi;

	@Reference(check = false)
	private QuestionApi questionApi;

	/**
	 * 评论消息推送和持久化消息
	 * 
	 * @param comment
	 */
	public void commentSendMsg(Comment comment) {
		logger.info("[commentSendMsg]:start.............");
		logger.info("[commentSendMsg]:params:{}", JsonUtils.toFastJson(comment));
		ThreadPoolUtil.execue(new Runnable() {
			@Override
			public void run() {
				try {
					switchSend(comment);
					switchPushMsg(comment);
				} catch (Exception e) {
					logger.error("持久化消息失败:", e);
				}
			}
		});
	}

	/**
	 * 评论下架推送
	 * 
	 * @param comments
	 */
	public void commentUpdownSendMsg(List<Comment> comments) {
		if (CollectionUtils.isEmpty(comments)) {
			return;
		}
		ThreadPoolUtil.execue(new Runnable() {
			@Override
			public void run() {

				logger.info("[commentUpdownSendMsg]:start.............");
				logger.info("[commentUpdownSendMsg]:params:{}", JsonUtils.toFastJson(comments));
				try {
					Set<String> userIds = new HashSet<>();
					for (int i = 0; i < comments.size(); i++) {
						userIds.add(comments.get(i).getCreateUserId().toString());
					}
					pushMessage(Lists.newArrayList(userIds), "您的评论有违纪嫌疑,已被管理员下架!");
				} catch (Exception e) {
					logger.error("[commentSendMsg]", e);
				}
			}
		});

	}

	/**
	 * 点赞持久化消息发送
	 * 
	 * @param like
	 */
	public void likeSendMsg(Like like) {
		logger.info("[likeSendMsg]:start.............");
		logger.info("[likeSendMsg]:params:{}", JsonUtils.toFastJson(like));
		ThreadPoolUtil.execue(new Runnable() {
			@Override
			public void run() {
				try {
					switchSend(like);
				} catch (Exception e) {
					logger.error("持久化消息失败:", e);
				}
			}
		});
	}

	/**
	 * 评论持久化消息发送
	 * 
	 * @param comment
	 */
	private void switchSend(Comment comment) {
		UserSimpleVO userSimpleVO = userRemote.getUserInfo(comment.getCreateUserId());
		String nickName = "";
		if (null != userSimpleVO) {
			nickName = userSimpleVO.getUserNickName();
			comment.setNickName(nickName);
			comment.setUserImg(userSimpleVO.getUserImg());
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
			if (answerVo == null) {
				return;
			}
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
				commentAssembleAnswer.setCoterieName(StringUtils.substring(answerVo.getContent(),0, 7));
			}
			if (StringUtils.isNotBlank(answerVo.getImgUrl())) {
				String img = getImgFirstUrl(answerVo.getImgUrl());
				commentAssembleAnswer.setBodyImg(img);
			}
			commentAssembleAnswer.setBodyTitle(StringUtils.substring(answerVo.getContent(),0, 20));
			
			this.sendMessage(commentAssembleAnswer);
			Question question = questionApi.queryDetail(answerVo.getQuestionId()).getData();
			if (question == null) {
				return;
			}
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
			}
			commentAssembleQuestion.setBodyImg("");
			commentAssembleQuestion.setBodyTitle(StringUtils.substring(question.getContent(),0, 20));
			this.sendMessage(commentAssembleQuestion);
		}
	}

	/**
	 * 点赞持久化消息发送
	 * 
	 * @param like
	 */
	private void switchSend(Like like) {
		UserSimpleVO userSimpleVO = userRemote.getUserInfo(like.getUserId());
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
			if (answerVo == null) {
				return;
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
			}
			if (StringUtils.isNotBlank(answerVo.getImgUrl())) {
				String img = getImgFirstUrl(answerVo.getImgUrl());
				likeAssembleAnswer.setBodyImg(img);
			}
			likeAssembleAnswer.setBodyTitle(StringUtils.substring(answerVo.getContent(),0, 20));
			this.sendMessage(likeAssembleAnswer);
			
			Question question = ResponseUtils.getResponseData(questionApi.queryDetail(answerVo.getQuestionId()));
			if (question == null) {
				return;
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
			}
			likeAssembleQuestion.setBodyImg("");
			likeAssembleQuestion.setBodyTitle(StringUtils.substring(question.getContent(),0, 20));
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
			/*String contentStr = nickName + "点赞了您的评论。";
			LikeAssemble likeAssemble = getLikeAssemble(like, contentStr);
			likeAssemble.setUserImg(userSimpleVO.getUserImg());
			likeAssemble.setUserNickName(userSimpleVO.getUserNickName());
			
			RemoteResource resource = resourceService.get(resourceId);
			this.releasePush(likeAssemble);
			this.topicPostPush(likeAssemble);
			if (like.getCoterieId() == 0) {
				like.setResourceUserId(0);
				this.dynamicPush(likeAssemble);
			}
*/
		}

	}

	private static CommentAssemble getCommentAssemble(Comment comment, String contentStr) {
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

	private static LikeAssemble getLikeAssemble(Like like, String contentStr) {
		LikeAssemble likeAssemble = new LikeAssemble();
		likeAssemble.setResourceId(like.getResourceId());
		likeAssemble.setModuleEnum(like.getModuleEnum());
		likeAssemble.setResourceUserId(like.getResourceUserId());
		likeAssemble.setContent(contentStr);
		return likeAssemble;
	}

	private String getBePingComment(Long kid) {
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

	/**
	 * 帖子评论消息
	 * 
	 * @param commentAssemble
	 * @param contentType
	 * @param bePingContent
	 */
	private void topicPostPush(CommentAssemble commentAssemble, long contentType, String bePingContent) {
		try {
			TopicPostVo topicPostVo = ResponseUtils.getResponseData(
					topicPostApi.quetyDetail(commentAssemble.getResourceId(), commentAssemble.getResourceUserId()));
			if (topicPostVo == null) {
				logger.info("[topicPostPush]:commentAssemble:{},errorMsg:帖子查询失败",
						JsonUtils.toFastJson(commentAssemble));
				throw QuanhuException.busiError("帖子查询失败");
			}
			if (contentType == 0) {
				if (StringUtils.isNotBlank(topicPostVo.getImgUrl())) {
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
				// 截取被回复的评论内容
				commentAssemble.setTitle(bePingContent);
			}
			if (null != topicPostVo.getContent()) {
				if (topicPostVo.getContent().length() > 7) {
					commentAssemble.setCoterieName(topicPostVo.getContent().substring(0, 7));
				} else {
					commentAssemble.setCoterieName(topicPostVo.getContent());
				}
				if (StringUtils.isNotBlank(topicPostVo.getImgUrl())) {
					String img = getImgFirstUrl(topicPostVo.getImgUrl());
					commentAssemble.setBodyImg(img);
				}
				commentAssemble.setBodyTitle(topicPostVo.getContent());
			}
			this.sendMessage(commentAssemble);
		} catch (QuanhuException e) {
			logger.error("[comment_message_get_topic_error]", e);
			throw QuanhuException.busiError(e);
		} catch (Exception e) {
			logger.error("[comment_message_get_topic_error]", e);
			throw QuanhuException.busiError(e.getMessage());
		}
	}

	/**
	 * 帖子点赞消息
	 * 
	 * @param likeAssemble
	 */
	private void topicPostPush(LikeAssemble likeAssemble) {
		try {
			TopicPostVo topicPostVo = ResponseUtils.getResponseData(
					topicPostApi.quetyDetail(likeAssemble.getResourceId(), likeAssemble.getResourceUserId()));
			if (topicPostVo == null) {
				logger.info("[topicPostPush]:likeAssemble:{},errorMsg:帖子查询失败", JsonUtils.toFastJson(likeAssemble));
				throw QuanhuException.busiError("帖子查询失败");
			}
			likeAssemble.setTargetUserId(topicPostVo.getUser().getUserId());
			if (null != topicPostVo.getContent() && topicPostVo.getContent().length() > 20) {
				likeAssemble.setTitle(topicPostVo.getContent().substring(0, 20));
			} else {
				likeAssemble.setTitle(topicPostVo.getContent());
			}
			if (StringUtils.isNotBlank(topicPostVo.getImgUrl())) {
				String img = getImgFirstUrl(topicPostVo.getImgUrl());
				likeAssemble.setImg(img);
			}
			if (null != topicPostVo.getContent()) {
				if (topicPostVo.getContent().length() > 7) {
					likeAssemble.setCoterieName(topicPostVo.getContent().substring(0, 7));
				} else {
					likeAssemble.setCoterieName(topicPostVo.getContent());
				}
				if (StringUtils.isNotBlank(topicPostVo.getImgUrl())) {
					String img = getImgFirstUrl(topicPostVo.getImgUrl());
					likeAssemble.setBodyImg(img);
				}
				likeAssemble.setBodyTitle(topicPostVo.getContent());
				likeAssemble.setCoterieId(topicPostVo.getCoterieId());
			}
			this.sendMessage(likeAssemble);
		} catch (QuanhuException e) {
			logger.error("[like_message_get_topic_error]", e);
			throw QuanhuException.busiError(e);
		} catch (Exception e) {
			logger.error("[like_message_get_topic_error]", e);
			throw QuanhuException.busiError(e.getMessage());
		}
	}

	/**
	 * 动态评论消息
	 * 
	 * @param commentAssemble
	 * @param contentType
	 * @param bePingContent
	 */
	private void dynamicPush(CommentAssemble commentAssemble, long contentType, String bePingContent) {
		try {
			Dymaic dymaic = ResponseUtils.getResponseData(dymaicService.get(commentAssemble.getResourceId()));
			if (dymaic == null) {
				logger.info("[dynamicPush]:commentAssemble:{},errorMsg:动态查询失败", JsonUtils.toFastJson(commentAssemble));
				throw QuanhuException.busiError("动态查询失败");
			}
			if (StringUtils.isNotBlank(dymaic.getExtJson())) {
				Map<String, Object> maps = JsonUtils.fromJson(dymaic.getExtJson(),
						new TypeReference<Map<String, Object>>() {
						});
				String title = StringUtils.toString(maps.get("title"), "");
				String image = StringUtils.toString(maps.get("imgUrl"), "");
				commentAssemble.setTargetUserId(dymaic.getUserId());
				String content = "";
				if (contentType == 0) {
					String imgUrl = "";
					if (StringUtils.isNotBlank(image)) {
						imgUrl = getImgFirstUrl(image);
						commentAssemble.setImg(imgUrl);
					}
					if (StringUtils.isNotBlank(title)) {
						commentAssemble.setTitle(title);
					}
					content = StringUtils.toString(maps.get("content"), "");
					if (StringUtils.isBlank(title) && StringUtils.isNotBlank(content)) {
						commentAssemble.setTitle(StringUtils.substring(content, 0, 20));
					}
					commentAssemble.setViewCode((byte) 2);
				}
				if (contentType != 0) {
					commentAssemble.setViewCode((byte) 1);
					// 截取被回复的评论内容
					commentAssemble.setTitle(bePingContent);
				}
				if (commentAssemble.getCoterieId() != 0) {
					/*
					 * if (!title.equals("") && title.length() > 7) {
					 * commentAssemble.setCoterieName(title.substring(0, 7)); }
					 * else { commentAssemble.setCoterieName(title); }
					 */
					commentAssemble.setCoterieName(StringUtils.substring(title, 0, 7));

					if (StringUtils.isBlank(title) && StringUtils.isNotBlank(content)) {
						/*
						 * if (content.length() > 7) {
						 * commentAssemble.setCoterieName(content.substring(0,
						 * 7)); } else {
						 * commentAssemble.setCoterieName(content); }
						 */

						commentAssemble.setCoterieName(StringUtils.substring(content, 0, 7));
					}
				}
				/*
				 * if (!title.equals("") && title.length() > 20) {
				 * commentAssemble.setBodyTitle(content.substring(0, 20)); }
				 * else { commentAssemble.setBodyTitle(title); }
				 */
				commentAssemble.setBodyTitle(StringUtils.substring(content, 0, 20));

				if (StringUtils.isBlank(title) && StringUtils.isNotBlank(content)) {
					/*
					 * if (content.length() > 20) {
					 * commentAssemble.setBodyTitle(content.substring(0,
					 * 20)); } else { commentAssemble.setBodyTitle(content);
					 * }
					 */

					commentAssemble.setBodyTitle(StringUtils.substring(content, 0, 20));
				}
				if (StringUtils.isNotBlank(image)) {
					String imgUrl = getImgFirstUrl(image);
					commentAssemble.setBodyImg(imgUrl);
				}
				this.sendMessage(commentAssemble);
			}
		} catch (QuanhuException e) {
			logger.error("[comment_message_get_dymaic_error]", e);
			throw QuanhuException.busiError(e);
		} catch (Exception e) {
			logger.error("[comment_message_get_dymaic_error]", e);
			throw QuanhuException.busiError(e.getMessage());
		}
	}

	/**
	 * 动态点赞消息
	 * 
	 * @param likeAssemble
	 */
	private void dynamicPush(LikeAssemble likeAssemble) {
		try {
			Dymaic dymaic = ResponseUtils.getResponseData(dymaicService.get(likeAssemble.getResourceId()));
			if (dymaic == null) {
				logger.info("[dynamicPush]:likeAssemble:{},errorMsg:动态查询失败", JsonUtils.toFastJson(likeAssemble));
				throw QuanhuException.busiError("动态查询失败");
			}
			if (StringUtils.isNotBlank(dymaic.getExtJson())) {
				Map<String, Object> maps = JsonUtils.fromJson(dymaic.getExtJson(),
						new TypeReference<Map<String, Object>>() {
						});
				String title = StringUtils.toString(maps.get("title"), "");
				String image = StringUtils.toString(maps.get("imgUrl"), "");
				String coterieId = StringUtils.toString(maps.get("coterieId"), "0");
				String imgUrl = "";
				if (StringUtils.isNoneBlank(image)) {
					imgUrl = getImgFirstUrl(image);
					likeAssemble.setImg(imgUrl);
				}
				likeAssemble.setTargetUserId(dymaic.getUserId());
				if (StringUtils.isNoneBlank(title)) {
					likeAssemble.setTitle(title);
				}
				String content = StringUtils.toString(maps.get("content"), "");
				if (StringUtils.isBlank(title) && StringUtils.isNoneBlank(content)) {
					likeAssemble.setTitle(StringUtils.substring(content, 0, 20));
				}
				if (StringUtils.equals(coterieId, "0")) {
					/*
					 * if (StringUtils.isNotBlank(title) && title.length() > 7)
					 * {
					 * likeAssemble.setCoterieName(StringUtils.substring(title,
					 * 0, 7)); } else { likeAssemble.setCoterieName(title); }
					 */
					likeAssemble.setCoterieName(StringUtils.substring(title, 0, 7));
					if (StringUtils.isBlank(title) && StringUtils.isNotBlank(content)) {
						/*
						 * if (content.length() > 7) {
						 * likeAssemble.setCoterieName(content.substring(0, 7));
						 * } else { likeAssemble.setCoterieName(content); }
						 */
						likeAssemble.setCoterieName(StringUtils.substring(content, 0, 7));
					}
					
					likeAssemble.setCoterieId(Long.valueOf(coterieId));
				}
				/*
				 * if (!title.equals("") && title.length() > 20) {
				 * likeAssemble.setBodyTitle(content.substring(0, 20)); }
				 * else { likeAssemble.setBodyTitle(title); }
				 */
				likeAssemble.setBodyTitle(StringUtils.substring(content, 0, 20));

				if (StringUtils.isBlank(title) && StringUtils.isNotBlank(content)) {

					/*
					 * if (content.length() > 20) {
					 * likeAssemble.setBodyTitle(content.substring(0, 20));
					 * } else { likeAssemble.setBodyTitle(content); }
					 */
					likeAssemble.setBodyTitle(StringUtils.substring(content, 0, 20));
				}
				if (StringUtils.isNotBlank(image)) {
					String imgUrls = getImgFirstUrl(image);
					likeAssemble.setBodyImg(imgUrls);
				}
				this.sendMessage(likeAssemble);
			}
		} catch (QuanhuException e) {
			logger.error("[like_message_get_dymaic_error]", e);
			throw QuanhuException.busiError(e);
		} catch (Exception e) {
			logger.error("[like_message_get_dymaic_error]", e);
			throw QuanhuException.busiError(e.getMessage());
		}
	}

	/**
	 * 文章评论消息
	 * 
	 * @param commentAssemble
	 * @param contentType
	 * @param bePingContent
	 */
	private void releasePush(CommentAssemble commentAssemble, long contentType, String bePingContent) {
		try {
			ReleaseInfoVo releaseInfoVo = ResponseUtils.getResponseData(
					releaseInfoApi.infoByKid(commentAssemble.getResourceId(), commentAssemble.getResourceUserId()));
			if (releaseInfoVo == null) {
				logger.info("[releasePush]:commentAssemble:{},errorMsg:文章查询失败", JsonUtils.toFastJson(commentAssemble));
				throw QuanhuException.busiError("文章查询失败");
			}
			commentAssemble.setTargetUserId(releaseInfoVo.getCreateUserId());
			String img = "";
			if (contentType == 0) {
				if (StringUtils.isNoneBlank(releaseInfoVo.getImgUrl())) {
					img = getImgFirstUrl(releaseInfoVo.getImgUrl());
					commentAssemble.setImg(img);
				}
				commentAssemble.setTitle(releaseInfoVo.getTitle());
				commentAssemble.setViewCode((byte) 2);
			}
			if (contentType != 0) {
				commentAssemble.setViewCode((byte) 1);
				// 截取被回复的评论内容
				commentAssemble.setTitle(bePingContent);
			}
			if (commentAssemble.getCoterieId() != 0 && null != releaseInfoVo.getTitle()) {
				/*
				 * if (releaseInfoVo.getTitle().length() > 7) {
				 * commentAssemble.setCoterieName(releaseInfoVo.getTitle().
				 * substring(0, 7)); } else {
				 * commentAssemble.setCoterieName(releaseInfoVo.getTitle()); }
				 */
				commentAssemble.setCoterieName(StringUtils.substring(releaseInfoVo.getTitle(), 0, 7));
			}
			if (StringUtils.isNoneBlank(releaseInfoVo.getImgUrl())) {
				img = getImgFirstUrl(releaseInfoVo.getImgUrl());
				commentAssemble.setBodyImg(img);
			}
			/*
			 * if (releaseInfoVo.getTitle().length() > 20) {
			 * commentAssemble.setBodyTitle(releaseInfoVo.getTitle().
			 * substring(0, 20)); } else {
			 * commentAssemble.setBodyTitle(releaseInfoVo.getTitle()); }
			 */
			commentAssemble.setBodyTitle(StringUtils.substring(releaseInfoVo.getTitle(), 0, 20));
			this.sendMessage(commentAssemble);
		} catch (QuanhuException e) {
			logger.error("[comment_message_get_release_error]", e);
			throw QuanhuException.busiError(e);
		} catch (Exception e) {
			logger.error("[comment_message_get_release_error]", e);
			throw QuanhuException.busiError(e.getMessage());
		}
	}

	/**
	 * 文章点赞消息
	 * 
	 * @param likeAssemble
	 */
	private void releasePush(LikeAssemble likeAssemble) {
		try {
			ReleaseInfoVo releaseInfoVo = ResponseUtils.getResponseData(
					releaseInfoApi.infoByKid(likeAssemble.getResourceId(), likeAssemble.getResourceUserId()));
			if (releaseInfoVo == null) {
				logger.info("[releasePush]:likeAssemble:{},errorMsg:文章查询失败", JsonUtils.toFastJson(likeAssemble));
				throw QuanhuException.busiError("文章查询失败");
			}
			likeAssemble.setTitle(releaseInfoVo.getTitle());
			likeAssemble.setTargetUserId(releaseInfoVo.getCreateUserId());
			if (StringUtils.isNoneBlank(releaseInfoVo.getImgUrl())) {
				String img = getImgFirstUrl(releaseInfoVo.getImgUrl());
				likeAssemble.setImg(img);
			}
			if (releaseInfoVo.getCoterieId() != 0 && null != releaseInfoVo.getTitle()) {
				if (releaseInfoVo.getTitle().length() > 7) {
					likeAssemble.setCoterieName(releaseInfoVo.getTitle().substring(0, 7));
				} else {
					likeAssemble.setCoterieName(releaseInfoVo.getTitle());
				}

				likeAssemble.setCoterieId(releaseInfoVo.getCoterieId());
			}
			if (StringUtils.isNoneBlank(releaseInfoVo.getImgUrl())) {
				String img = getImgFirstUrl(releaseInfoVo.getImgUrl());
				likeAssemble.setBodyImg(img);
			}
			likeAssemble.setBodyTitle(StringUtils.substring(releaseInfoVo.getTitle(),0, 20));
			this.sendMessage(likeAssemble);
		} catch (QuanhuException e) {
			logger.error("[like_message_get_release_error]", e);
			throw QuanhuException.busiError(e);
		} catch (Exception e) {
			logger.error("[like_message_get_release_error]", e);
			throw QuanhuException.busiError(e.getMessage());
		}
	}

	/**
	 * 一级评论评论极光消息推送
	 * 
	 * @param comment
	 */
	private void switchPushMsg(Comment comment) {
		if (comment.getTopId() != 0l) {
			return;
		}
		UserSimpleVO userSimpleVO = userRemote.getUserInfo(comment.getCreateUserId());
		String nickName = userSimpleVO.getUserNickName();
		if (comment.getModuleEnum().equals(ModuleContants.TOPIC_POST)) {
			pushMessage(String.valueOf(comment.getTargetUserId()), nickName + "评论了您发布的帖子。");
		}
		if (comment.getModuleEnum().equals(ModuleContants.RELEASE)) {
			pushMessage(String.valueOf(comment.getTargetUserId()), nickName + "评论了您发布的内容。");
		}
		return;
	}

	/**
	 * 评论持久化消息
	 * 
	 * @param commentAssemble
	 */
	private void sendMessage(CommentAssemble commentAssemble) {
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
		messageVo.setBody(body);

		try {
			messageAPI.sendMessage(messageVo, false);
			logger.info("[comment_message]:messageVo:{}", JsonUtils.toFastJson(messageVo));
		} catch (Exception e) {
			logger.error("[message_send]", e);
		}

	}

	/**
	 * 点赞持久化消息
	 * 
	 * @param likeAssemble
	 */
	private void sendMessage(LikeAssemble likeAssemble) {
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
		body.setBodyImg(
				StringUtils.isBlank(likeAssemble.getBodyImg()) ? likeAssemble.getImg() : likeAssemble.getBodyImg());
		body.setBodyTitle(likeAssemble.getBodyTitle());
		messageVo.setBody(body);
		try {
			messageAPI.sendMessage(messageVo, false);
			logger.info("[like_message]:mesasge:{}", JsonUtils.toFastJson(messageVo));
		} catch (Exception e) {
			logger.error("持久化消息失败:", e);
		}
	}

	private void pushMessage(String targetUserId, String msg) {
		pushMessage(Lists.newArrayList(targetUserId), msg);
	}

	private void pushMessage(List<String> targetUserIds, String msg) {
		PushReqVo pushReqVo = new PushReqVo();
		pushReqVo.setCustIds(targetUserIds);
		pushReqVo.setMsg(msg);
		pushReqVo.setPushType(PushReqVo.CommonPushType.BY_ALIAS);
		try {
			pushAPI.commonSendAlias(pushReqVo);
			logger.info("[push_send]:pushReqVo:{}", JsonUtils.toFastJson(pushReqVo));
		} catch (Exception e) {
			logger.error("[push_send]", e);
		}
	}

	private static String getImgFirstUrl(String imgs) {
		String[] arries = StringUtils.split(imgs, ",");
		if (ArrayUtils.isNotEmpty(arries)) {
			return arries[0];
		} else {
			return imgs;
		}
	}
}
