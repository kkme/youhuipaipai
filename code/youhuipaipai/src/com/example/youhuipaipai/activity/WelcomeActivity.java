package com.example.youhuipaipai.activity;

import java.util.HashMap;

import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.entity.LoadingImage;

import frame.http.bean.FailureResult;
import frame.imgtools.ImgShowUtil;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;

//欢迎页
public class WelcomeActivity extends BaseActivity{
	private ImageView loadImage;
	@Override
	protected int getContentViewID() {
		return R.layout.welcome;
	}
	@Override
	protected void initView() {
		super.initView();
		loadImage=getView(R.id.iv);
		loadImage.setBackgroundResource(R.drawable.loading);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				goByIntent(LoadingActivity.class, true);
			}
		}, 1000);
	}
}
