package com.example.youhuipaipai.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.adapter.ViewPageAdapter;
import com.example.youhuipaipai.entity.LoadBean;
import com.example.youhuipaipai.entity.LoadingImage;
import com.example.youhuipaipai.view.PageDisplay;

import frame.http.bean.FailureResult;
import frame.imgtools.ImgShowUtil;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;

//加载页后的广告页
public class LoadingActivity extends BaseActivity implements OnPageChangeListener{
	private List<View> views;
	private ViewPager viewPager;
	private PageDisplay pageDisplay;
	private LoadingImage loadBean;
	private List<LoadingImage> list;
	private int current=0;
	@Override
	protected int getContentViewID() {
		return R.layout.loading;
	}
	@Override
	protected void initView() {
		super.initView();
		viewPager=getView(R.id.viewPager);
		pageDisplay=getView(R.id.pageDisplay);
		
	}
	
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				current++;
				if(current==list.size()){
					goByIntent(HomeActivity.class,true);	
				}
				viewPager.setCurrentItem(current);
				mHandler.sendEmptyMessageDelayed(1, 1000);
				
				break;

			default:
				break;
			}
			
		};
	};
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
		
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
		
	}
	
	@Override
	public void onPageSelected(int position) {
		pageDisplay.setCurrentIndex(position);
	}
	@Override
	protected void getDatas() {
		super.getDatas();
		getImages();
		
	}
	private void initGuider(int pageSize) {
		if(pageSize==0){
			goByIntent(HomeActivity.class,true);
		}
		views = new ArrayList<View>();
		for (int i = 0; i < pageSize; i++) {
			views.add(getPagerItem(i));
		}
		pageDisplay.setPageCount(pageSize);
		ViewPageAdapter adapter = new ViewPageAdapter(CTX, views,false);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(this);
	}
	private View getPagerItem(int pos) {
		ImageView item = new ImageView(this);
		ImgShowUtil isu=new ImgShowUtil(list.get(pos).getStartPage_AdvertisementImg(), list.get(pos).getStartPage_ID());
		isu.setCoverDown(item);
		return item;
	}
	private void getImages(){
		HttpUtil hu=new HttpUtil();
		HashMap<String, String> hm=new HashMap<String, String>();
		hm.put("requestCommand", "LoadingAdPages");
		hm.put("type", "0");
		hu.post(this, hm, new CallbackListener() {
			@Override
			public void onSuccess(JSONObject result) {	
				LoadBean loadBean=JSON.parseObject(result.toJSONString(), LoadBean.class);
				list=loadBean.getData();
				initGuider(list.size());
				mHandler.sendEmptyMessageDelayed(1, 1500);
			}
			
			@Override
			public void onFailed(FailureResult result) {
				Log.e("onFailed", "--->"+result.errorString);
				initGuider(0);
			}
		});
	}
}
