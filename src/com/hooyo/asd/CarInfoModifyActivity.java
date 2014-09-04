package com.hooyo.asd;

import java.util.HashMap;
import java.util.Map;

import com.hooyo.util.WebServiceHelper;
import com.ljlistivew.activity.LJListViewActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CarInfoModifyActivity extends Activity {
	private static final int QUERY_ZHUCHE_REQUEST_CODE=11;
	private static final int QUERY_GUACHE_REQUEST_CODE=12;
	private static final int QUERY_DRIVERA_REQUEST_CODE=21;
	private static final int QUERY_DRIVERB_REQUEST_CODE=22;
	private static final int QUERY_YYY_REQUEST_CODE=23;
	 
	 
	private ImageView bt_save;
	private String ID;
	private TextView stv_DriverB;
	private TextView stv_yyy;
	private TextView stv_zhuche;
	private TextView stv_driverA;
	private TextView stv_gc;
	
	private String zhucheID="";
	private String guacheID="";
	private String driverAID="";
	private String driverBID="";
	private String yyyID="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_info_modify2);
		
		findViews();
		
		Bundle bundle=this.getIntent().getExtras();
//		String zhuche=bundle.getString("CARNO");
//		zhuche=zhuche.replace(" ", "");
//		tv_carNum.setText(zhuche);
//		tv_driverA.setText(bundle.getString("driver"));
		ID=bundle.getString("ID");
		bt_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SaveData();
			}
		});
		
		stv_zhuche.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();

			    intent.setClass(CarInfoModifyActivity.this, GetCarActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("PTYPE", "12");			 
				intent.putExtras(bundle);				
				startActivityForResult(intent,QUERY_ZHUCHE_REQUEST_CODE);

			}
		});
		
		stv_gc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();

			    intent.setClass(CarInfoModifyActivity.this, GetCarActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("PTYPE", "3");			 
				intent.putExtras(bundle);				
				startActivityForResult(intent,QUERY_GUACHE_REQUEST_CODE);

			}
		});
		
		stv_driverA.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();

			    intent.setClass(CarInfoModifyActivity.this, GetDriverActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("PTYPE", "12");			 
				intent.putExtras(bundle);				
				startActivityForResult(intent,QUERY_DRIVERA_REQUEST_CODE);

			}
		});
		stv_DriverB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();

			    intent.setClass(CarInfoModifyActivity.this, GetDriverActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("PTYPE", "12");			 
				intent.putExtras(bundle);				
				startActivityForResult(intent,QUERY_DRIVERB_REQUEST_CODE);

			}
		});
		
		stv_yyy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();

			    intent.setClass(CarInfoModifyActivity.this, GetDriverActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("PTYPE", "3");			 
				intent.putExtras(bundle);				
				startActivityForResult(intent,QUERY_YYY_REQUEST_CODE);

			}
		});
//		SaveData();
		
	}
	
	@Override
 	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		
		case QUERY_ZHUCHE_REQUEST_CODE: {
			if (resultCode == RESULT_OK) {
				String[] info=data.getStringExtra("CAR").split("-"); 
				stv_zhuche.setText(info[1]);
				zhucheID=info[0];			
				 
			} else if (resultCode == RESULT_CANCELED) {
				// 用户取消 
			} else {
				 
				toast("选择失败!");
				 
			}
		}
		break;
		
		case QUERY_GUACHE_REQUEST_CODE: {
			if (resultCode == RESULT_OK) {
				String[] info=data.getStringExtra("CAR").split("-"); 
				stv_gc.setText(info[1]);
				guacheID=info[0];	
			} else if (resultCode == RESULT_CANCELED) {
				// 用户取消 
			} else {
				 
				toast("选择失败!");
				 
			}
		}
		break;
		
		case QUERY_DRIVERA_REQUEST_CODE: {
			if (resultCode == RESULT_OK) {
				String[] info=data.getStringExtra("DRIVER").split("-"); 
				 stv_driverA.setText(info[1]);
				 driverAID=info[0];
			} else if (resultCode == RESULT_CANCELED) {
				// 用户取消 
			} else {
				 
				toast("选择失败!");
				 
			}
		}
		break;
		case QUERY_DRIVERB_REQUEST_CODE: {
			if (resultCode == RESULT_OK) {
				String[] info=data.getStringExtra("DRIVER").split("-"); 
				stv_DriverB.setText(info[1]);
				driverBID=info[0];				  
			} else if (resultCode == RESULT_CANCELED) {
				// 用户取消 
			} else {
				 
				toast("选择失败!");
				 
			}
		}
		break;
		
		case QUERY_YYY_REQUEST_CODE: {
			if (resultCode == RESULT_OK) {
				String[] info=data.getStringExtra("DRIVER").split("-"); 
				stv_yyy.setText(info[1]);
				yyyID=info[0];		
				
			} else if (resultCode == RESULT_CANCELED) {
				// 用户取消 
			} else {
				 
				toast("选择失败!");
				 
			}
		}
		break;
		}
		
 	
		
//		if (requestCode == QUERY_DRIVERB_REQUEST_CODE) {// 第一张图片
//			if (resultCode == RESULT_OK) {
//				 stv_DriverB.setText(data.getStringExtra("DRIVER") );
//			} else if (resultCode == RESULT_CANCELED) {
//				// 用户取消 
//			} else {
//				 
//				toast("选择失败!");
//				 
//			}
//		}		
		
	}
	
	public void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	private void SaveData() {
		if(guacheID.equals(""))
		{
			Toast.makeText(CarInfoModifyActivity.this, "请选择挂车！",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if(zhucheID.equals(""))
		{
			Toast.makeText(CarInfoModifyActivity.this, "请选择主车！",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if(driverAID.equals(""))
		{
			Toast.makeText(CarInfoModifyActivity.this, "请选择驾驶员A！",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if(driverBID.equals(""))
		{
			Toast.makeText(CarInfoModifyActivity.this, "请选择驾驶员B！",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if(yyyID.equals(""))
		{
			Toast.makeText(CarInfoModifyActivity.this, "请选择押运员！",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		Map<String, String> params=new HashMap<String, String>();		
        params.put("ID", ID);  
        params.put("MainCarID", zhucheID);
        params.put("GuaCarID", guacheID);
        params.put("DriverAID", driverAID); 
        params.put("DriverBID",driverBID);
        params.put("YaYunID", yyyID);  
        try{
		String Str=WebServiceHelper.connectWebService("ModifyCarInfo",params);
		if(Str.equals("OK"))
			Toast.makeText(CarInfoModifyActivity.this, "车辆信息修改成功！",
					Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(CarInfoModifyActivity.this, "修改失败！001",
					Toast.LENGTH_SHORT).show();
        }catch(Exception e)
        {
        	Toast.makeText(CarInfoModifyActivity.this, "修改失败！002:"+e.toString(),
					Toast.LENGTH_SHORT).show();
        }
	}
	private void findViews() {
		 
 
		  stv_zhuche=(TextView)findViewById(R.id.stv_zhuche);
		 stv_driverA=(TextView)findViewById(R.id.stv_driverA);
		 stv_gc=(TextView)findViewById(R.id.stv_gc);
//		 tv_carNum=(TextView)findViewById(R.id.tv_CarNo2);
//		 tv_driverA=(TextView)findViewById(R.id.tv_driverA);;
//		  ed_gcNo=(EditText)findViewById(R.id.edgc2);
		// ed_driverB=(EditText)findViewById(R.id.edDriverB);;
		  stv_DriverB=(TextView)findViewById(R.id.stv_DriverB);
//		  ed_yyy=(EditText)findViewById(R.id.edyyy);
		  stv_yyy=(TextView)findViewById(R.id.stv_yyy);
		  bt_save=(ImageView)findViewById(R.id.bt_savecarinfo);
		  
		
	}
}
