package com.yryz.quanhu.resource.topic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yryz.common.constant.CommonConstants;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.message.MessageConstant;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.read.api.ReadApi;
import com.yryz.quanhu.message.message.entity.Message;
import com.yryz.quanhu.resource.api.ResourceDymaicApi;
import com.yryz.quanhu.resource.enums.ResourceTypeEnum;
import com.yryz.quanhu.resource.questionsAnswers.service.APIservice;
import com.yryz.quanhu.resource.questionsAnswers.service.SendMessageService;
import com.yryz.quanhu.resource.questionsAnswers.service.impl.SendMessageServiceImpl;
import com.yryz.quanhu.resource.questionsAnswers.vo.MessageBusinessVo;
import com.yryz.quanhu.resource.topic.dao.TopicDao;
import com.yryz.quanhu.resource.topic.dao.TopicPostDao;
import com.yryz.quanhu.resource.topic.dto.TopicPostDto;
import com.yryz.quanhu.resource.topic.entity.Topic;
import com.yryz.quanhu.resource.topic.entity.TopicPost;
import com.yryz.quanhu.resource.topic.entity.TopicPostExample;
import com.yryz.quanhu.resource.topic.entity.TopicPostWithBLOBs;
import com.yryz.quanhu.resource.topic.service.TopicPostService;
import com.yryz.quanhu.resource.topic.service.TopicService;
import com.yryz.quanhu.resource.topic.vo.BehaviorVo;
import com.yryz.quanhu.resource.topic.vo.TopicAndPostVo;
import com.yryz.quanhu.resource.topic.vo.TopicPostVo;
import com.yryz.quanhu.resource.topic.vo.TopicVo;
import com.yryz.quanhu.resource.vo.ResourceTotal;
import net.sf.jsqlparser.statement.select.Top;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TopicPostServiceImpl implements TopicPostService {

    @Autowired
    private TopicPostDao topicPostDao;

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private APIservice apIservice;

    @Autowired
    private TopicService topicService;

    @Reference
    private CountApi countApi;

    @Reference
    private ReadApi readApi;

    @Autowired
    private SendMessageService sendMessageService;

    @Reference
    private ResourceDymaicApi resourceDymaicApi;

    /**
     * 发布帖子
     * @param topicPostDto
     * @return
     */
    @Override
    public Integer saveTopicPost(TopicPostDto topicPostDto) {
        /**
         * 校验参数
         */
        Long topicId = topicPostDto.getTopicId();
        Long createUserId = topicPostDto.getCreateUserId();
        if (null == topicId || null == createUserId) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        String imgUrl=topicPostDto.getImgUrl();
        String viderUrl=topicPostDto.getVideoUrl();
        String content=topicPostDto.getContent();
        Topic topic=this.topicDao.selectByPrimaryKey(topicId);
        if(null==topic){
            throw QuanhuException.busiError("跟帖的话题不存在");
        }
        if(StringUtils.isNotBlank(imgUrl) && StringUtils.isNotBlank(viderUrl)){
            throw QuanhuException.busiError("图片和视频不能同时发布");
        }
        if(StringUtils.isBlank(imgUrl) && StringUtils.isBlank(viderUrl) && StringUtils.isBlank(content)){
            throw QuanhuException.busiError("文本，视频，图片不能都为空");
        }
        TopicPostWithBLOBs topicPost = new TopicPostWithBLOBs();
        BeanUtils.copyProperties(topicPostDto, topicPost);
        topicPost.setKid(apIservice.getKid());
        topicPost.setCreateDate(new Date());
        topicPost.setCityCode("");
        topicPost.setGps("");
        topicPost.setDelFlag(CommonConstants.DELETE_NO);
        topicPost.setShelveFlag(CommonConstants.SHELVE_YES);

        Integer result= this.topicPostDao.insertSelective(topicPost);

        /**
         * 发送消息
         */
        MessageBusinessVo messageBusinessVo=new MessageBusinessVo();
        messageBusinessVo.setImgUrl(topicPost.getImgUrl());
        messageBusinessVo.setTitle(topicPost.getContent());
        messageBusinessVo.setTosendUserId(topic.getCreateUserId());
        messageBusinessVo.setModuleEnum(ResourceTypeEnum.POSTS);
        messageBusinessVo.setKid(topicPost.getKid());
        messageBusinessVo.setIsAnonymity(null);
        messageBusinessVo.setCoterieId(null);
        sendMessageService.sendNotify4Question(messageBusinessVo, MessageConstant.TOPIC_HAVE_POST,true);

        /**
         * 资源聚合
         */
        ResourceTotal resourceTotal=new ResourceTotal();
        resourceTotal.setCreateDate(DateUtils.getDate());
        TopicPostWithBLOBs post=this.topicPostDao.selectByPrimaryKey(topicPost.getKid());
        TopicPostVo topicPostVo=new TopicPostVo();
        if(post!=null) {
            BeanUtils.copyProperties(post, topicPostVo);
            resourceTotal.setExtJson(JSON.toJSONString(topicPostVo));
        }
        resourceTotal.setResourceId(post.getKid());
        resourceTotal.setModuleEnum(Integer.valueOf(ModuleContants.TOPIC_POST));
        resourceTotal.setUserId(topicPost.getCreateUserId());
        resourceDymaicApi.commitResourceDymaic(resourceTotal);
        return result;
    }


    /**
     * 查询帖子详情
     *
     * @param kid
     * @param userId
     * @return
     */
    @Override
    public TopicPostVo getDetail(Long kid, Long userId) {
        TopicAndPostVo topicAndPostVo = new TopicAndPostVo();
        /**
         * 检验参数
         */
        if (null == kid) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }

        TopicPostExample example=new TopicPostExample();
        TopicPostExample.Criteria criteria=example.createCriteria();
       // criteria.andDelFlagEqualTo(CommonConstants.DELETE_NO);
       // criteria.andShelveFlagEqualTo(CommonConstants.SHELVE_YES);
        criteria.andKidEqualTo(kid);
        List<TopicPostWithBLOBs> topicPostWithBLOBsList = this.topicPostDao.selectByExampleWithBLOBs(example);
        if (null == topicPostWithBLOBsList || topicPostWithBLOBsList.isEmpty()) {
            //throw QuanhuException.busiError("查询的帖子不存在");
            return null;
        }
        TopicPostWithBLOBs topicPostWithBLOBs=topicPostWithBLOBsList.get(0);
        Long createUserId = topicPostWithBLOBs.getCreateUserId();
        TopicPostVo vo = new TopicPostVo();
        BeanUtils.copyProperties(topicPostWithBLOBs, vo);
        if (null != createUserId) {
            vo.setUser(apIservice.getUser(createUserId));
        }
        vo.setModuleEnum(ResourceTypeEnum.POSTS);

        //虚拟阅读数
        readApi.read(kid);
        return vo;
    }

    /**
     * 帖子列表查询
     *
     * @param dto
     * @return
     */
    @Override
    public PageList<TopicPostVo> queryList(TopicPostDto dto) {
        Long topicId = dto.getTopicId();
        /**
         * 校验参数
         */
        if (null == topicId) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        PageList<TopicPostVo> data = new PageList<>();
        Integer pageNum = dto.getCurrentPage() == null ? 1 : dto.getCurrentPage();
        Integer pageSize = dto.getPageSize() == null ? 10 : dto.getPageSize();
        Integer pageStartIndex = (pageNum - 1) * pageSize;
        TopicPostExample example = new TopicPostExample();
        TopicPostExample.Criteria criteria = example.createCriteria();
        criteria.andTopicIdEqualTo(topicId);
        criteria.andShelveFlagEqualTo(CommonConstants.SHELVE_YES);
        criteria.andDelFlagEqualTo(CommonConstants.DELETE_NO);
        example.setPageStartIndex(pageStartIndex);
        example.setPageSize(pageSize);
        example.setOrderByClause("create_date desc");

        List<TopicPostWithBLOBs> topicPosts = this.topicPostDao.selectByExampleWithBLOBs(example);
        List<TopicPostVo> list = new ArrayList<>();
        for (TopicPostWithBLOBs topicPost : topicPosts) {
            TopicPostVo vo = new TopicPostVo();
            BeanUtils.copyProperties(topicPost, vo);
            Long createUserId = topicPost.getCreateUserId();
            if (null != createUserId) {
                vo.setUser(apIservice.getUser(createUserId));
            }
            vo.setModuleEnum(ResourceTypeEnum.POSTS);
            Response<Map<String,Long>> countData=countApi.getCount("10,11",vo.getKid(),null);
            if(ResponseConstant.SUCCESS.getCode().equals(countData.getCode())){
                Map<String,Long> count=countData.getData();
                if(count!=null){
                    BehaviorVo behaviorVo=new BehaviorVo();
                    if(count.containsKey("likeCount")){
                        behaviorVo.setLikeCount(count.get("likeCount"));
                    }
                    if(count.containsKey("commentCount")){
                        behaviorVo.setCommentCount(count.get("commentCount"));
                    }
                    vo.setBehaviorVo(behaviorVo);
                }
            }
            list.add(vo);
        }
        data.setCount(0L);
        data.setCurrentPage(pageNum);
        data.setPageSize(pageSize);
        data.setEntities(list);
        return data;
    }


    @Override
    public Integer deleteTopicPost(Long kid, Long userId) {
        /**
         * 传入参数校验
         */
        if (null == kid || null == userId) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        TopicPost topicPost = this.topicPostDao.selectByPrimaryKey(kid);
        if (null == topicPost) {
            throw QuanhuException.busiError("删除的帖子不存在");
        }
        if (userId.compareTo(topicPost.getCreateUserId()) != 0) {
            throw new QuanhuException(ExceptionEnum.USER_NO_RIGHT_TODELETE);
        }
        topicPost.setDelFlag(CommonConstants.DELETE_YES);

        /**
         * 发送消息
         */
        return this.topicPostDao.updateByPrimaryKey(topicPost);
    }

    @Override
    public Long countPostByTopicId(Long kid) {
        TopicPostExample example=new TopicPostExample();
        TopicPostExample.Criteria criteria=example.createCriteria();
        criteria.andTopicIdEqualTo(kid);
        Long count=this.topicPostDao.countByExample(example);
        return count;
    }

	@Override
	public List<Long> getKidByCreatedate(String startDate, String endDate) {
		return topicPostDao.selectKidByCreatedate(startDate, endDate);
	}

	@Override
	public List<TopicPostWithBLOBs> getByKids(List<Long> kidList) {
        TopicPostExample example=new TopicPostExample();
        TopicPostExample.Criteria criteria=example.createCriteria();
        criteria.andKidIn(kidList);
		List<TopicPostWithBLOBs> list=topicPostDao.selectByExampleWithBLOBs(example);
        return list;
	}
}
