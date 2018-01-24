package com.yryz.quanhu.resource.topic.service;

import com.yryz.quanhu.resource.topic.dto.TopicDto;
import com.yryz.quanhu.resource.topic.entity.Topic;
import com.yryz.quanhu.resource.topic.vo.TopicVo;

public interface TopicService {

    public TopicVo saveTopic(TopicDto dto);

    public TopicVo queryDetail(Long kid,Long userId);
}
