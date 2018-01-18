package com.yryz.quanhu.user.service;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
import com.yryz.quanhu.user.dto.UserRelationDto;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/18 13:23
 * Created by huangxy
 */
public interface UserRelationApi {

    public static enum TYPE{
        FOLLOW,                                 //关注
        CANCEL_FOLLOW,                          //取消关注
        BLACK,                                  //拉黑
        CANCEL_BLACK,                           //取消拉黑
        FRIEND,                                 //好友
        CANCEL_FRIEND                           //取消好友
    }

    public static enum REMARK_SOURCE{
        PLATFORM,                               //平台
        COTERIE,                                //私圈
        IM                                      //云信IM
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
     * 设置备注名
     * @param dto
     * @return
     */
    Response<Boolean> setRemarkName(UserRelationDto dto);

    /**
     * 恢复备注名
     * @param dto
     * @return
     */
    Response<Boolean> recoverRemarkName(UserRelationDto dto);

    /**
     * 查询用户关系
     * @param dto
     * @return
     */
    Response<List<UserRelationDto>> selectBy(UserRelationDto dto);

    /**
     * 查询用户关系(分页)
     * @param dto
     * @return
     */
    Response<PageList<UserRelationDto>> selectByPage(UserRelationDto dto);

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
