package com.yryz.quanhu.resource.topic.api;


import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.topic.dto.TopicPostDto;
import com.yryz.quanhu.resource.topic.vo.TopicPostVo;

public interface TopicPostApi {

    public Response<TopicPostVo> savePost(TopicPostDto dto);

    public Response<TopicPostVo> quetyDetail(Long kid,Long userId);

    public Response<PageList<TopicPostVo>> listPost(TopicPostDto dto);

    public Response<Integer> deleteTopicPost(Long kid,Long userId);
}
