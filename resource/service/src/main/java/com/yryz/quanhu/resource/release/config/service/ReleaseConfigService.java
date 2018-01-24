package com.yryz.quanhu.resource.release.config.service;

import com.yryz.quanhu.resource.release.config.vo.ReleaseConfigVo;

/**
* @author wangheng
* @date 2018年1月23日 下午2:21:34
*/
public interface ReleaseConfigService {
    /**  
    * @Description: 获取 分类对应的发布模板
    * @author wangheng
    * @param @param classifyId
    * @param @return
    * @return ReleaseConfigVo
    * @throws  
    */
    public ReleaseConfigVo getTemplate(Long classifyId);

    /**  
    * @Description: 更新 发布模板
    * @author wangheng
    * @param @param releaseConfigVo
    * @param @return
    * @return int
    * @throws  
    */
    public int updateTemplate(ReleaseConfigVo releaseConfigVo);
}
