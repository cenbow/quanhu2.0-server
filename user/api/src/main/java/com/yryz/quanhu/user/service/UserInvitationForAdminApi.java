package com.yryz.quanhu.user.service;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.user.dto.UserInvitationDto;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/1 13:58
 * Created by huangxy
 */
public interface UserInvitationForAdminApi {

    public Response<PageList<UserInvitationDto>> listCount(UserInvitationDto dto);

    public Response<PageList<UserInvitationDto>> list(UserInvitationDto dto);

}
