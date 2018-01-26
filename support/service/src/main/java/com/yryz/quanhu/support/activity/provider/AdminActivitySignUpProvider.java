package com.yryz.quanhu.support.activity.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.support.activity.api.AdminActivityPrizesApi;
import com.yryz.quanhu.support.activity.api.AdminActivitySignUpApi;
import com.yryz.quanhu.support.activity.dto.AdminActivityInfoDto;
import com.yryz.quanhu.support.activity.dto.AdminActivityInfoSignUpDto;
import com.yryz.quanhu.support.activity.entity.ActivityInfo;
import com.yryz.quanhu.support.activity.entity.ActivityInfoAndEnrolConfig;
import com.yryz.quanhu.support.activity.service.AdminActivitySignUpService;
import com.yryz.quanhu.support.activity.vo.AdminActivityInfoSignUpVo;
import com.yryz.quanhu.support.activity.vo.AdminActivityInfoVo;
import com.yryz.quanhu.support.activity.vo.AdminActivityRecordVo;
import com.yryz.quanhu.support.activity.vo.AdminOutActivityUsrePrizes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service(interfaceClass = AdminActivitySignUpApi.class)
public class AdminActivitySignUpProvider implements AdminActivitySignUpApi {

	private static final Logger logger = LoggerFactory.getLogger(AdminActivitySignUpProvider.class);

	@Autowired
	AdminActivitySignUpService adminActivitySignUpService;

	/**
	 * 后台发布报名类活动
	 * @param activityInfo
	 * @param activityEnrolConfig
	 * @param request
	 * @return
	 */
	@Override
	public Response<Integer> activityRelease(ActivityInfoAndEnrolConfig activityInfoAndEnrolConfig){
		Integer count = null;
		try {
			count = adminActivitySignUpService.activityRelease(activityInfoAndEnrolConfig);
		} catch (Exception e) {
			logger.error("后台发布报名类活动失败:"+ JsonUtils.toFastJson(activityInfoAndEnrolConfig));
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(count);
	}
	/**
	 * (后台)报名活动列表
	 * @param pageNo
	 * @param pageSize
	 * @param adminActivityInfoSignUpDto
	 * @return
	 */
	@Override
	public Response<PageList<AdminActivityInfoSignUpVo>> adminlist(Integer pageNo, Integer pageSize,
                                                            AdminActivityInfoSignUpDto adminActivityInfoSignUpDto){
		PageList<AdminActivityInfoSignUpVo> pageList = null;
		try {
			pageList = adminActivitySignUpService.adminlist(pageNo,pageSize,adminActivityInfoSignUpDto);
		} catch (Exception e) {
			logger.error("查询(后台)报名活动列表失败:"+ JsonUtils.toFastJson(adminActivityInfoSignUpDto));
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(pageList);
	}
	/**
	 * （后台）获取报名类活动详情
	 * @param id
	 * @return
	 */
	@Override
	public Response<ActivityInfoAndEnrolConfig> getActivitySignUpDetail(String id){
		ActivityInfoAndEnrolConfig activityInfoAndEnrolConfig = null;
		try {
			activityInfoAndEnrolConfig = adminActivitySignUpService.getActivitySignUpDetail(id);
		} catch (Exception e) {
			logger.error("（后台）获取报名类活动详情失败:"+ JsonUtils.toFastJson(id));
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(activityInfoAndEnrolConfig);
	}
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
	@Override
	public Response<ActivityInfo> getActivityDetail(String id){
		ActivityInfo activityInfo = null;
		try {
			activityInfo = adminActivitySignUpService.getActivityDetail(id);
		} catch (Exception e) {
			logger.error("根据活动ID获取活动主表详情失败:"+ JsonUtils.toFastJson(id));
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(activityInfo);
	}
	/**
	 * (后台)报名人员列表
	 * */
	@Override
	public Response<PageList<AdminActivityRecordVo>> attendlist(Integer pageNo, Integer pageSize,
                                                         AdminActivityRecordVo adminActivityRecordVo){
		PageList<AdminActivityRecordVo> pageList = null;
		try {
			pageList = adminActivitySignUpService.attendlist(pageNo,pageSize,adminActivityRecordVo);
		} catch (Exception e) {
			logger.error("(后台)报名人员列表失败:"+ JsonUtils.toFastJson(adminActivityRecordVo));
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(pageList);
	}
	/**
	 * (后台)所有上线的活动列表
	 * */
	@Override
	public Response<PageList<AdminActivityInfoVo>> adminAllSharelist(Integer pageNo, Integer pageSize, AdminActivityInfoDto adminActivityInfoDto){
		PageList<AdminActivityInfoVo> pageList = null;
		try {
			pageList = adminActivitySignUpService.adminAllSharelist(pageNo,pageSize,adminActivityInfoDto);
		} catch (Exception e) {
			logger.error("(后台)所有上线的活动列表失败:"+ JsonUtils.toFastJson(adminActivityInfoDto));
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(pageList);
	}
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
	@Override
	public Response<Integer> activityInfoEdit(ActivityInfo activityInfo){
		Integer count = null;
		try {
			count = adminActivitySignUpService.activityInfoEdit(activityInfo);
		} catch (Exception e) {
			logger.error("后台编辑活动失败:"+ JsonUtils.toFastJson(activityInfo));
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(count);
	}
	
	/**
	 * (后台)所有上线的活动列表+不分页
	 * @return
	 */
	@Override
	public Response<List<AdminActivityInfoVo>>	adminAllSharelistNoPage(){
		List<AdminActivityInfoVo> list = null;
		try {
			list = adminActivitySignUpService.adminAllSharelistNoPage();
		} catch (Exception e) {
			logger.error("(后台)所有上线的活动列表+不分页失败");
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(list);
	}
	
	

}
