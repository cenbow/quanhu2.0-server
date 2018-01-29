/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年10月12日
 * Id: RequestLogMongo.java, 2017年10月12日 下午4:52:27 yehao
 */
package com.yryz.quanhu.support.requestlog.mongo;

import com.yryz.common.mongodb.AbsBaseMongoDAO;
import com.yryz.quanhu.support.requestlog.entity.RequestLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年10月12日 下午4:52:27
 * @Description mongo处理类
 */
@Service
public class RequestLogMongo extends AbsBaseMongoDAO<RequestLog> {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestLogMongo.class);

	// 数据库
	public static final String DB_CIRCLE = "MESSAGE";

	// 集合
	public static final String REQUEST_LOG_MODEL = "RequestLog";
	
//	@Autowired
//	ReplicaSetMongo mongo;
	
	/*public static String getModel(Long startTime){
		Date date = null;
		if(startTime == null || startTime.longValue() == 0){
			date = new Date();
		} else {
			date = new Date(startTime);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int no = calendar.get(Calendar.WEEK_OF_YEAR);
		return REQUEST_LOG_MODEL + "_" + no; 
	}
	
	*//**
	 * 保存mongo
	 * @param circleHotCalculation
	 *//*
	public void save(RequestLog requestLog){
		try {
			MongoCollection<Document> conn = mongo.getCollection(DB_CIRCLE, getModel(requestLog.getStartTime()));
			Document document = MongoUtils.bean2Document(requestLog);
			conn.insertOne(document);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	*//**
	 * 更新mongo
	 * @param circleHotCalculation
	 *//*
	public void update(RequestLog requestLog) {
		try {
			MongoCollection<Document> conn = mongo.getCollection(DB_CIRCLE, getModel(requestLog.getStartTime()));

			BsonDocument filter = new BsonDocument("requestId", new BsonString(requestLog.getRequestId()));
			Document change = MongoUtils.bean2Document(requestLog);
		    Document update = new Document("$set", change);
		    
		    FindOneAndUpdateOptions opts = new FindOneAndUpdateOptions();
		    opts.upsert(true);
		    conn.findOneAndUpdate(filter, update, opts);
		    
		} catch (Exception e) {
			throw new MongoOptException("[mongo:logStatus]", e.getCause());
		}
	}
	
	*//**
	 * 单一查询
	 * @param eventId
	 * @return
	 *//*
	public RequestLog get(String requestId){
		try {
			MongoCollection<Document> conn = mongo.getCollection(DB_CIRCLE, getModel(null));

			BsonDocument query = new BsonDocument();
			if(StringUtils.isNotEmpty(requestId)){
				query.put("requestId", new BsonString(requestId));
			}
			
			Document doc = conn.find(query).first();
			if(doc != null){
				RequestLog RequestLog = new RequestLog();
				RequestLog = MongoUtils.document2Bean(doc, RequestLog);
				return RequestLog;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new MongoOptException("[mongo:list]", e.getCause());
		}
		return null;
	}
	
	public List<RequestLog> getList(int start , int limit){
		
		if(start < 0 || limit < 0){
			return null;
		}
		
		List<RequestLog> result = new ArrayList<RequestLog>();
		try {
			MongoCollection<Document> conn = mongo.getCollection(DB_CIRCLE, getModel(null));
			BsonDocument query = new BsonDocument();
			MongoCursor<Document> cursor = conn.find(query).skip(start).limit(limit).iterator();
			// parse result
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				RequestLog requestLog = new RequestLog();
				requestLog = MongoUtils.document2Bean(doc, requestLog);
				if (requestLog != null) {
					result.add(requestLog);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new MongoOptException("[mongo:list]", e.getCause());
		}
		return result;
	}*/



	/**
	 * mongoTemplate
	 * @param mongoTemplate
	 */
	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * 新增日志
	 *
	 * @param requestLog
	 */
	public void saveLog(RequestLog requestLog) {
		try {
			mongoTemplate.save(requestLog, REQUEST_LOG_MODEL);
		} catch (Exception e) {
			logger.error("saveLog to mongo error", e);
		}
	}

}
