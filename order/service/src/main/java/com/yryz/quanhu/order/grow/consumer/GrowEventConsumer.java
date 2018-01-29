package com.yryz.quanhu.order.grow.consumer;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.quanhu.grow.entity.GrowEventInfo;
import com.yryz.quanhu.order.common.AbsMQlistener;
import com.yryz.quanhu.order.common.AmqpConstant;
import com.yryz.quanhu.order.grow.manage.service.GrowEventManageService;
import com.yryz.quanhu.order.grow.service.provider.RuleGrowServiceProvider;
import com.yryz.quanhu.order.grow.type.GrowTypeEnum;
import com.yryz.quanhu.order.grow.utils.GrowKeyUtil;

/**
 * Created by lsn on 2017/8/28.
 */
@Transactional
@Service
public class GrowEventConsumer {

	private static final Logger logger = LoggerFactory.getLogger(GrowEventConsumer.class);

//	@Autowired
//	ShardedRedisPool shardedRedisPool;

    // RedisTemplate 含有泛型,无法使用 @Autowired by type 注入,只能使用@Resource by name注入
    @Autowired
    private StringRedisTemplate redisTemplate;
    

	
	@Autowired
	RuleGrowServiceProvider ruleGrowServiceProvider;

	@Autowired
	GrowEventManageService growEventManageService;

	/**
	 * QueueBinding: exchange和queue的绑定
	 * Queue:队列声明
	 * Exchange:声明exchange
	 * key:routing-key
	 * @param data
	 * "#{a}"
	 */
	@RabbitListener(bindings = @QueueBinding(
			value= @Queue(value= AmqpConstant.GROW_RECEIVE_QUEUE,durable="true"),
			exchange=@Exchange(value=AmqpConstant.EVENT_DIRECT_EXCHANGE,ignoreDeclarationExceptions="true",type=ExchangeTypes.DIRECT),
			key=AmqpConstant.GROW_RECEIVE_QUEUE)
	)
	public void handleAsynMessage(String message) {
		Map<String, String>  data =  AbsMQlistener.onMessage(message);
		// 成长事件有可能触发另一目标被动式增加成长值
		// 成长事件关注点： eventCode , userId ， resourceId , ownerId
		String eventCode = data.get("eventCode");
		String userId = data.get("userId");
        //外部传入积分值
        String eventGrow = data.get("eventGrow"); 
        
		if (StringUtils.isBlank(userId)) {
			logger.info("-------处理成长事件，结果：直接丢掉消息，原因：userId为空,传入数据：" + data.toString());
			return;
		}

		// 首先判断是否成长事件，如果不是成长事件，直接丢掉消息
		GrowEventInfo sei = growEventManageService.getByCode(eventCode);
		boolean isGrowEvent = (sei != null && sei.getId() != null);
		logger.info("是否成长事件判定,结果：" + isGrowEvent + "传入数据：" + data.toString());
		
        // 判断外部传入的成长值是否存在,如果存在就取传入的分值
        if (!StringUtils.isBlank(eventGrow)){
        	sei.setEventGrow(Integer.valueOf(eventGrow));
        }
		
		
		// 成长配置管理表中不存在该类型事件，则不属于成长事件
		if (isGrowEvent) {
			double amount = 0.0;
			try {
				Object amountObj = data.get("amount");
				amount = Double.valueOf(amountObj.toString());
				logger.info("传入amount值为：" + amount);
			} catch (Exception e) {
				//业务本身需要传入amount的场景不多，不做处理
			}
			String growType = sei.getGrowType();
			String resourceId = "";
			switch (growType) {
			case "0":
				break;
			case "1": // 被动式成长，需要知道资源ID和对应的资源所有者
				resourceId = data.get("resourceId");
				userId = data.get("ownerId");
				break;
			default:
				break;
			}

			String eventType = sei.getEventType();
		//	ShardedJedis jedis = shardedRedisPool.getSession("EVENT");
			try {
				GrowTypeEnum ste = null;
				// 数据库记录的积分类型与编码中的枚举映射 关系 1：一次性触发 Once 2：每次触发 Pertime 3：条件日期循环触发
				// Loop
				switch (eventType) {
				case "1":
					logger.info("-------处理成长事件，事件类型eventType=1：一次性触发,传入数据：" + data.toString());
					// 基于redis的积分状态判定
					String statusOnceKey = GrowKeyUtil.getGrowStatusKey(userId, eventCode, resourceId,
							GrowTypeEnum.Once);
					String statusOnce = redisTemplate.opsForValue().get(statusOnceKey);
					if (!"true".equals(statusOnce)) {
						ste = GrowTypeEnum.Once;
						ruleGrowServiceProvider.getService(ste).processStatus(userId, eventCode, resourceId, sei, 
								amount);
					}
					break;
				case "2":
					logger.info("-------处理成长事件，事件类型eventType=2：每次触发,传入数据：" + data.toString());
					ste = GrowTypeEnum.Pertime;
					ruleGrowServiceProvider.getService(ste).processStatus(userId, eventCode, resourceId, sei, 
							amount);
					break;
				case "3":
					logger.info("-------处理成长事件，事件类型eventType=3：循环触发,传入数据：" + data.toString());
					String statusLoopKey = GrowKeyUtil.getGrowStatusKey(userId, eventCode, resourceId,
							GrowTypeEnum.Loop);
					String statusLoop = redisTemplate.opsForValue().get(statusLoopKey);
					if (!"true".equals(statusLoop)) {
						ste = GrowTypeEnum.Loop;
						ruleGrowServiceProvider.getService(ste).processStatus(userId, eventCode, resourceId, sei, 
								amount);
					}
					break;
				default:
					break;
				}
			} finally {
				//shardedRedisPool.releaseSession(jedis, "EVENT");
			}
		} else {
			logger.info("-------处理成长事件，结果：直接丢掉消息，原因：传入事件类型未在成长事件配置表中维护,传入数据：" + data.toString());
		}
	}
}
