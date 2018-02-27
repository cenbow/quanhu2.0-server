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
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.List;

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


    /**
     * 设置私圈信息
     * @param info  coterieId必填
     */
    @Override
    public Response<CoterieInfo> modifyCoterieInfo(CoterieInfo info) {

        try {
            if (info == null ||  info.getCoterieId()==null) {
                return ResponseUtils.returnCommonException("私圈ID不存在");
            }
            String name = StringUtils.trim(info.getName());
            if (StringUtils.isNotEmpty(name)) {
                List<CoterieInfo> clist = coterieAdminService.findByName(name);
                if (!clist.isEmpty()&&!clist.get(0).getCoterieId().equals(info.getCoterieId())) {
                    return ResponseUtils.returnCommonException("私圈名称已存在");
                }
            }
            if (info.getJoinFee() != null && (info.getJoinFee() > 50000 || info.getJoinFee() < 0)) {
                return ResponseUtils.returnCommonException("加入私圈金额设置不正确。");
            }
            if (info.getConsultingFee()!=null && (info.getConsultingFee() > 10000 || info.getConsultingFee() < 0)) {
                return ResponseUtils.returnCommonException("私圈咨询费金额设置不正确。");
            }
            coterieAdminService.modify(info);
            return ResponseUtils.returnObjectSuccess(coterieAdminService.find(info.getCoterieId()));
        } catch (Exception e) {
            return ResponseUtils.returnException(e);
        }
    }
}
