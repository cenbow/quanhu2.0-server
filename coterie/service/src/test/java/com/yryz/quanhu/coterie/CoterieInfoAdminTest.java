package com.yryz.quanhu.coterie;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieAdminAPI;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.coterie.vo.CoterieBasicInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieSearchParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CoterieInfoAdminTest {
    @Autowired
    private CoterieAdminAPI coterieAdminAPI;

    @Test
    public void getCreateCoterie() {

        CoterieSearchParam param = new CoterieSearchParam();
        param.setStatus((byte)11);
//        param.setRecommend((byte)11);
        param.setPageNum(1);
        param.setPageSize(10);
//        param.setName("安慕希");
    	PageList<CoterieInfo> list=ResponseUtils.getResponseData(coterieAdminAPI.getCoterieByPage(param));
    	System.out.println(list);
    }


    @Test
    public void queryAbleCreteCoterieUserIds(){
        Response<Set<Long>> result=coterieAdminAPI.queryAbleCreteCoterieUserIds();
        System.out.println("用户数："+ResponseUtils.getResponseData(result).size());
    }
}
