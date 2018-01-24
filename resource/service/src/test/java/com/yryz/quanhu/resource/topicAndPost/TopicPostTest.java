package com.yryz.quanhu.resource.topicAndPost;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.quanhu.resource.topic.api.TopicApi;
import com.yryz.quanhu.resource.topic.api.TopicPostApi;
import com.yryz.quanhu.resource.topic.dto.TopicDto;
import com.yryz.quanhu.resource.topic.dto.TopicPostDto;
import com.yryz.quanhu.resource.topic.entity.TopicPost;
import com.yryz.quanhu.resource.topic.vo.TopicPostVo;
import com.yryz.quanhu.resource.topic.vo.TopicVo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicPostTest {

    @Reference
    private TopicApi topicApi;

    @Reference
    private TopicPostApi topicPostApi;

    /**
     * 发布话题
     */
    @Test
    public void saveTopic() {
        TopicDto dto = new TopicDto();
        dto.setContent("斯蒂芬斯蒂芬斯蒂芬");
        dto.setCoterieId("123456");
        dto.setCreateUserId(727447149320273920L);
        dto.setTitle("哈哈哈哈哈哈");
        dto.setCreateUserId(1000L);
        dto.setImgUrl("http://wewew.com/sdsd");
        topicApi.saveTopic(dto);
    }

    /**
     * 查询话题详情
     */
    @Test
    public void queryTopicDetil() {
        Response<TopicVo> data = topicApi.queryDetail(180504L, 724007310011252736L);
        if (ResponseConstant.SUCCESS.getCode().equals(data.getCode())) {
            System.out.println("==========" + JSON.toJSONString(data.getData()));
        }
    }

    /**
     * 查询话题列表
     */
    @Test
    public void queryTopicList() {
        TopicDto dto = new TopicDto();
        dto.setPageNum(1);
        dto.setPageSize(5);
        Response<PageList<TopicVo>> data = this.topicApi.queryTopicList(dto);
        Assert.assertTrue(ResponseConstant.SUCCESS.getCode().equals(data.getCode()));
        System.out.println("=======" + JSON.toJSONString(data.getData()));
    }

    /**
     * 标记删除
     */
    @Test
    public void DeleteTopic(){
        Response<Integer> data=this.topicApi.deleteTopic(180504L,724007L);
        System.out.println("=========="+JSON.toJSONString(data));
    }


    /**
     * 发布帖子
     */
    @Test
    public void saveTopicPost() {
        TopicPostDto dto = new TopicPostDto();
        dto.setAudioUrl("http://ffff.com/ddd");
        dto.setContent("aaaaaaaaaaaa");
        dto.setCreateUserId(1000L);
        dto.setTopicId(180504L);
        dto.setDelFlag(Byte.valueOf("10"));
        dto.setShelveFlag(Byte.valueOf("10"));
        dto.setCreateUserId(1200L);
        dto.setAudioUrl("https://ssss.com");
        dto.setVideoUrl("http://");
        dto.setVideoThumbnailUrl("http://");
        dto.setImgThumbnailUrl("http://");
        topicPostApi.savePost(dto);
    }

    /**
     * 查询帖子列表
     */
    @Test
    public void queryListTopicPost() {
        TopicPostDto dto = new TopicPostDto();
        dto.setTopicId(180504L);
        dto.setPageNum(1);
        dto.setPageSize(5);
        Response<PageList<TopicPostVo>> data = topicPostApi.listPost(dto);
        System.out.println("===============" + JSON.toJSONString(data.getData()));
    }

    @Test
    public void queryPostDetail(){
       Response<TopicPostVo> data= topicPostApi.quetyDetail(164439L,0L);
    }
}
