package com.hooyo.asd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hooyo.asd.Login.LoginFailureHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListItemActivity extends Activity {

	private ListView lv;
	private ProgressDialog proDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_item);
		Bundle bundle=this.getIntent().getExtras();
		String xmbName=bundle.getString("XMBNAME");
		this.setTitle(xmbName);
		proDialog = ProgressDialog.show(ListItemActivity.this, "待装车.",
				"数据加载中...", true, true);
		//  
		Thread loginThread = new Thread(new BindListHandler());
		loginThread.start();
		
	}
	private void bindListView(String xmbName)
	{
		   //设置listView listView1
	    lv = (ListView) findViewById(R.id.listView1);
	    ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    // 创建Map用于添加数据
	    Map<String, Object> map = new HashMap<String, Object>();
	    // 添加数据，这一这里的icon text 要与下面的new String[]{"icon","text"}, new
	    // int[]{R.id.icon,R.id.text});对应起来
	     
	    map.put("gys", "张三");
	    map.put("goods", "水泥");
	    map.put("ton", "1吨");
	    list.add(map);
	     
	    
	    map.put("gys", "张2");
	    map.put("goods", "轮毂");
	    map.put("ton", "2吨");
//	    list.add(map);
	    
	    map.put("gys", "张3");
	    map.put("goods", "油漆");
	    map.put("ton", "3吨");
	    list.add(map);
	    // 创建SimpleAdapter数据适配器
	    SimpleAdapter adapter1 = new SimpleAdapter(this, list,
	                    R.layout.listviewitem, 
	                    new String[] { "gys", "goods","ton" }, // 指定列名的集合，这里的列的顺序，要与下面的int集合的值的顺序一致。
	                    new int[] {R.id.gys, R.id.goods,R.id.ton });// listview中存放数据的控件的集合，注意顺序要与上面的String数组顺序一致。
	    // 为ListView指定适配器
	    lv.setAdapter(adapter1);
	    adapter1.notifyDataSetChanged();

	    
	    proDialog.dismiss();}

class BindListHandler implements Runnable {
	@Override
	public void run() {
		bindListView("");
	}
}
}
