package com.yryz.quanhu.support.activity.service.impl;

import com.github.pagehelper.PageHelper;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.support.activity.dao.ActivityEnrolConfigDao;
import com.yryz.quanhu.support.activity.dao.ActivityInfoDao;
import com.yryz.quanhu.support.activity.dao.ActivityRecordDao;
import com.yryz.quanhu.support.activity.entity.ActivityEnrolConfig;
import com.yryz.quanhu.support.activity.entity.ActivityInfo;
import com.yryz.quanhu.support.activity.entity.ActivityInfoAndEnrolConfig;
import com.yryz.quanhu.support.activity.service.AdminActivityEnrolConfigService;
import com.yryz.quanhu.support.activity.service.AdminActivityRecordService;
import com.yryz.quanhu.support.activity.service.AdminActivitySignUpService;
import com.yryz.quanhu.support.activity.util.DateUtils;
import com.yryz.quanhu.support.activity.util.JsonUtils;
import com.yryz.quanhu.support.activity.vo.*;
import com.yryz.quanhu.support.activity.constants.Constant;
import com.yryz.quanhu.support.activity.dto.AdminActivityInfoDto;
import com.yryz.quanhu.support.activity.dto.AdminActivityInfoSignUpDto;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

//import com.github.pagehelper.PageHelper;


@Service
public class AdminActivitySignUpServiceImpl implements AdminActivitySignUpService {
	@Autowired
	ActivityInfoDao activityInfoDao;
	@Autowired
	AdminActivityEnrolConfigService activityEnrolConfigService;
	@Autowired
	AdminActivityRecordService activityRecordService;
	@Autowired
	ActivityRecordDao activityRecordDao;
	@Autowired
	ActivityEnrolConfigDao activityEnrolConfigDao;
	/*@Autowired
	EventReportDao eventReportDao;*/
	/*@Autowired
	CustAPI custAPI;*/
	private Logger logger = LoggerFactory.getLogger(AdminActivitySignUpServiceImpl.class);
	
	/**
	 * 设置活动状态
	 */
	public void getActivityStatus(ActivityInfo activity, AdminActivitySignUpHomeAppVo vo) {
		Date date = new Date();
		if (DateUtils.getDistanceOfTwoDate(date, activity.getBeginTime()) > 0) {
			// 未开始
			vo.setActivityStatus(1);
			// 进行中
		} else if (DateUtils.getDistanceOfTwoDate(activity.getBeginTime(), date) >= 0
				&& DateUtils.getDistanceOfTwoDate(date, activity.getEndTime()) >= 0) {
			vo.setActivityStatus(2);
		} else if (DateUtils.getDistanceOfTwoDate(date, activity.getEndTime()) < 0) {
			vo.setActivityStatus(3);
		}
	}
	/**
	 *  后台发布报名类活动
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int activityRelease(ActivityInfoAndEnrolConfig activityInfoAndEnrolConfig) {
		int result=0;
		ActivityInfo activityInfo =new ActivityInfo();
		ActivityEnrolConfig activityEnrolConfig = new ActivityEnrolConfig();
		try {
			BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
			BeanUtils.copyProperties(activityInfo, activityInfoAndEnrolConfig);
			BeanUtils.copyProperties(activityEnrolConfig, activityInfoAndEnrolConfig);
			activityInfo.setActivityChannelCode("");
			activityInfo.setModuleEnum(Constant.ACTIVITY_ENUM);
			activityInfoDao.addActivity(activityInfo);
			if(null==activityInfo.getId() || activityInfo.getId().intValue()==0){
				logger.info("insert activity return null");
				Integer id = activityInfoDao.selectMaxId();
				logger.info("insert activity getMaxId:"+id);
				activityEnrolConfig.setActivityInfoId(Long.valueOf(id));
				activityInfo.setSort(id);
				activityInfo.setId((long)id);
			}else{
				activityInfo.setSort(activityInfo.getId().intValue());
				activityEnrolConfig.setActivityInfoId(activityInfo.getId());
			}
			//activityInfo.setActivityLink("/activity/platform-activity/signup/"+activityEnrolConfig.getActivityInfoId());
			activityInfo.setActivityChannelCode("HD-"+activityInfo.getId());
			activityInfoDao.update(activityInfo);
			result= activityEnrolConfigDao.insert(activityEnrolConfig);
		
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return result;
		
		
	}
	/**
	 * (后台)报名活动列表
	 */
	@Override
	public PageList<AdminActivityInfoSignUpVo> adminlist(Integer pageNo, Integer pageSize,
														 AdminActivityInfoSignUpDto adminActivityInfoSignUpDto) {
		PageHelper.startPage(pageNo, pageSize);
		List<AdminActivityInfoSignUpVo> list = activityInfoDao.selectSignAdminlist((pageNo-1)*pageSize, pageSize, adminActivityInfoSignUpDto);
		if(CollectionUtils.isEmpty(list)){
			return new PageList<AdminActivityInfoSignUpVo>(pageNo,pageSize,list,0L);
		}
		Date date = new Date();
		for(AdminActivityInfoSignUpVo adminActivityInfoSignUpVo :list){
			//TODO 设置分享数
			Integer count = null;
			//Integer count = eventReportDao.getShareCount(adminActivityInfoSignUpVo.getId().toString(),"1006");
			if(count!=null && count>0){
				adminActivityInfoSignUpVo.setShareCount(count);
			}else{
			adminActivityInfoSignUpVo.setShareCount(0);
			}
			//设置货币、积分总收益
			ActivityEnrolConfig activityEnrolConfig = null;
			try {
				activityEnrolConfig = activityEnrolConfigService.getActivityEnrolConfigByActId(adminActivityInfoSignUpVo.getId());
				if(activityEnrolConfig == null){
					//throw new CommonException("获取报名活动配置表失败");
				}
				else if(activityEnrolConfig.getSignUpType()==1){
					//adminActivityInfoSignUpVo.setCurrencyTotalIncome(activityPayRecordDao.getTotalIncome(activityEnrolConfig.getSignUpType(),adminActivityInfoSignUpVo.getId()));
				}
				else if(activityEnrolConfig.getSignUpType()==2){
					//adminActivityInfoSignUpVo.setIntegralTotalIncome(activityPayRecordDao.getTotalIncome(activityEnrolConfig.getSignUpType(),adminActivityInfoSignUpVo.getId()));
				}
				//设置活动状态
				if (DateUtils.getDistanceOfTwoDate(date, adminActivityInfoSignUpVo.getBeginTime()) > 0) {
					// 未开始
					adminActivityInfoSignUpVo.setActivityStatus(1);
					// 进行中
				} else if (DateUtils.getDistanceOfTwoDate(adminActivityInfoSignUpVo.getBeginTime(), date) >= 0
						&& DateUtils.getDistanceOfTwoDate(date, adminActivityInfoSignUpVo.getEndTime()) >= 0) {
					adminActivityInfoSignUpVo.setActivityStatus(2);
				} else if (DateUtils.getDistanceOfTwoDate(date, adminActivityInfoSignUpVo.getEndTime()) < 0) {
					adminActivityInfoSignUpVo.setActivityStatus(3);
				}
			} catch (Exception e) {
				logger.info("获取报名列表失败");
			}
			
		}
		return new PageList<AdminActivityInfoSignUpVo>(pageNo,pageSize,list,activityInfoDao.selectSignAdminlistCount(adminActivityInfoSignUpDto));
	}
	
	/**
	 * （后台）获取报名类活动详情
	 * @param id
	 * @return
	 */
	@Override
	public ActivityInfoAndEnrolConfig getActivitySignUpDetail(String id) {
		ActivityInfoAndEnrolConfig activityInfoAndEnrolConfig =new ActivityInfoAndEnrolConfig();
		try {
			AdminActivityInfoVo1 activityInfo =activityInfoDao.selectByPrimaryKey(Long.valueOf(id));
			ActivityEnrolConfig activityEnrolConfig = activityEnrolConfigDao.selectByActivityId(activityInfo.getId());
			BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
			BeanUtils.copyProperties(activityInfoAndEnrolConfig, activityInfo);
			BeanUtils.copyProperties(activityInfoAndEnrolConfig, activityEnrolConfig);
			activityInfoAndEnrolConfig.setId(activityInfo.getId());
			@SuppressWarnings("unchecked")
			Map<String,String> map = JsonUtils.fromJson(activityEnrolConfig.getConfigSources(), Map.class);
			activityInfoAndEnrolConfig.setSourceMap(map);
			
		} catch (Exception e) {
			logger.info("获取活动详情失败");
		}
		return activityInfoAndEnrolConfig;
	}
	/**
	 *  后台编辑报名类活动
	 * @param activityInfoAndEnrolConfig
	 * @param request
	 * @return
	 */
	/*@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void activitySignEdit(ActivityInfoAndEnrolConfig activityInfoAndEnrolConfig) {
		ActivityInfo activityInfo =new ActivityInfo();
		ActivityEnrolConfig activityEnrolConfig = new ActivityEnrolConfig();
		
		try {
			BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
			BeanUtils.copyProperties(activityInfo, activityInfoAndEnrolConfig);
			BeanUtils.copyProperties(activityEnrolConfig, activityInfoAndEnrolConfig);
		} catch (Exception e) {
			
		}
		activityEnrolConfig.setActivityInfoId(activityInfo.getId());
		activityInfoDao.update(activityInfo);
		activityEnrolConfigDao.updateByActivityId(activityEnrolConfig);
		
	}*/
	/**
	 *  后台修改备注、下线操作、推荐
	 * @param activityInfo
	 * @param request
	 * @return
	 */
	/*@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void operationEdit(ActivityInfo activityInfo) {
		activityInfoDao.update(activityInfo);
	}*/
	/**
	 * 根据活动ID获取活动主表详情
	 * @param id
	 * @return
	 */
	@Override
	public ActivityInfo getActivityDetail(String id) {
		return activityInfoDao.selectByPrimaryKey(Long.valueOf(id));
	}
	/**
	 * (后台)报名人员列表
	 * */
	@Override
	public PageList<AdminActivityRecordVo> attendlist(Integer pageNo, Integer pageSize,
													  AdminActivityRecordVo adminActivityRecordVo) {
		//PageHelper.startPage(pageNo, pageSize);
		/*if(pageNo==null||pageNo<=0){
			pageNo=0;
		}else{
			pageNo=(pageNo-1)*pageSize;
		}
		if(pageSize==null||pageSize<=0){
			pageSize=10;
		}*/
		pageNo=0;
		pageSize=99999;
		List<AdminActivityRecordVo> list = activityRecordDao.attendlist(pageNo, pageSize, adminActivityRecordVo);
		if(CollectionUtils.isEmpty(list)){
			return new PageList<AdminActivityRecordVo>(pageNo,pageSize,list,0L);
		}
		for(AdminActivityRecordVo vo:list){
			//TODO 根据用户id获取用户昵称、账号绑定的手机号
			/*CustInfo custInfo = custAPI.getCustInfo(vo.getCreateUserId());
			if(custInfo==null){
				vo.setNickName("");
				vo.setCustPhone("");
			}else{
				vo.setNickName(custInfo.getCustNname());
				vo.setCustPhone(custInfo.getCustPhone());
			}*/
			@SuppressWarnings("unchecked")
			List<Map<String,String>> voMap = JsonUtils.fromJson(vo.getEnrolSources(), List.class);
			//Map<Object,Object> map=JSON.parseObject(JSON.toJSONString(vo.getEnrolSources()), Map.class);
			vo.setMap(voMap);
		}
		return new PageList<AdminActivityRecordVo>(pageNo,pageSize,list,activityRecordDao.attendlistCount(adminActivityRecordVo));
	}
	/**
	 * (后台)所有上线的活动列表
	 * */
	@Override
	public PageList<AdminActivityInfoVo> adminAllSharelist(Integer pageNo, Integer pageSize, AdminActivityInfoDto adminActivityInfoDto) {
		PageHelper.startPage(pageNo, pageSize);
		List<AdminActivityInfoVo> list = activityInfoDao.adminAllSharelist(pageNo, pageSize, adminActivityInfoDto);
		if(CollectionUtils.isEmpty(list)){
			return new PageList<AdminActivityInfoVo>(pageNo,pageSize,list,0L);
		}
		Date date = new Date();
		for(com.yryz.quanhu.support.activity.vo.AdminActivityInfoVo vo:list){
			if(vo.getBeginTime()!=null && vo.getEndTime()!=null){
				if (DateUtils.getDistanceOfTwoDate(date, vo.getBeginTime()) > 0) {
					// 未开始
					vo.setActivityStatus(11);
					// 进行中
				} else if (DateUtils.getDistanceOfTwoDate(vo.getBeginTime(), date) >= 0
						&& DateUtils.getDistanceOfTwoDate(date, vo.getEndTime()) >= 0) {
					vo.setActivityStatus(12);
				} else if (DateUtils.getDistanceOfTwoDate(date, vo.getEndTime()) < 0) {
					vo.setActivityStatus(13);
				}
			}

		}
		return new PageList<AdminActivityInfoVo>(pageNo,pageSize,list,activityInfoDao.adminAllSharelistCount(adminActivityInfoDto));
	}
	
	/**
	 * 修改活动顺序（后台管理）
	 */
	/*@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateActivitySortBatch(Long[] ids, String custId) {
		for(int i=0;i<ids.length;i++){
			ActivityInfo activityInfo=new ActivityInfo();
			activityInfo.setId(ids[i]);
			activityInfo.setLastUpdateUserId(custId);
			activityInfo.setSort(ids.length-i);
			activityInfoDao.update(activityInfo);
		}
	}*/
	/**
	 *  后台编辑活动
	 * @param activityInfoAndEnrolConfig
	 * @param request
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int activityInfoEdit(ActivityInfo activityInfo) {
		if(activityInfo.getShelveFlag()!=null && activityInfo.getShelveFlag()==11 ){
			activityInfo.setRecommend(10);
		}
		return activityInfoDao.update(activityInfo);
	}
	
	/**
	 * (后台)所有上线的活动列表+不分页
	 * @return
	 */
	@Override
	public List<com.yryz.quanhu.support.activity.vo.AdminActivityInfoVo> adminAllSharelistNoPage() {
		return activityInfoDao.adminAllSharelistNoPage();
	}
	
	
}
