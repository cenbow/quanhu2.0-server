package com.yryz.quanhu.message.im.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * IM聊天群数据实体类
 * 
 * @author xiepeng
 *
 */
@SuppressWarnings("serial")
public class TeamModel implements Serializable{

	/** 群ID */
	private String tid;

	/** 所属圈子ID */
	private String appId;

	/** 群名称，最大长度64字符 */
	private String tname;

	/** 群主用户帐号，最大长度32字符 */
	private String owner;

	/** 群公告，最大长度1024字符 */
	private String announcement;

	/** 群描述，最大长度512字符 */
	private String intro;

	/** 邀请发送的文字，最大长度150字符 */
	private String msg;

	/** 管理后台建群时，0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群。其它会返回414 */
	private int magree;

	/** 群建好后，sdk操作时，0不用验证，1需要验证,2不允许任何人加入。其它返回414 */
	private int joinmode;

	/** 群头像，最大长度1024字符 */
	private String icon;

	/** 群创建时间 */
	private Date createTime;
	/**
	 * 加入群成员ids
	 */
	private String members;
	/**
	 * 群状态 0:正常 1:删除
	 */
	private Byte status;
	/**
	 * 群组禁言 1-禁言 0-解禁
	 */
	private Byte mute;
	
	/**
	 * 群人数
	 */
	private Integer memberNum;
	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getMagree() {
		return magree;
	}

	public void setMagree(int magree) {
		this.magree = magree;
	}

	public int getJoinmode() {
		return joinmode;
	}

	public void setJoinmode(int joinmode) {
		this.joinmode = joinmode;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMembers() {
		return members;
	}

	public void setMembers(String members) {
		this.members = members;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getMute() {
		return mute;
	}

	public void setMute(Byte mute) {
		this.mute = mute;
	}

	public Integer getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(Integer memberNum) {
		this.memberNum = memberNum;
	}

	/**
	 * 
	 * @exception 
	 */
	public TeamModel() {
		super();
	}

	/**
	 * @param tid
	 * @param memberNum
	 * @exception 
	 */
	public TeamModel(String tid, Integer memberNum) {
		super();
		this.tid = tid;
		this.memberNum = memberNum;
	}

	@Override
	public String toString() {
		return "TeamModel [tid=" + tid + ", appId=" + appId + ", tname=" + tname + ", owner=" + owner
				+ ", announcement=" + announcement + ", intro=" + intro + ", msg=" + msg + ", magree=" + magree
				+ ", joinmode=" + joinmode + ", icon=" + icon + ", createTime=" + createTime + ", members=" + members
				+ ", status=" + status + ", mute=" + mute + ", memberNum=" + memberNum + "]";
	}

}
