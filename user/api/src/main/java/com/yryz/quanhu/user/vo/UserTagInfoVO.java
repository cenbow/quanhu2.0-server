package com.yryz.quanhu.user.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/2/2
 * @description
 */
public class UserTagInfoVO implements Serializable {
    private static final long serialVersionUID = -7080012351087632243L;

    private List<UserTagVO> userTagInfoList;

    public List<UserTagVO> getUserTagInfoList() {
        return userTagInfoList;
    }

    public void setUserTagInfoList(List<UserTagVO> userTagInfoList) {
        this.userTagInfoList = userTagInfoList;
    }
}
