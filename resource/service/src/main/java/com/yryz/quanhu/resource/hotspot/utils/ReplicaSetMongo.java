package com.yryz.quanhu.resource.hotspot.utils;

import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.yryz.common.context.Context;


//@Component
public class ReplicaSetMongo {
	
	@Value("${spring.data.mongodb.uri}")
	private String mongoURI;
	
	
	private static Logger logger = Logger.getLogger(ReplicaSetMongo.class);
	
	private Map<String, MongoDatabase> MONGO_DATABASE = new Hashtable<String, MongoDatabase>(); // jedis连接池
		
	private ReplicaSetMongo() throws Exception {
		init();
	}
    
    
    
	public void init() throws Exception {
		String sourceArr[] = mongoURI.split(",");
		for (int i = 0; i < sourceArr.length; i++)
		{
			String s = sourceArr[i];
			try {
				load(s);
			} catch (Exception e) {
				logger.error("Init Mongodb Pool(" + s + ") Failed! " + e.getMessage());
				throw new Exception(e);
			}
		}
	}
	
	private void load(String source) {
		MongoClientURI connectionString = new MongoClientURI(mongoURI);
	    MongoClient client = new MongoClient(connectionString);
	    MongoIterable<String> mongoIterable = client.listDatabaseNames();
	    MongoDatabase db = client.getDatabase(source);
	    
	    MONGO_DATABASE.put(source, db);
	    logger.info("Init Mongodb databse connection " + source + " Over");
	}
	
	public MongoDatabase getDB(String db) {
		return MONGO_DATABASE.get(db);
	}
	
	public MongoCollection<Document> getCollection(String db, String collectionName) {
		return MONGO_DATABASE.get(db).getCollection(collectionName);
	}
	
	
}
