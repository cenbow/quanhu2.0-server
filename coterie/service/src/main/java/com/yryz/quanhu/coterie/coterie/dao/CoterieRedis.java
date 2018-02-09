package com.yryz.quanhu.coterie.coterie.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.coterie.coterie.entity.Coterie;

@Service
public class CoterieRedis {
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	/**
	 * 保存私圈信息
	 * @param model
	 */
	public void save(Coterie model){
		String key=getCoterieModelKey(model.getCoterieId());
		redisTemplate.opsForValue().set(key, GsonUtils.parseJson(model), 2, TimeUnit.DAYS);
	}
	
	/**
	 * 获取单个私圈
	 * @param coterieId
	 * @return
	 */
	public Coterie get(Long coterieId){
		String key=getCoterieModelKey(coterieId);
		String val = redisTemplate.opsForValue().get(key);
		if(StringUtils.isNotEmpty(val)){
			return GsonUtils.json2Obj(val, Coterie.class);
		}
		return null;
	}
	
	public Map<Long,Coterie> multiGet(List<Long> kidList){
		if(kidList==null || kidList.isEmpty()){
			return Maps.newHashMap();
		}
		
		// 准备批量查询缓存的key
        List<String> keyList = new ArrayList<>();
		for (Long kid : kidList) {
            keyList.add(getCoterieModelKey(kid));
        }
		
		List<String> list=redisTemplate.opsForValue().multiGet(keyList);
		Map<Long,Coterie> result=Maps.newHashMap();
		for (int i = 0; i < list.size(); i++) {
			Coterie c=GsonUtils.json2Obj(list.get(i), Coterie.class);
			if(c!=null && c.getCoterieId()!=null){
				result.put(c.getCoterieId(),c);
			}
		}
		return result;
	}
	
	public String getCoterieModelKey(Long coterieId){
		return "QUANHU:COTERIE:MODEL:"+coterieId;
	}
}
