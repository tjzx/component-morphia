package com.issun.component.morphia.bean;

import org.mongodb.morphia.annotations.Entity;

@Entity(noClassnameStored = true)
public class Dept extends BeanEntity{
	
	private String name;
	private MongoDBConfig config;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MongoDBConfig getConfig() {
		return config;
	}
	public void setConfig(MongoDBConfig config) {
		this.config = config;
	}
}
