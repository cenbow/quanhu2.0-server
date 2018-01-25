package com.yryz.quanhu.resource.coterie.release.info.api;

import java.util.Map;

import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.coterie.release.info.vo.CoterieReleaseInfoVo;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;

/**
* @Description: 私圈文章
* @author wangheng
* @date 2018年1月24日 下午1:17:12
*/
public interface CoterieReleaseInfoApi {
    /**  
    * @Description: 发布
    * @author wangheng
    * @param @param record
    * @param @return
    * @return ReleaseInfo
    * @throws  
    */
    public Response<ReleaseInfo> release(ReleaseInfo record);

    /**  
    * @Description: 详情
    * @author wangheng
    * @param @param kid
    * @param @param headerUserId
    * @param @return
    * @return ReleaseInfoVo
    * @throws  
    */
    public Response<CoterieReleaseInfoVo> infoByKid(Long kid, Long headerUserId);
    
    
    /**  
    * @Description: 付费阅读文章，创建订单
    * @author wangheng
    * @param @param kid
    * @param @param headerUserId
    * @param @return
    * @return Response<Map<String,Object>>
    * @throws  
    */
    public Response<Map<String, Object>> createOrder(Long kid, Long headerUserId);
}
