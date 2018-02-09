package com.yryz.quanhu.message;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.message.common.im.yunxin.YunxinConfig;
import com.yryz.quanhu.message.im.api.ImAPI;
import com.yryz.quanhu.message.im.entity.BlackAndMuteListVo;
import com.yryz.quanhu.message.im.entity.ImRelation;
import com.yryz.quanhu.message.im.entity.ImUser;
import com.yryz.quanhu.message.push.api.PushAPI;
import com.yryz.quanhu.message.push.entity.PushReqVo;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/19
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ImTest {

    @Reference(retries = 0)
    ImAPI imAPI;
    @Reference
    UserApi userApi;
    
    @Test
    public void addTest() {
        AdminUserInfoDTO infoDTO = new AdminUserInfoDTO();
        infoDTO.setPhone("186");
        infoDTO.setAppId("vebff12m1762");
        PageList<UserBaseInfoVO> pageList = userApi.listUserInfo(1, 100, infoDTO).getData();
        for(UserBaseInfoVO infoVO : pageList.getEntities()){
        	ImUser user = new ImUser();
            user.setUserId(infoVO.getUserId().toString());
            user.setIconUrl(infoVO.getUserImg());
            user.setNick(infoVO.getUserNickName());
        	System.out.println(JsonUtils.toFastJson(infoVO));
        	Response<Boolean> response = imAPI.addUser(user);
        	System.out.println("addTest: "+ response);
        }
        
        //Response<Boolean> response = imAPI.addUser(user);
        //System.out.println("addTest: "+ response);
    }

    //@Test
    public void setSpecialRelationTest() {
        ImRelation imRelation = new ImRelation();
        imRelation.setUserId("724011759597371392");
        imRelation.setTargetUserId("724011759597371393");
        imRelation.setRelationType("1");
        imRelation.setRelationValue("1");
        Response<Boolean> response = imAPI.setSpecialRelation(imRelation);
        System.out.println("setSpecialRelation: " + JSON.toJSONString(response));
    }

   //@Test
    public void blackAndMuteListTest() {
        ImRelation imRelation = new ImRelation();
        imRelation.setUserId("724011759597371392");
        Response<BlackAndMuteListVo> blackAndMuteList = imAPI.listBlackAndMuteList(imRelation);
        System.out.println("blackAndMuteList: " + JSON.toJSONString(blackAndMuteList));
    }
}
