package com.example.youhuipaipai.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.adapter.ArrayWheelAdapter;

//只是包含一个view
public class WheelViewPop {

    /* 从底部浮出的选择框 */
    private PopupWindow window;
    public WheelView wv;
    private String[]cityArray;
    public WheelViewPop(Context context,String[]cityArray) {
    	this.cityArray=cityArray;
        initContentView(context);
        // 设置动画效果
        window.setAnimationStyle(R.style.head_pop_anim_style);
    }


    private void initContentView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.wheelview,
                null);
        wv = (WheelView) view.findViewById(R.id.citys_wv);
        wv.setAdapter(new ArrayWheelAdapter<String>(cityArray));
        wv.setCurrentItem(0);
        
        wv.setHandler(handler);
        window = new PopupWindow(view, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, true);
        window.setOutsideTouchable(false);
        window.setBackgroundDrawable(new ColorDrawable());
    }

    public void show(View parent) {
        window.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    TextView textView;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            textView.setText(cityArray[wv.getCurrentItem()]);
        };
    }   ;

}
