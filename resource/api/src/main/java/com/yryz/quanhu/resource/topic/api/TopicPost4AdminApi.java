package com.yryz.quanhu.resource.topic.api;


import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.topic.dto.TopicPostDto;
import com.yryz.quanhu.resource.topic.entity.TopicPostWithBLOBs;
import com.yryz.quanhu.resource.topic.vo.TopicPostVo;

import java.util.List;

public interface TopicPost4AdminApi {


    public Response<TopicPostVo> quetyDetail(Long kid, Long userId);

    public Response<PageList<TopicPostVo>> listPost(TopicPostDto dto);

    public Response<Integer> deleteTopicPost(Long kid, Long userId);

    public  Response<Integer> shelve(Long kid,Byte shelveFlag);
}
