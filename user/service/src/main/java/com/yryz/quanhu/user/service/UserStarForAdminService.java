package com.yryz.quanhu.user.service;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.user.dto.UserStarAuthDto;
import com.yryz.quanhu.user.dto.UserStarAuthDto;
import com.yryz.quanhu.user.dto.UserTagDTO;
import com.yryz.quanhu.user.vo.UserTagVO;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/3 16:44
 * Created by huangxy
 */
public interface UserStarForAdminService {

    /**
     * 用户达人审核
     * @param dto
     * @return
     */
    public Boolean updateAuth(UserStarAuthDto dto);

    /**
     * 达人推荐更新
     * @param dto
     * @return
     */
    public Boolean updateRecmd(UserStarAuthDto dto,boolean isTop);


    /**
     * 查询达人标签详情
     * @param dto
     * @return
     */
    public List<UserTagDTO>  getTags(UserStarAuthDto dto);

    /**
     * 更新达人推荐标签
     * @param dto
     * @return
     */
    public Boolean updateTags(UserStarAuthDto dto);

    /**
     * 查看认证信息资料
     * @param dto
     * @return
     */
    public UserStarAuthDto getAuth(UserStarAuthDto dto);

    /**
     * 查询达人认证列表
     * @param dto
     * @return
     */
    public PageList<UserStarAuthDto> listByAuth(UserStarAuthDto dto);

    /**
     * 查询达人认证日志
     * @param dto
     * @return
     */
    public PageList<UserStarAuthDto> listByAuthLog(UserStarAuthDto dto);

    /**
     * 查询达人推荐列表
     * @param dto
     * @return
     */
    public PageList<UserStarAuthDto> listByRecmd(UserStarAuthDto dto);




}
