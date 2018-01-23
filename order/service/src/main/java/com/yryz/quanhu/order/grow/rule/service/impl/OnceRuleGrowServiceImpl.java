package com.yryz.quanhu.order.grow.rule.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.quanhu.grow.entity.GrowEventInfo;
import com.yryz.quanhu.order.grow.entity.GrowStatusOnce;
import com.yryz.quanhu.order.grow.manage.service.GrowLevelManageService;
import com.yryz.quanhu.order.grow.rule.service.OnceRuleGrowService;
import com.yryz.quanhu.order.grow.service.GrowFlowService;
import com.yryz.quanhu.order.grow.service.GrowStatusOnceService;
import com.yryz.quanhu.order.grow.type.GrowTypeEnum;
import com.yryz.quanhu.order.grow.utils.GrowKeyUtil;
import com.yryz.quanhu.order.score.service.EventAcountService;

import redis.clients.jedis.ShardedJedis;

@Transactional
@Service
public class OnceRuleGrowServiceImpl extends BaseRuleGrowServiceImpl implements OnceRuleGrowService{

	@Autowired
	GrowStatusOnceService growStatusOnceService;
	
	@Autowired
	EventAcountService eventAcountService;
	
	@Autowired
	GrowFlowService growFlowService;
	
	@Autowired
	GrowLevelManageService growLevelManageService;
	
    @Autowired
    private StringRedisTemplate redisTemplate;
	
	@Override
	public boolean processStatus(String userId, String eventCode , String resourceId , GrowEventInfo sei ,  double amount) {
		//一次性事件因为有持久层状态记录，因此也会对Redis中的状态缓存设置有效期
		String statusKey = GrowKeyUtil.getGrowStatusKey(userId, eventCode , resourceId , GrowTypeEnum.Once);
		//尽管状态机放行，这里仍先查询一次性积分事件状态表有没有记录过状态
		GrowStatusOnce sso = growStatusOnceService.getByCode(userId, eventCode , resourceId);
		if(sso != null && sso.getId() != null && sso.getGrowFlag() == 1){
			//判断数据库已记录过状态，则不再需要记录流水，仅更新状态机
			return updateStatus(statusKey,  false, true);
		}
		//数据库无状态，则记录数据库积分流水，同时更新持久状态与redis状态
		//1. 记录积分流水并更新总值
		saveGrowFlow(userId, eventCode, sei , amount);
		//2. 一次性积分状态记录
		Date now = new Date();
		sso = new GrowStatusOnce(userId , eventCode);
		sso.setGrowFlag(1);
		sso.setCreateTime(now);
		sso.setResourceId(resourceId);
		sso.setUpdateTime(now);
		growStatusOnceService.save(sso);
		//3. 更新一次性积分事件的redis状态记录
		return updateStatus(statusKey,  true, true);
	}

}
