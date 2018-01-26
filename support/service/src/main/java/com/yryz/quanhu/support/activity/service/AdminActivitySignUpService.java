package com.yryz.quanhu.support.activity.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.support.activity.entity.ActivityInfo;
import com.yryz.quanhu.support.activity.entity.ActivityInfoAndEnrolConfig;
import com.yryz.quanhu.support.activity.dto.AdminActivityInfoDto;
import com.yryz.quanhu.support.activity.dto.AdminActivityInfoSignUpDto;
import com.yryz.quanhu.support.activity.vo.AdminActivityInfoSignUpVo;
import com.yryz.quanhu.support.activity.vo.AdminActivityInfoVo;
import com.yryz.quanhu.support.activity.vo.AdminActivityRecordVo;

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
	 *  后台编辑报名类活动
	 * @param activityInfoAndEnrolConfig
	 * @param request
	 * @return
	 */
	//void activitySignEdit(ActivityInfoAndEnrolConfig activityInfoAndEnrolConfig);
	/**
	 *  后台修改备注、下线操作
	 * @param activityInfo
	 * @param request
	 * @return
	 */
	//void operationEdit(ActivityInfo activityInfo);

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
	 * 修改活动顺序（后台管理）
	 */
	//void updateActivitySortBatch(Long[] idsArr, String custId);
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
