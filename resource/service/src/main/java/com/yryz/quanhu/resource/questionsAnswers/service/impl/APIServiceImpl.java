package com.yryz.quanhu.resource.questionsAnswers.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.resource.questionsAnswers.service.APIservice;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.springframework.stereotype.Service;

@Service
public class APIServiceImpl implements APIservice {
    @Reference
    private IdAPI idAPI;

    @Reference
    private UserApi userApi;

    @Reference
    private CoterieApi coterieApi;

    @Override
    public Long getKid() {
        Response<Long> longResponse=idAPI.getSnowflakeId();
        if(ResponseConstant.SUCCESS.getCode().equals(longResponse.getCode())){
            return  longResponse.getData();
        }
        return  null;
    }

    @Override
    public UserSimpleVO getUser(Long kid) {
        if(null==kid){
            return null;
        }
        Response<UserSimpleVO> vo=userApi.getUserSimple(kid);
        if(ResponseConstant.SUCCESS.getCode().equals(vo.getCode())){
            return  vo.getData();
        }
        return null;
    }

    @Override
    public CoterieInfo getCoterieinfo(Long citeriaId){
        Response<CoterieInfo>  coterieInfoResponse= coterieApi.queryCoterieInfo(citeriaId);
        if(ResponseConstant.SUCCESS.getCode().equals(coterieInfoResponse.getCode())){
            CoterieInfo coterieInfo=coterieInfoResponse.getData();
            return coterieInfo;
        }
        return null;

    }

}
