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
