package com.yryz.quanhu.resource;


import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.advertisement.api.AdvertisementAPI;
import com.yryz.quanhu.resource.advertisement.dto.AdvertisementAdminDto;
import com.yryz.quanhu.resource.advertisement.entity.Advertisement;
import com.yryz.quanhu.resource.advertisement.vo.AdvertisementAdminVo;
import com.yryz.quanhu.resource.advertisement.vo.AdvertisementVo;
import org.apache.http.client.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdvertisementTest {

    @Autowired
    private AdvertisementAPI advertisementAPI;

    @Test
    public void listAdmin(){
        AdvertisementAdminDto advertisementAdminDto = new AdvertisementAdminDto();
        Response<PageList<AdvertisementAdminVo>> response = advertisementAPI.listAdmin(advertisementAdminDto);
    }

    @Test
    public void add(){
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvDef(1);
        advertisement.setAdvName("贡嘎");
        advertisement.setAdvSort(0);
        advertisement.setAdvStatus(10);
        advertisement.setAdvType(10);
        advertisement.setAdvUrl("/");
        advertisement.setEndDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
        advertisement.setEndTime(DateUtils.formatDate(new Date(), "HH:mm:ss"));
        advertisement.setImgUrl("/");
        advertisement.setNote("xxxxx");
        advertisement.setSkipType(5001);
        advertisement.setStartDate("2018-1-23");
        advertisement.setStartTime("09:00:00");
        Response<Integer> add = advertisementAPI.add(advertisement);
    }

    @Test
    public void update(){
        Advertisement advertisement = new Advertisement();
        advertisement.setKid(180428L);
        advertisement.setAdvDef(1);
        advertisement.setAdvName("贡嘎嘎嘎嘎嘎嘎嘎");
        advertisement.setAdvSort(0);
        advertisement.setAdvStatus(10);
        advertisement.setAdvType(10);
        advertisement.setAdvUrl("/");
        advertisement.setEndDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
        advertisement.setEndTime(DateUtils.formatDate(new Date(), "HH:mm:ss"));
        advertisement.setImgUrl("/");
        advertisement.setNote("xxxxx");
        advertisement.setSkipType(5001);
        advertisement.setStartDate("2018-1-23");
        advertisement.setStartTime("09:00:00");
        Response<Integer> add = advertisementAPI.update(advertisement);
    }

    @Test
    public void detail(){
        Response<AdvertisementVo> response = advertisementAPI.detail(180428L);
    }
}

