package com.rongzhong.component.pay.entity;


/**
 * 身份证相关信息
 * @author Administrator
 *
 */
public class IdCardInfo {
	private String name; // 姓名
	private String idCardNo; // 证件号
	private String validate; // 有效期
	private String remark; // 备注
	
	public IdCardInfo() {
		super();
	}
	public IdCardInfo(String name, String idCardNo) {
		super();
		this.name = name;
		this.idCardNo = idCardNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
