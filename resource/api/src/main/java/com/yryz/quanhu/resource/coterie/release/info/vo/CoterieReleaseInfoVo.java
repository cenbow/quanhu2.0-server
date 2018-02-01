package com.yryz.quanhu.resource.coterie.release.info.vo;

import com.yryz.common.constant.ModuleContants;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
* @Description: 私圈文章
* @author wangheng
* @date 2018年1月24日 下午1:13:13
*/
public class CoterieReleaseInfoVo extends ReleaseInfo {

    private static final long serialVersionUID = 1L;

    /**  
    * @Fields : 可读标识（10：不可读；11：可读）
    */
    private Byte canReadFlag;

    /**  
     * @Fields : 创建者用户信息
     */
    UserSimpleVO user;

    /**
    * 功能枚举
    */
    private String moduleEnum = ModuleContants.RELEASE;

    public Byte getCanReadFlag() {
        return canReadFlag;
    }

    public void setCanReadFlag(Byte canReadFlag) {
        this.canReadFlag = canReadFlag;
    }

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
