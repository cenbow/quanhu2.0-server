package com.yryz.quanhu.resource.topicAndPost;


import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.topic.api.TopicApi;
import com.yryz.quanhu.resource.topic.api.TopicPostApi;
import com.yryz.quanhu.resource.topic.dto.TopicDto;
import com.yryz.quanhu.resource.topic.dto.TopicPostDto;
import com.yryz.quanhu.resource.topic.vo.TopicPostVo;
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

    @Test
    public void saveTopic(){
        TopicDto dto=new TopicDto();
        dto.setContent("Ai 的未来，可以主宰人类世界吗？");
        dto.setCoterieId("123456");
        dto.setCreateUserId(10000);
        dto.setTitle("AIAIAIAI");
        dto.setCreateUserId(1000L);
        dto.setDelFlag(Byte.valueOf( "10"));
        dto.setRecommend(Byte.valueOf( "10"));
        dto.setImgUrl("http://wewew.com/sdsd");
        dto.setShelveFlag(Byte.valueOf( "10"));
        topicApi.saveTopic(dto);
    }

    @Test
    public void saveTopicPost(){
        TopicPostDto dto=new TopicPostDto();
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


    @Test
    public void queryList(){
        TopicPostDto dto=new TopicPostDto();
        dto.setTopicId(180504L);
        dto.setPageNum(1);
        dto.setPageSize(5);
        Response<PageList<TopicPostVo>> data=topicPostApi.listPost(dto);
        System.out.print("==============="+data.getData().getEntities().size());
    }
}
