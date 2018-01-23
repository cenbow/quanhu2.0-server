package   com.yryz.quanhu.order.grow.rule.service;

import com.yryz.quanhu.order.grow.type.GrowTypeEnum;

/**
 * 事件触发次数限制型循环积分规则接口
 * @author lsn
 *
 */
public interface LoopRuleGrowService extends RuleGrowService{
	
	default GrowTypeEnum getGrowType(){
		return GrowTypeEnum.Loop;
	};
	
}