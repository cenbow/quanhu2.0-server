package com.yryz.quanhu.user.service;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.user.dto.UserStarAuthDto;
import com.yryz.quanhu.user.dto.UserTagDTO;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/3 11:10
 * Created by huangxy
 * 达人后台管理api
 *
 */
public interface UserStarForAdminApi {

    /**
     * 添加更新标签
     * @param dto
     * @return
     */
    public Response<Boolean> updateTags(UserStarAuthDto dto);

    /**
     * 用户达人审核
     * @param dto
     * @return
     */
    public Response<Boolean> updateAuth(UserStarAuthDto dto);

    /**
     * 达人推荐更新
     * @param dto
     * @return
     */
    public Response<Boolean> updateRecmd(UserStarAuthDto dto);

    /**
     * 设置/取消置顶
     * @param dto
     * @return
     */
    public Response<Boolean> updateRecmdTop(UserStarAuthDto dto);

    /**
     * 批量更新排序
     * @param kids
     * @param sorts
     * @return
     */
    public Response<Boolean> updateRecmdsort(List<UserStarAuthDto> dtos);

    /**
     * 查询达人标签
     * @param dto
     * @return
     */
    public Response<List<UserTagDTO>> getTags(UserStarAuthDto dto);
    /**
     * 查看认证信息资料
     * @param dto
     * @return
     */
    public Response<UserStarAuthDto> getAuth(UserStarAuthDto dto);

    /**
     * 查询达人认证列表
     * @param dto
     * @return
     */
    public Response<PageList<UserStarAuthDto>> listByAuth(UserStarAuthDto dto);

    /**
     * 查询达人认证日志
     * @param dto
     * @return
     */
    public Response<PageList<UserStarAuthDto>> listByAuthLog(UserStarAuthDto dto);

    /**
     * 查询达人推荐列表
     * @param dto
     * @return
     */
    public Response<PageList<UserStarAuthDto>> listByRecmd(UserStarAuthDto dto);



}
