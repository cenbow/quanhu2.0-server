package com.yryz.quanhu.user.contants;

public class UserStarContants {
	/**
	 * 认证类型
	 *
	 */
    public enum StarAuthType{
    	/** 个人认证 */
    	PERSON((byte)10),
    	/** 企业认证 */
    	BUSSIESS((byte)11);
    	private byte type;
    	StarAuthType(byte type) {
			this.type = type;
		}
    	public byte getType(){
    		return this.type;
    	}
    }
    
    /**
     * 认证方式
     *
     */
    public enum StarAuthWay{
    	/** 用户申请 */
    	USER_APLLY((byte)10),
    	/** 平台设置 */
    	ADMIN_SET((byte)11);
    	private byte way;
    	StarAuthWay(byte way) {
			this.way = way;
		}
    	public byte getWay(){
    		return this.way;
    	}
    }
    
    /**
     * 认证审核状态
     *
     */
    public enum StarAuditStatus{
    	/** 待申请 */
    	WAIT_AUDIT((byte)10),
    	/** 审核通过 */
    	AUDIT_SUCCESS((byte)11),
    	/** 审核失败 */
    	AUDIT_FAIL((byte)12),
    	/** 取消认证 */
    	CANCEL_AUTH((byte)13);
    	private byte status;
    	StarAuditStatus(byte status) {
			this.status = status;
		}
    	public byte getStatus(){
    		return this.status;
    	}
    }
    
    /**
     * 达人推荐状态
     *
     */
    public enum StarRecommendStatus{
    	/** 否 */
    	FALSE((byte)10),
    	/** 是 */
    	TRUE((byte)11);
    	private byte status;
    	StarRecommendStatus(byte status) {
			this.status = status;
		}
    	public byte getStatus(){
    		return this.status;
    	}
    }
}
