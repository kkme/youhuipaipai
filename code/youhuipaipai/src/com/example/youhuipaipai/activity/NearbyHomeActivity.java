package com.example.youhuipaipai.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import baidu.LocationUtil;
import baidu.LocationUtil.OnLocationGetListener;

import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.adapter.MerchantAdapter;
import com.example.youhuipaipai.entity.BaseMerchant;
import com.example.youhuipaipai.entity.Merchant;
import com.example.youhuipaipai.fragment.DropdownTwolistFragment;
import com.example.youhuipaipai.fragment.DropdownTwolistFragment.INotifyListener;
import com.example.youhuipaipai.view.Nearby_PopwindowView;
import com.example.youhuipaipai.view.Nearby_PopwindowView.NotifyListener;
import com.example.youhuipaipai.view.XListView;
import com.example.youhuipaipai.view.XListView.IXListViewListener;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.MyPreference;
import frame.util.TimeUtil;
import frame.util.ToastUtil;

public class NearbyHomeActivity extends BaseActivity implements
		IXListViewListener {
	private TextView left_tv, map_tv, nearby_tv, mylocation_tv;
	private ImageView refresh_iv;
	private LinearLayout refresh_ll;
	Animation animation;
	private XListView lv;
	private String longitude, latitude;
	private int pageIndex = 1;// 页数
	private MerchantAdapter adapter;
	private String classifyname;
	private String citycode="";
	private DropdownTwolistFragment fragment;
	
	private String popClassifyId;
	ArrayList<Merchant> list=new ArrayList<Merchant>();
	private int merchantTotalPage=0;
	
	private boolean isFromHeader=false;
	
	private Nearby_PopwindowView pow;
	protected String areaid="0";
	protected String ordertype="0";
	@Override
	protected int getContentViewID() {
		return R.layout.nearby_home;
	}

	@Override
	protected void initView() {
		super.initView();
		fragment=(DropdownTwolistFragment) getSupportFragmentManager().findFragmentById(R.id.dropdownTwolistFragment);
		
		left_tv = getView(R.id.left_tv);
		map_tv = getView(R.id.map_tv);
		nearby_tv = getView(R.id.nearby_tv);
		refresh_ll = getView(R.id.refresh_ll);
		refresh_iv = getView(R.id.refresh_iv);
		mylocation_tv = getView(R.id.mylocation_tv);
		lv = getView(R.id.lv);
		adapter=new MerchantAdapter(list, NearbyHomeActivity.this);
		lv.setAdapter(adapter);
		animation = AnimationUtils.loadAnimation(this, R.anim.loading_img);
		LinearInterpolator li = new LinearInterpolator();
		animation.setInterpolator(li);
		classifyname=getIntent().getStringExtra("keyCode");
		nearby_tv.setText(classifyname);
	}

	@Override
	protected void setListener() {
		super.setListener();
		left_tv.setOnClickListener(this);
		nearby_tv.setOnClickListener(this);
		refresh_ll.setOnClickListener(this);
		map_tv.setOnClickListener(this);
		showProgressDialog("正在加载", "请稍候...");
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				List <Merchant>list=adapter.getList();
				if(null!=list&&list.size()>1&&position!=list.size()-1){
					String merchantid=((MerchantAdapter)parent.getAdapter().getItem(position)).getItem(position).getMerchantid();
					goByIntent("merchantid",merchantid, ShopDetailActivity.class, false);
				}
			}	
		});
		lv.setXListViewListener(this);
		String timeString=TimeUtil.getNowTime();
		MyPreference.putString(NearbyHomeActivity.this, "timeNow", timeString);
		lv.setRefreshTime(timeString);
		fragment.setListener(new INotifyListener() {
			@Override
			public void notify(String classifyId, String areaId, String orderid) {
				NearbyHomeActivity.this.popClassifyId = classifyId;
				NearbyHomeActivity.this.areaid = areaId;
				NearbyHomeActivity.this.ordertype = orderid;
                pageIndex = 1;
                isFromHeader=true;
				showProgressDialog("正在请求","请稍候...");
                getList();
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
		case R.id.nearby_tv:
			if("".equals(citycode)){
				ToastUtil.showShort(NearbyHomeActivity.this,getString(R.string.no_now_address));	
				return;
			}
			 pow = new Nearby_PopwindowView(this,
					new NotifyListener() {

						@Override
						public void notify(String selectString,String name) {
							popClassifyId=selectString;
							nearby_tv.setText(name);
							isFromHeader=true;
							getList();
							pageIndex=1;
							showProgressDialog("正在请求","请稍候...");
						}
					},citycode);
//			pow.setType(citycode);
			pow.show(nearby_tv);
			break;
		case R.id.refresh_ll:
			initMyLocation();
			break;
		case R.id.map_tv:
			goByIntent("merchants", list, MapActivity.class, false);
			break;
			
		}
	}

	@Override
	protected void getDatas() {
		super.getDatas();
		initMyLocation();
	}

	private void initMyLocation() {
		refresh_iv.startAnimation(animation);
		LocationUtil.getInstance(this).getLocation(new OnLocationGetListener() {
			@Override
			public void onGetLocation(BDLocation location) {
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						refresh_iv.clearAnimation();
					}
				}, 1000);
				mylocation_tv.setText("当前位置：" + location.getAddrStr());
				longitude = location.getLongitude() + "";
				latitude = location.getLatitude() + "";
				citycode=MyPreference.getString(NearbyHomeActivity.this, "selectAreaId");
				getList();
			}
		});
	}

	@Override
	public void onRefresh() {
		pageIndex = 1;
		String refreshTime=MyPreference.getString(this, "timeNow");
		lv.setRefreshTime(refreshTime);
		getList();
	}

	@Override
	public void onLoadMore() {
		
		++pageIndex;
		getList();

	}

	private void getList() {//获取附近商家列表
		HttpUtil hu = new HttpUtil();
		HashMap<String, String> hm = new HashMap<String, String>();
		if(isFromHeader){
			hm.put("requestCommand", "NearMerchantByList");
			hm.put("classid",popClassifyId);
		}else{
			hm.put("requestCommand", "NearMerchant");
			hm.put("classname",classifyname);
		}
		hm.put("index",pageIndex+"");
		hm.put("type",citycode);
		hm.put("longitude", longitude);
		hm.put("latitude", latitude);
		hm.put("areaid", areaid);
		hm.put("ordertype", ordertype);
		hu.post(this, hm, new CallbackListener() {
			@Override
			public void onSuccess(JSONObject result) {
				dismissProgressDialog();
				BaseMerchant merchant=JSONObject.parseObject(result.toJSONString(),BaseMerchant.class);
				if(!lv.mPullLoading){
					list.clear();
				}  
				list.addAll(merchant.getData());
				
				if(pageIndex==1){
					adapter.clear();
					if(list.size()==0){
						ToastUtil.showShort(NearbyHomeActivity.this, getString(R.string.no_data));
					}
				}
				merchantTotalPage=Integer.parseInt(merchant.getTotalpage());
				adapter.resetData(list);
					if(lv.mPullRefreshing){
						lv.stopRefresh();
						String timeString=TimeUtil.getNowTime();
						MyPreference.putString(NearbyHomeActivity.this, "timeNow", timeString);
					}
					if(lv.mPullLoading){
						lv.stopLoadMore();
					}
					if(merchantTotalPage==pageIndex||merchantTotalPage==0){
						lv.setPullLoadEnable(false);
//						ToastUtil.showShort(this, getString(R.string.xlistview_footer_add_over));
//						return;
					}else{
						lv.setPullLoadEnable(true);
					}
					
				
				
			}
			@Override
			public void onFailed(FailureResult result) {
				dismissProgressDialog();
				ToastUtil.showShort(NearbyHomeActivity.this, result.errorString);
				TextView textView=new TextView(NearbyHomeActivity.this);
				textView.setText("加载失败");
				textView.setTextSize(30);
				lv.setEmptyView(textView);
			}
		});
	}

}
