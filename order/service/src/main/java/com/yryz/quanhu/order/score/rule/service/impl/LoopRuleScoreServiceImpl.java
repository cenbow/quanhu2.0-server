package com.yryz.quanhu.order.score.rule.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.quanhu.order.score.entity.ScoreStatus;
import com.yryz.quanhu.order.score.rule.service.LoopRuleScoreService;
import com.yryz.quanhu.order.score.service.EventAcountService;
import com.yryz.quanhu.order.score.service.ScoreFlowService;
import com.yryz.quanhu.order.score.service.ScoreStatusService;
import com.yryz.quanhu.order.score.type.ScoreTypeEnum;
import com.yryz.quanhu.order.score.utils.EventUtil;
import com.yryz.quanhu.score.entity.ScoreEventInfo;

/**
 * 前置状态机拦截基于redis中的状态数据，若Redis数据丢失导致状态拦截漏掉时，都通过该持久化状态服务进行处理 状态更新持久化处理服务
 *
 * @author lsn
 */
@Service
@Transactional
public class LoopRuleScoreServiceImpl extends BaseRuleScoreServiceImpl implements LoopRuleScoreService {

    private static final Logger logger = LoggerFactory.getLogger(LoopRuleScoreServiceImpl.class);

    @Autowired
    ScoreStatusService scoreStatusService;

    @Autowired
    EventAcountService eventAcountService;

    @Autowired
    ScoreFlowService scoreFlowService;
    
    
    // RedisTemplate 含有泛型,无法使用 @Autowired by type 注入,只能使用@Resource by name注入
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean processStatus(String custId, String eventCode, ScoreEventInfo sei,  double amount) {
        String recordKey = EventUtil.getScoreRecordKey(custId, eventCode);
        String statusKey = EventUtil.getScoreStatusKey(custId, eventCode, ScoreTypeEnum.Loop);
        Date now = new Date();
        Exception exception = null;
        if (redisTemplate.hasKey(recordKey)) {
            Long newCount = 0L;
            // key存在，则查询状态
            // 1.更新状态记录数
            //newCount = jedis.incr(recordKey);
            newCount = redisTemplate.opsForValue().increment(recordKey,1);
            Integer eventCount = Integer.valueOf(String.valueOf(newCount));

            // 2.同步状态记录数到数据库
            boolean needScore = false;
            boolean status = false;
            try {
                ScoreStatus scoreStatus = new ScoreStatus(custId, eventCode, eventCount);
                scoreStatus.setCreateTime(now); // 这里的createTime字段用于更新时的条件判断，并不实际更新字段值
                scoreStatus.setUpdateTime(now);
                int updateCount = scoreStatusService.update(scoreStatus);
                if (updateCount == 0) {
                    // redis的状态记录不在DB事务中，数据库记录有可能不存在，则从redis中同步
                    scoreStatus.setCreateTime(now);
                    scoreStatusService.save(scoreStatus);
                }
                // 3.根据更新后的状态记录数，对比积分规则 配置，若不满足积分规则，更新状态为false
                // 更新记录后刚好达到限制数，则需要触发积分操作，小于时，未达到积分条件，
                // 大于时，数据库已记录过积分流水了，只需要同步redis中的状态机
                if (eventCount <= sei.getEventLimit()) {
                    needScore = true;
                }
                if (eventCount >= sei.getEventLimit()) {
                    status = true;
                }
                if (needScore) {
                    saveScoreFlow(custId, eventCode, sei, 0, amount);
                }
            } catch (Exception e) {
                //持久化出错后，要重置redis记录数
                //jedis.decr(recordKey);
            	redisTemplate.opsForValue().increment(recordKey, -1);
                logger.error("-----redis有记录时，持久化处理循环事件积分出错！传入数据custId=" + custId + ",eventCode=" + eventCode + ",amount=" + amount + "出错信息：" + e.getLocalizedMessage());
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
        ScoreStatus ss = scoreStatusService.getByCode(custId, "", eventCode, now);
        // 若数据库中存在状态记录
        if (ss != null && ss.getId() != null) {
            logger.info("------DB中状态记录：ss.eventCount=" + ss.getEventCount() + "次.custId=" + custId);
            int newCount = ss.getEventCount() + 1;
            // 1. 同步状态记录到Redis中
            //jedis.set(recordKey, String.valueOf(newCount), "NX", "PX", getRedisExpireMillis());
            eventAcountService.redislocksset(redisTemplate, recordKey, String.valueOf(newCount), "NX", "PX", getRedisExpireMillis());
//            redisTemplate.opsForValue().set(recordKey, String.valueOf(newCount),getRedisExpireMillis(), TimeUnit.MILLISECONDS);
            // 2. 更新数据库状态记录数值
            ss.setEventCount(newCount);
            boolean needScore = false;
            boolean status = false;
            try {
                scoreStatusService.update(ss);
                // 3. 同时更新Redis中的状态值
                logger.info("------newCount=" + newCount + "。eventLimit=" + sei.getEventLimit() + "--------custId:" + custId);
                if (newCount <= sei.getEventLimit()) {
                    needScore = true;
                }
                if (newCount >= sei.getEventLimit()) {
                    status = true;
                }
                logger.info("------needScore=" + needScore + "。status=" + status + "--------custId:" + custId);
                if (needScore) {
                    saveScoreFlow(custId, eventCode, sei, 0, amount);
                }
            } catch (Exception e) {
                //jedis.decr(recordKey);
                logger.error("-----redis无记录数据库有记录时，持久化处理循环事件积分出错！传入数据custId=" + custId + ",eventCode=" + eventCode + ",amount=" + amount + "出错信息：" + e.getLocalizedMessage());
                exception = e;
//				throw e;
            }
            // 同步积分状态机数据
            if(exception == null) {
                return updateStatus(statusKey, needScore, status);
            }
            return false;
        }
        // 若数据库中也不存在，即redis和数据库都无状态记录数据， 则初始化状态记录
//        jedis.set(recordKey, String.valueOf(1), "NX", "PX", getRedisExpireMillis());
        //redisTemplate.opsForValue().set(recordKey, String.valueOf(1),getRedisExpireMillis(), TimeUnit.MILLISECONDS);
        eventAcountService.redislocksset(redisTemplate, recordKey, String.valueOf(1), "NX", "PX", getRedisExpireMillis());
        ScoreStatus initScoreStatus = new ScoreStatus(custId, eventCode, 1);
        initScoreStatus.setCreateTime(now);
        // 同步保存数据库初始状态数记录
        boolean needScore = false;
        boolean status = false;
        try {
            scoreStatusService.save(initScoreStatus);
            if (1 <= sei.getEventLimit()) {
                needScore = true;
            }
            if(1 == sei.getEventLimit()){
                status = true;
            }
            if (needScore) {
                saveScoreFlow(custId, eventCode, sei, 0, amount);
            }
        } catch (Exception e) {
            //jedis.del(recordKey);
            redisTemplate.opsForValue().increment(recordKey, -1);
            logger.error("-----redis和数据库都无记录时，持久化处理循环事件积分出错！传入数据custId=" + custId + ",eventCode=" + eventCode + ",amount=" + amount + "出错信息：" + e.getLocalizedMessage());
            exception = e;
        }
        if(exception == null) {
            return updateStatus(statusKey, needScore, status);
        }
        return false;
    }
}
