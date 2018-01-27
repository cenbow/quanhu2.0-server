package com.yryz.quanhu.resource.topic.service;

import java.util.List;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.resource.topic.dto.TopicDto;
import com.yryz.quanhu.resource.topic.entity.Topic;
import com.yryz.quanhu.resource.topic.vo.TopicVo;

public interface TopicService {

    public TopicVo saveTopic(TopicDto dto);

    public TopicVo queryDetail(Long kid,Long userId);

    public PageList<TopicVo> queryTopicList(TopicDto dto);

    public Integer deleteTopic(Long kid,Long userId);
    
    public List<Long> getKidByCreatedate(String startDate,String endDate);
    
    public List<Topic> getByKids(List<Long> kidList);
}
