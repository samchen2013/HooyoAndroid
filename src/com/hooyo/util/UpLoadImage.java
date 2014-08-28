package com.hooyo.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class UpLoadImage {
	public static void UpLoad(String srcUrl,String fileName )
	{
		try{  
//            srcUrl = "/sdcard/"; //路径  
//            fileName = "aa.jpg";  //文件名  			
            FileInputStream fis = new FileInputStream(srcUrl);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            byte[] buffer = new byte[1024];  
            int count = 0;  
            while((count = fis.read(buffer)) >= 0){  
                baos.write(buffer, 0, count);  
            }  
            String uploadBuffer = new String(Base64Coder.encode(baos.toByteArray()));  //进行Base64编码  
            String methodName = "UpLoadImage";  
            Map<String, String> params=new HashMap<String, String>();
            params.put("image", uploadBuffer);
            params.put("id", fileName);
            WebServiceHelper.connectWebService(methodName, params);   //调用webservice  
            Log.i("connectWebService", "start");  
            fis.close();  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
	}

}
