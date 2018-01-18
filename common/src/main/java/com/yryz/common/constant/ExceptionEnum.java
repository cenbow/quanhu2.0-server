package com.yryz.common.constant;

import java.util.HashMap;
import java.util.Map;

public enum ExceptionEnum {
    SysException("102",  "系统异常", "网络开小差了，请稍候再试！"),
    NEEDTOKEN("105", "用户未登录或者被挤掉了", "请重新登录"),
    TOKEN_EXPIRE("106","短期token过期","请重新登录"),
    PARAM_MISSING("110","参数缺失","网络开小差了，请稍候再试！"),
    Exception("111","未知错误","网络开小差了，请稍后再试"),
    TOKEN_INVALID("122","无效token","请重新登录"),
    NO_TOKEN("123","token被后清掉了","请重新登录"),
    NEED_PHONE("124","需要绑定手机号","需要绑定手机号"),
    USER_MISSING("125","用户不存在","用户不存在"),
    USER_NO_TALK("126","用户被禁言","用户被禁言"),
    USER_FREEZE("127","圈乎用户，您的账号因违规被冻结，如有疑问请联系客服处理。","用户被冻结"),
    USER_DISTORY("128","圈乎用户，您的账号因违规被冻结，如有疑问请联系客服处理。","用户被注销"),
    ValidateException("2000","数据验证失败！","网络开小差了，请稍候再试！"),
    LockException("3000", "分布式锁异常", "网络开小差了，请稍候再试！"),
    BusiException("4000", "业务逻辑异常", "网络开小差了，请稍候再试！");
    private String code;

    private String showmsg;

    private String errorMsg;

    /*ExceptionEnum(String code, String msg, String errorMsg) {
        this.code = code;
        this.msg = msg;
        this.errorMsg = errorMsg;
    }*/

    ExceptionEnum(String code, String errorMsg,String showmsg) {
        this.code = code;
        this.showmsg = showmsg;
        this.errorMsg = errorMsg;
    }

    public String getCode() {
        return code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getShowMsg() {
        return showmsg;
    }

    public static final Map<String, ExceptionEnum> exceptionEnumMap = new HashMap<>();

    static {
        if (ExceptionEnum.values() != null && ExceptionEnum.values().length > 0) {
            for (ExceptionEnum e : ExceptionEnum.values()) {
                exceptionEnumMap.put(e.getCode(), e);
            }
        }
    }

}
