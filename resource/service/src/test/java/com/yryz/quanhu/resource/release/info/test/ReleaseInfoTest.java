package com.yryz.quanhu.resource.release.info.test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoApi;
import com.yryz.quanhu.resource.release.info.dto.ReleaseInfoDto;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;

/**
* @Description: 平台文章
* @author wangheng
* @date 2018年1月24日 下午8:00:29
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReleaseInfoTest {

    @Reference
    ReleaseInfoApi releaseInfoApi;

    /**  
    * @Description: 平台文章 发布
    * @author wangheng
    * @param @throws JsonProcessingException
    * @return void
    * @throws  
    */
    @Test
    public void test001() throws JsonProcessingException {
        ReleaseInfo record = new ReleaseInfo();
        int random = new Random().nextInt();
        
        record.setContent(random + "正文哈哈哈哈");
        record.setContentSource("[{\"text\":\"" + record.getContent() + "\"}]");
        record.setCreateUserId(724011759597371392L);
        record.setImgUrl("imgUrl");
        record.setTitle(random + "标题哈哈哈");

        System.out.println(new ObjectMapper().writeValueAsString(releaseInfoApi.release(record)));
    }

    /**  
    * @Description: 平台文章 详情
    * @author wangheng
    * @param @throws JsonProcessingException
    * @return void
    * @throws  
    */
    @Test
    public void test002() throws JsonProcessingException {
        System.out.println(new ObjectMapper()
                .writeValueAsString(releaseInfoApi.infoByKid(736943287656628224L, 724011759597371392L)));
    }

    /**  
     * @Description: 平台文章 删除
     * @author wangheng
     * @param @throws JsonProcessingException
     * @return void
     * @throws  
     */
    @Test
    public void test003() throws JsonProcessingException {
        ReleaseInfo upInfo = new ReleaseInfo();
        upInfo.setKid(730182871691157504L);
        upInfo.setLastUpdateUserId(724007310011252736L);
        System.out.println(new ObjectMapper().writeValueAsString(releaseInfoApi.deleteBykid(upInfo)));
    }

    /**  
    * @Description: 平台文章 列表
    * @author wangheng
    * @param @throws JsonProcessingException
    * @return void
    * @throws  
    */
    @Test
    public void test004() throws JsonProcessingException {
        ReleaseInfoDto dto = new ReleaseInfoDto();
        // dto.setCoterieId(5259149661L);
        System.out.println(new ObjectMapper()
                .writeValueAsString(releaseInfoApi.pageByCondition(dto, 727909974996672512L, false, true)));
    }

    /**  
    * @Description: ids 集合查询
    * @author wangheng
    * @param @throws JsonProcessingException
    * @return void
    * @throws  
    */
    @Test
    public void test005() throws JsonProcessingException {
        Set<Long> set = new HashSet<>();
        set.add(732811597835960320L);
        System.out.println(new ObjectMapper().writeValueAsString(releaseInfoApi.selectByKids(set)));
    }
}
