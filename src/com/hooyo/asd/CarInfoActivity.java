package com.hooyo.asd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CarInfoActivity extends Activity {

	private ImageButton bt_Modify;
	private ImageButton bt_Sign;
	private ImageButton bt_Load;
	private TextView tv_CarNo;
	private String goodsInfo;
	private String carNo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_info);
		InitView();
		Bundle bundle=this.getIntent().getExtras();
		 goodsInfo=bundle.getString("GOODSINFO");
		 carNo=bundle.getString("CarNo");
		tv_CarNo.setText(carNo);
		
		bt_Load.setOnClickListener(Load);
		bt_Sign.setOnClickListener(Sign);
		bt_Modify.setOnClickListener(Modify);
	}
	
	private OnClickListener Load = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(); 
//		    intent.setClass(CarInfoActivity.this, CameraActivity.class);LoadRecordActivity
			intent.setClass(CarInfoActivity.this, LoadRecordActivity.class);
			
			Bundle bundle = new Bundle();
			bundle.putString("Info",carNo+"-"+goodsInfo);
			intent.putExtras(bundle);
			 
			startActivity(intent);
		}
	};
	
	private OnClickListener Modify = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(); 
		    intent.setClass(CarInfoActivity.this, CarInfoModifyActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("Info",carNo+"-"+goodsInfo);
			intent.putExtras(bundle);
			 
			startActivity(intent);
		}
	};
	
	private OnClickListener Sign = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(CarInfoActivity.this, "Ç©µ½³É¹¦!",
					Toast.LENGTH_SHORT).show();
		}
	};
	private void InitView()
	{
		bt_Modify=(ImageButton) findViewById(R.id.bt_Modify);
		bt_Sign=(ImageButton) findViewById(R.id.bt_Sign);
		bt_Load=(ImageButton) findViewById(R.id.bt_Load);
		tv_CarNo=(TextView)findViewById(R.id.tv_CarNo);
	}
}
