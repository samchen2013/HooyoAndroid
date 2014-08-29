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
			// ����.net ��WebService.

			String nameSpace = "http://tempuri.org/";
 
			String url = "http://58.221.119.25/MobileService.asmx"; // 10.0.2.2
																					// ΪAndroidģ�����ı���(localhost)IP
			String soapAction = nameSpace + methodName;

			// �������Ӳ���
			SoapObject request = new SoapObject(nameSpace, methodName);

			// �������Բ����� ����صĺ����������뵽�����С�
			 Iterator<Map.Entry<String, String>> it=map.entrySet().iterator(); 
			//Map��entrySet()����һ��Set���ϣ��ڼ����д����Map.Entry���͵�Ԫ�أ�ÿ��Map.Entry�������Map�� 
			//��һ�Ѽ���ֵ�� 
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
					SoapEnvelope.VER10);// soapЭ��汾������SoapEnvelope.VER11��Soap
										// V1.1��;

			// ע�⣺��������Ƕ�dotnetwebserviceЭ���֧��,���dotnet��webservice
			// ��ָ��rpc��ʽ����true����Ҫ��false
			envelope.dotNet = true;

			// envelope.setOutputSoapObject(request);//�����������
			envelope.bodyOut = request; // enveloper.bodyOut=request ��
										// envelope.setOutputSoapObject(request)
										// Ч����ͬ��
			// step4 ����HttpTransportSE����
			//AndroidHttpTransport ht=new AndroidHttpTransport(url);
			HttpTransportSE ht = new HttpTransportSE(url);
			// step5 ����WebService
			ht.call(soapAction, envelope); // �ؼ���һ�����ܶ����ⶼ����һ������ʱ�������⡣Ҫô��IIS�����⣬Ҫô��ksoap2��ز���û���úá�
			if (envelope.getResponse() != null) {
				SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
				//Boolean result = Boolean.parseBoolean(response.toString());
				
//				if (result) {
//					return "OK";
//				} else {
//					return "�û����������";
//				}
				String ms=response.toString(); 
				return "OK"+ms;
			} else {
				return "����������û�п�����";
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
			// ����.net ��WebService.

			String nameSpace = "http://tempuri.org/";
			String methodName = "HelloWorld";
			String url = "http://192.168.2.104/WebSite/Service.asmx"; // 10.0.2.2
																					// ΪAndroidģ�����ı���(localhost)IP
			String soapAction = nameSpace + methodName;

			// �������Ӳ���
			SoapObject request = new SoapObject(nameSpace, methodName);

			// �������Բ����� ����صĺ����������뵽�����С�
			request.addProperty("a",userName);
			request.addProperty("b", passwords);
			//request.addProperty("sYardCode", "WLY");

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER10);// soapЭ��汾������SoapEnvelope.VER11��Soap
										// V1.1��;

			// ע�⣺��������Ƕ�dotnetwebserviceЭ���֧��,���dotnet��webservice
			// ��ָ��rpc��ʽ����true����Ҫ��false
			envelope.dotNet = true;

			// envelope.setOutputSoapObject(request);//�����������
			envelope.bodyOut = request; // enveloper.bodyOut=request ��
										// envelope.setOutputSoapObject(request)
										// Ч����ͬ��
			// step4 ����HttpTransportSE����
			//AndroidHttpTransport ht=new AndroidHttpTransport(url);
			HttpTransportSE ht = new HttpTransportSE(url);
			// step5 ����WebService
			ht.call(soapAction, envelope); // �ؼ���һ�����ܶ����ⶼ����һ������ʱ�������⡣Ҫô��IIS�����⣬Ҫô��ksoap2��ز���û���úá�
			if (envelope.getResponse() != null) {
				SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
				//Boolean result = Boolean.parseBoolean(response.toString());
				
//				if (result) {
//					return "OK";
//				} else {
//					return "�û����������";
//				}
				String ms=response.toString(); 
				return "OK"+ms;
			} else {
				return "����������û�п�����";
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
