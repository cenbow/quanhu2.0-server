package com.yryz.quanhu.message.push.entity;

import java.io.Serializable;

/**
 * 阿里验证码 合法性检查 参数
 * @author jk
 *
 */
public class AfsCheckRequest implements Serializable {
	private static final long serialVersionUID = 4919032099708573171L;
	private Integer platform=3;//1：Android端； 2：iOS端； 3：PC端及其他
	private String session;
	private String sig;
	private String token;
	private String scene;

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

}
