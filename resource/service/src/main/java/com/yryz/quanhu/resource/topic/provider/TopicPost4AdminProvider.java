package com.yryz.quanhu.resource.topic.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.resource.topic.api.TopicPost4AdminApi;
import com.yryz.quanhu.resource.topic.api.TopicPostApi;
import com.yryz.quanhu.resource.topic.dto.TopicPostDto;
import com.yryz.quanhu.resource.topic.entity.TopicPostWithBLOBs;
import com.yryz.quanhu.resource.topic.service.TopicPostService;
import com.yryz.quanhu.resource.topic.vo.TopicPostVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(interfaceClass = TopicPost4AdminApi.class)
public class TopicPost4AdminProvider implements TopicPost4AdminApi {
    private static final Logger logger = LoggerFactory.getLogger(TopicPost4AdminProvider.class);

    @Autowired
    private TopicPostService topicPostService;

    @Override
    public Response<TopicPostVo> quetyDetail(Long kid, Long userId) {
        try {
            TopicPostVo vo = this.topicPostService.getDetail(kid, userId);
            return ResponseUtils.returnObjectSuccess(vo);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("注册未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<TopicPostVo>> listPost(TopicPostDto dto) {
        try {
            PageList<TopicPostVo> data = this.topicPostService.queryList(dto);
            return ResponseUtils.returnObjectSuccess(data);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("注册未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> deleteTopicPost(Long kid, Long userId) {
        try {
            Integer data = this.topicPostService.deleteTopicPost(kid, userId);
            return ResponseUtils.returnObjectSuccess(data);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("注册未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> shelve(Long kid, Byte shelveFlag) {
        return null;
    }


}
