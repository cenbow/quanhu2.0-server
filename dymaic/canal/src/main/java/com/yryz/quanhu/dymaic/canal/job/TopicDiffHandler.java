package com.yryz.quanhu.dymaic.canal.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.yryz.quanhu.resource.topic.api.TopicApi;
import com.yryz.quanhu.resource.topic.entity.Topic;

@Component
public class TopicDiffHandler implements DiffHandler {
	private static final Log logger = LogFactory.getLog(TopicDiffHandler.class);
	@Resource
	private ResourceInfoRepository resourceInfoRepository;
	@Reference
	private TopicApi topicApi;
	@Resource
	private DiffExecutor diffExecutor;

	@PostConstruct
	public void register() {
		diffExecutor.register(this);		
	}
	
	@Override
	public void handler() {
		String yesterday=DateUtils.getNextDay();
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
    			List<Topic> clist=resList.getData();
    			List<ResourceInfo> rlist=new ArrayList<>();
    			for (int i = 0; i < clist.size(); i++) {
    				TopicInfo topic=GsonUtils.parseObj(clist.get(i), TopicInfo.class);
    				ResourceInfo resource=new ResourceInfo();
    				resource.setCreateDate(topic.getCreateDate());
    				resource.setKid(topic.getKid());
    				resource.setLastHeat(0L);
    				resource.setTopicInfo(topic);
    				resource.setResourceType(1);
    				rlist.add(resource);
				}
    			resourceInfoRepository.saveAll(rlist);
    		}
    	}
	}

}
