# Morphia-Conponent

基于Morphia封装，简单、好用的MongoDB的CURD组件。

# Features

- 提供Model和MongoDB主键的映射
- 提供通用的MongoDB增删改查模板

# Quick Start

**1.提供一份MongoDB的配置文件mongodb.xml,放置于编译文件路径classes**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<mongo-config>
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
</mongo-config>

```

**2.存储对象继承BeanEntity(MongoDB存储的抽象类,以提供Model的`Unid`同MongoDB的`_id`的映射关系)**


```java
package com.issun.component.morphia.bean;

import org.mongodb.morphia.annotations.Entity;

@Entity(noClassnameStored = true)
public class Dept extends BeanEntity{
	
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
```

**3.Model的DAO实现类继承MongoDbBaseTemplate<T>**

MongoDbBaseTemplate<T>为MongoDB CURD的模板对象，提供相关的通用方法：

- 增 ----》`commonSave`
- 删 ----》`commonDelete`(通过Unid进行删除)
- 改 ----》`commonUpdate`
- 查 ----》`commonGet`(通过Unid进行获取)

但使用这些方法之前需要进行MongoDbBaseTemplate的初始化工作,`setBeanClass`&&`initDatastroe`.

eg.

``````java
package com.issun.component.morphia;

import java.util.List;

import org.mongodb.morphia.query.Query;

import com.issun.component.morphia.bean.Dept;

public class DeptDAOImpl extends MongoDbBaseTemplate<Dept> implements DeptDAO {
	
	private static String DATASTORE_ALIAS ="orgDb";
	
	public DeptDAOImpl(){
		
		//初始化MongoDb模板
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

	@Override
	public List<Dept> getDeptByName(String name) {
		
		Query<Dept> deptQuery = this.getDatastore().createQuery(Dept.class);
		deptQuery.field(Dept.NAME).equal(name);
		
		return deptQuery.asList();
	}
	
	

}

```

这里面的`DATASTORE_ALIAS`对应Mongodb.xml里面的alias。



