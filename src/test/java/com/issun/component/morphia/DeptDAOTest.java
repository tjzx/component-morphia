package com.issun.component.morphia;

import java.util.List;

import net.sf.json.JSONArray;
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
	public void testSave(){
		
		Dept dept = new Dept();
		dept.setUnid("8989");
		dept.setName("事业部");
		
		deptDAO.save(dept);
	}
	
	
	@Test
	public void testGet(){
		Dept dept = deptDAO.get("8989");
		System.out.println(JSONObject.fromObject(dept));
	}
	
	@Test
	public void testGetByName(){
		List<Dept> depts = deptDAO.getDeptByName("事业部");
		System.out.println(JSONArray.fromObject(depts));
	}
	
	
	@Test
	public void testUpdate(){
		Dept dept = new Dept();
		dept.setUnid("8989");
		dept.setName("事");
		deptDAO.update(dept);
	}
	
	@Test
	public void testDelete(){
		deptDAO.delete("8989");
	}
	
	
}
