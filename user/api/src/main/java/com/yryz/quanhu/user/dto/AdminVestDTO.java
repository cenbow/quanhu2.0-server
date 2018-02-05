package com.yryz.quanhu.user.dto;

import com.yryz.quanhu.user.vo.UserBaseInfoVO;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/2/5
 * @description
 */
public class AdminVestDTO extends UserBaseInfoVO {

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private String keyword;

    private String userPwd;


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
