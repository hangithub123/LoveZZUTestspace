package com.tch.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileCopyUtil 
{
	
	public String copyFile(String fileType,String fileData,String destDir) throws IOException
	{
		Date date = new Date();
		String dirName = new SimpleDateFormat("yyyyMMdd").format(date);
		String newFileName = new SimpleDateFormat("yyyyMMddHHmmss_SSS").format(date);
		
		//destDir的值D:/apache-tomcat-6.0.36/webapps/chat/
		String dirPath = destDir+dirName;
		File dir = new File(dirPath);
		if(!dir.exists()  && !dir.isDirectory())
		{
			//目录不存在，创建目录
			dir.mkdir();
		}
		
		
		String newFilePath = dirPath+"/"+newFileName+"."+fileType;
		FileInputStream in=new FileInputStream(new File(fileData));
		FileOutputStream out=new FileOutputStream(newFilePath);
		int length=2097152;
		byte[] buffer=new byte[length];
		while(true){
			int ins=in.read(buffer);
			if(ins==-1){
				in.close();
				out.flush();
				out.close();
				return newFilePath;
			}else
				out.write(buffer,0,ins);
		}
		
		
	}

}
