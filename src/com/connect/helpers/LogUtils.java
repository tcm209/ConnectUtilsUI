package com.connect.helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils {

	/*
	 * 写入日志
	 */
	public static void writeLog(Object content){
		
			String url=System.getProperty("user.dir");
			File file=new File(url+"\\logs");
			if(!file.exists())    
			{    
			    file.mkdirs();    
			}
			
			BufferedWriter bw = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String logName=sdf.format(new Date());
				bw = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(file+"\\"+logName+".txt", true)));
				SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				bw.write("文档执行的日期是："+sdfTime.format(new Date())+"："+content);
				bw.flush();
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
			   if(null!=bw){
			    try {
			     bw.close();
			    } catch (IOException e) {
			     System.out.println("流操作异常");
			    }
			   }
			}
			  
		
		
	}
}
