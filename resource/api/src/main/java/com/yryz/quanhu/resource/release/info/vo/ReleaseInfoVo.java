package com.yryz.quanhu.resource.release.info.vo;

import com.yryz.common.constant.ModuleContants;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
* @Description: 文章
* @author wangheng
* @date 2018年1月22日 下午4:46:32
*/
public class ReleaseInfoVo extends ReleaseInfo {

    private static final long serialVersionUID = 1L;

    /**
    * 功能枚举
    */
    private String moduleEnum = ModuleContants.RELEASE;

    /**  
    * @Fields : 创建者用户信息
    */
    UserSimpleVO user;

    public UserSimpleVO getUser() {
        return user;
    }

    public void setUser(UserSimpleVO user) {
        this.user = user;
    }

    public String getModuleEnum() {
        return moduleEnum;
    }
}
