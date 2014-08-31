package com.hooyo.asd;

import com.hooyo.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.hooyo.util.WebServiceHelper;

public class IndexPage extends Activity {
//	private Button reback_button;
//	private TextView view_result;
	private Spinner spinner; 
	private ListView lv;
	Map<String, String> depts;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.indexpage);
		findView();
		Bundle bundle=this.getIntent().getExtras();
		String userName=bundle.getString("MAP_USERNAME");
//		view_result.setText("hello  "+userName+" ,login success!");
		setListener();
		
		//���������б�
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		
		depts=getDeptsByUserName(userName);
		Iterator<Map.Entry<String, String>> it=depts.entrySet().iterator(); 
		//Map��entrySet()����һ��Set���ϣ��ڼ����д����Map.Entry���͵�Ԫ�أ�ÿ��Map.Entry�������Map�� 
		//��һ�Ѽ���ֵ�� 
		        adapter.add("");
		        while(it.hasNext()) 
		        { 
		            Map.Entry entry=it.next(); 
		            adapter.add(entry.getKey().toString()); 
		        } 
//		adapter.add("��ѡ����");
//		adapter.add("��Ŀ��1");
//		 adapter.add("��Ŀ��2");
		 adapter.setDropDownViewResource(android.R.layout. simple_spinner_dropdown_item );
		 spinner = (Spinner) this .findViewById(R.id.spinner1 );
		 spinner .setPrompt( " ��ѡ����Ŀ�� �� " );
//		 spinner.setSelection(0,true);
		 spinner.setOnItemSelectedListener(new OnItemSelectedListener() { 			 
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					if(spinner.getSelectedItem().toString().equals(("")))
					{}else
					{
					 Toast.makeText(IndexPage.this, spinner.getSelectedItem().toString(), 0).show();
//					 bindListView();
					 Intent intent = new Intent();
//						intent.setClass(IndexPage.this, ListItemActivity.class);
					 intent.setClass(IndexPage.this, com.ljlistivew.activity.LJListViewActivity.class);
					 String xmbName= spinner.getSelectedItem().toString();
					 String xmbId=depts.get(xmbName);
						Bundle bundle = new Bundle();
						bundle.putString("XMBNAME", xmbName);
						bundle.putString("XMBID", xmbId);
						intent.putExtras(bundle);
						 
						startActivity(intent);
						 
					}
				}
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub				
				}
	         });
         spinner .setAdapter(adapter);  
	} 
	
	//���÷����ȡ�����б��װ��map
	private Map<String, String> getDeptsByUserName(String userName) {
		Map<String, String> params=new HashMap<String, String>();		
        params.put("userName", userName);        
		String jsonStr=WebServiceHelper.connectWebService("GetDeptList",params);
		//����json�ַ���
		Gson gson=new Gson();		
		List<Dept> xmbs=gson.fromJson(jsonStr,new TypeToken<List<Dept>>(){}.getType());
		Map<String, String> depts=new HashMap<String, String>();
		for(int i=0;i<xmbs.size();i++)
		{
			Dept o=xmbs.get(i);
			 depts.put( o.getName(),o.getId());
		}		 
		return depts;
	}

	private OnClickListener back = new OnClickListener() {
		@Override
		public void onClick(View v) {
			IndexPage.this.finish();
		}
	};
	
	private void setListener(){
//		reback_button.setOnClickListener(back);
	}
	
	private void findView() {
//		reback_button = (Button) findViewById(R.id.report_back);
//		view_result = (TextView) findViewById(R.id.result); 
	}
	

}
