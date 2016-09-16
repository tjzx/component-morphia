package com.issun.component.morphia.pool;

import org.mongodb.morphia.Datastore;

import com.issun.component.morphia.bean.MongoDBConfig;
import com.issun.component.morphia.util.MongoDbUtil;

/**
 * Datastore连接池
 * 提供Datastore的获取
 * 
 * @author ZHe
 *
 */
public class DatastorePool {
	
    // ------------------------------------------------------- Static Variables
	
	private static DatastorePool datastorePool;
	
	
    // ------------------------------------------------------- Private Methods
	
	/**
	 * Single Instance Design
	 */
	private DatastorePool(){
		
	}
	
    // ------------------------------------------------------- Public Methods

	public static DatastorePool getInstance() {
		if(null == datastorePool){
			datastorePool = new DatastorePool();
		}
		return datastorePool;
	}

	/**
	 * 获取对应datastroeAlias的Datastore对象
	 * @param datastoreAlias
	 * @return Datastore
	 */
	public Datastore getDatastore(String datastoreAlias) {
		MongoDBConfig mongoDbConfig =  MongoDBXml.get(datastoreAlias);
		return MongoDbUtil.createDatastore(mongoDbConfig);
	}

}
