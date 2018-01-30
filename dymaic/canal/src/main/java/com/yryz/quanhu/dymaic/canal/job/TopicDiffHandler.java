package com.yryz.quanhu.dymaic.canal.job;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
import com.yryz.quanhu.dymaic.canal.entity.ResourceInfo;
import com.yryz.quanhu.dymaic.canal.entity.TopicInfo;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.topic.api.TopicApi;
import com.yryz.quanhu.resource.topic.entity.Topic;
import com.yryz.quanhu.resource.vo.ResourceVo;

@Component
public class TopicDiffHandler implements DiffHandler {
	private static final Log logger = LogFactory.getLog(TopicDiffHandler.class);
	@Resource
	private ResourceInfoRepository resourceInfoRepository;
	@Reference
	private TopicApi topicApi;
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
		String yesterday=DateUtils.getYesterDay();
    	Response<List<Long>> res=topicApi.getKidByCreatedate(yesterday+" 00:00:00", yesterday+" 23:59:59");
    	if(!res.success()){
    		logger.error("diff topic error:"+res.getErrorMsg());
    		return;
    	}
    	
    	List<Long> diffList=new ArrayList<>();
    	List<Long> idList=res.getData();
    	for (int i = 0; i < idList.size(); i++) {
    		long id=idList.get(i);
    		Optional<ResourceInfo> info=resourceInfoRepository.findById(id);
    		if(!info.isPresent()){
    			diffList.add(id);
    		}
		}
    	
    	if(!diffList.isEmpty()){
    		Response<List<Topic>> resList=topicApi.getByKids(diffList);
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
    			
    			List<Topic> clist=resList.getData();
    			List<ResourceInfo> rlist=new ArrayList<>();
    			for (int i = 0; i < clist.size(); i++) {
    				TopicInfo topic=GsonUtils.parseObj(clist.get(i), TopicInfo.class);
    				ResourceInfo resource=new ResourceInfo();
    				resource.setCreateDate(topic.getCreateDate());
    				resource.setKid(topic.getKid());
    				resource.setLastHeat(0L);
    				if (resMap != null) {
    					ResourceVo r = resMap.get(topic.getKid());
    					if (r != null && r.getHeat()!=null) {
    						resource.setLastHeat(r.getHeat());
    					}
    				}
    				resource.setTopicInfo(topic);
    				resource.setResourceType(1);
    				rlist.add(resource);
				}
    			resourceInfoRepository.saveAll(rlist);
    		}
    	}
	}

}
