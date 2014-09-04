package com.hooyo.asd;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;

import com.ljlistivew.activity.LJListViewActivity;
import com.ljlistview.view.LJListView;
import com.ljlistview.view.LJListView.IXListViewListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hooyo.asd.IndexPage;
import com.hooyo.asd.ListCarsActivity;
import com.hooyo.asd.R;
import com.hooyo.model.Dept;
import com.hooyo.model.Driver;
import com.hooyo.model.GYS;
import com.hooyo.util.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class GetDriverActivity extends Activity implements IXListViewListener {
	private LJListView mListView;
	private ArrayAdapter<String> mAdapter;
	private ArrayList<String> items = new ArrayList<String>();
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	private String gfmc,hwmc,ton;
	private List<Driver> drivers;
	private Button bt_search;
	private EditText ed_drivername;
	private String pType;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_driver);
		Bundle bundle=this.getIntent().getExtras();
		 pType=bundle.getString("PTYPE");
		this.setTitle("选择人员");
//		String xmbId=bundle.getString("XMBID");
		
		mListView = (LJListView) findViewById(R.id.xlv_drivers);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, items);
		mListView.setAdapter(mAdapter);
		mListView.setPullLoadEnable(false, ""); // 如果不想让脚标显示数据可以mListView.setPullLoadEnable(false,null)或者mListView.setPullLoadEnable(false,"共5条数据")
		mListView.setPullRefreshEnable(true);
		mListView.setIsAnimation(true);
		mListView.setXListViewListener(GetDriverActivity.this);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				TextView tv = (TextView) arg1
						.findViewById(R.id.list_item_textview);
				Toast.makeText(GetDriverActivity.this, tv.getText().toString(),
						0).show();

				Intent intent = new Intent();
				intent.putExtra("DRIVER", tv.getText().toString());
				GetDriverActivity.this.setResult(RESULT_OK, intent); // 12
				GetDriverActivity.this.finish();

				// intent.setClass(GetDriverActivity.this,
				// ListCarsActivity.class);
				// Bundle bundle = new Bundle();
				// bundle.putString("GOODSINFO", tv.getText().toString());
				//
				// bundle.putString("Id",gyss.get(position-1).getID());
				// intent.putExtras(bundle);
				// startActivity(intent);
			}
		});
		mHandler = new Handler();
		
		bt_search=(Button)findViewById(R.id.bt_search);
		ed_drivername=(EditText) findViewById(R.id.ed_Searchdrivername);
		bt_search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(ed_drivername.getText().toString().trim()=="")
				{
					toast("请输入查询人员的关键字或名字！");
				}
				geneItems(ed_drivername.getText().toString(),pType);
				mListView.onFresh();
			}
		});
		//mListView.onFresh();
	}

	public void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	private void geneItems(String name,String Type) {
		Map<String, String> params=new HashMap<String, String>();		
		params.put("strindex", name);        
		params.put("type", Type);   //：“12”是驾驶员，“3”是押运员
		String jsonStr=WebServiceHelper.connectWebService("getDriverList",params);
		//解析json字符串
		Gson gson=new Gson();		
		drivers=gson.fromJson(jsonStr,new TypeToken<List<Driver>>(){}.getType());
		for (Driver o : drivers) {
			 
		items.add(o.getID()+"-"+o.getXM());
		}
//		for (int i = 0; i != 5; ++i) {
//			items.add("供应商 " + (++start)+"轮毂"+"12吨");
//		}
	}

	private void onLoad() {
		mListView.setCount(drivers.size()+"");
		mListView.stopRefresh();
		mListView.stopLoadMore();
		
		SimpleDateFormat    sDateFormat    =   new    SimpleDateFormat("hh:mm:ss");       
		String    refreshtime    =    sDateFormat.format(new    java.util.Date());   
		mListView.setRefreshTime(refreshtime);
	}
	
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = ++refreshCnt;
				items.clear();
				geneItems(ed_drivername.getText().toString(),pType);
				// mAdapter.notifyDataSetChanged();
				mAdapter = new ArrayAdapter<String>(GetDriverActivity.this, R.layout.list_item, items);
				mListView.setAdapter(mAdapter);
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems(ed_drivername.getText().toString(),pType);
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

}