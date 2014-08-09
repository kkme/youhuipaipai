package com.example.youhuipaipai.activity;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.TextView;
import baidu.LocationUtil;
import baidu.LocationUtil.OnLocationGetListener;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.application.MyApplication;
import com.example.youhuipaipai.entity.Merchant;

public class MapActivity extends BaseActivity{
	private MapView bmapView;
	private ArrayList<Merchant> merchants;
	private MapController controller;
	private TextView leftText,centerText,mylocation_tv;
	@Override
	protected int getContentViewID() {
		return R.layout.mapview_shopplace;
	}
	@Override
	protected void initView() {
		super.initView();
		MyApplication application=(MyApplication) getApplication();
		application.initManager(getApplicationContext());
		bmapView=getView(R.id.bmapView);
		leftText=getView(R.id.tv_back);
		centerText=getView(R.id.center_tv);
		mylocation_tv = getView(R.id.mylocation_tv);
		leftText.setOnClickListener(this);
		controller = bmapView.getController();
		controller.setZoom(12);
//		bmapView.setBuiltInZoomControls(true);  
		merchants=(ArrayList<Merchant>) getIntent().getSerializableExtra("merchants");
		final LocationUtil lu=LocationUtil.getInstance(this);
		lu.getLocation(new OnLocationGetListener() {
			
			@Override
			public void onGetLocation(BDLocation location) {
				mylocation_tv.setText("当前位置：" + location.getAddrStr());
				lu.drawIcons(MapActivity.this,location.getLongitude()+"",location.getLatitude()+"", merchants, bmapView);
				GeoPoint point=new GeoPoint((int)( location.getLatitude()*1e6), (int)(location.getLongitude()*1e6));
				controller.animateTo(point);
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_back:
			finish();
			break;
		default:
			break;
		}
		super.onClick(v);
	}
}
