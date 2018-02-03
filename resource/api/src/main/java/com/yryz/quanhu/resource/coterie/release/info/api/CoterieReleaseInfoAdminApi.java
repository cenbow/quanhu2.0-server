package com.yryz.quanhu.resource.coterie.release.info.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.coterie.release.info.vo.CoterieReleaseInfoVo;
import com.yryz.quanhu.resource.release.info.dto.ReleaseInfoDto;

/**
* @Description: 私圈文章管理
* @author wangheng
* @date 2018年1月24日 下午1:17:12
*/
public interface CoterieReleaseInfoAdminApi {
    /**  
    * @Description: 列表分页查询
    * @author wangheng
    * @param dto
    * @return PageList<CoterieReleaseInfoVo>
    * @throws  
    */
    public Response<PageList<CoterieReleaseInfoVo>> pageByCondition(ReleaseInfoDto dto);
}
