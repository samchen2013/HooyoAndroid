package com.hooyo.asd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CarInfoActivity extends Activity {

	private Button bt_Modify;
	private Button bt_Sign;
	private Button bt_Load;
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
	}
	private OnClickListener Load = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(); 
		    intent.setClass(CarInfoActivity.this, CameraActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("Info",carNo+"-"+goodsInfo);
			intent.putExtras(bundle);
			 
			startActivity(intent);
		}
	};
	private void InitView()
	{
		bt_Modify=(Button) findViewById(R.id.bt_Modify);
		bt_Sign=(Button) findViewById(R.id.bt_Sign);
		bt_Load=(Button) findViewById(R.id.bt_Load);
		tv_CarNo=(TextView)findViewById(R.id.tv_CarNo);
	}
}
