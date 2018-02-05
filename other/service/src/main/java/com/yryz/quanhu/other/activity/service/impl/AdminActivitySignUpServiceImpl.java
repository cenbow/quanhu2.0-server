package com.yryz.quanhu.other.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.other.activity.dao.ActivityEnrolConfigDao;
import com.yryz.quanhu.other.activity.dao.ActivityInfoDao;
import com.yryz.quanhu.other.activity.dao.ActivityRecordDao;
import com.yryz.quanhu.other.activity.dto.AdminActivityInfoDto;
import com.yryz.quanhu.other.activity.dto.AdminActivityInfoSignUpDto;
import com.yryz.quanhu.other.activity.entity.ActivityEnrolConfig;
import com.yryz.quanhu.other.activity.entity.ActivityInfo;
import com.yryz.quanhu.other.activity.entity.ActivityInfoAndEnrolConfig;
import com.yryz.quanhu.other.activity.service.ActivityVoteRedisService;
import com.yryz.quanhu.other.activity.service.AdminActivityEnrolConfigService;
import com.yryz.quanhu.other.activity.service.AdminActivitySignUpService;
import com.yryz.quanhu.other.activity.vo.*;
import com.yryz.quanhu.other.activity.util.DateUtils;
import com.yryz.quanhu.other.activity.util.JsonUtils;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
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

import java.util.*;

import static com.yryz.common.constant.ModuleContants.ACTIVITY_ENUM;


@Service
public class AdminActivitySignUpServiceImpl implements AdminActivitySignUpService {
	@Autowired
	ActivityInfoDao activityInfoDao;
	@Autowired
	AdminActivityEnrolConfigService activityEnrolConfigService;
	@Autowired
	ActivityRecordDao activityRecordDao;
	@Autowired
	ActivityEnrolConfigDao activityEnrolConfigDao;
	@Reference(check=false)
	private IdAPI idApi;
	@Reference(check=false)
	UserApi userApi;
	@Reference(check=false)
	CountApi countApi;

	@Autowired
	ActivityVoteRedisService activityVoteRedisService;

	private Logger logger = LoggerFactory.getLogger(AdminActivitySignUpServiceImpl.class);
	
	/**
	 * 设置活动状态
	 */
	public void getActivityStatus(ActivityInfo activity, AdminActivitySignUpHomeAppVo vo) {
		Date date = new Date();
		if (DateUtils.getDistanceOfTwoDate(date, activity.getBeginTime()) > 0) {
			// 未开始
			vo.setActivityStatus(11);
			// 进行中
		} else if (DateUtils.getDistanceOfTwoDate(activity.getBeginTime(), date) >= 0
				&& DateUtils.getDistanceOfTwoDate(date, activity.getEndTime()) >= 0) {
			vo.setActivityStatus(12);
		} else if (DateUtils.getDistanceOfTwoDate(date, activity.getEndTime()) < 0) {
			vo.setActivityStatus(13);
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
			activityInfo.setKid(idApi.getSnowflakeId().getData());
			activityInfo.setActivityChannelCode("HD-"+activityInfo.getKid());
			activityInfo.setModuleEnum(ACTIVITY_ENUM);
			activityInfoDao.insertByPrimaryKeySelective(activityInfo);
			activityEnrolConfig.setActivityInfoId(activityInfo.getKid());
			activityEnrolConfig.setKid(idApi.getSnowflakeId().getData());
			result= activityEnrolConfigDao.insertByPrimaryKeySelective(activityEnrolConfig);
		
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
		Page page = PageHelper.startPage(pageNo, pageSize);
		List<AdminActivityInfoSignUpVo> list = activityInfoDao.selectSignAdminlist((pageNo-1)*pageSize, pageSize, adminActivityInfoSignUpDto);
		if(CollectionUtils.isEmpty(list)){
			return new PageList<AdminActivityInfoSignUpVo>(pageNo,pageSize,list,0L);
		}
		Date date = new Date();
		for(AdminActivityInfoSignUpVo adminActivityInfoSignUpVo :list){
			//设置分享数
			Integer count = 0;
			Response<Map<String, Long>> mapResponse = null;
			try {
				mapResponse = countApi.getCount(BehaviorEnum.Share.getCode(),adminActivityInfoSignUpVo.getKid(),null);
			} catch (Exception e) {
				logger.error("查询分享数异常:",e);
			}
			if (null!=mapResponse && mapResponse.success()){
				count = mapResponse.getData().get(BehaviorEnum.Share.getKey()).intValue();
			}
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
				else if(activityEnrolConfig.getSignUpType()==11){
					adminActivityInfoSignUpVo.setCurrencyTotalIncome(adminActivityInfoSignUpVo.getJoinCount()*activityEnrolConfig.getAmount());
				}
				else if(activityEnrolConfig.getSignUpType()==12){
					adminActivityInfoSignUpVo.setIntegralTotalIncome(adminActivityInfoSignUpVo.getJoinCount()*activityEnrolConfig.getAmount());
				}
				//设置活动状态
				if (DateUtils.getDistanceOfTwoDate(date, adminActivityInfoSignUpVo.getBeginTime()) > 0) {
					// 未开始
					adminActivityInfoSignUpVo.setActivityStatus(11);
					// 进行中
				} else if (DateUtils.getDistanceOfTwoDate(adminActivityInfoSignUpVo.getBeginTime(), date) >= 0
						&& DateUtils.getDistanceOfTwoDate(date, adminActivityInfoSignUpVo.getEndTime()) >= 0) {
					adminActivityInfoSignUpVo.setActivityStatus(12);
				} else if (DateUtils.getDistanceOfTwoDate(date, adminActivityInfoSignUpVo.getEndTime()) < 0) {
					adminActivityInfoSignUpVo.setActivityStatus(13);
				}
			} catch (Exception e) {
				logger.info("获取报名列表失败");
			}
			
		}
		return new PageList<AdminActivityInfoSignUpVo>(pageNo,pageSize,list,page.getTotal());
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
			ActivityEnrolConfig activityEnrolConfig = activityEnrolConfigDao.selectByActivityId(activityInfo.getKid());
			BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
			BeanUtils.copyProperties(activityInfoAndEnrolConfig, activityInfo);
			BeanUtils.copyProperties(activityInfoAndEnrolConfig, activityEnrolConfig);
			activityInfoAndEnrolConfig.setKid(activityInfo.getKid());
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
		Page page = PageHelper.startPage(pageNo, pageSize);
		List<AdminActivityRecordVo> list = activityRecordDao.attendlist(pageNo, pageSize, adminActivityRecordVo);
		if(CollectionUtils.isEmpty(list)){
			return new PageList<AdminActivityRecordVo>(pageNo,pageSize,list,0L);
		}
		Set<String> userIds = new HashSet<>();
		for(AdminActivityRecordVo vo:list){
			List<Map<String,String>> voMap = JsonUtils.fromJson(vo.getEnrolSources(), List.class);
			//Map<Object,Object> map=JSON.parseObject(JSON.toJSONString(vo.getEnrolSources()), Map.class);
			vo.setMap(voMap);
			userIds.add(String.valueOf(vo.getCreateUserId()));
		}
		Response<Map<String,UserBaseInfoVO>> users = userApi.getUser(userIds);
		for(AdminActivityRecordVo vo:list){
			vo.setNickName(users.getData().get(vo.getCreateUserId().toString()).getUserNickName());
			vo.setCustPhone(users.getData().get(vo.getCreateUserId().toString()).getUserPhone());
		}
		return new PageList<AdminActivityRecordVo>(pageNo,pageSize,list,page.getTotal());
	}
	/**
	 * (后台)所有上线的活动列表
	 * */
	@Override
	public PageList<AdminActivityInfoVo> adminAllSharelist(Integer pageNo, Integer pageSize, AdminActivityInfoDto adminActivityInfoDto) {
		Page page = PageHelper.startPage(pageNo, pageSize);
		List<AdminActivityInfoVo> list = activityInfoDao.adminAllSharelist(pageNo, pageSize, adminActivityInfoDto);
		if(CollectionUtils.isEmpty(list)){
			return new PageList<AdminActivityInfoVo>(pageNo,pageSize,list,0L);
		}
		Date date = new Date();
		for(AdminActivityInfoVo vo:list){
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
		return new PageList<AdminActivityInfoVo>(pageNo,pageSize,list,page.getTotal());
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
		Integer count = activityInfoDao.update(activityInfo);
		if(count==1){
			activityVoteRedisService.delVoteInfo(activityInfo.getKid());
		}
		return count;
	}
	
	/**
	 * (后台)所有上线的活动列表+不分页
	 * @return
	 */
	@Override
	public List<AdminActivityInfoVo> adminAllSharelistNoPage() {
		return activityInfoDao.adminAllSharelistNoPage();
	}
	
	
}
