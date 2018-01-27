package com.yryz.quanhu.resource.topic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.CommonConstants;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.resource.enums.ResourceTypeEnum;
import com.yryz.quanhu.resource.questionsAnswers.service.APIservice;
import com.yryz.quanhu.resource.topic.dao.TopicDao;
import com.yryz.quanhu.resource.topic.dto.TopicDto;
import com.yryz.quanhu.resource.topic.entity.Topic;
import com.yryz.quanhu.resource.topic.entity.TopicExample;
import com.yryz.quanhu.resource.topic.service.TopicPostService;
import com.yryz.quanhu.resource.topic.service.TopicService;
import com.yryz.quanhu.resource.topic.vo.TopicVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {


    @Autowired
    private TopicDao topicDao;

    @Autowired
    private APIservice apIservice;


    @Autowired
    private TopicPostService topicPostService;

    /**
     * 发布话题
     *
     * @param topicDto
     * @return
     */
    @Override
    public TopicVo saveTopic(TopicDto topicDto) {
        /**
         * 校验参数
         */
        String content = topicDto.getContent();
        String title = topicDto.getTitle();
        if (StringUtils.isBlank(content) || StringUtils.isBlank(title)) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        Topic topic = new Topic();
        BeanUtils.copyProperties(topicDto, topic);
        topic.setKid(apIservice.getKid());
        topic.setDelFlag(CommonConstants.DELETE_NO);
        topic.setShelveFlag(CommonConstants.SHELVE_YES);
        topic.setRecommend(CommonConstants.recommend_NO);
        topic.setCityCode("");
        topic.setGps("");
        topic.setRevision(0);
        topic.setCreateDate(new Date());
        Integer result = this.topicDao.insertSelective(topic);
        if (result > 0) {
            TopicVo vo = new TopicVo();
            BeanUtils.copyProperties(topic, vo);
            return vo;
        }
        return null;
    }

    /**
     * 查询话题的详情
     *
     * @param kid
     * @param userId
     * @return
     */
    @Override
    public TopicVo queryDetail(Long kid, Long userId) {
        /**
         * 校验参数
         */
        if (null == kid) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }

        TopicExample example=new TopicExample();
        TopicExample.Criteria criteria=example.createCriteria();
        criteria.andKidEqualTo(kid);
        criteria.andDelFlagEqualTo(CommonConstants.DELETE_NO);
        criteria.andShelveFlagEqualTo(CommonConstants.SHELVE_YES);

        List<Topic> topics = this.topicDao.selectByExample(example);
        if (null == topics || topics.isEmpty()) {
            //throw QuanhuException.busiError("查询的话题不存在");
            return null;
        }
        Topic topic=topics.get(0);
        TopicVo topicVo = new TopicVo();
        BeanUtils.copyProperties(topic, topicVo);
        Long createUserId = topic.getCreateUserId();
        if (null != createUserId) {
            topicVo.setUser(apIservice.getUser(createUserId));
        }
        Long replyCount=this.topicPostService.countPostByTopicId(topicVo.getKid());
        topicVo.setReplyCount(replyCount);
        topicVo.setModuleEnum(ResourceTypeEnum.TOPIC);
        return topicVo;
    }

    /**
     * 查询话题的列表
     *
     * @param dto
     * @return
     */
    @Override
    public PageList<TopicVo> queryTopicList(TopicDto dto) {
        PageList<TopicVo> pageList = new PageList<>();
        Integer pageNum = dto.getPageNum() == null ? 1 : dto.getPageNum();
        Integer pageSize = dto.getPageSize() == null ? 10 : dto.getPageSize();
        Integer pageStartIndex = (pageNum - 1) * pageSize;
        // Byte recommend =dto.getRecommend()==null?CommonConstants.recommend_YES:dto.getRecommend();
        String orderBy = StringUtils.isBlank(dto.getOrderBy()) ? "recommend desc ,create_date desc " : dto.getOrderBy();
        TopicExample example = new TopicExample();
        TopicExample.Criteria criteria=example.createCriteria();
        criteria.andShelveFlagEqualTo(CommonConstants.DELETE_NO);
        criteria.andShelveFlagEqualTo(CommonConstants.SHELVE_YES);
        if(dto.getRecommend()!=null){
            criteria.andRecommendEqualTo(dto.getRecommend());
        }
        example.setPageStartIndex(pageStartIndex);
        example.setPageSize(pageSize);
        example.setOrderByClause(orderBy);
        List<Topic> list = this.topicDao.selectByExampleWithBLOBs(example);
        List<TopicVo> topicVos = new ArrayList<>();
        for (Topic topic : list) {
            TopicVo vo = new TopicVo();
            BeanUtils.copyProperties(topic, vo);
            Long createUserId = topic.getCreateUserId();
            if (createUserId != null) {
                vo.setUser(apIservice.getUser(createUserId));
            }
            topicVos.add(vo);
        }
        pageList.setEntities(topicVos);
        pageList.setPageSize(pageSize);
        pageList.setCurrentPage(pageNum);
        pageList.setCount(0L);
        return pageList;
    }


    /**
     * 话题标记删除
     *
     * @param kid
     * @param userId
     * @return
     */
    @Override
    public Integer deleteTopic(Long kid, Long userId) {
        /**
         * 校验参数
         */
        if (null == kid || null == userId) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }

        Topic topic = this.topicDao.selectByPrimaryKey(kid);
        if (null == topic) {
            throw QuanhuException.busiError("删除的话题不存在");
        }
        if (topic.getCreateUserId().compareTo(userId) != 0) {
            throw new QuanhuException(ExceptionEnum.USER_NO_RIGHT_TODELETE);
        }
        Topic topicParam = new Topic();
        topicParam.setKid(kid);
        topicParam.setDelFlag(CommonConstants.DELETE_YES);
        return this.topicDao.updateByPrimaryKeySelective(topicParam);
    }
}
