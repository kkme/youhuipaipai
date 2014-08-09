package com.example.youhuipaipai.adapter;



import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.youhuipaipai.activity.AdvertisementActivity;
import com.example.youhuipaipai.activity.ShopDetailActivity;


public class ViewPageAdapter extends PagerAdapter {
	private Context context;
	/**
	 * view集合
	 */
	private List<View> mListViews;
	private boolean isclick;//是否可以点击页面
	public ViewPageAdapter(Context context, List<View> views,boolean isclick) {
		this.context = context;
		this.mListViews = views;
		this.isclick=isclick;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mListViews.get(position));// 删除页卡
	}

	@Override
	public int getCount() {
		return mListViews.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) { // 这个方法用来实例化页卡
		View view=mListViews.get(position);
//		if(isclick){
//			view.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					Intent intent = new Intent(context, AdvertisementActivity.class);
//					context.startActivity(intent);
//				}
//			});
//		}
		
		container.addView(view);// 添加页卡
		return mListViews.get(position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

}
