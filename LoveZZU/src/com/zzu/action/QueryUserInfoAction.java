package com.zzu.action;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zzu.entity.UserInfo;
import com.zzu.service.UserService;

import net.sf.json.JSONObject;

@Component(value="queryuserinfoAction")
public class QueryUserInfoAction extends ActionSupport implements ModelDriven<UserInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] userinfoDatas=new String[9];
	//注入service和实体类属性
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
		if(!userinfo.getPhone().equals("")){
		userinfoDatas=userService.query(userinfo);
		
		//返回数据
		HttpServletResponse response= ServletActionContext.getResponse();
		response.setHeader("Content-type", "text/html;charset=UTF-8");   
		response.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();   
    	 json.put("imageUrl", userinfoDatas[0]);
    	 json.put("nickname", userinfoDatas[1]);
    	 json.put("phone", userinfoDatas[2]);
    	 json.put("Qr_codeUrl", userinfoDatas[3]);
    	 json.put("gender", userinfoDatas[4]);
    	 json.put("hometown", userinfoDatas[5]);
    	 json.put("academy", userinfoDatas[6]);
    	 json.put("departments",userinfoDatas[7] );
    	 json.put("professional", userinfoDatas[8]);
    	 
    	 
          System.out.println("QueryUserInfoAction层json"+json);
        
     PrintWriter out=response.getWriter();    	
    	out.println(json);
    	out.flush();
    	out.close();
		
		}else{ System.out.println("QueryuserinfoAction层,请求的phone为空");}
		
		return NONE;
	}
}
