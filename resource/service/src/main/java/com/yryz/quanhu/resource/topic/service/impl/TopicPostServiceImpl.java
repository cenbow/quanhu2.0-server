package com.yryz.quanhu.resource.topic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.CommonConstants;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.quanhu.resource.topic.dao.TopicPostDao;
import com.yryz.quanhu.resource.topic.dto.TopicPostDto;
import com.yryz.quanhu.resource.topic.entity.TopicPost;
import com.yryz.quanhu.resource.topic.entity.TopicPostExample;
import com.yryz.quanhu.resource.topic.entity.TopicPostWithBLOBs;
import com.yryz.quanhu.resource.topic.service.TopicPostService;
import com.yryz.quanhu.resource.topic.vo.TopicPostVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TopicPostServiceImpl implements TopicPostService {

    @Autowired
    private TopicPostDao topicPostDao;

    @Reference
    private UserApi userApi;

    @Reference
    private IdAPI idAPI;


    /**
     * 发布帖子
     * @param topicPostDto
     * @return
     */
    @Override
    public TopicPostVo saveTopicPost(TopicPostDto topicPostDto) {
        /**
         * 校验参数
         */
        Long topicId=topicPostDto.getTopicId();
        if(null ==topicId){
            throw  new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        TopicPostWithBLOBs topicPost=new TopicPostWithBLOBs();
        BeanUtils.copyProperties(topicPostDto,topicPost);
        topicPost.setGps("");
        Response<Long> longResponse=idAPI.getKid("qh_topic_post");
        if(ResponseConstant.SUCCESS.getCode().equals(longResponse.getCode())){
            topicPost.setKid(longResponse.getData());
        }
        topicPost.setCreateDate(new Date());
        topicPost.setCityCode("");
        topicPost.setGps("");
        this.topicPostDao.insertSelective(topicPost);
        TopicPostVo vo=new TopicPostVo();
        BeanUtils.copyProperties(topicPost,vo);
        return vo;
    }


    /**
     * 查询帖子详情
     * @param kid
     * @param userId
     * @return
     */
    @Override
    public TopicPostVo getDetail(Long kid, Long userId) {

        /**
         * 检验参数
         */
        if(null==kid){
            throw  new  QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        TopicPostWithBLOBs topicPostWithBLOBs=this.topicPostDao.selectByPrimaryKey(kid);
        if(null==topicPostWithBLOBs){
            throw  QuanhuException.busiError("查询的帖子不存在");
        }
        Long createUserId=topicPostWithBLOBs.getCreateUserId();
        TopicPostVo vo=new TopicPostVo();
        BeanUtils.copyProperties(topicPostWithBLOBs,vo);
        if(null!=createUserId){
            Response<UserSimpleVO> userSimpleVOResponse = userApi.getUserSimple(createUserId);
            if (ResponseConstant.SUCCESS.getCode().equals(userSimpleVOResponse.getCode())) {
                UserSimpleVO userSimpleVO = userSimpleVOResponse.getData();
                vo.setUser(userSimpleVO);
            }
        }
        return vo;
    }

    /**
     * 帖子列表查询
     * @param dto
     * @return
     */
    @Override
    public PageList<TopicPostVo> queryList(TopicPostDto dto) {
        Long topicId=dto.getTopicId();
        /**
         * 校验参数
         */
        if(null==topicId){
            throw  new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        PageList<TopicPostVo> data=new PageList<>();
        Integer pageNum=dto.getPageNum()==null?1:dto.getPageNum();
        Integer pageSize=dto.getPageSize()==null?10:dto.getPageSize();
        Integer pageStartIndex=(pageNum-1)*pageSize;
        TopicPostExample example=new TopicPostExample();
        TopicPostExample.Criteria criteria= example.createCriteria();
        criteria.andTopicIdEqualTo(topicId);
        criteria.andShelveFlagEqualTo(CommonConstants.DELETE_NO);
        criteria.andDelFlagEqualTo(CommonConstants.DELETE_NO);
        example.setPageStartIndex(pageStartIndex);
        example.setPageSize(pageSize);
        example.setOrderByClause("create_date desc");

        List<TopicPostWithBLOBs>  topicPosts=this.topicPostDao.selectByExampleWithBLOBs(example);
        List<TopicPostVo> list=new ArrayList<>();
        for(TopicPostWithBLOBs topicPost:topicPosts){
            TopicPostVo vo=new TopicPostVo();
            BeanUtils.copyProperties(topicPost,vo);
            Long crateUserId=topicPost.getCreateUserId();
            if(null!=crateUserId){
                Response<UserSimpleVO> userSimpleVOResponse=userApi.getUserSimple(crateUserId);
                if(ResponseConstant.SUCCESS.getCode().equals(userSimpleVOResponse.getCode())){
                    vo.setUser(userSimpleVOResponse.getData());
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
        if(null==kid || null==userId){
            throw  new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        TopicPost topicPost=this.topicPostDao.selectByPrimaryKey(kid);
        if(null ==topicPost){
            throw QuanhuException.busiError("删除的帖子不存在");
        }
        if(userId.compareTo(topicPost.getCreateUserId())!=0){
            throw new QuanhuException(ExceptionEnum.USER_NO_RIGHT_TODELETE);
        }
        topicPost.setDelFlag(CommonConstants.DELETE_YES);
        return this.topicPostDao.updateByPrimaryKey(topicPost);
    }
}
