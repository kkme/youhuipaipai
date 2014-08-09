package com.example.youhuipaipai.view;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;


public abstract class BaseView implements OnClickListener {
	
	protected Context mContext;
	protected View contentView;

	public BaseView(Context context) {
		mContext = context;
		inflateView(setLayout());
		initViews();
		setListeners();
	}
	private void inflateView(int id) {
		contentView = View.inflate(mContext, id, null);
	}

	protected abstract int setLayout();
	public <A extends View> A getView(int id){
		return (A) contentView.findViewById(id);
	}
	protected void initViews() {
	}
	protected void setListeners() {

	}

	@Override
	public void onClick(View v) {

	}

	public View getContent() {
		return contentView;
	}
}
