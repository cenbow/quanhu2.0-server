package com.yryz.quanhu.resource.topic.api;

import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.topic.dto.TopicDto;
import com.yryz.quanhu.resource.topic.vo.TopicVo;

public interface TopicApi {

    public Response<TopicVo> saveTopic(TopicDto dto);

    public Response<TopicVo> queryDetail(Long kid,Long userId);
}
