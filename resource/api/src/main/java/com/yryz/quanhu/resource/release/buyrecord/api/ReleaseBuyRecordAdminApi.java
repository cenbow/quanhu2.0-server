package com.yryz.quanhu.resource.release.buyrecord.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.release.buyrecord.dto.ReleaseBuyRecordDto;
import com.yryz.quanhu.resource.release.buyrecord.vo.CoterieBuyRecordVo;

/**
* @author wangheng
* @date 2018年2月5日 上午11:25:48
*/
public interface ReleaseBuyRecordAdminApi {

    Response<PageList<CoterieBuyRecordVo>> pageByCondition(ReleaseBuyRecordDto releaseBuyRecordDto);
}
