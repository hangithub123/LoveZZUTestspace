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
import com.zzu.entity.User;
import com.zzu.service.UserService;

import net.sf.json.JSONObject;


@Transactional
@Component(value="loginAction")
@Scope(value="prototype")
public class LoginAction extends ActionSupport implements ModelDriven<User>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//ע������
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="user")
	private User user;
	
	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}
	
	@Override
	public String execute() throws Exception {
		boolean isSuccessful=false;
		if(!user.equals("") && !user.getPhone().equals("")){
		System.out.println("LoginAction��User"+user.toString());
		isSuccessful=userService.query(user);
		}else{System.out.println("�ͻ��˴�����user����Ϊ��");}
	//��������
		HttpServletResponse response= ServletActionContext.getResponse();
		//������ķ��س��֣����ַ��������û�м�response.setCharacterEncoding("UTF-8");��仰��
      //������ص������ǡ����С��������룬˵��������Ľ������⣬Ӧ�ü�����Ƿ�����response.setHeader("Content-type", "text/html;charset=UTF-8");��仰
		response.setHeader("Content-type", "text/html;charset=UTF-8");   
		response.setCharacterEncoding("UTF-8"); 
		JSONObject json = new JSONObject();   
    	 json.put("isSuccessful", isSuccessful);    	
          System.out.println("LoginAction��json"+json);
        
         PrintWriter out=response.getWriter();    	
    	   out.println(json);
    	   out.flush();
    	   out.close();

		return NONE;
	}
	
	


}
