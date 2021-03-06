package com.yryz.quanhu.resource.topic.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.resource.topic.api.TopicPostApi;
import com.yryz.quanhu.resource.topic.dto.TopicPostDto;
import com.yryz.quanhu.resource.topic.entity.TopicPost;
import com.yryz.quanhu.resource.topic.entity.TopicPostWithBLOBs;
import com.yryz.quanhu.resource.topic.service.TopicPostService;
import com.yryz.quanhu.resource.topic.vo.TopicAndPostVo;
import com.yryz.quanhu.resource.topic.vo.TopicPostVo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass = TopicPostApi.class)
public class TopicPostProvider implements TopicPostApi {
    private static final Logger logger = LoggerFactory.getLogger(TopicPostProvider.class);

    @Autowired
    private TopicPostService topicPostService;

    @Override
    public Response<Integer> savePost(TopicPostDto dto) {
        try {
            Integer result = this.topicPostService.saveTopicPost(dto);
            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("注册未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<TopicPostVo> quetyDetail(Long kid, Long userId) {
        try {
            TopicPostVo vo = this.topicPostService.getDetail(kid, userId);
            if(vo==null){
                throw   QuanhuException.busiError("查询的帖子不存在");
            }
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
	public Response<List<Long>> getKidByCreatedate(String startDate, String endDate) {
		try {
			List<Long> data = this.topicPostService.getKidByCreatedate(startDate, endDate);
            return ResponseUtils.returnObjectSuccess(data);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("未知异常", e);
            return ResponseUtils.returnException(e);
        }
	}

	@Override
	public Response<List<TopicPostWithBLOBs>> getByKids(List<Long> kidList) {
		try {
			List<TopicPostWithBLOBs> data = this.topicPostService.getByKids(kidList);
            return ResponseUtils.returnObjectSuccess(data);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("未知异常", e);
            return ResponseUtils.returnException(e);
        }
	}


}
