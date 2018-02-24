package com.yryz.quanhu.coterie.coterie.service;


import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.coterie.coterie.vo.CoterieAdmin;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieSearchParam;
import com.yryz.quanhu.coterie.coterie.vo.CoterieUpdateAdmin;

public interface CoterieAdminService {

    /**
     * 分页查询
     */
    PageList<CoterieInfo> queryCoterieByCoterieSearch(CoterieSearchParam param);

    /**
     * 审核私圈
     */
    Response<String> audit(CoterieUpdateAdmin info);

    /**
     * 私圈详情
     * @param coterieId
     * @return
     */
    public CoterieInfo getCoterieInfo(Long coterieId);
}
