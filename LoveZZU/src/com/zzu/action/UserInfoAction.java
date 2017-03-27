package com.zzu.action;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import com.zzu.entity.UserInfo;
import com.zzu.service.UserService;

import net.sf.json.JSONObject;


@Transactional
@Component(value="userinfoAction")
@Scope(value="prototype")
public class UserInfoAction extends ActionSupport implements ModelDriven<UserInfo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//ע��service��ʵ��������
		@Resource(name="userService")
		private UserService userService;
		@Resource(name="userinfo")
		private UserInfo userinfo;
		
	@Override
	public UserInfo getModel() {
		// TODO Auto-generated method stub
		return userinfo;
	}
	@Override
	public String execute() throws Exception {
		boolean isSuccessful=false;
		System.out.println("ģ������"+userinfo.toString());
		
		if(!userinfo.equals("") && !userinfo.getPhone().equals("")){
			
			isSuccessful=userService.saveOrupdate(userinfo);
		
		}else{System.out.println("�ͻ��˴�����userinfo����Ϊ��");}
			//��������
			HttpServletResponse response= ServletActionContext.getResponse();
			response.setHeader("Content-type", "text/html;charset=UTF-8");   
			response.setCharacterEncoding("UTF-8");
			JSONObject json = new JSONObject();   
			
	    	 json.put("isSuccessful", isSuccessful);
	          System.out.println("UserInfoAction��json"+json);
	        
	     PrintWriter out=response.getWriter();    	
	    	out.println(json);
	    	out.flush();
	    	out.close();

		return NONE;
	}

	
}
