package com.yryz.quanhu.order.score.consumer;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.yryz.quanhu.order.common.AbsMQlistener;
import com.yryz.quanhu.order.common.AmqpConstant;
import com.yryz.quanhu.order.score.manage.service.ScoreEventManageService;
import com.yryz.quanhu.order.score.service.provider.RuleScoreServiceProvider;
import com.yryz.quanhu.order.score.type.ScoreTypeEnum;
import com.yryz.quanhu.order.score.utils.EventUtil;
import com.yryz.quanhu.score.entity.ScoreEventInfo;

/**
 * Created by lsn on 2017/8/28.
 */
@Service
public class ScoreEventConsumer   {




	private static final Logger logger = LoggerFactory.getLogger(ScoreEventConsumer.class);

//    @Autowired
//    ShardedRedisPool shardedRedisPool;
    
    // RedisTemplate 含有泛型,无法使用 @Autowired by type 注入,只能使用@Resource by name注入
    @Autowired
    private StringRedisTemplate redisTemplate;
    
	@Autowired
	private RabbitTemplate rabbitTemplate; 

    @Autowired
    RuleScoreServiceProvider ruleScoreServiceProvider;

    @Autowired
    ScoreEventManageService scoreEventManageService;

    
	/**
	 * QueueBinding: exchange和queue的绑定
	 * Queue:队列声明
	 * Exchange:声明exchange
	 * key:routing-key
	 * @param data
	 * "#{a}"
	 */
	@RabbitListener(bindings = @QueueBinding(
			value= @Queue(value= AmqpConstant.SCORE_RECEIVE_QUEUE,durable="true"),
			exchange=@Exchange(value=AmqpConstant.EVENT_DIRECT_EXCHANGE,ignoreDeclarationExceptions="true",type=ExchangeTypes.DIRECT),
			key=AmqpConstant.SCORE_RECEIVE_QUEUE)
	)
	public void handleMessage(String message){
		Map<String, String>  data =  AbsMQlistener.onMessage(message);
        // 积分事件关注点： eventCode , userId
        String eventCode = data.get("eventCode");
        String userId = data.get("userId");
        //外部传入积分值
        String eventScore = data.get("eventScore"); 

        
        if (StringUtils.isBlank(userId)) {
            logger.info("-------处理积分事件，结果：直接丢掉消息，原因：userId为空 , 传入数据：" + data.toString());
            return;
        }
        // 判断是否积分事件
        ScoreEventInfo sei = scoreEventManageService.getByCode(eventCode);
        // 判断外部传入的积分值是否存在,如果存在就取传入的分值
        if (!StringUtils.isBlank(eventScore)){
        	sei.setEventScore(Integer.valueOf(eventScore));
        }
        // 积分配置管理表中不存在该类型事件，则不属于积分事件
        boolean isScoreEvent = (sei != null && sei.getId() != null);
        logger.info("是否积分事件判定,结果：" + isScoreEvent + "传入数据：" + data.toString());
        if (isScoreEvent) {
            double amount = 0.0;
            try {
            	Object amountObj = data.get("amount");
				amount = Double.valueOf(amountObj.toString());
                logger.info("传入amount值为：" + amount);
            } catch (Exception e) {
            	//业务本身需要传入amount的场景不多，不做处理
            }
            String eventType = sei.getEventType();
           // ShardedJedis jedis = shardedRedisPool.getSession("EVENT");
            try {
                ScoreTypeEnum ste = null;
                // 数据库记录的积分类型与编码中的枚举映射 关系 1：一次性触发 Once 2：每次触发 Pertime 3：条件日期循环触发
                // Loop 4: 区间连续特性每次触发型
                switch (eventType) {
                    case "1":
                        // 基于redis的积分状态判定
                        logger.info("-------处理积分事件，事件类型eventType=1：一次性触发 ,传入数据：" + data.toString());
                        String statusOnceKey = EventUtil.getScoreStatusKey(userId, eventCode, ScoreTypeEnum.Once);
                       // String statusOnce = JedisUtils.get(statusOnceKey);
                        String statusOnce = redisTemplate.opsForValue().get(statusOnceKey);
                        if (!"true".equals(statusOnce)) {
                            ste = ScoreTypeEnum.Once;
                            ruleScoreServiceProvider.getService(ste).processStatus(userId, eventCode, sei,  amount);
                        }
                        break;
                    case "2":
                        logger.info("-------处理积分事件，事件类型eventType=2：每次触发,传入数据：" + data.toString());
                        ste = ScoreTypeEnum.Pertime;
                        ruleScoreServiceProvider.getService(ste).processStatus(userId, eventCode, sei,  amount);
                        break;
                    case "3":
                        logger.info("-------处理积分事件，事件类型eventType=3：循环触发,传入数据：" + data.toString());
                        String statusLoopKey = EventUtil.getScoreStatusKey(userId, eventCode, ScoreTypeEnum.Loop);
                        String statusLoop = redisTemplate.opsForValue().get(statusLoopKey);
                        if (!"true".equals(statusLoop)) {
                            ste = ScoreTypeEnum.Loop;
                            ruleScoreServiceProvider.getService(ste).processStatus(userId, eventCode, sei,  amount);
                        }
                        break;
                    case "4":
                        logger.info("-------处理积分事件，事件类型eventType=4：签到区间循环,传入数据：" + data.toString());
                        String statusSignKey = EventUtil.getScoreStatusKey(userId, eventCode, ScoreTypeEnum.Sign);
                        String statusSign = redisTemplate.opsForValue().get(statusSignKey);
                     //   if (!"true".equals(statusSign)) {
                            ste = ScoreTypeEnum.Sign;
                            ruleScoreServiceProvider.getService(ste).processStatus(userId, eventCode, sei,  amount);
                      //  }
                        break;
                    default:
                        break;
                }
            } finally {
                //shardedRedisPool.releaseSession(jedis, "EVENT");
            }

        }else {
            logger.info("-------处理积分事件，结果：直接丢掉消息，原因：传入事件类型未在积分事件配置表中维护,传入数据：" + data.toString());
        }
    }


}
