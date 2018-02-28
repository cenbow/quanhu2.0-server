package com.yryz.quanhu.order.grow.rule.service.impl;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.grow.entity.GrowEventInfo;
import com.yryz.quanhu.grow.entity.GrowFlow;
import com.yryz.quanhu.order.grow.entity.GrowLevel;
import com.yryz.quanhu.order.grow.manage.service.GrowLevelManageService;
import com.yryz.quanhu.order.grow.rule.service.RuleGrowService;
import com.yryz.quanhu.order.grow.service.GrowFlowService;
import com.yryz.quanhu.order.score.service.EventAcountService;
import com.yryz.quanhu.score.vo.EventAcount;

import net.sf.json.JSONObject;

/**
 * 循环规则积分事件服务基类
 * 
 * @author lsn
 *
 */
@Transactional
@Service
public abstract class BaseRuleGrowServiceImpl implements RuleGrowService {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseRuleGrowServiceImpl.class);

	@Autowired
	GrowFlowService growFlowService;

	@Autowired
	EventAcountService eventAcountService;

	@Autowired
	GrowLevelManageService growLevelManageService;
	
    @Autowired
    private StringRedisTemplate redisTemplate;

	Long getRedisExpireMillis() {
		Calendar curDate = Calendar.getInstance();
		Calendar tommorowDate = new GregorianCalendar(curDate.get(Calendar.YEAR), curDate.get(Calendar.MONTH),
				curDate.get(Calendar.DATE) + 1, 0, 0, 0);
		return tommorowDate.getTimeInMillis() - curDate.getTimeInMillis();
	}

	/**
	 * statusKey: 状态key needScore: 是否需要记录积分流水
	 */
	@Override
	public boolean updateStatus(String statusKey,  boolean needScore, boolean status) {
		boolean statusPresent = redisTemplate.hasKey(statusKey);
		if (statusPresent) {
			eventAcountService.redislocksset(redisTemplate,statusKey, String.valueOf(status), "XX", "PX", getRedisExpireMillis());
			return true;
		}
		eventAcountService.redislocksset(redisTemplate,statusKey, String.valueOf(status), "NX", "PX", getRedisExpireMillis());
		return true;
	}

	// 规则判定的最终目标是要记录积分流水，记录积分流水与更新积分总值总是同步进行的
	public Long saveGrowFlow(String userId, String eventCode, GrowEventInfo sei, double amount) {
		// 计算新增积分数
		Date now = new Date();
		// 数据库记录的积分类型与编码中的枚举映射 关系 0：一次性触发 Once 1：每次触发 Pertime 2：条件日期循环触发 Loop
		Integer newGrow = sei.getEventGrow();

		if (amount > 0) {
			//统一转换金额单位为：元
			newGrow = (int) (amount * sei.getAmountPower() / 100);
		}
		// switch (sei.getEventType()) {
		// case "2":
		// newGrow = sei.getEventGrow() * sei.getEventLimit();
		// break;
		// default:
		// newGrow = sei.getEventGrow();
		// }
		long allGrow = 0;
		logger.info("-------处理成长值运算事件传入userId ，每次触发传入数据：userId = "+userId);
		EventAcount ea = eventAcountService.getLastAcount(userId);
		logger.info("-------处理成长值运算事件ea，每次触发传入数据：={}",JsonUtils.toFastJson(ea,null));
		// 总值表无数据 ，则初始化该表
		if (ea == null || ea.getId() == null  || ea.getGrow() == null) {
			allGrow = 0L + newGrow;
			GrowLevel level = growLevelManageService.getByLevelValue((int) allGrow);
			ea = new EventAcount(userId);
			ea.setGrow(Long.valueOf(Math.abs(newGrow)));
			ea.setGrowLevel(level.getLevel());
			ea.setCreateTime(now);
			ea.setUpdateTime(now);
			logger.info("-------处理成长值运算事件if(EventAcount) == null，每次触发传入数据：={}",JsonUtils.toFastJson(ea,null));
			eventAcountService.save(ea);
		} else {
			// 同积分总账更新方式，更新成长值时，可能会覆盖积分值
			// 更新时，由于积分和成长都在更新，可能取出来的跟积分无关的数据在更新积分时被回写到数据库
			allGrow = ea.getGrow() + newGrow;
			logger.info("-------处理成长值运算事件else(EventAcount)，每次触发传入数据：ea.getGrow(): " + ea.getGrow()+" newGrow: "+newGrow+" allGrow: "+allGrow);
			GrowLevel level = growLevelManageService.getByLevelValue((int) allGrow);
			//数据库运算
			ea.setGrow(Long.valueOf(Math.abs(newGrow)));
			ea.setGrowLevel(level.getLevel());
			ea.setUpdateTime(now);
			ea.setScore(null);
			logger.info("-------处理成长值运算事件else(EventAcount)，每次触发传入数据：={}",JsonUtils.toFastJson(ea,null));
			if(ea.getGrow()<1){
				throw new RuntimeException("-------处理成长事件，结果：直接捕获视为异常消息，原因：ea.getGrow()查询成长总值为空,传入数据：" + ea.getGrow()); 
			}
			eventAcountService.update(ea);
		}
		// 无论总值表有无数据，流水是要记的
		GrowFlow sf = new GrowFlow(userId, eventCode, newGrow);
		sf.setAllGrow(Long.valueOf(Math.abs(allGrow)));
		sf.setCreateTime(now);
		sf.setUpdateTime(now);
		logger.info("-------处理成长值(流水表GrowFlow)运算事件，每次触发传入数据：={}",JSONObject.fromObject(sf));
		growFlowService.save(sf);

		return 0L;
	}

}
