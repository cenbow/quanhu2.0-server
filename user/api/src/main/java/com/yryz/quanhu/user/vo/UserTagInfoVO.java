package com.yryz.quanhu.user.vo;

import java.util.List;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/2/2
 * @description
 */
public class UserTagInfoVO {

    private List<UserTagVO> userTagList;

    public List<UserTagVO> getUserTagList() {
        return userTagList;
    }

    public void setUserTagList(List<UserTagVO> userTagList) {
        this.userTagList = userTagList;
    }
}
