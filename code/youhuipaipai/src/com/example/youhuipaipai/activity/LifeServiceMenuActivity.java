package com.example.youhuipaipai.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.youhuipaipai.R;

public class LifeServiceMenuActivity extends BaseActivity {

	private TextView left_tv;
	private ListView menuListView;
	private List<Map<String, Object>> list;
	private Intent intent;
	private String[] menus = {"交通违章查询", "天气,PM2.5查询", "火车票查询", "飞机票查询",
			"影讯查询", "快递查询", "公交查询", "菜价查询", "驾校查询", "病症查询", "试题查询"};
	
	@Override
	protected int getContentViewID() {
		return R.layout.lifeservice_menu;
	}
	
	@Override
	protected void initView() {
		super.initView();
		left_tv = getView(R.id.left_tv);
		list = new ArrayList<Map<String, Object>>();
		menuListView = getView(R.id.lifeservice_menu_listview);
		SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.lifeservice_menu_item,
				new String[]{"title"}, new int[]{R.id.menu_tv});
		menuListView.setAdapter(adapter);
		
		intent = getIntent();
	}
	
	@Override
	protected void setListener() {
		super.setListener();
		left_tv.setOnClickListener(this);
		menuListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long id) {
				intent.setClass(LifeServiceMenuActivity.this, TrafficQueryActivity.class);
				intent.putExtra("title", menus[(int)id]);
				intent.putExtra("menu_idx", (int)id);
				startActivity(intent);
//				switch((int)id){
//				case 0:
//					intent.setClass(LifeServiceMenuActivity.this, TrafficQueryActivity.class);
//					startActivity(intent);
//					break;
//				}
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
        switch (v.getId()) {
        case R.id.left_tv:
        	finish();
        	break;
        }
	}
	
	@Override
	protected void getDatas() {
		super.getDatas();
		for(int i=0;i<menus.length;i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", menus[i]);
			list.add(map);
		}
	}
	
}
