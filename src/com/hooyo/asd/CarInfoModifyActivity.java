package com.hooyo.asd;

import java.util.HashMap;
import java.util.Map;

import com.hooyo.util.WebServiceHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CarInfoModifyActivity extends Activity {

	private String carNo;
	private TextView tv_carNum;
	private TextView tv_driverA;
	private EditText ed_gcNo;
	private EditText ed_driverB;
	private EditText ed_yyy;
	private ImageView bt_save;
	private String ID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_info_modify2);
		
		findViews();
		
		Bundle bundle=this.getIntent().getExtras();
		String zhuche=bundle.getString("CARNO");
		zhuche=zhuche.replace(" ", "");
		tv_carNum.setText(zhuche);
		tv_driverA.setText(bundle.getString("driver"));
		ID=bundle.getString("ID");
		
//		SaveData();
		
	}
	private void SaveData() {
		Map<String, String> params=new HashMap<String, String>();		
        params.put("ID", ID);  
        params.put("MainCarID", tv_carNum.getText().toString());
        params.put("GuaCarID", ed_gcNo.getText().toString());
        params.put("DriverAID", tv_driverA.getText().toString()); 
        params.put("DriverBID",ed_driverB.getText().toString());
        params.put("YaYunID", ed_yyy.getText().toString());  
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
		 
		 tv_carNum=(TextView)findViewById(R.id.tv_CarNo2);
		 tv_driverA=(TextView)findViewById(R.id.tv_driverA);;
		  ed_gcNo=(EditText)findViewById(R.id.edgc2);
		 ed_driverB=(EditText)findViewById(R.id.edDriverB);;
		  ed_yyy=(EditText)findViewById(R.id.edyyy);
		  bt_save=(ImageView)findViewById(R.id.bt_savecarinfo);
		  
		
	}
}
