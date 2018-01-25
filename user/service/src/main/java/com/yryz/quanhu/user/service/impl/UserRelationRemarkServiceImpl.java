package com.yryz.quanhu.user.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.exception.QuanhuException;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.dao.UserRelationRemarkDao;
import com.yryz.quanhu.user.dto.UserRelationRemarkDto;
import com.yryz.quanhu.user.service.UserRelationRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/19 15:12
 * Created by huangxy
 */
@Service
public class UserRelationRemarkServiceImpl implements UserRelationRemarkService{

    private static final String TABLE_NAME = "qh_user_relation_remark";

    @Autowired
    private UserRelationRemarkDao userRelationRemarkDao;

    @Reference
    private IdAPI idAPI;

    @Override
    public Boolean setRemarkName(UserRelationRemarkDto remarkDto) {

        int updateCount = 0;
        try{

            String sourceUserId = remarkDto.getSourceUserId();
            String targetUserId = remarkDto.getTargetUserId();

            /**
             * ★★★★★关键验证★★★★★
             * 自己不允许对自己设置备注
             */
            if(sourceUserId.equalsIgnoreCase(targetUserId)){
                throw new QuanhuException("","","don't allow set remark name to self");
            }

            /**
             * 验证关系（待议？）
             */
            UserRelationRemarkDto dbRemarkDto = userRelationRemarkDao.selectByUser(remarkDto);
            if(null == dbRemarkDto){                    //不存在
                dbRemarkDto = new UserRelationRemarkDto();
                dbRemarkDto.setNewRecord(true);
                dbRemarkDto.setDelFlag(10);
                dbRemarkDto.setSourceUserId(sourceUserId);
                dbRemarkDto.setTargetUserId(targetUserId);
                dbRemarkDto.setCreateUserId(Long.parseLong(sourceUserId));
                dbRemarkDto.setRemarkType(remarkDto.getRemarkType());
                dbRemarkDto.setKid(idAPI.getKid(TABLE_NAME).getData());
            }else {
                dbRemarkDto.setNewRecord(false);
            }

            //重新赋值
            dbRemarkDto.setRemarkValue(remarkDto.getRemarkValue());
            dbRemarkDto.setLastUpdateUserId(Long.parseLong(sourceUserId));

            //新增，修改
            if(dbRemarkDto.isNewRecord()){
                updateCount = userRelationRemarkDao.insert(dbRemarkDto);
            }else{
                updateCount = userRelationRemarkDao.update(dbRemarkDto);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return updateCount==1?true:false;
    }

    @Override
    public Boolean resetRemarkName(UserRelationRemarkDto remarkDto) {
        int updateCount = 0;
        try{
            //原备注名置空
            remarkDto.setRemarkValue("");
            remarkDto.setLastUpdateUserId(Long.parseLong(remarkDto.getSourceUserId()));
            updateCount = userRelationRemarkDao.resetByUser(remarkDto);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return updateCount==1?true:false;
    }

    @Override
    public UserRelationRemarkDto getRemarkDto(String sourceUserId, String targetUserId, UserRelationConstant.TYPE type) {
        try {
            UserRelationRemarkDto dto = new UserRelationRemarkDto();
            dto.setRemarkType(type.getCode());
            dto.setSourceUserId(sourceUserId);
            dto.setTargetUserId(targetUserId);
            return userRelationRemarkDao.selectByUser(dto);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserRelationRemarkDto> selectBy(String sourceUserId, UserRelationConstant.TYPE type) {
        try{
            UserRelationRemarkDto dto = new UserRelationRemarkDto();
            dto.setRemarkType(type.getCode());
            dto.setSourceUserId(sourceUserId);

            return userRelationRemarkDao.getList(dto);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserRelationRemarkDto> selectBy(String sourceUserId,UserRelationConstant.TYPE type,Set<String> targetUserIds) {
        return userRelationRemarkDao.selectByUserIds(UserRelationRemarkDto.class,type.getCode(),sourceUserId,targetUserIds);
    }
}
