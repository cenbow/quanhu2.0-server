package com.yryz.quanhu.dymaic.canal.job;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.yryz.common.response.Response;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.coterie.vo.Coterie;
import com.yryz.quanhu.dymaic.canal.dao.CoterieInfoRepository;
import com.yryz.quanhu.dymaic.canal.dao.ResourceInfoRepository;
import com.yryz.quanhu.dymaic.canal.dao.UserRepository;
import com.yryz.quanhu.dymaic.canal.entity.CoterieInfo;
import com.yryz.quanhu.dymaic.canal.entity.ReleaseInfo;
import com.yryz.quanhu.dymaic.canal.entity.ResourceInfo;
import com.yryz.quanhu.dymaic.canal.entity.TopicInfo;
import com.yryz.quanhu.dymaic.canal.entity.TopicPostInfo;
import com.yryz.quanhu.dymaic.canal.entity.UserInfo;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoApi;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;
import com.yryz.quanhu.resource.topic.api.TopicApi;
import com.yryz.quanhu.resource.topic.api.TopicPostApi;
import com.yryz.quanhu.resource.topic.entity.Topic;
import com.yryz.quanhu.resource.topic.vo.TopicPostVo;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;

public class EsSyncDiffJob  implements SimpleJob{
	private static final Log logger = LogFactory.getLog(EsSyncDiffJob.class);
	@Resource
	private UserRepository userRepository;
	
	@Resource
	private CoterieInfoRepository coterieInfoRepository;
	
	@Resource
	private ResourceInfoRepository resourceInfoRepository;
	
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Reference
	private UserApi userApi;
	
	@Reference
	private CoterieApi coterieApi;
	
	@Reference
	private TopicApi topicApi;
	
	@Reference
	private TopicPostApi topicPostApi;
	
	@Reference
	private ReleaseInfoApi releaseInfoApi;
	
	
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("elastic job demo..." + shardingContext.getJobName());

        switch (shardingContext.getShardingItem()) {
            case 0:
				diffUser();
				diffCoterie();
            	diffTopic();
            	diffTopicPost();
            	diffReleaseInfo();
        }
    }
    
    public void diffUser(){
    	String yesterday=DateUtils.getNextDay();
    	Response<List<Long>> res=userApi.getUserIdByCreateDate(yesterday+" 00:00:00", yesterday+" 23:59:59");
    	if(!res.success()){
    		logger.error("diff user error:"+res.getErrorMsg());
    		return;
    	}
    	
    	List<Long> diffList=new ArrayList<>();
    	List<Long> userIdList=res.getData();
    	for (int i = 0; i < userIdList.size(); i++) {
    		long userId=userIdList.get(i);
    		Optional<UserInfo> info=userRepository.findById(userId);
    		if(!info.isPresent()){
    			diffList.add(userId);
    		}
		}
    	
    	if(!diffList.isEmpty()){
    		Response<List<UserBaseInfoVO>> resList=userApi.getAllByUserIds(diffList);
    		if(resList.success()){
    			List<UserInfo> list=GsonUtils.parseList(resList.getData(), UserInfo.class);
    			userRepository.saveAll(list);
    		}
    	}
    }
    
    public void diffCoterie(){
    	String yesterday=DateUtils.getNextDay();
    	Response<List<Long>> res=coterieApi.getKidByCreateDate(yesterday+" 00:00:00", yesterday+" 23:59:59");
    	if(!res.success()){
    		logger.error("diff coterie error:"+res.getErrorMsg());
    		return;
    	}
    	
    	List<Long> diffList=new ArrayList<>();
    	List<Long> idList=res.getData();
    	for (int i = 0; i < idList.size(); i++) {
    		long id=idList.get(i);
    		Optional<CoterieInfo> info=coterieInfoRepository.findById(id);
    		if(!info.isPresent()){
    			diffList.add(id);
    		}
		}
    	
    	if(!diffList.isEmpty()){
    		Response<List<Coterie>> resList=coterieApi.getByKids(diffList);
    		if(resList.success()){
    			List<Coterie> clist=resList.getData();
    			List<CoterieInfo> list=new ArrayList<>();
    			for (int i = 0; i < clist.size(); i++) {
    				Coterie c=clist.get(i);
    				CoterieInfo info=GsonUtils.parseObj(c, CoterieInfo.class);
    				info.setKid(c.getCoterieId());
    				info.setCoterieName(c.getName());
    				info.setState(c.getStatus());
    				list.add(info);
				}
    			coterieInfoRepository.saveAll(list);
    		}
    	}
    }
    
    public void diffTopic(){
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
    
    public void diffTopicPost(){
    	String yesterday=DateUtils.getNextDay();
    	Response<List<Long>> res=topicPostApi.getKidByCreatedate(yesterday+" 00:00:00", yesterday+" 23:59:59");
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
    		Response<List<TopicPostVo>> resList=topicPostApi.getByKids(diffList);
    		if(resList.success()){
    			List<TopicPostVo> clist=resList.getData();
    			List<ResourceInfo> rlist=new ArrayList<>();
    			for (int i = 0; i < clist.size(); i++) {
    				TopicPostInfo d=GsonUtils.parseObj(clist.get(i), TopicPostInfo.class);
    				ResourceInfo resource=new ResourceInfo();
    				resource.setCreateDate(d.getCreateDate());
    				resource.setKid(d.getKid());
    				resource.setLastHeat(0L);
    				resource.setTopicPostInfo(d);
    				resource.setResourceType(2);
    				rlist.add(resource);
				}
    			resourceInfoRepository.saveAll(rlist);
    		}
    	}
    }
    
    public void diffReleaseInfo(){
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
    			List<ReleaseInfoVo> clist=resList.getData();
    			List<ResourceInfo> rlist=new ArrayList<>();
    			for (int i = 0; i < clist.size(); i++) {
    				ReleaseInfo d=GsonUtils.parseObj(clist.get(i), ReleaseInfo.class);
    				ResourceInfo resource=new ResourceInfo();
    				resource.setCreateDate(d.getCreateDate());
    				resource.setKid(d.getKid());
    				resource.setLastHeat(0L);
    				resource.setReleaseInfo(d);
    				resource.setResourceType(3);
    				rlist.add(resource);
				}
    			resourceInfoRepository.saveAll(rlist);
    		}
    	}
    }
    
    
}
