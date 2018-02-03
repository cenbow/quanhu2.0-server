package com.yryz.common.exception;

import com.yryz.common.constant.ExceptionEnum;
import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("serial")
public class QuanhuException extends IllegalArgumentException {

    private String code;
    private String msg;
    private String errorMsg;

    public QuanhuException(ExceptionEnum exceptionEnum) {
        if (exceptionEnum != null) {
            this.code = exceptionEnum.getCode();
            this.msg = exceptionEnum.getShowMsg();
            this.errorMsg = exceptionEnum.getErrorMsg();
        }
    }

    public QuanhuException(String code, String msg, String errorMsg , Throwable cause) {
        super(errorMsg,cause);
        this.code = code;
        this.msg = msg;
        this.errorMsg = errorMsg;
    }
    
    public QuanhuException(String code, String msg, String errorMsg) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.errorMsg = errorMsg;
    }
    
    public static QuanhuException busiError(ExceptionEnum exceptionEnum){
    	return busiError(exceptionEnum.getCode(), exceptionEnum.getShowMsg(), exceptionEnum.getErrorMsg());
    }
    
    public static QuanhuException busiError(String errorMsg) {
        return busiError(null, errorMsg);
    }

    public static QuanhuException busiError(String code, String errorMsg) {
        if (StringUtils.isBlank(errorMsg)) {
        	errorMsg = ExceptionEnum.BusiException.getShowMsg();
        }
        if (StringUtils.isBlank(code)) {
        	code = ExceptionEnum.BusiException.getCode();
        }
        return new QuanhuException(code, ExceptionEnum.BusiException.getShowMsg(), errorMsg);
    }
    
    public static QuanhuException busiError(String code,String msg, String errorMsg) {
        if (StringUtils.isBlank(msg)) {
            msg = ExceptionEnum.BusiException.getShowMsg();
        }
        if (StringUtils.isBlank(errorMsg)) {
            errorMsg = ExceptionEnum.BusiException.getErrorMsg();
        }
        if (StringUtils.isBlank(code)) {
        	code = ExceptionEnum.BusiException.getCode();
        }
        return new QuanhuException(code, msg, errorMsg);
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
