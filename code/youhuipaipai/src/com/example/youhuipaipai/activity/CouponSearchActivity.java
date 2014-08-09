package com.example.youhuipaipai.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.entity.Area;
import com.example.youhuipaipai.entity.BaseArea;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.MyPreference;
import frame.util.HttpUtil.CallbackListener;
import frame.util.ToastUtil;

/**    
 * @项目名:youhuipaipai  
 * 
 * @类名:CouponSearchActivity.java  
 * 
 * @创建人:liulong 
 *
 * @类描述:优惠券搜索
 * 
 * @date:2014-3-21
 * 
 * @Version:1.0 
 *
 ******************************************/ 
public class CouponSearchActivity extends BaseActivity{
	
	private TextView back_tv;
	private Button search_btn,searchdel_btn;
	private EditText search_et;
	private String typeid;//所在地区的编号
	private Button button1,button2,button3,button4,button5,button6,
			button7,button8,button9,button10,button11,button12;
	private Button[] keySearchs;
	private Button[] recentButtons;
	
	private String recentSearchString;
	
	private String[]searchStrings;
	@Override
	protected int getContentViewID() {
		return R.layout.coupon_search;
	}
	
	@Override
	protected void initView() {
		super.initView();
		back_tv=getView(R.id.back_tv);
		search_btn=getView(R.id.search_btn);
		searchdel_btn=getView(R.id.searchdel_btn);
		search_et=getView(R.id.search_et);
		typeid=MyPreference.getString(CouponSearchActivity.this, "selectAreaId");
        
		button1=getView(R.id.button1);
		button2=getView(R.id.button2);
		button3=getView(R.id.button3);
		button4=getView(R.id.button4);
		button5=getView(R.id.button5);
		button6=getView(R.id.button6);
		button7=getView(R.id.button7);
		button8=getView(R.id.button8);
		button9=getView(R.id.button9);
		button10=getView(R.id.button10);
		button11=getView(R.id.button11);
		button12=getView(R.id.button12);
		recentButtons=new Button[]{button1,button2,button3,button4,button5,button6};
		keySearchs=new Button[]{button7,button8,button9,button10,button11,button12};
		recentSearchString=MyPreference.getString(this,"recentSearch");
		if(recentSearchString.contains(",")){
			searchStrings=recentSearchString.split(",");
			int searchCount=searchStrings.length;
			for(int i=0;i<6;i++){
				if(i<searchCount){
					recentButtons[i].setText(searchStrings[i]);
				}else{
					recentButtons[i].setVisibility(View.INVISIBLE);
				}
				recentButtons[i].setOnClickListener(this);
				keySearchs[i].setOnClickListener(this);
			}
		}else{
			searchStrings=new String[]{};
			for(int i=0;i<6;i++){
				recentButtons[i].setVisibility(View.INVISIBLE);	
			}
		}
		
	}

	@Override
	protected void setListener() {
		super.setListener();
		back_tv.setOnClickListener(this);
		search_btn.setOnClickListener(this);
		searchdel_btn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.back_tv:
			finish();
			break;

		case R.id.search_btn:
			String resultString=search_et.getText().toString();
			if("".equals(resultString)){
				ToastUtil.showShort(CouponSearchActivity.this, getString(R.string.no_search_content));
				return;
			}
			if(recentSearchString.contains(resultString)){
				return;
			}
			if(null!=searchStrings){
				if(searchStrings.length<6){
					recentSearchString=resultString+","+recentSearchString;
				}else if(searchStrings.length==6){
					int pos=recentSearchString.lastIndexOf(",");
					recentSearchString=recentSearchString.substring(0, pos);
					recentSearchString=resultString+","+recentSearchString;
				}
			}
			setIntentResult(null,resultString);
			break;
			
		case R.id.searchdel_btn:
			search_et.getText().clear();
			break;
		case R.id.button1:
			setIntentResult(button1,"");
			break;
		case R.id.button2:
			setIntentResult(button2,"");
			break;
		case R.id.button3:
			setIntentResult(button3,"");
			break;
		case R.id.button4:
			setIntentResult(button4,"");
			break;
		case R.id.button5:
			setIntentResult(button5,"");
			break;
		case R.id.button6:
			setIntentResult(button6,"");
			break;
		case R.id.button7:
			setIntentResult(button7,"");
			break;
		case R.id.button8:
			setIntentResult(button8,"");
			break;
		case R.id.button9:
			setIntentResult(button9,"");
			break;
		case R.id.button10:
			setIntentResult(button10,"");
			break;
		case R.id.button11:
			setIntentResult(button11,"");
			break;
		case R.id.button12:
			setIntentResult(button12,"");
			break;
			
		}
	}
	@Override
	protected void getDatas() {
		super.getDatas();
		getHotKeyWord();
	}
	
	@Override
	protected void onStop() {
		MyPreference.putString(this, "recentSearch", recentSearchString);
		super.onStop();
	}
	
	private List<Area> areas=new ArrayList<Area>();
	
	private void getHotKeyWord(){
		showProgressDialog("正在请求","请稍候...");
		HttpUtil hu=new HttpUtil();
		HashMap<String, String> hm=new HashMap<String, String>();
		hm.put("requestCommand", "GetHotKeyWord");
		hm.put("type", typeid);//所在地区的编号
		hu.post(this, hm, new CallbackListener() {
			@Override
			public void onSuccess(JSONObject result) {
				BaseArea area=JSON.parseObject(result.toJSONString(),BaseArea.class);
				areas=area.getData();
				int count =areas.size();
				for(int i=0;i<6;i++){
					if(i<count){
						keySearchs[i].setText(areas.get(i).getName());
					}else{
						keySearchs[i].setVisibility(View.INVISIBLE);
					}
				}
				
				dismissProgressDialog();
			}
			@Override
			public void onFailed(FailureResult result) {
				dismissProgressDialog();
				ToastUtil.showShort(CouponSearchActivity.this, result.message);
			}
		});
	}
	
	/**
	 * @param button
	 * @param keyword
	 * 回调
	 */
	private void setIntentResult(Button button,String keyword){
		if("".equals(keyword)){
			keyword=button.getText().toString();
		}
		if(null!=keyword){
			Intent intent=new Intent(this,AllshoplistActivity.class);
			intent.putExtra("keyword", keyword);
			setResult(1, intent);
			finish();
		}
	}
}
