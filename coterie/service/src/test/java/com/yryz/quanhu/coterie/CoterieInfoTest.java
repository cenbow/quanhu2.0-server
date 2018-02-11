package com.yryz.quanhu.coterie;

import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.coterie.vo.Coterie;
import com.yryz.quanhu.coterie.coterie.vo.CoterieBasicInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;

import org.assertj.core.util.Lists;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CoterieInfoTest {
    @Autowired
    private CoterieApi coterieApi;

    @Test
    public void applyCreate() {
    	CoterieBasicInfo info=new CoterieBasicInfo();
    	info.setIcon("http://icon");
    	info.setIntro("jk 的私圈2");
    	info.setJoinCheck(0);
    	info.setJoinFee(0);
    	info.setName("功守道2");
    	info.setOwnerId("729669966696603648");
    	coterieApi.applyCreate(info);
    }

    @Test
    public void modifyCoterieInfo() {
    	CoterieInfo info=new CoterieInfo();
    	info.setIntro("jk 的私圈3");
    	info.setJoinFee(100);
    	info.setName("功守道3");
    	info.setOwnerId("729669966696603648");
    	info.setCoterieId(747457453497794560L);
    	coterieApi.modifyCoterieInfo(info);
    }
    
    @Test
    public void getMyCreateCoterie() {
    	List<CoterieInfo> list=ResponseUtils.getResponseData(coterieApi.getMyCreateCoterie("365768348024832",1,10));
    	System.out.println(list);
    }
    
    @Test
    public void getMyJoinCoterie() {
    	List<CoterieInfo> list=ResponseUtils.getResponseData(coterieApi.getMyJoinCoterie("729669966696603648", 1, 10));
    	System.out.println(list);
    }
    
    @Test
    public void queryCoterieInfo() {
    	CoterieInfo list=ResponseUtils.getResponseData(coterieApi.queryCoterieInfo(747587986143854592L));
    	System.out.println(list);
    }
    
    @Test
    public void getMyCoterieCount() {
    	Integer list=ResponseUtils.getResponseData(coterieApi.getMyCoterieCount("729669966696603648"));
    	System.out.println(list);
    }
    
    @Test
    public void queryHotCoterieList() {
    	List<CoterieInfo> list=ResponseUtils.getResponseData(coterieApi.queryHotCoterieList(1,10));
    	System.out.println(list);
    }
    
    @Test
    public void regroupQr() {
    	String list=ResponseUtils.getResponseData(coterieApi.regroupQr(8764485668L));
    	System.out.println(list);
    }
    
    @Test
    public void getCreateCoterie() {
    	List<CoterieInfo> list=ResponseUtils.getResponseData(coterieApi.getCreateCoterie("729669966696603648", 1, 10));
    	System.out.println(list);
    }
    
    @Test
    public void getByKids() {
    	List<Long> kidList=Lists.newArrayList();
    	kidList.add(367337454723072L);
    	kidList.add(367784315871232L);
    	kidList.add(367787629371392L);
    	List<Coterie> list=ResponseUtils.getResponseData(coterieApi.getByKids(kidList));
    	System.out.println(list.size());
    }
    
}
