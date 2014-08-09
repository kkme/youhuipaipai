package com.example.youhuipaipai.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.entity.BaseNearbyClassify;
import com.example.youhuipaipai.entity.NearbyClassify;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.ToastUtil;

public class Nearby_PopwindowView {
	private Context context;
	private PopupWindow window;
	private String selectString;
	private NotifyListener listener;
	private ArrayList<NearbyClassify> s;
	private PopAdapter popAdapter;
	private String type;//地区编号
	public Nearby_PopwindowView(Context context,NotifyListener listener,String type) {
		this.context=context;
		s=new ArrayList<NearbyClassify>();
		this.type=type;
		this.listener=listener;
		popAwindow();
	}
	public interface NotifyListener{
		void notify(String selectString,String name);
	}
	public void popAwindow() {
		getClassifyList();
		View layout = (View) LayoutInflater.from(context).inflate(R.layout.popwindow_listview, null);
		ListView lv=(ListView) layout.findViewById(R.id.lv);
		popAdapter=new PopAdapter();
		lv.setAdapter(popAdapter);
		if (window == null) {
			WindowManager windowManager = (WindowManager)context.getSystemService(Activity.WINDOW_SERVICE);
			window = new PopupWindow(layout, windowManager.getDefaultDisplay()
					.getWidth(),windowManager.getDefaultDisplay().getHeight(), true);
		}
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectString=s.get(position).getId();
				String nameString=s.get(position).getName();
				listener.notify(selectString,nameString);
				window.dismiss();
			}
			
		});
		window.setContentView(layout);
		window.setBackgroundDrawable(context.getResources().getDrawable(
				R.color.white));
	}
	class PopAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			return s.size();
		}

		@Override
		public Object getItem(int position) {	
			return s.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView=LayoutInflater.from(context).inflate(R.layout.listview_item, null);
			TextView tv=(TextView) convertView.findViewById(R.id.tv);
			tv.setText(s.get(position).getName());
			return convertView;
		}
	}
	public void show(View v){
		window.showAsDropDown(v, 0, 10);
	}
	public void dismss(){
		window.dismiss();
	}
	private void getClassifyList(){//获取分类列表
		HttpUtil hu = new HttpUtil();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("requestCommand", "GetFirstClass");
		hm.put("type", type);
		hu.post(context, hm, new CallbackListener() {

			@Override
			public void onSuccess(JSONObject result) {
				BaseNearbyClassify classify=JSONObject.parseObject(result.toJSONString(),BaseNearbyClassify.class);
				s.clear();
				s.addAll(classify.getData());
				popAdapter.notifyDataSetChanged();
			}
			@Override
			public void onFailed(FailureResult result) {
				ToastUtil.showShort(context, result.errorString);
			}
		});
	}
	
	/**
	 * @param type
	 * 设置地区
	 */
	public void setType(String type){
		this.type=type;
	}
}
