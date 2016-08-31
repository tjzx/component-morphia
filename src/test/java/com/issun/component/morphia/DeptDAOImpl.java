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
