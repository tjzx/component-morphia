package com.issun.component.morphia;

import org.mongodb.morphia.Datastore;

import com.issun.component.morphia.bean.MongoDBConfig;
import com.issun.component.morphia.interfaces.MongoDbConfigSource;
import com.issun.component.morphia.utils.MongoDbUtil;

/**
 * MongoDB链接池(用于datastroe的获取)
 */
public class MongoDBPool {
	/**
	 * 内容维持的单例
	 */
	private static MongoDBPool mongoDBPool;
	/**
	 * MongoDbConfig源
	 */
	private MongoDbConfigSource mongoDbConfigSource;
	
	
	private MongoDBPool(MongoDbConfigSource mongoDbConfigSource){
		this.mongoDbConfigSource = mongoDbConfigSource;
	}

	public static MongoDBPool getInstance(MongoDbConfigSource mongoDbConfigSource) {
		if(null == mongoDBPool){
			mongoDBPool = new MongoDBPool(mongoDbConfigSource);
		}
		return mongoDBPool;
	}

	public Datastore getDatastore(String datastoreAlias) {
		MongoDBConfig mongoDbConfig =  mongoDbConfigSource.get(datastoreAlias);
		return MongoDbUtil.createDatastore(mongoDbConfig);
	}

}
