package com.tch.action;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.tch.util.FileCopyUtil;

public class FileUploadAction 
{
	// 注意这些变量名，看见了没？ 和Struts2的FormBean是一样的。
	// 要和客户端添加字段时的关键字保持一致！！
	private String fileName = null;
	private String fileData = null;
	//针对每个字段的关键字，下面的这些Set方法必须一个不少，否则你什么也得不到
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setFileData(String fileData) {
		this.fileData = fileData;
	}

	public String upload()
	{
		String webappPath = findServerPath();
		
		/*几种获取tomcat下webapps路径的方法:
		一、web项目根目录获取D:\apache-tomcat-6.0.36\webapps\XXXX项目名\
		String path = ServletActionContext.getServletContext().getRealPath("/");
		
		二、项目相对路径/XXXX项目名
		String appPath = ServletActionContext.getServletContext().getContextPath();
		
		三、D:\apache-tomcat-6.0.36\bin把bin文件夹变到 webapps文件里面
		nowpath=System.getProperty("user.dir");  
		tempdir=nowpath.replace("bin", "webapps");   

		四、获取类全路径，截取见findServerPath
		*/
		
		//读取web.xml中的设置：保存上传图片的路径
		String chatFile_path = ServletActionContext.getServletContext().getInitParameter("UPLOAD_CHAT_FILE_PATH");
		FileCopyUtil util = new FileCopyUtil();
		String[] array = fileName.split("\\.");
		//取得文件类型如jpg、png
		String fileType = array[array.length-1];
		
		//拼接得到图片保证的全路径
		String destDir = webappPath + chatFile_path;
		//先获取HttpServletResponse，保存返回信息
	    HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get( ServletActionContext.HTTP_RESPONSE );

	    //打开数据流，把要给客户端返回的数据写进数据流
	    OutputStream writer = null;

	    try{
	    	//此时debug调试，可看到fileData为保存的缓存文件的绝对路径。格式为：X:/xxx/xxx/xxx/xxx_xxx_xxx.tmp
	    	//上传任务已完成，只需要调用工具类，将临时文件拷贝到指定目录，并修改后缀名
	    	String newFilePath = util.copyFile(fileType, fileData, destDir);
	    	
	    	int index = newFilePath.indexOf("webapps");
	    	String path = newFilePath.substring(index+7);
	        writer = response.getOutputStream();

	        //将需要的信息，写入response，供客户端使用。
	        writer.write(path.getBytes());
	    } 
	    catch( Exception e ) {
	    	e.printStackTrace();
			
	    } 
	    finally{
	        if( writer != null ) {
	            try {
	                writer.close();
	            } 
	            catch( Exception e ) {
	            }
	        }
	    }
	    // 这里不用再返回其他字符串了。
	    // 客户端接收的不是这里返回的数据，这个是显示结果网页才会用到的返回值。

	    return null;

	}

	
	private String findServerPath()
	{
		
        String classPath = this.getClass().getClassLoader().getResource("/").getPath();  
        try {  
            classPath =URLDecoder.decode(classPath, "UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        
        int i = classPath.indexOf("webapps");
        String path = classPath.substring(1,i+7);
        return path;  
    }  

}
