package com.issun.component.morphia.bean;

import org.mongodb.morphia.annotations.Entity;

@Entity(noClassnameStored = true)
public class Dept extends BeanEntity{
	
	public static String NAME = "name";
	
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
