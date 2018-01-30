package com.yryz.quanhu.dymaic;

import javax.annotation.Resource;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.dymaic.dto.StarInfoDTO;
import com.yryz.quanhu.dymaic.service.ElasticsearchService;
import com.yryz.quanhu.user.vo.StarInfoVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 按照下面模板可以进行单元测试 测试dubbo提供者直接@Autowired
 */
@RunWith(SpringRunner.class)
//@SpringBootTest
@ContextConfiguration(locations = {"classpath:test.xml"})
public class EsTest {
    @Autowired
    private ElasticsearchService elasticsearchService;

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void searchStarUserTest() {
        StarInfoDTO starInfoDTO = new StarInfoDTO();
        starInfoDTO.setTagId(12L);
        starInfoDTO.setCurrentPage(0);
        starInfoDTO.setPageSize(5);
        starInfoDTO.setUserId(737237750614581248L);
        Response<PageList<StarInfoVO>> pageListResponse = elasticsearchService.searchStarUser(starInfoDTO);
        System.out.println("pageListResponse: " + GsonUtils.parseJson(pageListResponse));
    }


}
