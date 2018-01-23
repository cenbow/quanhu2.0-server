package com.yryz.quanhu.resource.QuestionsAnswers;


import com.alibaba.dubbo.config.annotation.Reference;
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

    @Test
    public void saveQuestion(){
        QuestionDto dto =new QuestionDto();
        dto.setKid(99999999L);
        dto.setChargeAmount(100L);
        dto.setTitle("cccc");
        dto.setContent("cccccccccccccc");
        dto.setContentSource("eeeee");
        dto.setCoterieId("ttttt0001");
        dto.setTargetId("1234567890");
        dto.setIsAnonymity(QuestionAnswerConstants.anonymityType.YES);
        dto.setIsOnlyShowMe(Byte.valueOf("11"));
        questionApi.saveQuestion(dto);

    }
}

