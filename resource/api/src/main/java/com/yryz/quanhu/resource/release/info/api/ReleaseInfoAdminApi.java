package com.yryz.quanhu.resource.release.info.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.release.info.dto.ReleaseInfoDto;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;

/**
* @Description: 平台文章管理后台Api
* @author wangheng
* @date 2018年1月22日 下午4:47:20
*/
public interface ReleaseInfoAdminApi {

    /**  
    * @Description: 马甲发布
    * @author wangheng
    * @param record
    * @return ReleaseInfo
    * @throws  
    */
    public Response<ReleaseInfo> release(ReleaseInfo record);

    /**  
    * @Description: 详情
    * @author wangheng
    * @param kid
    * @param headerUserId
    * @return ReleaseInfoVo
    * @throws  
    */
    public Response<ReleaseInfoVo> infoByKid(Long kid);

    /**  
    * @Description: 列表分页查询
    * @author wangheng
    * @param dto
    * @return PageList<ReleaseInfoVo>
    * @throws  
    */
    public Response<PageList<ReleaseInfoVo>> pageByCondition(ReleaseInfoDto dto);

    /**  
    * @Description: 批量上下架
    * @author wangheng
    * @param @param kids
    * @param @param shelveFlag
    * @param @param lastUpdateUserId
    * @param @return
    * @return Response<Integer>
    * @throws  
    */
    public Response<Integer> batchShelve(Long[] kids, Byte shelveFlag, Long lastUpdateUserId);
}
