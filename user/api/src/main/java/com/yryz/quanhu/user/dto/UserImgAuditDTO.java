package com.yryz.quanhu.user.dto;

import java.util.List;

import com.yryz.common.entity.GenericEntity;
import com.yryz.quanhu.user.contants.Constants.ImgAuditStatus;

/**
 * 用户头像审核
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class UserImgAuditDTO extends GenericEntity{
    /**
     * kid集合
     */
    private List<Long> kids;
    /**
     * 用户id集合
     */
    private List<Long> userIds;
    /**
     * 审核状态 {@link ImgAuditStatus}
     */
    private Byte auditStatus;
    /**
     * 操作人名称
     */
    private String operational;
	public Byte getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Byte auditStatus) {
		this.auditStatus = auditStatus;
	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

	public String getOperational() {
		return operational;
	}

	public void setOperational(String operational) {
		this.operational = operational;
	}

	public UserImgAuditDTO() {
		super();
	}

	public UserImgAuditDTO(List<Long> kids, List<Long> userIds, Byte auditStatus, String operational) {
		super();
		this.kids = kids;
		this.userIds = userIds;
		this.auditStatus = auditStatus;
		this.operational = operational;
	}

	public List<Long> getKids() {
		return kids;
	}

	public void setKids(List<Long> kids) {
		this.kids = kids;
	}



}
