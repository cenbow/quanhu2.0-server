package com.yryz.quanhu.dymaic.canal.rabbitmq.handler;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import com.yryz.quanhu.dymaic.canal.constant.CommonConstant;
import com.yryz.quanhu.dymaic.canal.elasticsearch.CoterieInfoEsRepository;
import com.yryz.quanhu.dymaic.canal.entity.CanalMsgContent;
import com.yryz.quanhu.dymaic.canal.entity.CoterieInfo;
import com.yryz.quanhu.dymaic.canal.entity.ReleaseInfo;

@Component
public class CoterieInfoEsHandlerImpl implements SyncHandler {
	@Resource
	private SyncExecutor syncExecutor;
	
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Resource
	private CoterieInfoEsRepository coterieInfoEsRepository;
	
	@PostConstruct
	private void register() {
		syncExecutor.register(this);
	}
	
	@Override
	public Boolean watch(CanalMsgContent msg) {
		if (CommonConstant.QuanHuDb.DB_NAME.equals(msg.getDbName())
				&& CommonConstant.QuanHuDb.TABLE_COTERIE.equals(msg.getTableName())) {
			return true;
		}
		return false;
	}

	@Override
	public void handler(CanalMsgContent msg) {
		boolean exists=elasticsearchTemplate.indexExists(CoterieInfo.class);
		if(!exists){
			elasticsearchTemplate.createIndex(CoterieInfo.class);
		}
		CoterieInfo uinfoBefore = EntityParser.parse(msg.getDataBefore(),CoterieInfo.class);
		CoterieInfo uinfoAfter = EntityParser.parse(msg.getDataAfter(),CoterieInfo.class);

		if (CommonConstant.EventType.OPT_UPDATE.equals(msg.getEventType())) {
			Optional<CoterieInfo> uinfo = coterieInfoEsRepository.findById(uinfoBefore.getKid());
			if (uinfo.isPresent()) {
				CoterieInfo userInfo = EntityParser.parse(uinfo.get(), msg.getDataAfter(),CoterieInfo.class);
				coterieInfoEsRepository.save(userInfo);
			} else {
				// 先收到了update消息，后收到insert消息
				coterieInfoEsRepository.save(uinfoAfter);
			}
		} else if (CommonConstant.EventType.OPT_DELETE.equals(msg.getEventType())) {
			coterieInfoEsRepository.deleteById(uinfoBefore.getKid());
		} else if (CommonConstant.EventType.OPT_INSERT.equals(msg.getEventType())) {
			// 先执行了update则不执行insert
			Optional<CoterieInfo> uinfo = coterieInfoEsRepository.findById(uinfoAfter.getKid());
			if (!uinfo.isPresent()) {
				coterieInfoEsRepository.save(uinfoAfter);
			}
		}
	}

}
