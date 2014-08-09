package com.example.youhuipaipai.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.application.PullService;

import frame.util.MyPreference;

public class HomeActivity extends ActivityGroup {

	private RadioGroup bottom_rg;// 定义 获取 设置监听 写处理方法
	private LinearLayout container_ll;
	private String longitude, latitude;
	private long exitTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		bottom_rg = (RadioGroup) findViewById(R.id.bottom_rg);
		container_ll = (LinearLayout) findViewById(R.id.container_ll);
		((RadioButton) bottom_rg.findViewById(R.id.bottom_main_rb)).setChecked(true);
//        LocationUtil.getInstance(this).getLocation(new OnLocationGetListener() {
//        	@Override
//        	public void onGetLocation(BDLocation location) {
//        		longitude = location.getLongitude() + "";
//        		latitude = location.getLatitude() + "";
//        		MyPreference.putString(HomeActivity.this, "longitude", longitude);
//        		MyPreference.putString(HomeActivity.this, "latitude", latitude);
//        	}
//        });
		switchActivity(1);
		setListener();
		if (MyPreference.getBoolean(this, Constant.IS_PUSH)) {
			PullService.stopAction(this);
		} else {
			PullService.startAction(this);
		}
	}

	protected void setListener() {
		bottom_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.bottom_main_rb:
					switchActivity(1);
					break;
				case R.id.bottom_classify_rb:
					switchActivity(2);
					break;
				case R.id.bottom_sign_rb:
					switchActivity(3);
					break;
				case R.id.bottom_coupon_rb:
					switchActivity(4);
					break;
				case R.id.bottom_my_rb:
					switchActivity(5);
					break;
				}

			}
		});
	}

	private void switchActivity(int flag) {
		Intent intent = new Intent();
		if (flag == 1) {
			intent.setClass(this, MainActivity.class);
		} else if (flag == 2) {
			intent.setClass(this, ClassifyActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		} else if (flag == 3) {
			intent.setClass(this, MipcaActivityCapture.class);
		} else if (flag == 4) {
			intent.setClass(this, CouponHomeActivity.class);
		} else if (flag == 5) {
			intent.setClass(this, MyActivity.class);
		}
		// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// Activity 转为 View
		Window subActivity = getLocalActivityManager().startActivity(flag + "", intent);
		container_ll.removeAllViews();
		// 容器添加View
		container_ll.addView(subActivity.getDecorView(), LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}

//	@Override
//	public void onBackPressed() {
//		
//	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		
		if(event.getKeyCode()==KeyEvent.KEYCODE_BACK&&event.getAction()!=KeyEvent.ACTION_UP){
			if (System.currentTimeMillis() - exitTime > 2000) {// 如果两次按键时间间隔大于800毫秒，则不退出
				Toast.makeText(HomeActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();// 更新firstTime
			} else {
				finish();
			}
			return true;
		}

		return super.dispatchKeyEvent(event);
	}

}
