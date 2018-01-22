package com.yryz.quanhu.message.common.im.yunxin.vo;

import java.io.Serializable;

/**
 * 群返回信息vo
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class ImTeamInfoVo implements Serializable{
	/**
	 * 群id
	 */
	private Integer tid;
	/**
	 * 群名称
	 */
	private String tname;
	/**
	 * 群头像
	 */
	private String icon;
	
	/**
	 * 群公告
	 */
	private String annuncement;
	/**
	 * 群主
	 */
	private String owner;
	/**
	 * 最大群用户数
	 */
	private int maxusers;
	/**
	 * 群建好后，sdk操作时，0不用验证，1需要验证,2不允许任何人加入
	 */
	private int joinmode;
	/**
	 * 群描述
	 */
	private String intro;
	/**
	 * 群用户数
	 */
	private String size;
	/**
	 * 群自定义扩展属性
	 */
	private String custom;

	private boolean mute;
	
	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getAnnuncement() {
		return annuncement;
	}

	public void setAnnuncement(String annuncement) {
		this.annuncement = annuncement;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getMaxusers() {
		return maxusers;
	}

	public void setMaxusers(int maxusers) {
		this.maxusers = maxusers;
	}

	public int getJoinmode() {
		return joinmode;
	}

	public void setJoinmode(int joinmode) {
		this.joinmode = joinmode;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public boolean isMute() {
		return mute;
	}

	public void setMute(boolean mute) {
		this.mute = mute;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
