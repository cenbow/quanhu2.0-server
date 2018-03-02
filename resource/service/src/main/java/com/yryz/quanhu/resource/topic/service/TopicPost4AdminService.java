package com.yryz.quanhu.resource.topic.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.resource.topic.dto.TopicPostDto;
import com.yryz.quanhu.resource.topic.entity.TopicPostWithBLOBs;
import com.yryz.quanhu.resource.topic.vo.TopicPostVo;

import java.util.List;

public interface TopicPost4AdminService {

    public TopicPostVo getDetail(Long kid, Long userId);

    public PageList<TopicPostVo> queryList(TopicPostDto dto);

    public Integer shelve(Long kid,Byte shelveFlag,Boolean isCascade);

}
