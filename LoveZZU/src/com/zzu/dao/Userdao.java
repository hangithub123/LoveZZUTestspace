package com.zzu.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.zzu.entity.User;
import com.zzu.entity.UserInfo;

@Repository(value="userDao")
public class Userdao implements Dao{

	@Resource(name="hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	   

	boolean isSuccessful=false;
	String[] userinfoDatas=new String[9];
	
	//保存用户并返回id
	public <T> Serializable save(T t) {
		Serializable id=null;
		hibernateTemplate.clear();
		 id=hibernateTemplate.save(t);
		 hibernateTemplate.flush();
		return id;
	}
	//查询用户并返回用户对象
	public <T> List<T> query(String sql, String values) {
		List<T> list = new ArrayList<T>();
		hibernateTemplate.clear();
		hibernateTemplate.flush();
		list.clear();
	
		 list=(List<T>) hibernateTemplate.find(sql, values);
		
		hibernateTemplate.flush();
		
		return list;
	}
	//更新操作
	@Override
	public <T> T update(T t) {
		hibernateTemplate.clear();
		hibernateTemplate.update(t);
		hibernateTemplate.flush();
		return null;
	}


	
	
	
}
