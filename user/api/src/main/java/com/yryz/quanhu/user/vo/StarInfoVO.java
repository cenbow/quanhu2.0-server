package com.yryz.quanhu.user.vo;

import java.io.Serializable;
import java.util.Map;

import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.user.dto.StarAuthInfo;
/**
 * 达人列表信息
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class StarInfoVO implements Serializable {
	/**
	 * 用户信息
	 */
	private UserSimpleVO userInfo = new UserSimpleVO();
	/**
	 * 达人信息
	 */
	private UserStarSimpleVo starInfo = new UserStarSimpleVo();
	
	public void parseUser(String userId, Map<String, UserSimpleVO> userMap) {
		if (userMap != null) {
			UserSimpleVO info = userMap.get(userId);
			if (info != null) {
				this.userInfo = (UserSimpleVO) GsonUtils.parseObj(info, UserSimpleVO.class);
			}else{
				this.userInfo = new UserSimpleVO();				
			}
		}
	}
	
	public void parseStar(Long userId, Map<String, StarAuthInfo> starMap) {
		if (starMap != null) {
			StarAuthInfo info = starMap.get(userId);
			if (info != null) {
				this.starInfo = (UserStarSimpleVo) GsonUtils.parseObj(info, UserStarSimpleVo.class);
				this.starInfo.setUserId(userId);
			}else{
				this.starInfo = new UserStarSimpleVo();				
			}
		}
	}
	
	public UserSimpleVO getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserSimpleVO userInfo) {
		this.userInfo = userInfo;
	}
	public UserStarSimpleVo getStarInfo() {
		return starInfo;
	}
	public void setStarInfo(UserStarSimpleVo starInfo) {
		this.starInfo = starInfo;
	}

	@Override
	public String toString() {
		return "StarInfoDTO [userInfo=" + userInfo + ", starInfo=" + starInfo + "]";
	}
}
