package com.yryz.quanhu.resource.release.info.vo;

import java.io.Serializable;

import com.yryz.quanhu.resource.release.config.vo.ReleaseConfigVo;

/**
* @author wangheng
* @date 2018年1月24日 下午1:05:12
*/
public class ReleaseInfoCfgVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private ReleaseConfigVo cfgVo;
    private ReleaseInfoVo infoVo;

    public ReleaseConfigVo getCfgVo() {
        return cfgVo;
    }

    public void setCfgVo(ReleaseConfigVo cfgVo) {
        this.cfgVo = cfgVo;
    }

    public ReleaseInfoVo getInfoVo() {
        return infoVo;
    }

    public void setInfoVo(ReleaseInfoVo infoVo) {
        this.infoVo = infoVo;
    }

}
