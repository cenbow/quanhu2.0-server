package com.yryz.quanhu.coterie.coterie.vo;

import java.io.Serializable;

/**
 * 
 * @author jiangkun
 * @version 1.0
 * @date 2017年10月18日 上午9:50:14
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
@SuppressWarnings("serial")
public class CoterieAuditInfo implements Serializable{
	private static final long serialVersionUID = 8593772834773281708L;
    /**
     * 审核人ID
     */
    private String custId;

    /**
     * 审核人姓名
     */
    private String custName;

    /**
     * 审核说明
     */
    private String remark;

    /**
     * 私圈ID
     */
    private String coterieId;

    /**
     * 审核结果:12审批未通过，13上架，14下架
     */
    private Byte status;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(String coterieId) {
        this.coterieId = coterieId == null ? null : coterieId.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "CoterieAuditInfo [custId=" + custId + ", custName=" + custName + ", remark=" + remark + ", coterieId="
				+ coterieId + ", status=" + status + "]";
	}
}
