package com.yryz.quanhu.user.service;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
import com.yryz.quanhu.user.dto.UserRelationDto;

import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/18 13:23
 * Created by huangxy
 */
public interface UserRelationApi {

    public static final int YES = 11;
    public static final int NO = 10;
    /**
     * 关系状态
     */
    public static enum STATUS{
        FANS,                                   //粉丝
        FOLLOW,                                 //关注
        TO_BLACK,                               //拉黑
        FROM_BLACK,                             //被拉黑
        FRIEND;                                 //好友
    }
    /**
     * 操作事件
     */
    public static enum EVENT{
        SET_FOLLOW,                             //关注
        CANCEL_FOLLOW,                          //取消关注
        SET_BLACK,                              //拉黑
        CANCEL_BLACK,                           //取消拉黑
    }

    /**
     * 设置关系
     * @param dto
     * @return
     */
    Response<Boolean> setRelation(UserRelationDto dto);

    /**
     * 检查关系
     * @param dto
     * @return
     */
    Response<Boolean> checkRelation(UserRelationDto dto);

    /**
     * 查询用户关系(分页)
     * @param dto
     * @return
     */
    Response<PageList<UserRelationDto>> selectByPage(UserRelationDto dto);

    /**
     * 查询用户指定关系
     * @param sourceUserId
     * @param status
     * @return  返回所有用户ID
     */
    Response<Set<String>> selectBy(String sourceUserId, STATUS status);

    /**
     * 查询用户关系
     * @param userSourceKid     用户
     * @param userTargetKids    目标用户
     * @return
     */
    Response<List<UserRelationDto>> selectBy(String userSourceKid, String[] userTargetKids);

    /**
     * 统计用户所有关系数量
     * @param userSourceKid
     * @return
     */
    Response<UserRelationCountDto> totalBy(String userSourceKid);

}
