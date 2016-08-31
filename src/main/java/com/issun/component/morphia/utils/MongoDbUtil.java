package com.issun.component.morphia.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.issun.component.morphia.bean.MongoDBConfig;
import com.issun.component.morphia.bean.MongoDBOptions;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;


/**
 * MongoDbConfig通用工具
 */
public class MongoDbUtil {

	public static Datastore createDatastore(MongoDBConfig mongoDbConfig) {
		
		
		// 添加集群的配置，可以设置多个IP地址多个端口号
		String[] addresses = mongoDbConfig.getAddress().split(",");
		String[] ports = mongoDbConfig.getPort().split(",");
		
		// 配置的ip地址与端口的个数需要能够相等
		if(addresses.length != ports.length || addresses.length < 1){
			//TODO 添加异常处理
		}
		
		MongoClient mongoClient = null;
		
		MongoCredential credential = null;
		if(mongoDbConfig.isNeedAuth()){// 添加验证票据
			credential = MongoCredential.createMongoCRCredential(mongoDbConfig.getUsername(), 
					mongoDbConfig.getDbname(), mongoDbConfig.getPassword().toCharArray());
		}
		
		MongoClientOptions options = toMongoOptions(mongoDbConfig.getOptions());
		if(addresses.length == 1){
			ServerAddress mongoServerAddress = new ServerAddress(mongoDbConfig.getAddress(), Integer.parseInt(mongoDbConfig.getPort()));
			
			if(null == credential){
				mongoClient = new MongoClient(mongoServerAddress, options);
			}else{
				mongoClient = new MongoClient(mongoServerAddress, Arrays.asList(credential), options);
			}
		}else{
			List<ServerAddress> listServerAddress = new ArrayList<ServerAddress>();
			for(int i = 0; i < addresses.length; i++){
				listServerAddress.add(new ServerAddress(addresses[i], Integer.parseInt(ports[i])));
			}
			if(null == credential){
				mongoClient = new MongoClient(listServerAddress, options);
			}else{
				mongoClient = new MongoClient(listServerAddress, Arrays.asList(credential), options);
			}
			mongoClient.setReadPreference(ReadPreference.secondary());
		}
		
		return createDatastore(mongoClient,mongoDbConfig.getDbname());
	}
	
	/**
	 * 转换为MongoDB的属性配置
	 * @param dbOptions 自定义的属性配置
	 * @return  MongoDB的属性配置  
	 */
	private static MongoClientOptions toMongoOptions(MongoDBOptions dbOptions){
		MongoClientOptions.Builder optionsBuilder = new MongoClientOptions.Builder();
		
		optionsBuilder.socketTimeout(dbOptions.getConnectTimeout());
		optionsBuilder.socketKeepAlive(dbOptions.isSocketKeepAlive());
		optionsBuilder.connectionsPerHost(dbOptions.getConnectionsPerHost());
		optionsBuilder.description(dbOptions.getDescription());
		optionsBuilder.connectTimeout(dbOptions.getConnectTimeout());
		// 读取类型
		String readPreference = dbOptions.getReadPreference();
		if(StringUtils.isEmpty(readPreference)){
			readPreference = "";
		}
		
		readPreference = readPreference.toLowerCase();
		
		// 就近读取
		if("nearest".equalsIgnoreCase(readPreference)){
			optionsBuilder.readPreference(ReadPreference.nearest());
		}else if("secondary".equals(readPreference)){// 从从属服务器读取
			optionsBuilder.readPreference(ReadPreference.secondary());
		}else if("primarypreferred".equals(readPreference)){// 优先从主服务器读取
			optionsBuilder.readPreference(ReadPreference.primaryPreferred());
		}else if("secondarypreferred".equals(readPreference)){// 优先从从服务器读取
			optionsBuilder.readPreference(ReadPreference.secondaryPreferred());
		}else{// 从主服务器读取
			optionsBuilder.readPreference(ReadPreference.primary());
		}
		optionsBuilder.cursorFinalizerEnabled(dbOptions.isCursorFinalizerEnabled());
		optionsBuilder.maxWaitTime(dbOptions.getMaxWaitTime());
		optionsBuilder.threadsAllowedToBlockForConnectionMultiplier(dbOptions.getThreadsAllowedToBlockForConnectionMultiplier());
		
		return optionsBuilder.build();
	}
	
	/**
	 * 根据Mongo，dbName，创建Datastore
	 * @param mongo
	 * @param dbName
	 * @return
	 */
	private static Datastore createDatastore(Mongo mongo,String dbName){
		Datastore datastore = null ;
		
		Morphia morphia = new Morphia();
		datastore = morphia.createDatastore((MongoClient) mongo,dbName);
		if (null == datastore){
			//TODO 异常处理
		}
		return datastore;
	}

}
