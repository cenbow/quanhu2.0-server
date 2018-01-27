package com.yryz.quanhu.resource.topic.service;

import java.util.List;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.resource.topic.dto.TopicPostDto;
import com.yryz.quanhu.resource.topic.vo.TopicAndPostVo;
import com.yryz.quanhu.resource.topic.vo.TopicPostVo;

public interface TopicPostService {

    public Integer saveTopicPost(TopicPostDto topicPostDto);

    public TopicAndPostVo getDetail(Long kid, Long userId);

    public PageList<TopicPostVo> queryList(TopicPostDto dto);

    public Integer deleteTopicPost(Long kid,Long userId);

    public Long countPostByTopicId(Long kid);
    
    public List<Long> getKidByCreatedate(String startDate,String endDate);
    
    public List<TopicPostVo> getByKids(List<Long> kidList);
}
