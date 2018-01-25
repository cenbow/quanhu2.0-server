package com.yryz.quanhu.resource.coterie.release.info.vo;

import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;

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

    public Byte getCanReadFlag() {
        return canReadFlag;
    }

    public void setCanReadFlag(Byte canReadFlag) {
        this.canReadFlag = canReadFlag;
    }
}
