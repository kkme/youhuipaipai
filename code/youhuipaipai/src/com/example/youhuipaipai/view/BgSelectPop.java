package com.example.youhuipaipai.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.example.youhuipaipai.R;

public class BgSelectPop implements View.OnClickListener {

	/* 从底部浮出的选择框 */
	private PopupWindow window;
	private Context context;

	private HeadPicListener listener;

	public BgSelectPop(Context context, boolean flag) {
		this.context = context;
		initContentView(context, flag);
		// 设置动画效果
		window.setAnimationStyle(R.style.head_pop_anim_style);
	}

	private void initContentView(Context context, boolean flag) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.axure_middle_publish_photo_dialog, null);
		view.findViewById(R.id.btn_email).setOnClickListener(this);
		view.findViewById(R.id.btn_sina).setOnClickListener(this);
		view.findViewById(R.id.btn_cancel).setOnClickListener(this);
		view.findViewById(R.id.btn_weixin).setOnClickListener(this);
		view.findViewById(R.id.btn_message).setOnClickListener(this);
		if (flag) {
			view.findViewById(R.id.btn_save_image).setVisibility(View.GONE);
		}
		view.findViewById(R.id.btn_save_image).setOnClickListener(this);
		view.findViewById(R.id.btn_send_message).setOnClickListener(this);
		view.findViewById(R.id.btn_weixin_friend).setOnClickListener(this);
		window = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		window.setOutsideTouchable(false);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_email:
			if (listener != null)
				listener.sendByEmail();
			window.dismiss();
			break;
		case R.id.btn_sina:
			if (listener != null)
				listener.sendBySina();
			window.dismiss();
			break;
		case R.id.btn_cancel:
			window.dismiss();
			break;

		case R.id.btn_weixin:
			if (listener != null)
				listener.sendByWeiXin();
			window.dismiss();
			break;
		case R.id.btn_message:
			if (listener != null)
				listener.sendByMessage();
			window.dismiss();
			break;
		case R.id.btn_weixin_friend:
			if (listener != null)
				listener.sendByWeiXinFriend();
			window.dismiss();
			break;

		case R.id.btn_send_message:
			if (listener != null)
				listener.sendByMyMessage();
			window.dismiss();
			break;
		case R.id.btn_save_image:
			if (listener != null)
				listener.savePic();
			window.dismiss();
			break;
		}
	}

	/*
	 * set the action listener
	 */
	public void setHeadActionListener(HeadPicListener listener) {
		this.listener = listener;
	}

	/*
	 * make the window show
	 */
	public void show(View parent) {
		window.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
	}

	public interface HeadPicListener {

		/**
		 * 通过邮件分享
		 */
		public void sendByEmail();

		/**
		 * 通过信息分享
		 */
		public void sendByMessage();

		/**
		 * 通过新浪分享
		 */
		public void sendBySina();

		/**
		 * 通过微信分享
		 */
		public void sendByWeiXin();

		/**
		 * 通过微信分享到好友
		 */
		public void sendByWeiXinFriend();

		/**
		 * 保存图片到本地
		 */
		public void savePic();

		/**
		 * 通过信息分享
		 */
		public void sendByMyMessage();

	}
}
