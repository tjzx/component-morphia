package com.issun.component.morphia;

import java.util.List;

import com.issun.component.morphia.bean.Dept;

public interface DeptDAO {

	boolean save(Dept dept);
	
	boolean update(Dept dept);
	
	boolean delete(String unid);
	
	Dept get(String unid);
	
	List<Dept> getDeptByName(String name);

}
