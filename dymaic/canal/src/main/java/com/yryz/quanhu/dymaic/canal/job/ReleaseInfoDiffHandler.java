package com.yryz.quanhu.dymaic.canal.job;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.dymaic.canal.dao.ResourceInfoRepository;
import com.yryz.quanhu.dymaic.canal.entity.ReleaseInfo;
import com.yryz.quanhu.dymaic.canal.entity.ResourceInfo;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoApi;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;
import com.yryz.quanhu.resource.vo.ResourceVo;

@Component
public class ReleaseInfoDiffHandler implements DiffHandler {
	private static final Log logger = LogFactory.getLog(ReleaseInfoDiffHandler.class);
	@Resource
	private ResourceInfoRepository resourceInfoRepository;
	@Reference
	private ReleaseInfoApi releaseInfoApi;
	@Resource
	private DiffExecutor diffExecutor;
	@Reference
	private ResourceApi resourceApi;
	
	@PostConstruct
	public void register() {
		diffExecutor.register(this);		
	}
	
	@Override
	public void handler() {
		String yesterday=DateUtils.getNextDay();
    	Response<List<Long>> res=releaseInfoApi.getKidByCreatedate(yesterday+" 00:00:00", yesterday+" 23:59:59");
    	if(!res.success()){
    		logger.error("diff topic error:"+res.getErrorMsg());
    		return;
    	}
    	
    	Set<Long> diffList=new HashSet<>();
    	List<Long> idList=res.getData();
    	for (int i = 0; i < idList.size(); i++) {
    		long id=idList.get(i);
    		Optional<ResourceInfo> info=resourceInfoRepository.findById(id);
    		if(!info.isPresent()){
    			diffList.add(id);
    		}
		}
    	
    	if(!diffList.isEmpty()){
    		Response<List<ReleaseInfoVo>> resList=releaseInfoApi.selectByKids(diffList);
    		if(resList.success()){
    			//热度信息获取
    			Set<String> resourceIds=new HashSet<>();
    			diffList.forEach(item->resourceIds.add(item.toString()));
    			Response<Map<String, ResourceVo>> response=resourceApi.getResourcesByIds(resourceIds);
    			if(!response.success()){
    				logger.error("resourceApi.getResourcesByIds 获取资源信息失败");
    				return;
    			}
    			Map<String, ResourceVo> resMap=response.getData();
    			
    			List<ReleaseInfoVo> clist=resList.getData();
    			List<ResourceInfo> rlist=new ArrayList<>();
    			for (int i = 0; i < clist.size(); i++) {
    				ReleaseInfo d=GsonUtils.parseObj(clist.get(i), ReleaseInfo.class);
    				ResourceInfo resource=new ResourceInfo();
    				resource.setCreateDate(d.getCreateDate());
    				resource.setKid(d.getKid());
    				resource.setLastHeat(0L);
    				if (resMap != null) {
    					ResourceVo r = resMap.get(d.getKid());
    					if (r != null && r.getHeat()!=null) {
    						resource.setLastHeat(r.getHeat());
    					}
    				}
    				resource.setReleaseInfo(d);
    				resource.setResourceType(3);
    				rlist.add(resource);
				}
    			resourceInfoRepository.saveAll(rlist);
    		}
    	}
	}

}
