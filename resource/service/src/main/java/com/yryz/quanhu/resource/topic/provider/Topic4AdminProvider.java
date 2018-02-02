package com.yryz.quanhu.resource.topic.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.resource.topic.api.Topic4AdminApi;
import com.yryz.quanhu.resource.topic.dto.TopicDto;
import com.yryz.quanhu.resource.topic.service.Topic4AdminService;
import com.yryz.quanhu.resource.topic.vo.TopicVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass = Topic4AdminApi.class)
public class Topic4AdminProvider implements Topic4AdminApi {

    private static final Logger logger= LoggerFactory.getLogger(Topic4AdminProvider.class);

    @Autowired
    private Topic4AdminService topic4AdminService;

    /**
     * 发布话题
     * @param dtot
     * @return
     */
    @Override
    public Response<TopicVo> saveTopic(TopicDto dto) {
        try {
            TopicVo  result=this.topic4AdminService.saveTopic(dto);
            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("注册未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }


    /**
     * 查询话题详情
     * @param kid
     * @param userId
     * @return
     */
    @Override
    public Response<TopicVo> queryDetail(Long kid, Long userId) {
        try {
            TopicVo  result=this.topic4AdminService.queryDetail(kid,userId);
            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("注册未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }



    @Override
    public Response<PageList<TopicVo>> queryTopicList(TopicDto dto) {
        try {
            PageList<TopicVo>  result=this.topic4AdminService.queryTopicList(dto);
            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("注册未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> shelve(Long id, Byte shelveFlag) {
        return null;
    }

    @Override
    public Response<Integer> recommend(Long id, Byte recommend) {
        return null;
    }


}
