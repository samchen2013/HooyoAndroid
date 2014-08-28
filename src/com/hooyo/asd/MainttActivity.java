package com.hooyo.asd;

import java.util.HashMap;
import java.util.Map;

import com.hooyo.util.WebServiceHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainttActivity extends Activity {
	private TextView msg=null;
	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintt);
		msg=(TextView)findViewById(R.id.txtMsg);
//		WebServHelper ws=	new WebServHelper();		
		String dd="88888888";
		try {
			//service对应的参数和参数值
//			Map<String, String> params = new HashMap<String, String>();
//			params.put("string1", "namea");
//			params.put("string", ":mima");
//			dd = ws.getService("helloworldsoap.xml", params);
			dd=WebServiceHelper.getLogin("sam", "heihei");
			Toast.makeText(MainttActivity.this, dd, Toast.LENGTH_LONG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dd=e.toString();
		}//WebServiceHelper.getLogin("aaa", "bbb");
		msg.setText(dd);
	}
}
