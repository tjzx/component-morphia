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
