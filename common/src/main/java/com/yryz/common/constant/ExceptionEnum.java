package com.yryz.common.constant;

public enum ExceptionEnum {
    SysException("100", "系统异常", "网络开小差了，请稍候再试！"),
    LockException("300", "分布式锁异常", "网络开小差了，请稍候再试！"),
    BusiException("500", "业务逻辑异常", "网络开小差了，请稍候再试！"),
    Exception("1000", "未知错误", "网络开小差了，请稍后再试"),
    NEEDTOKEN("1001", "用户未登录或者被挤掉了", "请重新登录"),
    TOKEN_EXPIRE("1002", "短期token过期", "请重新登录"),
    TOKEN_INVALID("1003", "无效token", "请重新登录"),
    NO_TOKEN("1004", "token被后台清掉了", "请重新登录"),
    NEED_PHONE("1005", "需要绑定手机号", "需要绑定手机号"),
    USER_MISSING("1006", "用户不存在", "用户不存在"),
    USER_NO_TALK("1007", "用户被平台禁言", "用户被平台禁言"),
    USER_FREEZE("1008", "圈乎用户，您的账号因违规被冻结，如有疑问请联系客服处理。", "用户被冻结"),
    USER_DISTORY("1009", "圈乎用户，您的账号因违规被冻结，如有疑问请联系客服处理。", "用户被注销"),
    USER_LOGIN_PWD_ERROR("1010","登录密码错误","登录密码错误"),
    
    USER_BLACK_TARGETUSER_ERROR("1011","您已把该资源作者拉黑，不允许操作","您已把该资源作者拉黑"),
    TARGETUSER_BLACK_USER_ERROR("1012","该资源作者已将您拉黑，不允许操作","该资源作者已将您拉黑"),
    USER_FOLLOW_MAX_COUNT_ERROR("1013","您的关注人数已达到上限，不允许操作","您的关注人数已达到上限"),
    USER_RELATION_TO_SELF_ERROR("1014","无法对您自己添加关系，不允许操作","不允许对自己设置任何关系"),
    USER_REMARK_TO_SELF_ERROR("1015","无法对您自己添加备注，不允许操作","不允许对自己设置备注信息"),
    USER_NOT_FRIEND_ERROR("1016","该用户和您暂不是好友关系，不允许操作","该用户和您暂不是好友关系"),

    USER_NO_RIGHT_TOREAD("1020", "您无权去查看。", "你无权执行此操作"),
    USER_NO_RIGHT_TODELETE("1021", "您无权执行删除操作。", "您无权执行删除操作。"),
    USER_NO_RIGHT_TOREJECT("1022", "您无权执行拒接回答操作。", "您无权执行拒接回答操作。"),
    SMS_VERIFY_CODE_ERROR("1023","验证码错误","验证码错误"),
    COTERIE_USER_NO_TALK("1024", "该用户私圈内禁言，禁止访问！", "您已被圈主禁言！"),
    USER_NOT_SUFFICIENT_FUNDS("1025", "您的余额不足。", "您的余额不足。"),
    COTERIE_NOT_HAVE_COTERIE("1026", "您不是圈主,禁止操作！", "您不是私圈圈主！"),
    COTERIE_NOT_MEMBER("1027", "您不是私圈成员，禁止访问！", "您不是私圈成员！"),
    COTERIE_NON_EXISTENT("1028", "未找到该私圈,访问出错", "私圈不存在！"),
    RESOURCE_NO_EXIST("1030", "资源不存在", "资源不存在。"),
    NOT_TO_PLAY_REWARD("1031", "打赏人不允许 打赏自己的资源！", "不能对自己打赏哦~"),
    
    ValidateException("2000", "数据验证失败！", "网络开小差了，请稍候再试！"),
    PARAM_MISSING("2001", "参数缺失", "网络开小差了，请稍候再试！"),


    PAGE_PARAM_ERROR("2002", "您的分页查询参数不正确，请更正！", "分页参数不正确！"),
    RPC_RESPONSE_DATA_EXCEPTION("2003", "网络开小差了，请稍候再试！", "RpcResponse对象返回Data为空！"),
    MONGO_EXCEPTION("2004", "网络开小差了，请稍候再试！", "mongoDB查询列表异常！"),
    PUSH_MESSAGE_EXCEPTION("2005", "推送消息出错！", "推送消息出错！"),
    RPC_RESPONSE_EXCEPTION("2006", "网络开小差了，请稍候再试！", "RpcResponse对象为空！"),

    TRANSMIT_CONTENT_ERROR("2050", "您输入的转发心得超长，请修改！", "您输入的转发心得超长，请修改！");




    private String code;

    private String showmsg;

    private String errorMsg;

    /*ExceptionEnum(String code, String msg, String errorMsg) {
        this.code = code;
        this.msg = msg;
        this.errorMsg = errorMsg;
    }*/

    ExceptionEnum(String code, String errorMsg, String showmsg) {
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

/*    public static final Map<String, ExceptionEnum> exceptionEnumMap = new HashMap<>();

    static {
        if (ExceptionEnum.values() != null && ExceptionEnum.values().length > 0) {
            for (ExceptionEnum e : ExceptionEnum.values()) {
                exceptionEnumMap.put(e.getCode(), e);
            }
        }
    }*/

}
