package com.yryz.quanhu.resource.coterie.release.info.test;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yryz.quanhu.resource.coterie.release.info.api.CoterieReleaseInfoApi;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoApi;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;

/**
* @Description: 平台文章
* @author wangheng
* @date 2018年1月24日 下午8:00:29
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class CoterieReleaseInfoTest {

    @Reference
    CoterieReleaseInfoApi coterieReleaseInfoApi;
    
    @Reference
    ReleaseInfoApi releaseInfoApi;

    /**  
    * @Description: 私圈文章 发布
    * @author wangheng
    * @param @throws JsonProcessingException
    * @return void
    * @throws  
    */
    @Test
    public void test001() throws JsonProcessingException {
        ReleaseInfo record = new ReleaseInfo();
        int random = new Random().nextInt();

        record.setModuleEnum("0092");
        record.setCoterieId(8626948196L);
        // 设置价格
        record.setContentPrice(100L);
        record.setContent(random + "私圈正文哈哈哈哈");
        record.setContentSource("[{\"text\":\"" + record.getContent() + "\"}]");
        record.setCreateUserId(727909974996672512L);
        record.setImgUrl("imgUrl");
        record.setTitle(random + "私圈标题哈哈哈");

        System.out.println(new ObjectMapper().writeValueAsString(coterieReleaseInfoApi.release(record)));
    }

    /**  
    * @Description: 私圈文章 详情
    * @author wangheng
    * @param @throws JsonProcessingException
    * @return void
    * @throws  
    */
    @Test
    public void test002() throws JsonProcessingException {
        System.out.println(new ObjectMapper()
                .writeValueAsString(coterieReleaseInfoApi.infoByKid(728714439552114688L, 724007310011252736L)));

        // 圈主 查看
        //System.out.println(new ObjectMapper().writeValueAsString(coterieReleaseInfoApi.infoByKid(736943287656628224L, 727909974996672512L)));

        // 圈粉 查看
        //System.out.println(new ObjectMapper().writeValueAsString(coterieReleaseInfoApi.infoByKid(736943287656628224L, 727909974996672512L)));

        // 路人 查看
        //System.out.println(new ObjectMapper().writeValueAsString(coterieReleaseInfoApi.infoByKid(736943287656628224L, 727909974996672512L)));
    }

    /**  
    * @Description: 付费文章，创建订单
    * @author wangheng
    * @param @throws JsonProcessingException
    * @return void
    * @throws  
    */
    @Test
    public void test003() throws JsonProcessingException {
        System.out.println(new ObjectMapper()
                .writeValueAsString(coterieReleaseInfoApi.createOrder(728714439552114688L, 724011759597371392L)));
    }

    /**  
    * @Description: 私圈文章，删除
    * @author wangheng
    * @param @throws JsonProcessingException
    * @return void
    * @throws  
    */
    @Test
    public void test004() throws JsonProcessingException {
        ReleaseInfo upInfo = new ReleaseInfo();
        upInfo.setKid(728714439552114688L);
        upInfo.setLastUpdateUserId(724007310011252736L);
        System.out.println(new ObjectMapper().writeValueAsString(releaseInfoApi.deleteBykid(upInfo)));
    }
}
