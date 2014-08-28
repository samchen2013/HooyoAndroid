package com.hooyo.asd;

import java.util.ArrayList;

import com.ljlistivew.activity.LJListViewActivity;
import com.ljlistview.view.LJListView;
import com.ljlistview.view.LJListView.IXListViewListener;
import com.hooyo.asd.IndexPage;
import com.hooyo.asd.ListCarsActivity;
import com.hooyo.asd.R;

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
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Bundle bundle=this.getIntent().getExtras();
		String gysName=bundle.getString("GOODSINFO");
		this.setTitle(gysName);
		geneItems();
		mListView = (LJListView) findViewById(R.id.xListView);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, items);
		mListView.setAdapter(mAdapter);
		mListView.setPullLoadEnable(false,"共5条数据"); //如果不想让脚标显示数据可以mListView.setPullLoadEnable(false,null)或者mListView.setPullLoadEnable(false,"")
		mListView.setPullRefreshEnable(true);
		mListView.setIsAnimation(true); 
		mListView.setXListViewListener(ListCarsActivity.this);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TextView tv=(TextView) arg1.findViewById(R.id.list_item_textview);
				Toast.makeText(ListCarsActivity.this, tv.getText().toString(), 0).show();
				 
				Intent intent = new Intent();

			    intent.setClass(ListCarsActivity.this, CarInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("CarNo", tv.getText().toString());
				bundle.putString("GoodsInfo", bundle.getString("GOODSINFO"));
				intent.putExtras(bundle);				
				startActivity(intent);
			}
		});
		mHandler = new Handler();
		mListView.onFresh();
	}

	private void geneItems() {
		for (int i = 0; i != 5; ++i) {
			items.add("苏F0878" + (++start));
		}
	}

	private void onLoad() {
		mListView.setCount("10");
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime("刚刚");
	}
	
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = ++refreshCnt;
				items.clear();
				geneItems();
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
				geneItems();
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}
}
