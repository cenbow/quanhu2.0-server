package com.yryz.quanhu.coterie.exception;


/**
 * rpc服务异常基类，所有服务业务异常必须继承此异常
 * 
 * @author xiepeng
 *
 *
 *         业务异常码定义规范为<code, msg> ：operate.serviceName.model.action.type 用户异常：
 *         yryz.cust 好友异常： yryz.relation IM异常： yryz.im 资金异常： yryz.order 红包异常：
 *         yryz.redpacket 打赏异常： yryz.reward 广告异常： yryz.advert 抽奖异常： yryz.lottery
 *         圈子异常： yryz.circle 随手晒异常：yryz.shine
 * 
 */
@SuppressWarnings("serial")
public class ServiceException extends RuntimeException {


	/** MSG信息描述 */
	public static final String MSG_PARAM_MISSING = "参数类型错误或为空";
	public static final String MSG_PARAM_INVALID = "参数有误";

	/** 系统性异常 */
	public static final String CODE_SYS_ERROR = "yryz.service.system_error";
	public static final String MSG_SYS_ERROR = "系统异常";

	public static final String CODE_PARAMS_ERROR = "yryz.service.params_error";
	public static final String MSG_PARAMS_ERROR = "参数错误";

	private String code;
	private String msg;

	public ServiceException(String code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public ServiceException() {
		super();
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

	public static String parseMsg(String msg, String... params) {
		if (null == params || params.length < 1) {
			return null;
		}

		StringBuilder sb = new StringBuilder(msg==null?"":msg);
		sb.append("{");
		for (int i = 0; i < params.length; i++) {
			if(i>0) {
				sb.append(",");
			}
			sb.append(params[i]);
		}
		sb.append("}");
		return sb.toString();
	}

	public static ServiceException sysError() {
		return new ServiceException(CODE_SYS_ERROR, MSG_SYS_ERROR);
	}

	public static ServiceException paramsError(String... params) {
		return new ServiceException(CODE_PARAMS_ERROR, parseMsg(MSG_PARAMS_ERROR, params));
	}
	
	/**
	 * Avoid Dubbo ExceptionFilter
	 * @param e
	 * @return
			 */
	public static ServiceException parse(ServiceException e) {
		return new ServiceException(e.getCode(), e.getMsg());
	}

}
