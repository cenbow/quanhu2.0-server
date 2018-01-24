package com.yryz.quanhu.resource.release.info.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.release.info.dto.ReleaseInfoDto;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;

/**
* @Description: 平台文章Api
* @author wangheng
* @date 2018年1月22日 下午4:47:20
*/
public interface ReleaseInfoApi {

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
    public Response<ReleaseInfoVo> infoByKid(Long kid, Long headerUserId);

    /**  
    * @Description: 分页查询
    * @author wangheng
    * @param @param dto
    * @param @param headerUserId
    * @param @return
    * @return PageList<ReleaseInfoVo>
    * @throws  
    */
    public Response<PageList<ReleaseInfoVo>> pageByCondition(ReleaseInfoDto dto, Long headerUserId);

    /**  
    * @Description: 作者删除
    * @author wangheng
    * @param @param record
    * @param @return
    * @return int
    * @throws  
    */
    public Response<Integer> deleteBykid(ReleaseInfo upInfo);

    /**  
    * @Description: 批量下架
    * @author wangheng
    * @param @param record
    * @param @param dto
    * @param @return
    * @return int
    * @throws  
    */
    public Response<Integer> shelvesByCondition(ReleaseInfo record, ReleaseInfoDto dto);
}
