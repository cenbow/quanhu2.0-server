package com.yryz.quanhu.coterie.coterie.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieAdminAPI;
import com.yryz.quanhu.coterie.coterie.service.CoterieAdminService;
import com.yryz.quanhu.coterie.coterie.vo.CoterieAdmin;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieSearchParam;
import com.yryz.quanhu.coterie.coterie.vo.CoterieUpdateAdmin;

import javax.annotation.Resource;

@Service(interfaceClass=CoterieAdminAPI.class)
public class CoterieAdminProvider implements CoterieAdminAPI {


    @Resource
    private CoterieAdminService coterieAdminService;


    @Override
    public Response<PageList<CoterieInfo>> getCoterieByPage(CoterieSearchParam param) {
        try {
            PageList<CoterieInfo> coterieInfos = coterieAdminService.queryCoterieByCoterieSearch(param);
            return ResponseUtils.returnObjectSuccess(coterieInfos);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        }
    }



    /**
     * 编辑私圈
     */
    public Response<String> audit(CoterieUpdateAdmin info) {

        return coterieAdminService.audit(info);
    }
}
