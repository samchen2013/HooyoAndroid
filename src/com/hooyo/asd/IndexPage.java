package com.hooyo.asd;

import java.util.ArrayList;
import java.util.HashMap;
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

public class IndexPage extends Activity {
	private Button reback_button;
	private TextView view_result;
	private Spinner spinner; 
	private ListView lv;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.indexpage);
		findView();
		Bundle bundle=this.getIntent().getExtras();
		String userName=bundle.getString("MAP_USERNAME");
		view_result.setText("hello  "+userName+" ,login success!");
		setListener();
		
		//设置下拉列表
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapter.add("请选择部门");
		adapter.add("项目部1");
		 adapter.add("项目部2");
		 adapter.setDropDownViewResource(android.R.layout. simple_spinner_dropdown_item );
		 spinner = (Spinner) this .findViewById(R.id.spinner1 );
		 spinner .setPrompt( " 请选择项目部 ： " );
//		 spinner.setSelection(0,true);
		 spinner.setOnItemSelectedListener(new OnItemSelectedListener() { 			 
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					if(spinner.getSelectedItem().toString().equals(("请选择部门")))
					{}else
					{
					 Toast.makeText(IndexPage.this, spinner.getSelectedItem().toString(), 0).show();
//					 bindListView();
					 Intent intent = new Intent();
//						intent.setClass(IndexPage.this, ListItemActivity.class);
					 intent.setClass(IndexPage.this, com.ljlistivew.activity.LJListViewActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("XMBNAME", spinner.getSelectedItem().toString());
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
	
	private OnClickListener back = new OnClickListener() {
		@Override
		public void onClick(View v) {
			IndexPage.this.finish();
		}
	};
	
	private void setListener(){
		reback_button.setOnClickListener(back);
	}
	
	private void findView() {
		reback_button = (Button) findViewById(R.id.report_back);
		view_result = (TextView) findViewById(R.id.result); 
	}
	

}
