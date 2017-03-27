package com.tch;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void main(String[] args) 
	{
		//模拟Android客户端
		//要用到三个东东来做Post：HttpClient，httpPost，MultipartEntity
		HttpClient httpClient = new DefaultHttpClient(); 

		httpClient.getParams().setParameter( CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1 );

		HttpPost httpPost = new HttpPost( "http://localhost:8080/upload/chat/uploadFile" );

		MultipartEntity postEntity = new MultipartEntity();

		// 字符用StringBody
		String fileName = "2.jpg";

		ContentBody cbFileName;
		try {
			cbFileName = new StringBody( fileName );

			// 文件用FileBody，并指定文件类型
				
			File file = new File( "D:\\FTP\\2.jpg");
			ContentBody cbFileData = new FileBody( file, "image/jpg" );

			// 把上面创建的这些Body全部加到Entity里面去。
			// 注意他们的key，这些key在Struts2服务器端Action的代码里必须保持一致！！
			postEntity.addPart( "fileName", cbFileName );
			postEntity.addPart( "fileData", cbFileData );
	
			httpPost.setEntity( postEntity );
			// 下面这句话就把数据提交到服务器去了
			HttpResponse response = httpClient.execute( httpPost );
	
			// 打开response的数据流，就可以读取服务器端的回执数据
			InputStream in = response.getEntity().getContent();
	
			int count = 0;    
	        while (count == 0) {    
	        	count = Integer.parseInt(""+response.getEntity().getContentLength());//in.available();    
	        }    
	        byte[] bytes = new byte[count];    
	        int readCount = 0; // 已经成功读取的字节的个数    
	        while (readCount <= count) {    
	         if(readCount == count)
	        	 break;    
	         readCount += in.read(bytes, readCount, count - readCount);    
	        }    
	            
	        //转换成字符串    
	        String readContent= new String(bytes, 0, readCount);
	
			System.out.println(readContent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
