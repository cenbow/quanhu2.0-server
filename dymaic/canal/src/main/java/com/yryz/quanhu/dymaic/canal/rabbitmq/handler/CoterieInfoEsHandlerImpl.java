package com.yryz.quanhu.dymaic.canal.rabbitmq.handler;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import com.yryz.common.entity.CanalMsgContent;
import com.yryz.common.utils.CanalEntityParser;
import com.yryz.quanhu.dymaic.canal.constant.CommonConstant;
import com.yryz.quanhu.dymaic.canal.dao.CoterieInfoRepository;
import com.yryz.quanhu.dymaic.canal.entity.CoterieInfo;

@Component
public class CoterieInfoEsHandlerImpl implements SyncHandler {
	@Resource
	private SyncExecutor syncExecutor;
	
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Resource
	private CoterieInfoRepository coterieInfoRepository;
	
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
		CoterieInfo uinfoBefore = CanalEntityParser.parse(msg.getDataBefore(),CoterieInfo.class);
		CoterieInfo uinfoAfter = CanalEntityParser.parse(msg.getDataAfter(),CoterieInfo.class);

		if (CommonConstant.EventType.OPT_UPDATE.equals(msg.getEventType())) {
			Optional<CoterieInfo> uinfo = coterieInfoRepository.findById(uinfoBefore.getKid());
			if (uinfo.isPresent()) {
				CoterieInfo userInfo = CanalEntityParser.parse(uinfo.get(), msg.getDataAfter(),CoterieInfo.class);
				coterieInfoRepository.save(userInfo);
			} else {
				// 先收到了update消息，后收到insert消息
				coterieInfoRepository.save(uinfoAfter);
			}
		} else if (CommonConstant.EventType.OPT_DELETE.equals(msg.getEventType())) {
			coterieInfoRepository.deleteById(uinfoBefore.getKid());
		} else if (CommonConstant.EventType.OPT_INSERT.equals(msg.getEventType())) {
			// 先执行了update则不执行insert
			Optional<CoterieInfo> uinfo = coterieInfoRepository.findById(uinfoAfter.getKid());
			if (!uinfo.isPresent()) {
				coterieInfoRepository.save(uinfoAfter);
			}
		}
	}

}
