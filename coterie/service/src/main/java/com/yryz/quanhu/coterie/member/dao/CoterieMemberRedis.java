package com.yryz.quanhu.coterie.member.dao;

import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.coterie.member.entity.CoterieMember;
import com.yryz.quanhu.coterie.member.entity.CoterieMemberApply;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CoterieMemberRedis {
	@Autowired
	private StringRedisTemplate redisTemplate;

	private static long timeNumber = 1;
	private static TimeUnit timeUnit = TimeUnit.HOURS;

	public String getCoterieMemberPermissionKey(Long coterieId, Long userId){
		return "QUANHU:COTERIE:MEMBER:PERMISSION:"+coterieId+"_"+userId;
	}

	public String getCoterieMemberModelKey(Long coterieId, Long userId){
		return "QUANHU:COTERIE:MEMBER:MODEL:"+coterieId+"_"+userId;
	}

	public String getCoterieMemberApplyKey(Long coterieId, Long userId){
		return "QUANHU:COTERIE:MEMBER:APPLY:"+coterieId+"_"+userId;
	}


	/********* permission ******************************************************/

	/**
	 * 缓存私圈成员权限信息
	 */
	public void savePermission(Long coterieId, Long userId, Integer value){
		String key=getCoterieMemberPermissionKey(coterieId, userId);
		redisTemplate.opsForValue().set(key, value.toString(), timeNumber, timeUnit);
	}

	/**
	 * 获取私圈成员权限信息
	 * @param coterieId
	 * @return
	 */
	public Integer getPermission(Long coterieId, Long userId){
		String key=getCoterieMemberPermissionKey(coterieId, userId);
		String val = redisTemplate.opsForValue().get(key);
		if(StringUtils.isNotEmpty(val)){
			return NumberUtils.createInteger(val);
		}
		return null;
	}

	/********* apply ******************************************************/

	/**
	 * 缓存私圈申请信息
	 */
    public void saveMemberApply(CoterieMemberApply model){
		String key=getCoterieMemberApplyKey(model.getCoterieId(), model.getUserId());
		redisTemplate.opsForValue().set(key, GsonUtils.parseJson(model), timeNumber, timeUnit);
	}

	/**
	 * 获取私圈申请信息
	 */
	public CoterieMemberApply getMemberApply(Long coterieId, Long userId){
		String key=getCoterieMemberApplyKey(coterieId, userId);
		String val = redisTemplate.opsForValue().get(key);
		if(StringUtils.isNotEmpty(val)){
			return GsonUtils.json2Obj(val, CoterieMemberApply.class);
		}
		return null;
	}

	/********* member ******************************************************/

	/**
	 * 保存私圈成员信息
	 */
	public void saveCoterieMember(CoterieMember model){
		String key=getCoterieMemberModelKey(model.getCoterieId(), model.getUserId());
		redisTemplate.opsForValue().set(key, GsonUtils.parseJson(model), timeNumber, timeUnit);
	}

	/**
	 * 获取私圈成员信息
	 */
	public CoterieMember getCoterieMember(Long coterieId, Long userId){
		String key=getCoterieMemberModelKey(coterieId, userId);
		String val = redisTemplate.opsForValue().get(key);
		if(StringUtils.isNotEmpty(val)){
			return GsonUtils.json2Obj(val, CoterieMember.class);
		}
		return null;
	}

//	public Map<Long,Coterie> multiGet(List<Long> kidList){
//		if(kidList==null || kidList.isEmpty()){
//			return Maps.newHashMap();
//		}
//
//		// 准备批量查询缓存的key
//        List<String> keyList = new ArrayList<>();
//		for (Long kid : kidList) {
//            keyList.add(getCoterieModelKey(kid));
//        }
//
//		List<String> list=redisTemplate.opsForValue().multiGet(keyList);
//		Map<Long,Coterie> result=Maps.newHashMap();
//		for (int i = 0; i < list.size(); i++) {
//			Coterie c=GsonUtils.json2Obj(list.get(i), Coterie.class);
//			if(c!=null && c.getCoterieId()!=null){
//				result.put(c.getCoterieId(),c);
//			}
//		}
//		return result;
//	}
}
