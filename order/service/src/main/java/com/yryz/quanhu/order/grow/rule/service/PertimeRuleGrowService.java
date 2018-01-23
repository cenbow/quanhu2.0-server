package   com.yryz.quanhu.order.grow.rule.service;

import com.yryz.quanhu.order.grow.type.GrowTypeEnum;

/**
 * 每次触发事件积分或一次永久积分服务接口
 * @author lsn
 *
 */
public interface PertimeRuleGrowService extends RuleGrowService{
	
	default GrowTypeEnum getGrowType(){
		return GrowTypeEnum.Pertime;
	};
	
}
