package com.zzu.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zzu.dao.Dao;
import com.zzu.dao.Userdao;
import com.zzu.entity.User;
import com.zzu.entity.UserInfo;
@Transactional
@Service(value="userService")
public class UserService {

	@Resource(name="userDao")
	private Dao dao;

	boolean isSuccessful=false;
	String[] userinfoDatas=new String[9];
	String sql=null;
	String values;
	Serializable id = null;
//保存用户操作
	public boolean save(User user){
		
		id=null;//初始化id
		sql="from User where phone=?";
		values=user.getPhone();
		List<User> list=dao.query(sql, values);
		if(list.isEmpty()){
			id=this.dao.save(user);
			 
		}else{System.out.println("用户:"+user.getPhone()+"已存在");}
			if(id !=null){
				isSuccessful=true;
				System.out.println("新用户已保存到数据库!");
			}else{
			System.out.println("User对象为空!!!");
			}
		
		return isSuccessful;
		
	}

//查询用户操作
	public boolean query(User user) {
		
		sql="from User where phone=?";
		values=user.getPhone();
		List<User> list=this.dao.query(sql, values);
		if(!list.isEmpty()){
			isSuccessful=true;
			System.out.println("用户:"+user.getPhone()+"存在");
		}else{System.out.println("用户:"+user.getPhone()+"不存在");
		}
		
		return isSuccessful;
	}
//保存或修改用户信息
	public boolean saveOrupdate(UserInfo userinfo) {
		
		id = null;//初始化id
			//得到User对象
		sql="from User where phone=?";
		values=userinfo.getPhone();
		List<User> list=this.dao.query(sql, values);
			
			for (User user : list) {
				//得到User对象的id
			//根据id查询关联的userinfo表的外键为ul_id=id的对象是否存在
				sql="from UserInfo where ul_id=?";
				values=user.getUid().toString();
				System.out.println("Uid:"+values+sql+","+values);
				List<UserInfo> infolist=dao.query(sql, values);
				//如果存在测执行更新操作
				if(infolist.size() ==1){
					 for (UserInfo userInfo2 : infolist) {
						String phone=userInfo2.getPhone();
						String nickname=userInfo2.getNickname();
						String gender=userInfo2.getGender();
						String hometown=userInfo2.getHometown();
						String image=userInfo2.getImageUrl();
						String codeurl=userInfo2.getQr_codeUrl();
						String departments=userInfo2.getDepartments();
						String academy=userInfo2.getAcademy();
						String professional=userInfo2.getProfessional();				 
						if(!userinfo.getPhone().equals("")){phone=userinfo.getPhone();}
						if(!userinfo.getNickname().equals("")){nickname=userinfo.getNickname();}
						if(!userinfo.getGender().equals("")){gender=userinfo.getGender();}
						if(!userinfo.getHometown().equals("")){hometown=userinfo.getHometown();}
						if(!userinfo.getImageUrl().equals("")){image=userinfo.getImageUrl();}
						if(!userinfo.getQr_codeUrl().equals("")){codeurl=userinfo.getQr_codeUrl();}
						if(!userinfo.getDepartments().equals("")){departments=userinfo.getDepartments();}
						if(!userinfo.getAcademy().equals("")){academy=userinfo.getAcademy();}
						if(!userinfo.getProfessional().equals("")){professional=userinfo.getProfessional();}
					    userInfo2.setPhone(phone);
			    	    userInfo2.setNickname(nickname);
					    userInfo2.setGender(gender);
					    userInfo2.setHometown(hometown);
					    userInfo2.setImageUrl(image);
			            userInfo2.setQr_codeUrl(codeurl);
			     	    userInfo2.setDepartments(departments);
					    userInfo2.setAcademy(academy);
					    userInfo2.setProfessional(professional);
					    this.dao.update(userInfo2);
					    System.out.println("用户信息已存在，进行修改操作");
						id=1;
					}	
				//如果不存在用户信息则执行级联保存操作	
				}else{

					//User对象中添加UserInfo对象
					user.getSetuserinfo().add(userinfo);
					//UserInfo对象中添加User对象
					userinfo.setUser(user);
					id=this.dao.save(user);
					System.out.println("用户信息不存在，进行保存操作");
				}	
				
			}

			if(id !=null){
				isSuccessful=true;
				System.out.println("个人信息已保存到数据库!");
			}else{
			System.out.println("Service层，UserInfo对象为空!!!");
			}
		
		return isSuccessful;
		
		
	}
//查询并返回用户信息
		public String[] query(UserInfo userinfo) {
			Integer Uid=null;
			//根据phone查询User对象
			sql="from User where phone=?";
			values=userinfo.getPhone();
			List<User> list=this.dao.query(sql, values);
			
			for (User user : list) {
				Uid=user.getUid();
			}
			if(Uid !=null){
				//根据User的id查询关联的userinfo表的外键为ul_id=id的对象是否存在
				sql="from UserInfo where ul_id=?";
				values=Uid.toString();
				System.out.println("Uid:"+values+sql+","+values);
				List<UserInfo> list2=dao.query(sql, values);
			
			for (UserInfo userInfo2 : list2) {
				
				userinfoDatas[0]=userInfo2.getImageUrl();
				userinfoDatas[1]=userInfo2.getNickname();
				userinfoDatas[2]=userInfo2.getPhone();
				userinfoDatas[3]=userInfo2.getQr_codeUrl();
				userinfoDatas[4]=userInfo2.getGender();
				userinfoDatas[5]=userInfo2.getHometown();
				userinfoDatas[6]=userInfo2.getAcademy();
				userinfoDatas[7]=userInfo2.getDepartments();
				userinfoDatas[8]=userInfo2.getProfessional();
			}
			}else{
				System.out.println("根据用户ID:"+Uid+"查不到UserInfo信息 ,并返回请求的phone");
				userinfoDatas[2]=userinfo.getPhone();
			}
		
			return userinfoDatas;
			
		}
}
