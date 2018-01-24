package com.yryz.quanhu.resource.topic.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.resource.topic.dto.TopicPostDto;
import com.yryz.quanhu.resource.topic.vo.TopicPostVo;

public interface TopicPostService {

    public TopicPostVo saveTopicPost(TopicPostDto topicPostDto);

    public TopicPostVo getDetail(Long kid,Long userId);

    public PageList<TopicPostVo> queryList(TopicPostDto dto);
}
