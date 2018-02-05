package com.yryz.quanhu.resource.release.buyrecord.vo;

import com.yryz.quanhu.resource.release.buyrecord.entity.ReleaseBuyRecord;
import com.yryz.quanhu.resource.vo.ResourceVo;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
* @Description: 私圈购买记录
* @author wangheng
* @date 2018年2月5日 上午10:47:29
*/
public class CoterieBuyRecordVo extends ReleaseBuyRecord {

    private static final long serialVersionUID = 1L;

    private String coterieName;

    private ResourceVo resourceVo;

    private Object extJsonObj;

    private UserSimpleVO user;

    public String getCoterieName() {
        return coterieName;
    }

    public void setCoterieName(String coterieName) {
        this.coterieName = coterieName;
    }

    public ResourceVo getResourceVo() {
        return resourceVo;
    }

    public void setResourceVo(ResourceVo resourceVo) {
        this.resourceVo = resourceVo;
    }

    public Object getExtJsonObj() {
        return extJsonObj;
    }

    public void setExtJsonObj(Object extJsonObj) {
        this.extJsonObj = extJsonObj;
    }

    public UserSimpleVO getUser() {
        return user;
    }

    public void setUser(UserSimpleVO user) {
        this.user = user;
    }
}
