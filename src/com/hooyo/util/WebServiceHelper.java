package com.hooyo.util;

import java.io.IOException;


import java.util.Iterator;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
 
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

 
//ksoap2
public class WebServiceHelper {
	public static String connectWebService(String methodName,Map<String,String> map)
	{
		try {
			// 调用.net 的WebService.

			String nameSpace = "http://tempuri.org/";
 
			String url = "http://58.221.119.25/MobileService.asmx"; // 10.0.2.2
																					// 为Android模拟器的本地(localhost)IP
			String soapAction = nameSpace + methodName;

			// 设置连接参数
			SoapObject request = new SoapObject(nameSpace, methodName);

			// 增加属性参数。 将相关的函数参数放入到过程中。
			 Iterator<Map.Entry<String, String>> it=map.entrySet().iterator(); 
			//Map的entrySet()返回一个Set集合，在集合中存放了Map.Entry类型的元素，每个Map.Entry对象代表Map中 
			//的一堆键和值。 
			        while(it.hasNext()) 
			        { 
			            Map.Entry entry=it.next(); 
			            request.addProperty(entry.getKey().toString(),entry.getValue().toString());
//			            System.out.println(entry.getKey()+":"+entry.getValue()); 
			        } 
//			request.addProperty("a","userName");
//			request.addProperty("b", "passwords");
			//request.addProperty("sYardCode", "WLY");

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER10);// soap协议版本必须用SoapEnvelope.VER11（Soap
										// V1.1）;

			// 注意：这个属性是对dotnetwebservice协议的支持,如果dotnet的webservice
			// 不指定rpc方式则用true否则要用false
			envelope.dotNet = true;

			// envelope.setOutputSoapObject(request);//设置请求参数
			envelope.bodyOut = request; // enveloper.bodyOut=request 与
										// envelope.setOutputSoapObject(request)
										// 效果相同。
			// step4 创建HttpTransportSE对象
			//AndroidHttpTransport ht=new AndroidHttpTransport(url);
			HttpTransportSE ht = new HttpTransportSE(url);
			// step5 调用WebService
			ht.call(soapAction, envelope); // 关键的一步，很多问题都在这一步调试时出现问题。要么是IIS有问题，要么是ksoap2相关参数没配置好。
			if (envelope.getResponse() != null) {
				SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
				//Boolean result = Boolean.parseBoolean(response.toString());
				
//				if (result) {
//					return "OK";
//				} else {
//					return "用户名或密码错！";
//				}
				String ms=response.toString(); 
				return "OK"+ms;
			} else {
				return "服务器可能没有开启！";
			}

		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return "error";
		}catch(Exception e)
		{
			e.printStackTrace();
			return "error";
		}
		
		 
	}
	
	public static String getLogin(String userName, String passwords) {

		try {
			// 调用.net 的WebService.

			String nameSpace = "http://tempuri.org/";
			String methodName = "HelloWorld";
			String url = "http://192.168.2.104/WebSite/Service.asmx"; // 10.0.2.2
																					// 为Android模拟器的本地(localhost)IP
			String soapAction = nameSpace + methodName;

			// 设置连接参数
			SoapObject request = new SoapObject(nameSpace, methodName);

			// 增加属性参数。 将相关的函数参数放入到过程中。
			request.addProperty("a",userName);
			request.addProperty("b", passwords);
			//request.addProperty("sYardCode", "WLY");

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER10);// soap协议版本必须用SoapEnvelope.VER11（Soap
										// V1.1）;

			// 注意：这个属性是对dotnetwebservice协议的支持,如果dotnet的webservice
			// 不指定rpc方式则用true否则要用false
			envelope.dotNet = true;

			// envelope.setOutputSoapObject(request);//设置请求参数
			envelope.bodyOut = request; // enveloper.bodyOut=request 与
										// envelope.setOutputSoapObject(request)
										// 效果相同。
			// step4 创建HttpTransportSE对象
			//AndroidHttpTransport ht=new AndroidHttpTransport(url);
			HttpTransportSE ht = new HttpTransportSE(url);
			// step5 调用WebService
			ht.call(soapAction, envelope); // 关键的一步，很多问题都在这一步调试时出现问题。要么是IIS有问题，要么是ksoap2相关参数没配置好。
			if (envelope.getResponse() != null) {
				SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
				//Boolean result = Boolean.parseBoolean(response.toString());
				
//				if (result) {
//					return "OK";
//				} else {
//					return "用户名或密码错！";
//				}
				String ms=response.toString(); 
				return "OK"+ms;
			} else {
				return "服务器可能没有开启！";
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return "error";

	}

}
