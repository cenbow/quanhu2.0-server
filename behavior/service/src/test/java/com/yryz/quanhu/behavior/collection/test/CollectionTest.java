package com.yryz.quanhu.behavior.collection.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yryz.quanhu.behavior.collection.api.CollectionInfoApi;
import com.yryz.quanhu.behavior.collection.dto.CollectionInfoDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectionTest {

    @Reference
    CollectionInfoApi collectionInfoApi;

    @Test
    public void single() {
        CollectionInfoDto collectionInfoDto = new CollectionInfoDto();
        collectionInfoDto.setResourceId(748972116664401920L);
        collectionInfoDto.setModuleEnum("1006");
        collectionInfoDto.setCreateUserId(727061873573347328L);
        System.out.println(JSON.toJSONString(collectionInfoApi.single(collectionInfoDto)));
    }

    @Test
    public void del() {
        CollectionInfoDto collectionInfoDto = new CollectionInfoDto();
        collectionInfoDto.setResourceId(748972116664401920L);
        collectionInfoDto.setModuleEnum("1006");
        collectionInfoDto.setCreateUserId(727061873573347328L);
        System.out.println(JSON.toJSONString(collectionInfoApi.del(collectionInfoDto)));
    }

    @Test
    public void list() {
        CollectionInfoDto collectionInfoDto = new CollectionInfoDto();
        collectionInfoDto.setCreateUserId(727061873573347328L);
        System.out.println(JSON.toJSONString(collectionInfoApi.list(collectionInfoDto)));
    }

    @Test
    public void collectionStatus() {
        CollectionInfoDto collectionInfoDto = new CollectionInfoDto();
        collectionInfoDto.setResourceId(748972116664401920L);
        collectionInfoDto.setCreateUserId(727061873573347328L);
        System.out.println(JSON.toJSONString(collectionInfoApi.collectionStatus(collectionInfoDto)));
    }

}
