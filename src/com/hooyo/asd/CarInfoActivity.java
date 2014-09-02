package com.hooyo.asd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hooyo.model.CarDetail;
import com.hooyo.util.WebServiceHelper;

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
	private TextView tv_Driver;
	private TextView tv_Goods;
	private TextView tv_Start;
	private TextView tv_End;
	private TextView tv_ArrTime;
	private TextView tv_LoadTime;
	private String goodsInfo;
	private String carNo;
	private String ID;
	private String driver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_info2);
		InitView();
		Bundle bundle=this.getIntent().getExtras();
		goodsInfo=bundle.getString("GOODSINFO");
		carNo=bundle.getString("CarNo");
		this.setTitle(bundle.getString("GYSINFO"));
		ID=bundle.getString("Id");
		getCarInfo(ID);
		
		
		bt_Load.setOnClickListener(Load);
		bt_Sign.setOnClickListener(Sign);
		bt_Modify.setOnClickListener(Modify);
	}
	
	private void getCarInfo(String id) {
		Map<String, String> params=new HashMap<String, String>();		
        params.put("ID", id);        
		String jsonStr=WebServiceHelper.connectWebService("GetCarDetail",params);
		//解析json字符串
		Gson gson=new Gson();		
		List<CarDetail> cars=gson.fromJson(jsonStr,new TypeToken<List<CarDetail>>(){}.getType());
//		for (CarDetail o : cars) {			 
//		items.add("      "+o.getZhuChe());
		CarDetail car=cars.get(0);
		this.tv_ArrTime.setText(car.getYQDDDate().substring(0, 10));
		tv_CarNo.setText(car.getZhuChe());
		this.tv_Driver.setText(car.getDriverA());
		this.tv_End.setText(car.getEQiDian());
		this.tv_Goods.setText(car.getGoodsName());
		this.tv_LoadTime.setText(car.getYQZCDate().substring(0, 10));
		this.tv_Start.setText(car.getSQiDian());
		driver=car.getDriverA();
		}
		
	
	//----------------------装车----------------------------
	private OnClickListener Load = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(); 
//		    intent.setClass(CarInfoActivity.this, CameraActivity.class);LoadRecordActivity
			intent.setClass(CarInfoActivity.this, LoadRecordActivity.class);
			
			Bundle bundle = new Bundle();
			bundle.putString("CARNO",carNo);
			bundle.putString("ID",ID);
			intent.putExtras(bundle);
			 
			startActivity(intent);
		}
	};
	//----------------------修改信息---------------------------
	private OnClickListener Modify = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(); 
		    intent.setClass(CarInfoActivity.this, CarInfoModifyActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("CARNO",carNo);
			bundle.putString("driver",driver);
			bundle.putString("ID",ID);
			intent.putExtras(bundle);
			 
			startActivity(intent);
		}
	};
	//-----------------签到---------------------------------
	private OnClickListener Sign = new OnClickListener() {
		@Override
		public void onClick(View v) {
			try
			{
			Map<String, String> params=new HashMap<String, String>();		
	        params.put("ID", ID);        
			String resultStr=WebServiceHelper.connectWebService("SignCar",params);
			 
			if(resultStr.equals("OK"))
			Toast.makeText(CarInfoActivity.this, "签到成功!",
					Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(CarInfoActivity.this, "签到出错请检查网络!",
						Toast.LENGTH_SHORT).show();
			}catch(Exception x)
			{
				Toast.makeText(CarInfoActivity.this, "签到出错请检查网络!",
						Toast.LENGTH_SHORT).show();
			}
				
		}
	};
	private void InitView()
	{
		bt_Modify=(ImageButton) findViewById(R.id.bt_Modify);
		bt_Sign=(ImageButton) findViewById(R.id.bt_Sign);
		bt_Load=(ImageButton) findViewById(R.id.bt_Load);
		tv_CarNo=(TextView)findViewById(R.id.tv_CarNo);
		tv_Driver=(TextView)findViewById(R.id.tv_Driver);
 		tv_Goods=(TextView)findViewById(R.id.tv_driverA);
		tv_Start =(TextView)findViewById(R.id.tv_Start);
		tv_End=(TextView)findViewById(R.id.tv_End);
		tv_ArrTime=(TextView)findViewById(R.id.tv_ArrTime);
		tv_LoadTime=(TextView)findViewById(R.id.tv_LoadTime);
	}
}
