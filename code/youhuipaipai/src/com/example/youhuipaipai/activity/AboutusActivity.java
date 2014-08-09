package com.example.youhuipaipai.activity;

import android.view.View;
import android.widget.TextView;

import com.example.youhuipaipai.R;

public class AboutusActivity extends BaseActivity{

	private TextView about_back;
	@Override
	protected int getContentViewID() {
		
		return R.layout.more_about;
	}
	@Override
	protected void initView() {
		super.initView();
		about_back=getView(R.id.about_back);
		
	}
	@Override
	protected void setListener() {
		super.setListener();
		about_back.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.about_back:
			finish();
			
			break;

		default:
			break;
		}
	}
	
}
