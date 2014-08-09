package com.example.youhuipaipai.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youhuipaipai.R;

public class TrafficQueryActivity extends BaseActivity {
	
	private TextView left_tv, center_tv;
	private WebView webview;
	private String title;
	private int menuIdx;
	private String baseUrl = "http://rocknetwork.sinaapp.com/chaxun/";
	private String[] urls = {"weizhang.html", "tianqi.html", "huoche.html",
			"feijipiao.html", "yingxun.html", "kuaidi.html", "gongjiao.html",
			"caijia.html", "jiaxiao.html", "bingzheng.html", "shiti.html"};
	
	@Override
	protected int getContentViewID() {
		return R.layout.traffic_query;
	}
	
	@Override
	protected void initView() {
		super.initView();
		left_tv = getView(R.id.left_tv);
		center_tv = getView(R.id.center_tv);
		webview = getView(R.id.webview);
		
		title = getIntent().getStringExtra("title");
		center_tv.setText(title);
		menuIdx = getIntent().getIntExtra("menu_idx", 0);
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void setListener() {
		super.setListener();
		left_tv.setOnClickListener(this);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				showProgressDialog("正在请求","请稍候...");
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				dismissProgressDialog();
			}
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				dismissProgressDialog();
				Toast.makeText(TrafficQueryActivity.this, description, Toast.LENGTH_SHORT).show();
			}
		});
		webview.loadUrl(baseUrl + urls[menuIdx]);
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()){
			webview.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
