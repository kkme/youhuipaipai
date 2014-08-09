package com.example.youhuipaipai.view;



import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.youhuipaipai.R;

/**
 * 页码展示控件 这里供ViewPager试用 当滑动至第几页时，第几个圆点为亮点
 * 
 * @author liuguangmao
 * 
 */
public class PageDisplay extends LinearLayout {

	private Context mContext;

	/**
	 * 总页数
	 */
	private int mPageCount;
	
	
	/**
	 * 当前页码
	 */
	private int mCurrentIndex;
	

	public PageDisplay(Context context) {
		super(context);
		this.mContext = context;
		mPageCount = 0;
	}

	public PageDisplay(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		mPageCount = 0;
	}

	private void initView() {
		setOrientation(LinearLayout.HORIZONTAL);
		/*//设置为包裹内容并居中
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_HORIZONTAL;
		setLayoutParams(params);*/
		setGravity(Gravity.CENTER);
		removeAllViews();
		for (int i = 0; i < mPageCount; i++) {
			ImageView imgv = obtainImageView();
			if (i == 0) {
				imgv.setBackgroundResource(R.drawable.shouye_banner_press);
			}else{
				imgv.setBackgroundResource(R.drawable.shouye_banner_normal);
			}
			addView(imgv);
		}
		mCurrentIndex = 0;
	}

	/**
	 * 设置总页数
	 * 
	 * @param pageCount
	 */
	public void setPageCount(int pageCount) {
		this.mPageCount = pageCount;
		initView();
	}
/*	public void addNewview(int currentIndex){
		ImageView iv=obtainImageView();
		addView(iv);
		setCurrentIndex(currentIndex);
	}*/
/*	public void removeOldView(int currentIndex){//移除当前导航点
		View view=this.getChildAt(currentIndex);
		removeView(view);
		View curIndexView = this.getChildAt(currentIndex-1); 
		curIndexView.setBackgroundResource(R.drawable.shouye_banner_press);
		this.mCurrentIndex = currentIndex-1;
	}*/
	/**
	 * 获取一个ImageView实例
	 */
	private ImageView obtainImageView() {
		ImageView imgv = new ImageView(mContext);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.leftMargin = 15;
		imgv.setLayoutParams(params);
		return imgv;
	}
	
	/**
	 * 设置当前页码
	 * @param currentIndex 当前页码
	 */
	public void setCurrentIndex(int currentIndex) {
		View oldIndexView  = this.getChildAt(mCurrentIndex);
		oldIndexView.setBackgroundResource(R.drawable.shouye_banner_normal);
		View curIndexView = this.getChildAt(currentIndex); 
		curIndexView.setBackgroundResource(R.drawable.shouye_banner_press);
		this.mCurrentIndex = currentIndex;
	}
}
