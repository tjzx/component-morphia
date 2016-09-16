package com.issun.component.morphia.bean;

import org.mongodb.morphia.annotations.Id;

/**
 * 存储Bean对象的基类
 * 
 * @author ZHe
 */
public abstract class BeanEntity {
	
    // ------------------------------------------------------- Static Variables
	
	/**
	 * 主键名称
	 */
	public static String UNID_FIELD = "unid";
	
    // ------------------------------------------------------- Instance Variables
	
	/**
	 * 主键
	 */
	@Id
	protected String unid;
	
    // ------------------------------------------------------- Public Methods

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}
	
	
}
