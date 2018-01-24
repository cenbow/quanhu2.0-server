package com.yryz.quanhu.resource.release.config.api;

import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.release.config.vo.ReleaseConfigVo;

/**
* @Description: 发布模板
* @author wangheng
* @date 2018年1月22日 下午2:56:39
*/
public interface ReleaseConfigApi {
    static String cacheKey(Long id) {
        return "quanhu:resource:release:config" + id;
    }

    /**  
    * @Description: 获取 分类对应的发布模板
    * @author wangheng
    * @param @param classifyId
    * @param @return
    * @return ReleaseConfigVo
    * @throws  
    */
    public Response<ReleaseConfigVo> getTemplate(Long classifyId);

    /**  
    * @Description: 更新 发布模板
    * @author wangheng
    * @param @param releaseConfigVo
    * @param @return
    * @return int
    * @throws  
    */
    public Response<Integer> updateTemplate(ReleaseConfigVo releaseConfigVo);
}
