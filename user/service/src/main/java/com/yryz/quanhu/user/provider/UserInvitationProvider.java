package com.yryz.quanhu.user.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.user.dto.UserInvitationDto;
import com.yryz.quanhu.user.service.UserInvitationForAdminApi;
import com.yryz.quanhu.user.service.UserInvitationService;
import com.yryz.quanhu.user.service.UserRelationApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/2 10:50
 * Created by huangxy
 */
@Service(interfaceClass = UserInvitationForAdminApi.class)
public class UserInvitationProvider implements UserInvitationForAdminApi{

    private static final Logger logger = LoggerFactory.getLogger(UserInvitationProvider.class);

    @Autowired
    private UserInvitationService userInvitationService;

    @Override
    public Response<PageList<UserInvitationDto>> listCount(UserInvitationDto dto) {
        try {
            logger.info("listCount={} start", JSON.toJSON(dto));
            return ResponseUtils.returnObjectSuccess(userInvitationService.listCount(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("listCount finish");
        }
    }

    @Override
    public Response<PageList<UserInvitationDto>> list(UserInvitationDto dto) {
        try {
            logger.info("list={} start", JSON.toJSON(dto));
            return ResponseUtils.returnObjectSuccess(userInvitationService.list(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseUtils.returnException(e);
        }finally {
            logger.info("list finish");
        }
    }
}
