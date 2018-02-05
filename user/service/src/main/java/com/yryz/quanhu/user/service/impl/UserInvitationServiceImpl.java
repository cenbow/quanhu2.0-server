package com.yryz.quanhu.user.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.PageModel;
import com.yryz.quanhu.dymaic.service.ElasticsearchService;
import com.yryz.quanhu.score.service.EventAcountApiService;
import com.yryz.quanhu.score.vo.EventAcount;
import com.yryz.quanhu.user.dao.UserBaseInfoDao;
import com.yryz.quanhu.user.dao.UserLoginLogDao;
import com.yryz.quanhu.user.dao.UserOperateInfoDao;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.dto.UserInvitationDto;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.entity.UserLoginLog;
import com.yryz.quanhu.user.provider.UserInvitationProvider;
import com.yryz.quanhu.user.service.UserInvitationService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/2 10:55
 * Created by huangxy
 */
@Service
public class UserInvitationServiceImpl implements UserInvitationService {

    private static final Logger logger = LoggerFactory.getLogger(UserInvitationServiceImpl.class);

    @Autowired
    private UserOperateInfoDao userOperateInfoDao;

    @Autowired
    private UserBaseInfoDao userBaseInfoDao;

    @Autowired
    private UserLoginLogDao userLoginLogDao;

    @Reference(check = false)
    private EventAcountApiService eventAcountApiService;


    @Value("${appId}")
    private String appId;
    /**
     * 统计任意用户的邀请人数，和被邀请人信息
     * @param dto
     * @return
     */
    @Override
    public PageList<UserInvitationDto> listCount(UserInvitationDto dto) {

        //分页查询列表用户信息
        PageHelper.startPage(dto.getPageNo(),dto.getPageSize());
        List<UserInvitationDto> infos = userOperateInfoDao.getUserByParam(dto);
        PageHelper.clearPage();


        //构建查询用户iD
        Set<Long> fromUserIds = new HashSet<>();
        for (int i = 0 ; i< infos.size() ; i++){
            fromUserIds.add(infos.get(i).getUserId());
        }

        //查询用户的邀请人关系
        List<UserInvitationDto> fromUserArray = userOperateInfoDao.getUserByUserIds(fromUserIds);
        if(CollectionUtils.isEmpty(fromUserArray)){
            fromUserArray = new ArrayList<>();
        }

        List<UserInvitationDto> returnArray = new ArrayList<>();

        //合并数据
        for (int i = 0 ; i < infos.size() ; i++){

            UserInvitationDto invitationDto = infos.get(i);
            invitationDto.setFromUser(new UserInvitationDto());

            //合并用户邀请人基本信息
            this.mergeFromArray(fromUserArray,invitationDto);

            returnArray.add(invitationDto);
        }
        return new PageModel<UserInvitationDto>().getPageList(returnArray);
    }



    private void mergeLastLogs(List<UserLoginLog> logs,UserInvitationDto dto){

        for(int i = 0 ; i< logs.size() ;i++){
            UserLoginLog loginLog = logs.get(i);
            if(loginLog.getUserId().longValue()==dto.getUserId().longValue()){
                dto.setUserLastLoginDate(loginLog.getCreateDate());
                break;
            }
        }

    }


    private void mergeFromArray(List<UserInvitationDto> fromUserArray,UserInvitationDto dto){
        Long fromUserId = dto.getUserInviterId();
        for(int i =0 ; i < fromUserArray.size() ; i++){
            UserInvitationDto _dto = fromUserArray.get(i);
            if(fromUserId.longValue() == _dto.getUserId().longValue()){
                dto.setFromUser(_dto);
                break;
            }
        }
    }



    private void mergeEvents(Map<Long,EventAcount> eventMaps,UserInvitationDto dto){
        EventAcount eventAcount = eventMaps.get(dto.getUserId());
        if(eventAcount!=null){
            dto.setUserScoreNum(eventAcount.getScore());
        }
    }

    /**
     * 用户任意用户邀请人数列表，
     * @param dto
     * @return
     */
    @Override
    public PageList<UserInvitationDto> list(UserInvitationDto dto) {

        //分页查询列表用户信息
        PageHelper.startPage(dto.getPageNo(),dto.getPageSize());
        List<UserInvitationDto> fromInfos = userOperateInfoDao.getUserByParam(dto);
        PageHelper.clearPage();

        //构建邀请人集合
        Set<Long> fromUserIds = new HashSet<>();
        for (int i = 0 ; i< fromInfos.size() ; i++){
            fromUserIds.add(fromInfos.get(i).getUserId());
        }

        //查询所有待查询邀请人所邀请的所有人
        List<UserInvitationDto> invitations = userOperateInfoDao.getUserByInviterIds(fromUserIds);
        Set<Long> allUserIds = new HashSet<>();
        List<String> allUserIdsArray = new ArrayList<>();
        for (int i = 0 ; i < invitations.size() ; i++){
            allUserIds.add(invitations.get(i).getUserId());
        }

        //查询积分成长值
        Map<Long,EventAcount> eventMap = new HashMap<>();
        try {
            Response<Map<Long,EventAcount>> rpc = eventAcountApiService.getEventAcountBatch(allUserIds);
            if(rpc.success()&&rpc.getData()!=null){
                eventMap = rpc.getData();
            }
        }catch (Exception e){
            logger.warn(e.getMessage(),e);
        }

        List<UserLoginLog> loginLogs = new ArrayList<>();
        //查询最后登录时间
        if(allUserIdsArray.size()>0){
            loginLogs = userLoginLogDao.getLastLoginTime(allUserIdsArray);
            if(CollectionUtils.isEmpty(loginLogs)){
                loginLogs = new ArrayList<>();
            }
        }


        List<UserInvitationDto> returnArray = new ArrayList<>();
        //合并数据

        for(int i = 0 ; i < invitations.size() ; i++){

            UserInvitationDto invitationDto = invitations.get(i);

            this.mergeEvents(eventMap,invitationDto);

            this.mergeLastLogs(loginLogs,invitationDto);

            this.mergeFromArray(fromInfos,invitationDto);

            returnArray.add(invitationDto);

        }
        return new PageModel<UserInvitationDto>().getPageList(returnArray);
    }
}
