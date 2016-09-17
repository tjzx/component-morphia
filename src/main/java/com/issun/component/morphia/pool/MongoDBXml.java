package com.issun.component.morphia.pool;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.issun.component.morphia.bean.MongoDBConfig;
import com.issun.component.morphia.bean.MongoDBOptions;
import com.issun.component.morphia.bean.type.OutputType;

/**
 * Xml的MongoDbConfig数据源
 * 
 * @author ZHe
 */
public class MongoDBXml{

	
    // ------------------------------------------------------- Static Variables
	
	/**
	 * classes所在路径
	 */
	private static final String CLASSLOADER_PATH = Thread.currentThread().getContextClassLoader()
			.getResource(".")
			.getPath();
	
	/**
	 * mongodb配置文件名
	 */
	private static final String CONFIG_NAME = "mongodb.xml";
	
	/**
	 * mongodb配置文件路径
	 */
	private static  final String CONFIG_PATH = CLASSLOADER_PATH;
	
	/**
	 * MongoDB配置对象的Map
	 * 格式为：datastoreAlias:MongoDBConfig
	 */
	private static Map<String,MongoDBConfig> mongoDBConfigMap = new HashMap<String, MongoDBConfig>();
	
    // ----------------------------------------------------- Static Initializer
	
	static {
		String mongoXmlFileName = 
				CONFIG_PATH+File.separator+CONFIG_NAME;
		loadXml(mongoXmlFileName);
	}
	
    // ------------------------------------------------------- public Methods
	
	/**
	 * 获取对应alias的MongoDBConfig配置对象
	 */
	public static MongoDBConfig get(String alias) {
		return mongoDBConfigMap.get(alias);
	}
	
	/**
	 * 重新加载MongoDB的XML配置文件
	 * @return 
	 */
	public static void reloadXml(String mongoXmlFileName){
		mongoDBConfigMap.clear();
		loadXml(mongoXmlFileName);
	}
	
	
    // ------------------------------------------------------- private Methods
	
	
	/**
	 * 读取MongoDb配置文件
	 */
	private static void loadXml(String mongoXmlFileName) {
		
		File mongoXml = new File(mongoXmlFileName);
		if(!mongoXml.exists()){
			return;
		}
		
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(mongoXml);
			Element rootElement = document.getRootElement();
			List<?> nodes = rootElement.selectNodes("mongo");
			
			for(int i =0 ;i < nodes.size();i++){
				MongoDBConfig mongodbConfig = new MongoDBConfig();
				Element element = (Element) nodes.get(i);
				
				String alias = (String)getNodeVal(element.selectSingleNode("alias"),
						OutputType.STRING);
				if(!StringUtils.isEmpty(alias)){
					mongodbConfig.setAlias(alias);
				}
				
				String address = (String)getNodeVal(element.selectSingleNode("address"),
						OutputType.STRING);
				if(!StringUtils.isEmpty(address)){
					mongodbConfig.setAddress(address);
				}

				String port = (String)getNodeVal(element.selectSingleNode("port"),
						OutputType.STRING);
				if(StringUtils.isEmpty(port)){
					mongodbConfig.setPort(MongoDBConfig.DEFAULT_PORT);
				}else{
					mongodbConfig.setPort(port);
				}
				
				String username = (String)getNodeVal(element.selectSingleNode("username"),
						OutputType.STRING);
				if(!StringUtils.isEmpty(username)){
					mongodbConfig.setUsername(username);
				}
				
				String password = (String)getNodeVal(element.selectSingleNode("password"),
						OutputType.STRING);
				if(!StringUtils.isEmpty(password)){
					mongodbConfig.setPassword(password);
				}
				
				String dbname = (String)getNodeVal(element.selectSingleNode("dbname"),
						OutputType.STRING);
				if(!StringUtils.isEmpty(dbname)){
					mongodbConfig.setDbname(dbname);
				}
				
				Boolean needAuth = (Boolean)getNodeVal(element.selectSingleNode("needAuth"),
						OutputType.BOOLEAN);
				if(null != needAuth){
					mongodbConfig.setNeedAuth(needAuth.booleanValue());
				}
				
				
				//增加MongoDB连接池配置
				MongoDBOptions options = new MongoDBOptions();
	
				//每个主机的连接数
				Integer connectionsPerHost = (Integer)getNodeVal(element.selectSingleNode("connectionsPerHost"),
						OutputType.INT);
				if(null != connectionsPerHost){
					options.setConnectionsPerHost(connectionsPerHost.intValue());
				}
				
				//线程队列数
				Integer threadsAllowedToBlockForConnectionMultiplier = (Integer)getNodeVal(element.selectSingleNode("threadsAllowedToBlockForConnectionMultiplier"),
						OutputType.INT);
				if(null != threadsAllowedToBlockForConnectionMultiplier){
					options.setThreadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier.intValue());
				}
				//最大等待连接的线程阻塞时间
				Integer maxWaitTime = (Integer)getNodeVal(element.selectSingleNode("maxWaitTime"),
						OutputType.INT);
				if(null != maxWaitTime){
					options.setMaxWaitTime(maxWaitTime.intValue());
				}
				
				//连接超时的毫秒
				Integer connectTimeout = (Integer)getNodeVal(element.selectSingleNode("connectTimeout"),
						OutputType.INT);
				if(null != connectTimeout){
					options.setConnectTimeout(connectTimeout.intValue());
				}
				
				//socket超时
				Integer socketTimeout = (Integer)getNodeVal(element.selectSingleNode("socketTimeout"),
						OutputType.INT);
				if(null == socketTimeout){
					options.setSocketTimeout(MongoDBConfig.DEFAULT_SOCKEY_TIMEOUT);
				}else{
					options.setConnectTimeout(socketTimeout.intValue());
				}
				
				//这个控制是否在一个连接时，系统会自动重试
				Boolean autoConnectRetry = (Boolean)getNodeVal(element.selectSingleNode("autoConnectRetry"),
						OutputType.BOOLEAN);
				if(null != autoConnectRetry){
					options.setAutoConnectRetry(autoConnectRetry.booleanValue());
				}
				
				
				//socket是否保持活动
				Boolean socketKeepAlive = (Boolean)getNodeVal(element.selectSingleNode("socketKeepAlive"),
						OutputType.BOOLEAN);
				if(null != socketKeepAlive){
					options.setSocketKeepAlive(socketKeepAlive.booleanValue());
				}
				
				mongodbConfig.setOptions(options);
				mongoDBConfigMap.put(alias, mongodbConfig);
			}
		
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 获取节点值
	 * @param <T>
	 * @param Node 节点
	 * @param OutputType 节点值的输出类型
	 * @return T
	 */
	private static <T> T getNodeVal(Node node, OutputType outputType) {
		T result = null;
		
		String nodeText = ""; 
		if(null != node){
			nodeText = node.getText();
		}
		switch(outputType){
		case STRING:
			result = (T)nodeText;
			break;
		case INT:{
				if(!StringUtils.isEmpty(nodeText)){
					result = (T)new Integer(Integer.parseInt(nodeText));
				}
			};
			break;
		case BOOLEAN:{
				if(!StringUtils.isEmpty(nodeText)){
					result = (T)new Boolean(Boolean.parseBoolean(nodeText));
				}
			};
			break;
			
		}
		
		return result;
	}

}



