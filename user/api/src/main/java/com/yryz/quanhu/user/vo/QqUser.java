package com.yryz.quanhu.user.vo;

import java.io.Serializable;

/**
 * qq用户信息
 * @author xiepeng
 *
 */
@SuppressWarnings("serial")
public class QqUser implements Serializable{
	/**
	 * 错误码 0-成功 其他失败
	 */
	private int ret = 0;
	/**
	 * 描述
	 */
	private String msg;
	/**
	 * 昵称
	 */
	private String nickname;
	private String figureurl_qq_l;
	private String figureurl_qq_2;
	/**
	 * 性别
	 */
	private String gender;
	
	public int getRet() {
		return ret;
	}
	public void setRet(int ret) {
		this.ret = ret;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getFigureurl_qq_l() {
		return figureurl_qq_l;
	}
	public void setFigureurl_qq_l(String figureurl_qq_l) {
		this.figureurl_qq_l = figureurl_qq_l;
	}
	public String getFigureurl_qq_2() {
		return figureurl_qq_2;
	}
	public void setFigureurl_qq_2(String figureurl_qq_2) {
		this.figureurl_qq_2 = figureurl_qq_2;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "QqUser [ret=" + ret + ", msg=" + msg + ", nickname=" + nickname + ", figureurl_qq_l=" + figureurl_qq_l
				+ ", figureurl_qq_2=" + figureurl_qq_2 + ", gender=" + gender + "]";
	}
}
