package com.ljlistivew.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ljlistview.view.LJListView;
import com.ljlistview.view.LJListView.IXListViewListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hooyo.asd.IndexPage;
import com.hooyo.asd.ListCarsActivity;
import com.hooyo.asd.R;
import com.hooyo.model.Dept;
import com.hooyo.model.GYS;
import com.hooyo.util.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class LJListViewActivity extends Activity implements IXListViewListener {
	private LJListView mListView;
	private ArrayAdapter<String> mAdapter;
	private ArrayList<String> items = new ArrayList<String>();
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	private String gfmc,hwmc,ton;
	private List<GYS> gyss;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Bundle bundle=this.getIntent().getExtras();
		String xmbName=bundle.getString("XMBNAME");
		this.setTitle(xmbName);
		String xmbId=bundle.getString("XMBID");
		geneItems("57");
		mListView = (LJListView) findViewById(R.id.xListView);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, items);
		mListView.setAdapter(mAdapter);
		mListView.setPullLoadEnable(false,""); //如果不想让脚标显示数据可以mListView.setPullLoadEnable(false,null)或者mListView.setPullLoadEnable(false,"共5条数据")
		mListView.setPullRefreshEnable(true);
		mListView.setIsAnimation(true); 
		mListView.setXListViewListener(LJListViewActivity.this);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				TextView tv=(TextView) arg1.findViewById(R.id.list_item_textview);
				Toast.makeText(LJListViewActivity.this, tv.getText().toString(), 0).show();
				 
				Intent intent = new Intent();

			    intent.setClass(LJListViewActivity.this, ListCarsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("GOODSINFO", tv.getText().toString());
				
				bundle.putString("Id",gyss.get(position-1).getID());
				intent.putExtras(bundle);				
				startActivity(intent);
			}
		});
		mHandler = new Handler();
		mListView.onFresh();
	}

	private void geneItems(String xmbId) {
		Map<String, String> params=new HashMap<String, String>();		
        params.put("ID", xmbId);        
		String jsonStr=WebServiceHelper.connectWebService("GetGYSList",params);
		//解析json字符串
		Gson gson=new Gson();		
		gyss=gson.fromJson(jsonStr,new TypeToken<List<GYS>>(){}.getType());
		for (GYS o : gyss) {
			 
		items.add(StringUtil.SetStringWidth(o.getSupplierName(),12)    //供应商
				+ StringUtil.SetStringWidth(o.getGoodsName(),21)       //货物
				+ StringUtil.SetStringWidth(o.getTon()+"吨",15)         //吨位
				+StringUtil.SetStringWidth("签到",5)
						);
		}
//		for (int i = 0; i != 5; ++i) {
//			items.add("供应商 " + (++start)+"轮毂"+"12吨");
//		}
	}

	private void onLoad() {
		mListView.setCount(gyss.size()+"");
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
				geneItems("57");
				// mAdapter.notifyDataSetChanged();
				mAdapter = new ArrayAdapter<String>(LJListViewActivity.this, R.layout.list_item, items);
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
				geneItems("57");
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

}