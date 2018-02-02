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

    private List<UserTagVO> userTagList;

    public List<UserTagVO> getUserTagList() {
        return userTagList;
    }

    public void setUserTagList(List<UserTagVO> userTagList) {
        this.userTagList = userTagList;
    }
}
