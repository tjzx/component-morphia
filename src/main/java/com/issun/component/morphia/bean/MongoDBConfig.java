package com.issun.component.morphia.bean;


/**
 * MongoDB配置对象
 * 
 * @author ZHe
 */
public class MongoDBConfig {
	
    // ------------------------------------------------------- Static Variables

	/**
	 * 默认的端口号
	 */
	public final static String DEFAULT_PORT="27017";
	
	/**
	 * 默认SOCKET超时时间
	 */
	public final static int DEFAULT_SOCKEY_TIMEOUT = 2000;
	
	
    // ------------------------------------------------------- Instance Variables
	
	
	/**
	 * 别名，可通过此别名获取 连接池 对象
	 */
	private String alias;
	
	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * 端口号
	 */
	private String port;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 数据库名称
	 */
	private String dbname;
	
	/**
	 * 是否需要授权
	 */
	private boolean needAuth;
	
	
	/**
	 * MongoDB连接池配置
	 */
	private MongoDBOptions options;
	
	
    // ------------------------------------------------------- Public Methods
	
	/**
	 * 返回配置的别名
	 * @return
	 */
	public String getAlias() {
		return alias;
	}
	/**
	 * 设置配置的别名
	 * @param alias
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * 返回数据库的地址
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置数据库的地址
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 返回数据库的端口号
	 * @return
	 */
	public String getPort() {
		return port;
	}
	/**
	 * 设置数据库的端口号
	 * @param port
	 */
	public void setPort(String port) {
		this.port = port;
	}
	
	/**
	 * 返回数据库的用户名
	 * @return
	 */
	public String getUsername() {
		return username;
	}
    /**
     * 设置数据库的用户名
     * @param username
     */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 返回数据库的密码
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置数据库的密码
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 返回数据库名称
	 * @return
	 */
	public String getDbname() {
		return dbname;
	}
	/**
	 * 设置数据库名称
	 * @param dbname
	 */
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	
	/**
	 * 获取MongoDB的连接池
	 * @return
	 */
	public MongoDBOptions getOptions() {
		return options;
	}
	
	/**
	 * 设置MongoDB的连接池
	 * @param options
	 */
	public void setOptions(MongoDBOptions options) {
		this.options = options;
	}
	
	public boolean isNeedAuth() {
		return needAuth;
	}
	
	public void setNeedAuth(boolean needAuth) {
		this.needAuth = needAuth;
	}
	
}
