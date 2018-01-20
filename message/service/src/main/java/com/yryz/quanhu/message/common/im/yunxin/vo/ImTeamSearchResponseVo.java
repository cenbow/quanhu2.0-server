package com.yryz.quanhu.message.common.im.yunxin.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 群信息查询返回vo
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class ImTeamSearchResponseVo implements Serializable{
	
	private int code;
	
	private List<ImTeamInfoVo> tinfos = new ArrayList<>();

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public List<ImTeamInfoVo> getTinfos() {
		return tinfos;
	}

	public void setTinfos(List<ImTeamInfoVo> tinfos) {
		this.tinfos = tinfos;
	}
	
}
