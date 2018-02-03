package com.yryz.quanhu.other.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.other.activity.dao.*;
import com.yryz.quanhu.other.activity.dto.AdminActivityInfoVoteDto;
import com.yryz.quanhu.other.activity.dto.AdminActivityVoteDetailDto;
import com.yryz.quanhu.other.activity.entity.ActivityInfo;
import com.yryz.quanhu.other.activity.entity.ActivityPrizes;
import com.yryz.quanhu.other.activity.entity.ActivityVoteConfig;
import com.yryz.quanhu.other.activity.service.AdminActivityVoteService;
import com.yryz.quanhu.other.activity.vo.AdminActivityInfoVo1;
import com.yryz.quanhu.other.activity.vo.AdminActivityVoteDetailVo;
import com.yryz.quanhu.other.activity.vo.AdminActivityVoteVo;
import com.yryz.quanhu.other.activity.util.DateUtils;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

import static com.yryz.common.constant.ModuleContants.ACTIVITY_ENUM;


@Service
public class AdminActivityVoteServiceImpl implements AdminActivityVoteService {

	private Logger logger = LoggerFactory.getLogger(AdminActivityVoteServiceImpl.class);

	@Autowired
	ActivityInfoDao activityInfoDao;

	@Autowired
	ActivityVoteDao activityVoteDao;

	@Autowired
	ActivityVoteConfigDao activityVoteConfigDao;

	@Autowired
	ActivityPrizesDao activityPrizesDao;

	@Autowired
	ActivityVoteDetailDao activityParticipationDao;

	@Reference
	IdAPI idAPI;

	@Reference(check=false)
	UserApi userApi;

	@Reference(check=false)
	CountApi countApi;

	/**
	 * 活动列表
	 */
	@Override
	public PageList adminlist(AdminActivityInfoVoteDto param) {
		Page page = PageHelper.startPage(param.getPageNo(), param.getPageSize());
		List<AdminActivityVoteVo> list = activityVoteDao.adminlist(param);
		if (CollectionUtils.isEmpty(list)) {
			return new PageList(param.getPageNo(), param.getPageSize(), list, 0L);
		}
		Date date = new Date();
		for (AdminActivityVoteVo activity : list) {
			try {
				//设置分享数
				Long count = 0L;
				Response<Map<String, Long>> mapResponse = null;
				try {
					mapResponse = countApi.getCount(BehaviorEnum.Share.getCode(),activity.getKid(),null);
				} catch (Exception e) {
					logger.error("查询分享数异常:",e);
				}
				if (null!=mapResponse && mapResponse.success()){
					count = mapResponse.getData().get(BehaviorEnum.Share.getKey());
				}
				activity.setShareCount(count);
				//设置总投票数
				Integer voteTotalCount = activityVoteDao.getVoteTotalCount(activity.getKid());
				if(voteTotalCount!=null && voteTotalCount>0){
					activity.setVoteTotalCount(voteTotalCount);
				}else{
					activity.setVoteTotalCount(0);
				}
				// 设置活动状态
				if (DateUtils.getDistanceOfTwoDate(date, activity.getBeginTime()) > 0) {
					// 未开始
					activity.setActivityStatus(11);
					// 进行中
				} else if (DateUtils.getDistanceOfTwoDate(activity.getBeginTime(), date) >= 0
						&& DateUtils.getDistanceOfTwoDate(date, activity.getEndTime()) >= 0) {
					activity.setActivityStatus(12);
				} else if (DateUtils.getDistanceOfTwoDate(date, activity.getEndTime()) < 0) {
					activity.setActivityStatus(13);
				}
				
			} catch (Exception e) {
				logger.info("获取报名列表失败");
			}
		}
		return new PageList(param.getPageNo(), param.getPageSize(), list, page.getTotal());
	}

	@Override
	@Transactional
	public Integer activityRelease(ActivityInfo activity, ActivityVoteConfig config, List<ActivityPrizes> prizes) {
		Assert.notNull(activity, "activity 不能为空");
		Assert.notNull(config, "config 不能为空");
		Assert.notNull(prizes, "prizes 不能为空");
		activity.setKid(idAPI.getSnowflakeId().getData());
		activity.setActivityChannelCode("HD-"+activity.getKid());
		activity.setModuleEnum(ACTIVITY_ENUM);
		activityInfoDao.insertByPrimaryKeySelective(activity);
		config.setKid(idAPI.getSnowflakeId().getData());
		config.setActivityInfoId(activity.getKid());
		activityVoteConfigDao.insert(config);

		for (ActivityPrizes activityPrizes : prizes) {
			activityPrizes.setKid(idAPI.getSnowflakeId().getData());
			activityPrizes.setActivityInfoId(activity.getKid());
			activityPrizes.setIssueNumConfig(activityPrizes.getIssueNum());
			activityPrizesDao.insertByPrimaryKeySelective(activityPrizes);
		}
		return 1;
	}

	@Override
	public AdminActivityInfoVo1 getActivityDetail(Long kid) {
		return activityInfoDao.selectByPrimaryKey(kid);
	}

	@Override
	public ActivityVoteConfig getConfigDetailByActivityId(String activityId) {
		return activityVoteConfigDao.selectVoteByActivityInfoId(Long.valueOf(activityId));
	}

	@Override
	public List<ActivityPrizes> getPrizesListByActivityId(String activityId) {
		return activityPrizesDao.selectAvailablePrizes(Long.valueOf(activityId));
	}

	@Override
	public PageList<AdminActivityVoteDetailVo> selectRankList(AdminActivityVoteDetailDto adminActivityVoteDetailDto) {
		Page page = PageHelper.startPage(adminActivityVoteDetailDto.getPageNo(), adminActivityVoteDetailDto.getPageSize());
		List<AdminActivityVoteDetailVo> list = activityParticipationDao.selectRankList(adminActivityVoteDetailDto.getActivityInfoId());
		if (CollectionUtils.isEmpty(list)) {
			return new PageList(adminActivityVoteDetailDto.getPageNo(), adminActivityVoteDetailDto.getPageSize(), list, 0L);
		}
		for (AdminActivityVoteDetailVo detailVo : list) {
			if(detailVo.getAddVote()!=null){
			detailVo.setTotalCount(detailVo.getVoteCount()+ detailVo.getAddVote().intValue());
			}else{
				detailVo.setTotalCount(detailVo.getVoteCount());
			}
			Set<String> userIds = new HashSet<String>();
			userIds.add(detailVo.getCreateUserId().toString());
			Response<Map<String,UserBaseInfoVO>> users = null;
			try {
				users = userApi.getUser(userIds);
			} catch (Exception e) {
				logger.error("查询用户信息异常",e);
			}
			if(null!=users && users.success()&&users.getData().get(detailVo.getCreateUserId().toString())!=null){
				detailVo.setNickName(users.getData().get(detailVo.getCreateUserId().toString()).getUserNickName());
			}
		}
		return new PageList(adminActivityVoteDetailDto.getPageNo(), adminActivityVoteDetailDto.getPageSize(), list,
				page.getTotal());
	}

	// 恭喜！您在YYYY中获得了第X名，奖励将由工作人员联系您后进行发放，先去看看获得的奖励吧！
	public Integer sendMessageVote(ActivityInfo activityInfo) {
		try {
			/*CustInfo custInfo = new CustInfo();
			ActivityInfo actInfo = activityInfoDao.selectByPrimaryKey(activityInfo.getId());
			// 排行榜
			List<AdminActivityVoteDetailVo> list = activityParticipationDao.selectRankList(activityInfo.getId());
			
			for (int i = 0; i < list.size(); i++) {
				InteractiveBody interactiveBody = new InteractiveBody();
                // 获取平台用户
				custInfo = custAPI.getCustInfo(list.get(i).getCreateUserId());
				if (custInfo != null) {
					interactiveBody.setCustId(custInfo.getCustId());
					interactiveBody.setCustName(custInfo.getCustNname());
				}
				interactiveBody.setBodyTitle(actInfo.getTitle());
				interactiveBody.setBodyImg(list.get(i).getCoverPlan());
				
				messageUtils.sendMessages(MessageConstant.ACTIVITY_VOTE_REWARD, custInfo.getCustId(), actInfo.getTitle(),(i+1+""),
						interactiveBody);
			}*/

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("活动投票奖励，发送消息错误:{}", e);
		}
		return 1;
	}

	@Override
	public Integer updateSave(ActivityInfo activity) {
		Assert.notNull(activity, "activity 不能为空");
		return activityVoteDao.update(activity);
	}

}
