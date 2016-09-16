package com.issun.component.morphia;

import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.Test;

import com.issun.component.morphia.bean.Dept;

public class DeptDAOTest {
	
	private DeptDAO deptDAO;
	
	@Before
	public void init(){
		deptDAO = new DeptDAOImpl();
	} 
	
	
	@Test
	public void testDelete(){
		deptDAO.delete("8989");
	}
	
	//@Test
	public void testSave(){
		
		Dept dept = new Dept();
		dept.setUnid("8989");
		dept.setName("事业部");
		
		deptDAO.save(dept);
		
	}
	
	//@Test
	public void testUpdate(){
		Dept dept = new Dept();
		dept.setUnid("8989");
		dept.setName("事");
		deptDAO.update(dept);
	}
	
	//@Test
	public void testGet(){
		Dept dept = deptDAO.get("8989");
		System.out.println(JSONObject.fromObject(dept));
	}
	
	
}
