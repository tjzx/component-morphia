package com.issun.component.morphia.bean.type;

/**
 * 输出数据类型
 * 
 * @author ZHe
 */
public enum OutputType {
	
	
	STRING(0),
	INT(1),
	BOOLEAN(2);
	
	private int type;
	
	private OutputType(int type) {
		this.type = type;
	}
	
    // ------------------------------------------------------- Public Methods
	
	public int getType(){
		return this.type;
	}

}
