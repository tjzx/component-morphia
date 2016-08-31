package com.issun.component.morphia.interfaces;

import com.issun.component.morphia.bean.MongoDBConfig;

/**
 * MongoDbConfig数据源接口
 */
public interface MongoDbConfigSource {

	/**
	 * 获取MongoDb配置对象
	 * @param alias
	 * @return MongoDBConfig
	 */
	MongoDBConfig get(String alias);
	
}
