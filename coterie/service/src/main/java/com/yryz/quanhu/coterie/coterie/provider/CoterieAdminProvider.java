package com.yryz.quanhu.coterie.coterie.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieAdminAPI;
import com.yryz.quanhu.coterie.coterie.service.CoterieAdminService;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieSearchParam;
import com.yryz.quanhu.coterie.coterie.vo.CoterieUpdateAdmin;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserLoginSimpleVO;

import javax.annotation.Resource;

@Service(interfaceClass=CoterieAdminAPI.class)
public class CoterieAdminProvider implements CoterieAdminAPI {


    @Resource
    private CoterieAdminService coterieAdminService;

    @Reference
    private UserApi userApi;


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

    @Override
    public Response<CoterieInfo> queryCoterieInfo(Long coterieId) {

        if (coterieId == null) {
            return ResponseUtils.returnCommonException("coterieId is not null");
        }
        try {
            CoterieInfo info = coterieAdminService.getCoterieInfo(coterieId);
            if (info != null) {
                //todo user info
                UserLoginSimpleVO simpleVO = ResponseUtils
                        .getResponseData(userApi.getUserLoginSimpleVO(Long.parseLong(info.getOwnerId())));

                if (null != simpleVO) {
                    info.setPhone(simpleVO.getUserPhone());
                    info.setOwnerName(simpleVO.getUserNickName());
                    info.setOwnerIntro(simpleVO.getUserDesc());
                    info.setStarTradeField(simpleVO.getStarTradeField());
                }
            }
            return ResponseUtils.returnObjectSuccess(info);
        } catch (Exception e) {
            return ResponseUtils.returnException(e);
        }
    }
}
