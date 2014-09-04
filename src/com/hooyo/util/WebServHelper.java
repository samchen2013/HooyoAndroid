package com.hooyo.util;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xmlpull.v1.XmlPullParser;

import android.util.Log;
import android.util.Xml;
import android.widget.Toast;


public class WebServHelper {

//	public String getService(InputStream inStream, String mobile) throws Exception
	public String getService(String soapfile, Map<String, String> params) throws Exception
	{
//		 "helloworldsoap.xml"
		InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(soapfile); 
		 
		String soap = readSoapFile(inStream, params);
		byte[] data = soap.getBytes();
		// Post
//		URL url = new URL("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx");
		
		URL url = new URL("http://192.168.2.104/WebSite/Service.asmx");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5 * 1000);
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(data);
		outStream.flush();
		outStream.close();
		if (conn.getResponseCode() == 200)
		{
			 
			return parseResponseXML(conn.getInputStream());
		}
		return "Error";
	}

	private String readSoapFile(InputStream inStream, Map<String, String> params) throws Exception
	{
		//  
		byte[] data = readInputStream(inStream);
		String soapxml = new String(data);
		//  	
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("mobile", mobile);
		// 
		return replace(soapxml, params);
	}

	/**
	 *  	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	private byte[] readInputStream(InputStream inputStream) throws Exception
	{
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1)
		{
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inputStream.close();
		return outSteam.toByteArray();
	}

	/**
	 *  
	 * 
	 * @param xml
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private String replace(String xml, Map<String, String> params) throws Exception
	{
		String result = xml;
		if (params != null && !params.isEmpty())
		{
			for (Map.Entry<String, String> entry : params.entrySet())
			{
				String name = "\\$" + entry.getKey();
				Pattern pattern = Pattern.compile(name);
				Matcher matcher = pattern.matcher(result);
				if (matcher.find())
				{
					result = matcher.replaceAll(entry.getValue());
				}
			}
		}
		return result;
	}

	/**
	 *  
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	private static String parseResponseXML(InputStream inStream) throws Exception
	{
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inStream, "UTF-8");
		int eventType = parser.getEventType();// 浜х敓绗竴涓簨浠�		
		while (eventType != XmlPullParser.END_DOCUMENT)
		{
			// 鍙涓嶆槸鏂囨。缁撴潫浜嬩欢
			switch (eventType)
			{
			case XmlPullParser.START_TAG:
				String name = parser.getName();// 鑾峰彇瑙ｆ瀽鍣ㄥ綋鍓嶆寚鍚戠殑鍏冪礌鐨勫悕绉�				
				if ("getMobileCodeInfoResult".equals(name))
				{
					return parser.nextText();
				}
//				break;
			}
			eventType = parser.next();
		}
		return null;
	}
}

