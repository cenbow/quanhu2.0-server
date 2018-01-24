package com.yryz.quanhu.resource.topic.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.topic.dto.TopicDto;
import com.yryz.quanhu.resource.topic.vo.TopicVo;

public interface TopicApi {

    public Response<TopicVo> saveTopic(TopicDto dto);

    public Response<TopicVo> queryDetail(Long kid,Long userId);

    public Response<Integer> deleteTopic(Long kid,Long userId);

    public Response<PageList<TopicVo>> queryTopicList(TopicDto dto);
}
