package com.example.youhuipaipai.listener;

import java.util.List;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;

public class ViewPageChangeListener implements OnPageChangeListener {
	private ViewPager viewPager;
	private List<ImageView> ivs;

	public ViewPageChangeListener(ViewPager viewpager, List<ImageView> ivs) {
		this.viewPager=viewpager;
		this.ivs=ivs;
	}

	public void onPageSelected(int position) {
		viewPager.setCurrentItem(position);
		for (ImageView iv : ivs) {
			
			
		}
	}

	public void onPageScrollStateChanged(int arg0) {

	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}
}