package   com.yryz.quanhu.order.grow.rule.service;

import com.yryz.quanhu.grow.entity.GrowEventInfo;
import com.yryz.quanhu.order.grow.type.GrowTypeEnum;

import redis.clients.jedis.ShardedJedis;

/**
 * 积分服务接口
 * 所有积分类型的上级接口
 * 包含积分状态机更新操作
 * @author lsn
 *
 */
public interface RuleGrowService {
	
	GrowTypeEnum getGrowType();
	
	boolean updateStatus(String statusKey , boolean needScore , boolean status);

	boolean processStatus(String userId , String eventCode , String resourceId , GrowEventInfo gei , double amount);
	
	Long saveGrowFlow(String userId , String eventCode , GrowEventInfo gei , double amount);
}
