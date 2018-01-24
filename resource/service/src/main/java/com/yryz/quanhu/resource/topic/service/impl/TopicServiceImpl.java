package com.yryz.quanhu.resource.topic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.resource.topic.dao.TopicDao;
import com.yryz.quanhu.resource.topic.dto.TopicDto;
import com.yryz.quanhu.resource.topic.entity.Topic;
import com.yryz.quanhu.resource.topic.service.TopicService;
import com.yryz.quanhu.resource.topic.vo.TopicVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TopicServiceImpl implements TopicService {


    @Autowired
    private TopicDao topicDao;

    @Reference
    private UserApi userApi;

    @Reference
    private IdAPI idAPI;

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
        Topic topic=new Topic();
        BeanUtils.copyProperties(topicDto,topic);
        Response<Long> longResponse=idAPI.getKid("qh_topic");
        if(ResponseConstant.SUCCESS.getCode().equals(longResponse.getCode())){
            topic.setKid(longResponse.getData());
        }
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
        if (null == kid || null == userId) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        Topic topic = this.topicDao.selectByPrimaryKey(kid);
        if (null == topic) {
            throw QuanhuException.busiError("查询的话题不存在");
        }
        TopicVo topicVo = new TopicVo();
        Long createUserId = topic.getCreateUserId();
        if (null != createUserId) {
            Response<UserSimpleVO> userSimpleVOResponse = userApi.getUserSimple(String.valueOf(createUserId));
            if (ResponseConstant.SUCCESS.getCode().equals(userSimpleVOResponse.getCode())) {
                UserSimpleVO userSimpleVO = userSimpleVOResponse.getData();
                topicVo.setUser(userSimpleVO);
            }
        }
        return topicVo;
    }
}
