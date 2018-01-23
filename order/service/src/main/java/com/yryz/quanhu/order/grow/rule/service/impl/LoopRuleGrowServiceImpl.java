package com.yryz.quanhu.order.grow.rule.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.quanhu.grow.entity.GrowEventInfo;
import com.yryz.quanhu.order.grow.entity.GrowStatus;
import com.yryz.quanhu.order.grow.manage.service.GrowLevelManageService;
import com.yryz.quanhu.order.grow.rule.service.LoopRuleGrowService;
import com.yryz.quanhu.order.grow.service.GrowFlowService;
import com.yryz.quanhu.order.grow.service.GrowStatusService;
import com.yryz.quanhu.order.grow.type.GrowTypeEnum;
import com.yryz.quanhu.order.grow.utils.GrowKeyUtil;
import com.yryz.quanhu.order.score.service.EventAcountService;

import redis.clients.jedis.ShardedJedis;

/**
 * 前置状态机拦截基于redis中的状态数据，若Redis数据丢失导致状态拦截漏掉时，都通过该持久化状态服务进行处理 状态更新持久化处理服务
 * 
 * @author lsn
 *
 */
@Transactional
@Service
public class LoopRuleGrowServiceImpl extends BaseRuleGrowServiceImpl implements LoopRuleGrowService {

	private static final Logger logger = LoggerFactory.getLogger(LoopRuleGrowServiceImpl.class);

	@Autowired
	GrowStatusService growStatusService;

	@Autowired
	EventAcountService eventAcountService;

	@Autowired
	GrowFlowService growFlowService;
	
	@Autowired
	GrowLevelManageService growLevelManageService;
	
    @Autowired
    private StringRedisTemplate redisTemplate;

	@Override
	public boolean processStatus(String userId, String eventCode, String resourceId, GrowEventInfo sei,
			 double amount) {
		String recordKey = GrowKeyUtil.getGrowRecordKey(userId, eventCode);
		String statusKey = GrowKeyUtil.getGrowStatusKey(userId, eventCode, "", GrowTypeEnum.Loop);
		Exception exception = null;
		Date now = new Date();
		if (redisTemplate.hasKey(recordKey)) {
			// key存在，则查询状态
			// 1.更新状态记录数
			Long newCount = redisTemplate.opsForValue().increment(recordKey,1);
			Integer eventCount = Integer.valueOf(String.valueOf(newCount));
			// 2.同步状态记录数到数据库
			GrowStatus growStatus = new GrowStatus(userId, eventCode, eventCount);
			growStatus.setUpdateTime(now);
			int updateCount = growStatusService.update(growStatus);
			if (updateCount == 0) {
				// redis的状态记录不在DB事务中，数据库记录有可能不存在，则从redis中同步
				growStatus.setCreateTime(now);
				growStatusService.save(growStatus);
			}
			// 3.根据更新后的状态记录数，对比积分规则 配置，若不满足积分规则，更新状态为false
			boolean needScore = false;
			boolean status = false;
			try {
				// 更新记录后刚好达到限制数，则需要触发积分操作，小于时，未达到积分条件，
				// 大于时，数据库已记录过积分流水了，只需要同步redis中的状态机
				if (eventCount <= sei.getEventLimit()) {
					needScore = true;
				}
				if (eventCount >= sei.getEventLimit()) {
					status = true;
				}
				if (needScore) {
					saveGrowFlow(userId, eventCode, sei , amount);
				}
			} catch (Exception e) {
				//jedis.decr(recordKey);
				logger.error("-----redis有记录时，持久化处理循环事件成长出错！传入数据userId=" + userId + ",eventCode=" + eventCode + ",amount=" + amount + "出错信息：" + e.getLocalizedMessage());
				exception = e;
//				throw e;
			}
			// 同步积分状态机数据
			if(exception == null) {
				return updateStatus(statusKey, needScore, status);
			}
			return false;
		}

		// 以下处理redis中不存在状态数记录的情况，有两种情况：1.当日未操作 2.redis数据丢失
		// 预防redis数据丢失情况，从数据库中恢复记录的过程
		GrowStatus ss = growStatusService.getByCode(userId, now, eventCode);
		// 若数据库中存在状态记录
		if (ss != null && ss.getId() != null) {
			int newCount = ss.getEventCount() + 1;
			// 1. 同步状态记录到Redis中
			eventAcountService.redislocksset(redisTemplate, recordKey, String.valueOf(newCount), "NX", "PX", getRedisExpireMillis());
			// 2. 更新数据库状态记录数值
			ss.setEventCount(newCount);
			ss.setUpdateTime(now);
			growStatusService.update(ss);
			// 3. 同时更新Redis中的状态值
			boolean needScore = false;
			boolean status = false;
			try {
				if (newCount <= sei.getEventLimit()) {
					needScore = true;
				}
				if (newCount >= sei.getEventLimit()) {
					status = true;
				}
				if (needScore) {
					saveGrowFlow(userId, eventCode, sei ,amount);
				}
			} catch (Exception e) {
				//jedis.decr(recordKey);
				logger.error("-----redis无记录数据库有记录时，持久化处理循环事件成长出错！传入数据userId=" + userId + ",eventCode=" + eventCode + ",amount=" + amount + "出错信息：" + e.getLocalizedMessage());
				exception = e;
//				throw e;
			}
			// 同步积分状态机数据
			if(exception == null) {
				return updateStatus(statusKey, needScore, status);
			}
			return  false;
		}
		// 若数据库中也不存在，即redis和数据库都无状态记录数据， 则初始化状态记录
		eventAcountService.redislocksset(redisTemplate, recordKey, String.valueOf(1), "NX", "PX", getRedisExpireMillis());
		GrowStatus initGrowStatus = new GrowStatus(userId, eventCode, 1);
		initGrowStatus.setCreateTime(now);
		initGrowStatus.setUpdateTime(now);
		// 同步保存数据库初始状态数记录
		boolean needScore = false; 
		boolean status = false;
		try {
			growStatusService.save(initGrowStatus);
			if (1 <= sei.getEventLimit()) {
				needScore = true;
			}
			if(1 == sei.getEventLimit()){
				status = true;
			}
			if (needScore) {
				saveGrowFlow(userId, eventCode, sei , amount);
			}
		} catch (Exception e) {
			//jedis.del(recordKey);
			logger.error("-----redis和数据库都无记录时，持久化处理循环事件成长出错！传入数据userId=" + userId + ",eventCode=" + eventCode + ",amount=" + amount + "出错信息：" + e.getLocalizedMessage());
			exception = e;
//			throw e;
		}
		if(exception == null) {
			return updateStatus(statusKey, needScore, status);
		}
		return  false;
	}
}
