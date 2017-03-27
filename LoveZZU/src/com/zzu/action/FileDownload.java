package com.zzu.action;

import java.io.InputStream;


import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
@Component(value="filedownload")
public class FileDownload extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String inputPath;

	public InputStream getTargetFile()throws Exception {
		
		return ServletActionContext.getServletContext().getResourceAsStream(inputPath);
	}

	public void setInputPath(String value) {
		inputPath = value;
	}


}
