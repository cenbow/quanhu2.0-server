package com.yryz.quanhu.resource.topic.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.resource.topic.dto.TopicDto;
import com.yryz.quanhu.resource.topic.entity.Topic;
import com.yryz.quanhu.resource.topic.vo.TopicVo;

import java.util.List;

public interface Topic4AdminService {

    public TopicVo saveTopic(TopicDto dto);

    public TopicVo queryDetail(Long kid, Long userId);

    public PageList<TopicVo> queryTopicList(TopicDto dto);

    public Integer deleteTopic(Long kid, Long userId);

    public Integer shalve(Long kid ,Byte shalveFlag);

    public Integer recommond(Long kid ,Byte recommondFlag);

}
