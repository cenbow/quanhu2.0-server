package com.yryz.quanhu.resource.topic.api;

import com.yryz.common.constant.CommonConstants;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.topic.dto.TopicDto;
import com.yryz.quanhu.resource.topic.entity.Topic;
import com.yryz.quanhu.resource.topic.vo.TopicVo;

import java.util.List;

public interface Topic4AdminApi {

    public Response<TopicVo> saveTopic(TopicDto dto);

    public Response<TopicVo> queryDetail(Long kid, Long userId);

    public Response<Integer> deleteTopic(Long kid, Long userId);

    public Response<PageList<TopicVo>> queryTopicList(TopicDto dto);

    // TODO: 2018/1/30 0030
    public Response<List<Long>> getKidByCreatedate(String startDate, String endDate);

    // TODO: 2018/1/30 0030
    public Response<List<Topic>> getByKids(List<Long> kidList);

    public Response<Integer> shelve(Long id, Byte shelveFlag);

    public Response<Integer> recommend(Long id, Byte recommend);
}
