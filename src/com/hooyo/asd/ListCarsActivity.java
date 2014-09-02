package com.hooyo.asd;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ljlistivew.activity.LJListViewActivity;
import com.ljlistview.view.LJListView;
import com.ljlistview.view.LJListView.IXListViewListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hooyo.asd.IndexPage;
import com.hooyo.asd.ListCarsActivity;
import com.hooyo.asd.R;
import com.hooyo.model.CarDetail;
import com.hooyo.model.GYS;
import com.hooyo.util.StringUtil;
import com.hooyo.util.WebServiceHelper;

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


public class ListCarsActivity extends Activity implements IXListViewListener{

	private LJListView mListView;
	private ArrayAdapter<String> mAdapter;
	private ArrayList<String> items = new ArrayList<String>();
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	private TextView tv_gfmc;
	private TextView tv_hwmc;
	private TextView tv_ton;
	private String Id;
	private List<CarDetail> cars;
	private String itemCount;
	private String gysName;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_cars);
		Bundle bundle=this.getIntent().getExtras();
		gysName=bundle.getString("GOODSINFO");
		Id=bundle.getString("Id");
		gysName=gysName.replace("签到", "");
		String trimgys=gysName;
		do{gysName=trimgys;
		trimgys=trimgys.replace("  ", " ");
		}while(trimgys!=gysName);
		
		this.setTitle(gysName);
		String[] s=gysName.split(" ");
		tv_gfmc=(TextView)findViewById(R.id.tv_gfmc);
		tv_hwmc=(TextView)findViewById(R.id.tv_hwmc);
		tv_ton=(TextView)findViewById(R.id.tv_ton);
		tv_gfmc.setText("       供方名称:"+s[0]);
		tv_hwmc.setText("       货物名称:"+s[1]);
		tv_ton.setText("       预计吨位"+s[2]);
		geneItems(Id);
		mListView = (LJListView) findViewById(R.id.xListView);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, items);
		mListView.setAdapter(mAdapter);
		mListView.setPullLoadEnable(false,""); //如果不想让脚标显示数据可以mListView.setPullLoadEnable(false,null)或者mListView.setPullLoadEnable(false,"")
		mListView.setPullRefreshEnable(true);
		mListView.setIsAnimation(true); 
		mListView.setXListViewListener(ListCarsActivity.this);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				TextView tv=(TextView) arg1.findViewById(R.id.list_item_textview);
				Toast.makeText(ListCarsActivity.this, tv.getText().toString(), 0).show();
				 
				Intent intent = new Intent();
			    intent.setClass(ListCarsActivity.this, CarInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("CarNo", tv.getText().toString());
				bundle.putString("GoodsInfo", bundle.getString("GOODSINFO"));
				bundle.putString("Id",cars.get(position-1).getID());
				bundle.putString("GYSINFO", gysName);
				intent.putExtras(bundle);				
				startActivity(intent);
			}
		});
		mHandler = new Handler();
//		mListView.onFresh();
	}

	private void geneItems(String Id) {
		Map<String, String> params=new HashMap<String, String>();		
        params.put("ID", Id);        
		String jsonStr=WebServiceHelper.connectWebService("GetCarList",params);
		//解析json字符串
		Gson gson=new Gson();		
		cars=gson.fromJson(jsonStr,new TypeToken<List<CarDetail>>(){}.getType());
		for (CarDetail o : cars) {			 
		items.add("      "+o.getZhuChe());
		}
//		for (int i = 0; i != 5; ++i) {
//			items.add("苏F0878" + (++start));
//		}
	}

	private void onLoad() {
		mListView.setCount(cars.size()+"");
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
				geneItems(Id);
				// mAdapter.notifyDataSetChanged();
				mAdapter = new ArrayAdapter<String>(ListCarsActivity.this, R.layout.list_item, items);
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
				geneItems(Id);
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}
}
