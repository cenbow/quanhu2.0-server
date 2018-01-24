package   com.yryz.quanhu.order.grow.rule.service;

import com.yryz.quanhu.order.grow.type.GrowTypeEnum;

public interface OnceRuleGrowService extends RuleGrowService{

	default GrowTypeEnum getGrowType(){
		return GrowTypeEnum.Once;
	};
	
}
