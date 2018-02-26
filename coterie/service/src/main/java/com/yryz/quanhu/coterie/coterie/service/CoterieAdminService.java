package com.yryz.quanhu.coterie.coterie.service;


import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.coterie.coterie.vo.CoterieAdmin;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieSearchParam;
import com.yryz.quanhu.coterie.coterie.vo.CoterieUpdateAdmin;

import java.util.List;

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

    /**
     * 根据名称查私圈
     * @param name
     * @return
     */
    List<CoterieInfo> findByName(String name);

    /**
     * 编辑私圈
     */
    void modify(CoterieInfo info);


    /**
     * 查询私圈
     */
    CoterieInfo find(Long coterieId);
}
