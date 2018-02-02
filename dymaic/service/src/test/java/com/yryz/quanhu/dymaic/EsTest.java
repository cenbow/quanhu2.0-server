package com.yryz.quanhu.dymaic;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.dymaic.service.ElasticsearchService;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.dto.StarInfoDTO;
import com.yryz.quanhu.user.vo.StarInfoVO;
import com.yryz.quanhu.user.vo.UserInfoVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @Resource
//    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void searchStarUserTest() {
        StarInfoDTO starInfoDTO = new StarInfoDTO();
        starInfoDTO.setTagId(12L);
        starInfoDTO.setCurrentPage(1);
        starInfoDTO.setPageSize(5);
        Response<PageList<StarInfoVO>> pageListResponse = elasticsearchService.searchStarUser(starInfoDTO);
        System.out.println("pageListResponse: " + GsonUtils.parseJson(pageListResponse));
    }

    @Test
    public void buildTest() {
        elasticsearchService.rebuildUserInfo();
    }

    @Test
    public void adminTest() {
        AdminUserInfoDTO dto = new AdminUserInfoDTO();
        dto.setStartDate("2018-01-01");
        dto.setEndDate("2018-03-01");
        dto.setPhone("13");
        dto.setNickName("q");
        Response<PageList<UserInfoVO>> pageListResponse = elasticsearchService.adminSearchUser(dto);
        System.out.println("pageListResponse " + GsonUtils.parseJson(pageListResponse));
    }


}
