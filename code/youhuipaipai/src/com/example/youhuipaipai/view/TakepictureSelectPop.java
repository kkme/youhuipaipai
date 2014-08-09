package com.example.youhuipaipai.view;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.youhuipaipai.R;

public class TakepictureSelectPop implements View.OnClickListener{
	
	/*从底部浮出的选择框 */
	private PopupWindow window;
	private Activity context;
	
	protected TextView title, line1, line2, line3, line4, toSea;
	
	private OnPopButtonClickedListener listener;
	
	public TakepictureSelectPop(Activity context){
		this.context = context;
		initContentView(context);
		//设置动画效果
		window.setAnimationStyle(R.style.head_pop_anim_style);
	}
	
	private void initContentView(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.head_selecte_pop_window, null);

		line1 = (TextView) view.findViewById(R.id.mine_head_selected_form_camera);
		line1.setOnClickListener(this);
		line2 = (TextView) view.findViewById(R.id.mine_head_selected_form_local);
		line2.setOnClickListener(this);
		line3 = (TextView) view.findViewById(R.id.mine_head_selected_cancel);
		line3.setOnClickListener(this);
		window = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		window.setOutsideTouchable(false);
		window.setBackgroundDrawable(new ColorDrawable());
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.mine_head_selected_form_camera:
			window.dismiss();
			listener.line1Clicked();
			break;
		case R.id.mine_head_selected_form_local:
			window.dismiss();
			listener.line2Clicked();
			break;
		case R.id.mine_head_selected_cancel:
			window.dismiss();
			listener.line3Clicked();
			break;
		default:
			break;
		}
	}
	
	
	public void setOnPopButtonClickedListener(OnPopButtonClickedListener listener){
		this.listener = listener;
	}
	
	public void show(View parent){
		window.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
	}
	
	public interface OnPopButtonClickedListener{
		void line1Clicked();
		void line2Clicked();
		void line3Clicked();

		
	}
}
