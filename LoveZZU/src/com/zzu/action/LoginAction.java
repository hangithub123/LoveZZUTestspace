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

	//注入属性
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
		System.out.println("LoginAction层User"+user.toString());
		isSuccessful=userService.query(user);
		}else{System.out.println("客户端传来的user数据为空");}
	//返回数据
		HttpServletResponse response= ServletActionContext.getResponse();
		//如果中文返回出现？？字符，这表明没有加response.setCharacterEncoding("UTF-8");这句话。
      //如果返回的中文是“烇湫”这种乱码，说明浏览器的解析问题，应该检查下是否忘加response.setHeader("Content-type", "text/html;charset=UTF-8");这句话
		response.setHeader("Content-type", "text/html;charset=UTF-8");   
		response.setCharacterEncoding("UTF-8"); 
		JSONObject json = new JSONObject();   
    	 json.put("isSuccessful", isSuccessful);    	
          System.out.println("LoginAction层json"+json);
        
         PrintWriter out=response.getWriter();    	
    	   out.println(json);
    	   out.flush();
    	   out.close();

		return NONE;
	}
	
	


}
