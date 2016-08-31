对Morphia组件的封装旨在提供一套对Bean的CURD模板操作，这样可以加快开发速度，以及便于维护。

**约束说明：** 这套CURD模板是基于Bean的`unid`(映射到默认的MongoDb主键`_id`)属性进行增删改查的，故要求Bean继承`BeanEntity`抽象类。例如：

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


# 使用说明：

## 配置文件

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

## DAO规范

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
需要说明的是`DATASTORE_ALIAS`值是对应到mongodb.xml配置文件里面对应alias值的MongoDb配置.

## 额外说明

`MongoDbBaseTemplate<T>`抽象类提供相应的CURD方法：

- 增 ----》`commonSave`
- 删 ----》`commonDelete`(通过Unid进行删除)
- 改 ----》`commonUpdate`
- 查 ----》`commonGet`(通过Unid进行获取)


