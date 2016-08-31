# 使用说明：

## Bean对象

要存储到MongoDb的Bean对象要继承BeanEntity对象(即：提供"unid"作为存储主键，并进行MongoDB默认主键"_id"的映射)


```java
package com.issun.component.morphia;

import com.issun.component.morphia.bean.Dept;

public class DeptDAOImpl extends MongoDbBaseTemplate<Dept> implements DeptDAO {
	
	private static String DATASTORE_ALIAS ="orgDb";
	
	public DeptDAOImpl(){
		
		this.setBeanClass(Dept.class);
		this.initDatastore(DATASTORE_ALIAS);
	}

	public boolean save(Dept dept) {
		return this.commonSave(dept);
	}
	
	public boolean update(Dept dept){
		return this.commonUpdate(dept);
	}

	@Override
	public boolean delete(String unid) {
		return this.commonDelete(unid);
	}

	@Override
	public Dept get(String unid) {
		return this.commonGet(unid);
	}
	
	

}

```

## MongoDB配置

提供一份配置文件mongodb.xml,例如

```xml
<?xml version="1.0" encoding="UTF-8"?>
<ns1:mongo-config xmlns:ns1="http://www.ccip.com/mongo-config"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.ccip.com/mongo-config mongodb.xsd">
	<mongo>
		<alias>orgDb</alias>
		<address>127.0.0.1</address>
		<port>27017</port>
		<dbname>orgDbb</dbname>
		<username />
		<password />
		<connectionsPerHost>500</connectionsPerHost>
		<threadsAllowedToBlockForConnectionMultiplier>5000</threadsAllowedToBlockForConnectionMultiplier>
		<maxWaitTime>5000</maxWaitTime>
		<connectTimeout>15000</connectTimeout>
		<socketTimeout>20000</socketTimeout>
		<autoConnectRetry>true</autoConnectRetry>
		<socketKeepAlive>true</socketKeepAlive>
	</mongo>
</ns1:mongo-config>

```

## DAO

DAO的实现类要继承MongoDbBaseTemplate<T>抽象类，并在构造函数中进行相关值的设置及初始化，例如


```java
package com.issun.component.morphia;

import com.issun.component.morphia.bean.Dept;

public class DeptDAOImpl extends MongoDbBaseTemplate<Dept> implements DeptDAO {
	
	private static String DATASTORE_ALIAS ="orgDb";
	
	public DeptDAOImpl(){
		
		this.setBeanClass(Dept.class);
		this.initDatastore(DATASTORE_ALIAS);
	}

	public boolean save(Dept dept) {
		return this.commonSave(dept);
	}
	
	public boolean update(Dept dept){
		return this.commonUpdate(dept);
	}

	@Override
	public boolean delete(String unid) {
		return this.commonDelete(unid);
	}

	@Override
	public Dept get(String unid) {
		return this.commonGet(unid);
	}
	
	

}

```


## 额外说明

`MongoDbBaseTemplate`抽象类提供相应的CURD方法：

- 增 ----》`commonSave`
- 删 ----》`commonDelete`(通过Unid进行删除)
- 改 ----》`commonUpdate`
- 查 ----》`commonGet`(通过Unid进行获取)



