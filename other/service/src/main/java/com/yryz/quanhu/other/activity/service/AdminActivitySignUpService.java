package com.yryz.quanhu.other.activity.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.other.activity.dto.AdminActivityInfoDto;
import com.yryz.quanhu.other.activity.dto.AdminActivityInfoSignUpDto;
import com.yryz.quanhu.other.activity.entity.ActivityInfo;
import com.yryz.quanhu.other.activity.entity.ActivityInfoAndEnrolConfig;
import com.yryz.quanhu.other.activity.vo.AdminActivityInfoSignUpVo;
import com.yryz.quanhu.other.activity.vo.AdminActivityInfoVo;
import com.yryz.quanhu.other.activity.vo.AdminActivityRecordVo;

import java.util.List;


public interface AdminActivitySignUpService {
	/**
	 * 后台发布报名类活动
	 * @param activityInfo
	 * @param activityEnrolConfig
	 * @param request
	 * @return
	 */
	int activityRelease(ActivityInfoAndEnrolConfig activityInfoAndEnrolConfig);
	/**
	 * (后台)报名活动列表
	 * @param pageNo
	 * @param pageSize
	 * @param adminActivityInfoSignUpDto
	 * @return
	 */
	PageList<AdminActivityInfoSignUpVo> adminlist(Integer pageNo, Integer pageSize,
                                                  AdminActivityInfoSignUpDto adminActivityInfoSignUpDto);
	/**
	 * （后台）获取报名类活动详情
	 * @param id
	 * @return
	 */
	ActivityInfoAndEnrolConfig getActivitySignUpDetail(String id);

	/**
	 * 根据活动ID获取活动主表详情
	 * @param id
	 * @return
	 */
	ActivityInfo getActivityDetail(String id);
	/**
	 * (后台)报名人员列表
	 * */
	PageList<AdminActivityRecordVo> attendlist(Integer pageNo, Integer pageSize,
                                               AdminActivityRecordVo adminActivityRecordVo);
	/**
	 * (后台)所有上线的活动列表
	 * */
	PageList<AdminActivityInfoVo> adminAllSharelist(Integer pageNo, Integer pageSize, AdminActivityInfoDto adminActivityInfoDto);
	/**
	 *  后台编辑活动
	 * @param activityInfoAndEnrolConfig
	 * @param request
	 * @return
	 */
	int activityInfoEdit(ActivityInfo activityInfo);
	
	/**
	 * (后台)所有上线的活动列表+不分页
	 * @return
	 */
	List<AdminActivityInfoVo>	adminAllSharelistNoPage();
	
	

}
