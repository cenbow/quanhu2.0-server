package com.yryz.quanhu.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.service.UserRelationService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/18 15:27
 * Created by huangxy
 */
@Service
@Transactional
public class UserRelationDBImpl implements UserRelationService{

    @Override
    public boolean setRelation(UserRelationDto dto) {
        return false;
    }

    @Override
    public boolean checkRelation(UserRelationDto dto) {
        return false;
    }

    @Override
    public boolean setRemarkName(UserRelationDto dto) {
        return false;
    }

    @Override
    public boolean recoverRemarkName(UserRelationDto dto) {
        return false;
    }

    @Override
    public List<UserRelationDto> selectBy(UserRelationDto dto) {
        return null;
    }

    @Override
    public PageList<UserRelationDto> selectByPage(UserRelationDto dto) {
        return null;
    }

    @Override
    public List<UserRelationDto> selectBy(String userSourceKid, String[] userTargetKids) {
        return null;
    }

    @Override
    public UserRelationCountDto totalBy(String userSourceKid) {
        return null;
    }
}
