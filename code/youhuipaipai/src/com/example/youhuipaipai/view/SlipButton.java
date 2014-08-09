package com.example.youhuipaipai.view;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.example.youhuipaipai.R;

public class SlipButton extends View implements OnTouchListener{

	public boolean NowChoose = false;//男
	public boolean OnSlip = false;//记录用户是否在滑动的变量
	private float DownX,NowX;//按下时的x,当前的x,
	private Rect Btn_On,Btn_Off;//打开和关闭状态下,游标的Rect
	
	private boolean isChgLsnOn = false;
	private OnChangedListener ChgLsn;
	
	private Bitmap bg_girl,bg_boy,slip_girl;
	
	public SlipButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public SlipButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init(){//初始化
		//载入图片资源
		bg_girl = BitmapFactory.decodeResource(getResources(), R.drawable.gerenziliao_switch_1);
		bg_boy = BitmapFactory.decodeResource(getResources(), R.drawable.gerenziliao_switch_2);
		slip_girl = BitmapFactory.decodeResource(getResources(), R.drawable.gerenziliao_switch_button);
		/*slip_boy = BitmapFactory.decodeResource(getResources(), R.drawable.gerenziliao_switch_button);*/
		//获得需要的Rect数据
		Btn_On = new Rect(0,0,slip_girl.getWidth(),slip_girl.getHeight());
		Btn_Off = new Rect(
				bg_boy.getWidth()-slip_girl.getWidth(),
				0,
				bg_boy.getWidth(),
				slip_girl.getHeight());
		setOnTouchListener(this);//设置监听器,也可以直接复写OnTouchEvent
	}
	
	@Override
	protected void onDraw(Canvas canvas) {//绘图函数
		super.onDraw(canvas);
		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		float x;
		
		{
			if(NowX<(bg_girl.getWidth()/2))//滑动到前半段与后半段的背景不同,在此做判断
				if(NowChoose){
					canvas.drawBitmap(bg_girl,matrix, paint);//女的背景
				}else{
					canvas.drawBitmap(bg_boy,matrix, paint);//男的背景
				}
			else
				canvas.drawBitmap(bg_girl,matrix, paint);//女的背景
			
			if(OnSlip)//是否是在滑动状态,
			{
				if(NowX >= bg_girl.getWidth())//是否划出指定范围,不能让游标跑到外头,必须做这个判断
					x = bg_girl.getWidth()-slip_girl.getWidth()/2;//减去游标1/2的长度...
				else
					x = NowX - slip_girl.getWidth()/2;
			}else{//非滑动状态
				if(NowChoose)//根据现在的开关状态设置画游标的位置
					x = Btn_Off.left;
				else
					x = Btn_On.left;
			}
		if(x<0)//对游标位置进行异常判断...
			x = 0;
		else if(x>bg_girl.getWidth()-slip_girl.getWidth())
			x = bg_girl.getWidth()-slip_girl.getWidth();
			if(NowChoose){
				canvas.drawBitmap(slip_girl,x-5, 5, paint);//画出游标.
			}else{
				canvas.drawBitmap(slip_girl,x+5, 5, paint);
			}
		}
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction())//根据动作来执行代码
		{
		case MotionEvent.ACTION_MOVE://滑动
			NowX = event.getX();
			break;
		case MotionEvent.ACTION_DOWN://按下
		if(event.getX()>bg_girl.getWidth()||event.getY()>bg_girl.getHeight())
            return false;
			OnSlip = true;
			DownX = event.getX();
			NowX = DownX;
			break;
		case MotionEvent.ACTION_UP://松开
			OnSlip = false;
			boolean LastChoose = NowChoose;
			if(event.getX()>=(bg_girl.getWidth()/2))
				NowChoose = true;
			else
				NowChoose = false;
			if(isChgLsnOn&&(LastChoose!=NowChoose))//如果设置了监听器,就调用其方法..
				ChgLsn.onChanged(NowChoose);
			break;
		default:
		
		}
		invalidate();//重画控件
		return true;
	}
	
	public void SetOnChangedListener(OnChangedListener l){//设置监听器,当状态修改的时候
		isChgLsnOn = true;
		ChgLsn = l;
	}
	
}