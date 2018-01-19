package com.yryz.quanhu.user.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.dao.UserRelationRemarkDao;
import com.yryz.quanhu.user.dto.UserRelationRemarkDto;
import com.yryz.quanhu.user.service.UserRelationRemarkService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/19 15:12
 * Created by huangxy
 */
public class UserRelationRemarkServiceImpl implements UserRelationRemarkService{

    @Autowired
    private UserRelationRemarkDao userRelationRemarkDao;

    @Reference
    private IdAPI idAPI;


    @Override
    public boolean setRemarkName(UserRelationRemarkDto remarkDto) {
        try{

            final String sourceUserId = remarkDto.getSourceUserId();
            final String targetUserId = remarkDto.getTargetUserId();

            /**
             * ★★★★★关键验证★★★★★
             * 自己不允许对自己设置备注
             */
            if(sourceUserId.equalsIgnoreCase(targetUserId)){
                throw new RuntimeException("don't allow set remark name to self");
            }

            /**
             * 验证关系（待议？）
             */

            UserRelationRemarkDto dbRemarkDto = userRelationRemarkDao.getUnion(remarkDto);
            if(null == dbRemarkDto){
                dbRemarkDto = new UserRelationRemarkDto();
                dbRemarkDto.setKid(idAPI.getKid("user_relation_remark"));
            }

        }catch (Exception e){

        }
        return false;
    }




    @Override
    public String getRemarkName(UserRelationRemarkDto remarkDto) {
        return null;
    }

    @Override
    public List<UserRelationRemarkDto> selectBy(UserRelationRemarkDto remarkDto) {
        return null;
    }
}
