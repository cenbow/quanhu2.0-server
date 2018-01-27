package com.yryz.quanhu.message.commonsafe.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.entity.AfsCheckRequest;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.message.commonsafe.api.CommonSafeApi;
import com.yryz.quanhu.message.commonsafe.constants.CheckSlipCodeReturn;
import com.yryz.quanhu.message.commonsafe.dto.IpLimitDTO;
import com.yryz.quanhu.message.commonsafe.dto.VerifyCodeDTO;
import com.yryz.quanhu.message.commonsafe.service.CommonSafeService;
import com.yryz.quanhu.message.commonsafe.vo.VerifyCodeVO;

@Service(interfaceClass = CommonSafeApi.class)
public class CommonSafeProvider implements CommonSafeApi {
    private Logger logger = LoggerFactory.getLogger(CommonSafeProvider.class);

    @Autowired
    private CommonSafeService commonService;

    @Override
    public Response<VerifyCodeVO> getVerifyCode(VerifyCodeDTO codeDTO) {
        try {
            checkSmsDTO(codeDTO);
            return ResponseUtils.returnObjectSuccess(commonService.getVerifyCode(codeDTO));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("验证码获取异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> checkVerifyCode(VerifyCodeDTO codeDTO) {
        try {
            logger.info("checkVerifyCode codeDTO request: {}", GsonUtils.parseJson(codeDTO));
            checkSmsDTO(codeDTO);
            if (StringUtils.isBlank(codeDTO.getVerifyCode())) {
                throw QuanhuException.busiError(ExceptionEnum.PARAM_MISSING.getCode(), "验证码不能为空");
            }
            return ResponseUtils.returnObjectSuccess(commonService.checkVerifyCode(codeDTO));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("验证码验证异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> checkSlipCode(VerifyCodeDTO verifyCodeDTO, AfsCheckRequest afsCheckReq) {
        try {
            logger.info("sendVerifyCodeForSlip request, verifyCodeDTO: {}, afsCheckRequest: {}",
                    GsonUtils.parseJson(verifyCodeDTO), GsonUtils.parseJson(afsCheckReq));
            checkSplipCodeDTO(verifyCodeDTO);
            boolean result = commonService.checkSmsSlipCode(verifyCodeDTO, afsCheckReq);
            if(!result){
            	if (afsCheckReq == null) {
                    //需要滑动验证码
            		return ResponseUtils.returnObjectSuccess(CheckSlipCodeReturn.NEED_CODE.getCode());
                }else{
                	return ResponseUtils.returnObjectSuccess(CheckSlipCodeReturn.FAIL.getCode());
                }
            }else{
            	return ResponseUtils.returnObjectSuccess(CheckSlipCodeReturn.SUCCESS.getCode());
            }
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("sendVerifyCodeForSlip error", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<String> getImgVerifyCode(VerifyCodeDTO codeDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response<Boolean> checkImgVerifyCode(VerifyCodeDTO codeDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response<Boolean> saveIpCount(IpLimitDTO dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response<Boolean> checkIpLimit(IpLimitDTO dto) {
        // TODO Auto-generated method stub
        return null;
    }


    private void checkSmsDTO(VerifyCodeDTO codeDTO) {
        if (codeDTO == null) {
            throw QuanhuException.busiError("传参不合法");
        }
        if (StringUtils.isEmpty(codeDTO.getVerifyKey())) {
            throw QuanhuException.busiError("手机号为空");
        }
        if (codeDTO.getServiceCode() == null) {
            throw QuanhuException.busiError("功能码不能为空");
        }
        if (StringUtils.isBlank(codeDTO.getAppId())) {
            throw QuanhuException.busiError("应用id不能为空");
        }
        if (StringUtils.isBlank(codeDTO.getCommonServiceType())) {
            throw QuanhuException.busiError("业务类型不能为空");
        }
    }
    
    private void checkSplipCodeDTO(VerifyCodeDTO codeDTO) {
        if (codeDTO == null) {
            throw QuanhuException.busiError("传参不合法");
        }
        if (StringUtils.isEmpty(codeDTO.getVerifyKey())) {
            throw QuanhuException.busiError("手机号为空");
        }
        if (StringUtils.isBlank(codeDTO.getAppId())) {
            throw QuanhuException.busiError("应用id不能为空");
        }
        if (StringUtils.isBlank(codeDTO.getCommonServiceType())) {
            throw QuanhuException.busiError("业务类型不能为空");
        }
    }
}
