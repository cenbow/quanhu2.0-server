package com.yryz.quanhu.resource.QuestionsAnswers;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.questionsAnswers.api.QuestionApi;
import com.yryz.quanhu.resource.questionsAnswers.constants.QuestionAnswerConstants;
import com.yryz.quanhu.resource.questionsAnswers.dto.QuestionDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionTest {

    @Reference
    private QuestionApi questionApi;


    /**
     * 圈粉发布问题，付费提问时，圈粉支付提问费用
     */
    @Test
    public void saveQuestion(){
        QuestionDto dto =new QuestionDto();
        dto.setChargeAmount(100L);
        dto.setTitle("停停停停停停停停停停停停停停停停停停");
        dto.setContent("幅度高达还是多好多事风格的风格发挥好");
        dto.setContentSource("eeeee");
        dto.setCoterieId(10000L);
        dto.setTargetId("729869321898680320");
        dto.setCreateUserId(729840906395049984L);
        dto.setIsAnonymity(QuestionAnswerConstants.anonymityType.YES);
        dto.setIsOnlyShowMe(QuestionAnswerConstants.showType.ONESELF);
        questionApi.saveQuestion(dto);
    }

    /**
     * 圈粉删除问题
     */
    @Test
    public void DeleteQuestion(){
        Response<Integer> data=questionApi.deleteQuestion(176002L,0L);
        System.out.println("========="+ JSON.toJSONString(data));
    }
}

