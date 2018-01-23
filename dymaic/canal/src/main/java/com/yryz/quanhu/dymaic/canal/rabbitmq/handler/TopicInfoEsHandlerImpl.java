package com.yryz.quanhu.dymaic.canal.rabbitmq.handler;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import com.yryz.quanhu.dymaic.canal.constant.CommonConstant;
import com.yryz.quanhu.dymaic.canal.elasticsearch.TopicInfoEsRepository;
import com.yryz.quanhu.dymaic.canal.entity.CanalMsgContent;
import com.yryz.quanhu.dymaic.canal.entity.TopicInfo;
import com.yryz.quanhu.dymaic.canal.entity.UserInfo;

@Component
public class TopicInfoEsHandlerImpl implements SyncHandler{
	@Resource
	private SyncExecutor syncExecutor;
	
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Resource
	private TopicInfoEsRepository topicInfoEsRepository;
	
	@PostConstruct
	private void register() {
		syncExecutor.register(this);
	}
	
	@Override
	public Boolean watch(CanalMsgContent msg) {
		if (CommonConstant.QuanHuDb.DB_NAME.equals(msg.getDbName())
				&& CommonConstant.QuanHuDb.TABLE_TOPIC.equals(msg.getTableName())) {
			return true;
		}
		return false;
	}

	@Override
	public void handler(CanalMsgContent msg) {
		boolean exists=elasticsearchTemplate.indexExists(TopicInfo.class);
		if(!exists){
			elasticsearchTemplate.createIndex(TopicInfo.class);
		}
		TopicInfo uinfoBefore = EntityParser.parse(msg.getDataBefore(),TopicInfo.class);
		TopicInfo uinfoAfter = EntityParser.parse(msg.getDataAfter(),TopicInfo.class);
		if (CommonConstant.EventType.OPT_UPDATE.equals(msg.getEventType())) {
			Optional<TopicInfo> uinfo = topicInfoEsRepository.findById(uinfoBefore.getKid());
			if (uinfo.isPresent()) {
				TopicInfo userInfo = EntityParser.parse(uinfo.get(), msg.getDataAfter(),TopicInfo.class);
				topicInfoEsRepository.save(userInfo);
			} else {
				// 先收到了update消息，后收到insert消息
				topicInfoEsRepository.save(uinfoAfter);
			}
		} else if (CommonConstant.EventType.OPT_DELETE.equals(msg.getEventType())) {
			topicInfoEsRepository.deleteById(uinfoBefore.getKid());
		} else if (CommonConstant.EventType.OPT_INSERT.equals(msg.getEventType())) {
			// 先执行了update则不执行insert
			Optional<TopicInfo> uinfo = topicInfoEsRepository.findById(uinfoAfter.getKid());
			if (!uinfo.isPresent()) {
				topicInfoEsRepository.save(uinfoAfter);
			}
		}		
	}

}
