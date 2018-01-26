package com.yryz.quanhu.dymaic.canal.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.yryz.quanhu.dymaic.canal.dao.UserRepository;
import com.yryz.quanhu.dymaic.canal.entity.UserInfo;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;

public class EsSyncDiffJob  implements SimpleJob{
	private static final Log logger = LogFactory.getLog(EsSyncDiffJob.class);
	@Resource
	private UserRepository userRepository;
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Reference
	private UserApi userApi;
	
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("elastic job demo..." + shardingContext.getJobName());

        switch (shardingContext.getShardingItem()) {
            case 0:
            	diffUser();
                break;
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
}
