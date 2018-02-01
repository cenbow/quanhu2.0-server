package com.yryz.quanhu.other.activity.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.other.activity.dao.ActivityUserPrizesDao;
import com.yryz.quanhu.other.activity.service.AdminActivityPrizesService;
import com.yryz.quanhu.other.activity.vo.ActivityInfoAppListVo;
import com.yryz.quanhu.other.activity.vo.AdminInActivityUserPrizes;
import com.yryz.quanhu.other.activity.vo.AdminOutActivityUsrePrizes;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
public class AdminActivityPrizesServiceImpl implements AdminActivityPrizesService {

	private Logger logger = LoggerFactory.getLogger(AdminActivityPrizesServiceImpl.class);

	@Autowired
	ActivityUserPrizesDao	ActivityUserPrizesDao;
	@Reference(check=false)
	UserApi userApi;
	
	/**
	 * 奖品列表
	 * @param dto
	 * @return
	 */
	@Override
	public PageList<AdminOutActivityUsrePrizes> listPrizes(AdminInActivityUserPrizes dto) {
		
		if(true==dto.getPage()){
			Page<AdminOutActivityUsrePrizes> page = PageHelper.startPage(dto.getPageNo(),dto.getPageNo());
		}
		List<AdminOutActivityUsrePrizes> list=ActivityUserPrizesDao.listPrizesByConditionAndPage(dto);
		if(CollectionUtils.isEmpty(list)){
			return new PageList(dto.getPageNo(), dto.getPageSize(), list, 0L);
		}
		for (AdminOutActivityUsrePrizes outActivityPrizes : list) {
			Set<String> userIds = new HashSet<String>();
			userIds.add(outActivityPrizes.getCreateUserId().toString());
			Response<Map<String,UserBaseInfoVO>> users = null;
			try {
				users = userApi.getUser(userIds);
			} catch (Exception e) {
				logger.error("查询用户信息异常",e);
			}
			if(users.success()&&users.getData().get(outActivityPrizes.getCreateUserId().toString())!=null){
				outActivityPrizes.setCustName(users.getData().get(outActivityPrizes.getCreateUserId().toString()).getUserNickName());
				outActivityPrizes.setCreateDate(users.getData().get(outActivityPrizes.getCreateUserId().toString()).getCreateDate());
				outActivityPrizes.setCustPhone(users.getData().get(outActivityPrizes.getCreateUserId().toString()).getUserPhone());
			}
		}
		Integer count = ActivityUserPrizesDao.listPrizesByConditionAndPageCount(dto);
		return new PageList(dto.getPageNo(), dto.getPageSize(), list, (long)count);
	}
	
	/**
	 * 批量修改奖品已使用
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateBatchUsed(Long[] ids) throws Exception {
//		Logger.println("批量修改奖品已使用入参:ids="+ids);
		if(0==ids.length){
			return	0;
		}
		return ActivityUserPrizesDao.updateBatchUsed(ids, (byte) 2);//2已使用
	}
}
