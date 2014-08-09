package com.example.youhuipaipai.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.adapter.SubClassifyAdapter;
import com.example.youhuipaipai.entity.Classify;
import com.example.youhuipaipai.entity.ClassifyEntity;
import com.example.youhuipaipai.entity.SubClassify;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.MyPreference;
import frame.util.ToastUtil;

public class ClassifyActivity extends BaseActivity {

	private RelativeLayout all_ll, food_ll, move_ll, entertainment_ll, wineshop_ll, newpiece_ll, daijinquan_ll,
			beauty_ll;
	private ListView lv;
	private List<Classify> list;
	private SubClassifyAdapter adapter;
	private List<RelativeLayout> animViews;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8;

	@Override
	protected int getContentViewID() {
		return R.layout.classify_home;
	}

	@Override
	protected void initView() {
		super.initView();
		all_ll = getView(R.id.all_ll);
		food_ll = getView(R.id.food_ll);
		move_ll = getView(R.id.move_ll);
		entertainment_ll = getView(R.id.entertainment_ll);
		wineshop_ll = getView(R.id.wineshop_ll);
		newpiece_ll = getView(R.id.newpiece_ll);
		daijinquan_ll = getView(R.id.daijinquan_ll);
		beauty_ll = getView(R.id.beauty_ll);
		lv = getView(R.id.listview);
		lv.setVisibility(View.GONE);

		tv1 = getView(R.id.tv1);
		tv2 = getView(R.id.tv2);
		tv3 = getView(R.id.tv3);
		tv4 = getView(R.id.tv4);
		tv5 = getView(R.id.tv5);
		tv6 = getView(R.id.tv6);
		tv7 = getView(R.id.tv7);
		tv8 = getView(R.id.tv8);

		animViews = new ArrayList<RelativeLayout>();
		animViews.add(all_ll);
		animViews.add(food_ll);
		animViews.add(move_ll);
		animViews.add(entertainment_ll);
		animViews.add(wineshop_ll);
		animViews.add(newpiece_ll);
		animViews.add(daijinquan_ll);
		animViews.add(beauty_ll);

		adapter = new SubClassifyAdapter(null, CTX);
		Log.d(TAG, "CTX = " + CTX);
		lv.setAdapter(adapter);

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (list == null)
			return;
		adapter.clear();
		switch (v.getId()) {
		case R.id.all_ll:
			goByIntent("classifyname", "全部分类", AllshoplistActivity.class, false);
			break;
		case R.id.food_ll:
			processDataWithAnim(1);
			break;
		case R.id.move_ll:
			processDataWithAnim(2);
			break;
		case R.id.entertainment_ll:
			processDataWithAnim(3);
			break;
		case R.id.wineshop_ll:
			processDataWithAnim(4);
			break;
		case R.id.newpiece_ll:// 新单
			processDataWithAnim(5);
			break;
		case R.id.daijinquan_ll:// 代金券
			processDataWithAnim(6);
			break;
		case R.id.beauty_ll:// 丽人
			processDataWithAnim(7);
			break;
		}
	}

	private void processDataWithAnim(int location) {
		if (list.size() <= location)
			return;
		if (list.get(location) == null)
			return;
		if (lv.getTag() != null) {
			adapter.resetData(list.get(location).subclasslist);
		} else {
			adapter.resetData(list.get(location).subclasslist);

			lv.setVisibility(View.VISIBLE);
			Animation a = AnimationUtils.loadAnimation(CTX, R.anim.classify_list_show_anim);
			lv.startAnimation(a);
			lv.setTag(a);
		}
		for (int i = 0; i < animViews.size(); i++) {
			animViews.get(i).setBackgroundColor(i == location ? Color.parseColor("#F2F2F2") : Color.WHITE);
		}
	}

	@Override
	protected void setListener() {
		super.setListener();
		all_ll.setOnClickListener(this);
		food_ll.setOnClickListener(this);
		move_ll.setOnClickListener(this);
		entertainment_ll.setOnClickListener(this);
		wineshop_ll.setOnClickListener(this);
		newpiece_ll.setOnClickListener(this);
		daijinquan_ll.setOnClickListener(this);
		beauty_ll.setOnClickListener(this);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent i = new Intent(ClassifyActivity.this, CouponHomeActivity.class);
				i.putExtra("keyword", ((SubClassify) parent.getAdapter().getItem(position)).subclassname);
				i.putExtra("requestCommand", "SearchMerchantList");
				startActivity(i);
			}
		});
	}

	@Override
	protected void getDatas() {
		super.getDatas();
		showProgressDialog("正在加载", "请稍候...");
		getClassifylist();
	}

	private void getClassifylist() {
		HttpUtil hu = new HttpUtil();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("requestCommand", "ClassList");
		hm.put("type", MyPreference.getString(ClassifyActivity.this, "selectAreaId"));
		hu.post(this, hm, new CallbackListener() {
			@Override
			public void onSuccess(JSONObject result) {
				dismissProgressDialog();
				ClassifyEntity ce = JSON.parseObject(result.toJSONString(), ClassifyEntity.class);
				list = JSON.parseArray(JSON.toJSONString(ce.data), Classify.class);

				adapter.resetData(list.get(1).subclasslist);
				
				try {
					int i = 0;
					tv1.setText(list.get(i++).classname);
					tv2.setText(list.get(i++).classname);
					tv3.setText(list.get(i++).classname);
					tv4.setText(list.get(i++).classname);
					tv5.setText(list.get(i++).classname);
					tv6.setText(list.get(i++).classname);
					tv7.setText(list.get(i++).classname);
					tv8.setText(list.get(i++).classname);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailed(FailureResult result) {
				dismissProgressDialog();
				ToastUtil.showShort(ClassifyActivity.this, result.message);
			}
		});
	}
}
