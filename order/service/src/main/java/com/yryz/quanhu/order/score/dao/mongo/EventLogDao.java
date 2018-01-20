package com.yryz.quanhu.order.score.dao.mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.yryz.common.mongodb.AbsBaseMongoDAO;
import com.yryz.quanhu.score.vo.EventInfo;





@Service
public class EventLogDao  extends AbsBaseMongoDAO<EventInfo>{

	private static final Logger logger = LoggerFactory.getLogger(EventLogDao.class);

	private static final Gson gson = GsonBuilderUtils.gsonBuilderWithBase64EncodedByteArrays().create();

//	@Autowired
//	ReplicaSetMongo mongo;

   
	/**
	 * mongoTemplate
	 * @param mongoTemplate
	 * @see com.yryz.common.mongodb.AbsBaseMongoDAO#setMongoTemplate(org.springframework.data.mongodb.core.MongoTemplate)
	 */
	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	/**
	 * 新增日志
	 *
	 * @param log
	 */
	public void saveLog(EventInfo log) {
		try {
//			MongoCollection<Document> conn = mongo.getCollection(MongoConstant.DB_EVENT, MongoConstant.EVENT_LOG);
// 		    Document insert = MongoConstant.bean2Document(log);
			
			// write mongo
			super.save(log);
//			conn.insertOne(insert);
		} catch (Exception e) {
			logger.error("记录事件日志到mongo异常！事件详情：" + gson.toJson(log), e);
		}
	}



}
