package com.issun.component.morphia.bean;

import org.mongodb.morphia.annotations.Id;

/**
 * 存储Bean对象的基类
 */
public abstract class BeanEntity {
	
	public static String UNID_FIELD = "unid";
	
	/**
	 * 主键
	 */
	@Id
	protected String unid;

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}
	
	
}
