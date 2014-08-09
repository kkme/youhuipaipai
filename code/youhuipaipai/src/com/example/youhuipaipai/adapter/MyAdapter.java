package com.example.youhuipaipai.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.entity.Classify;

public class MyAdapter extends BaseAdapter {

	Context context;
	LayoutInflater inflater;
	List<Classify> list;
	private int selectedPosition = -1;

	public MyAdapter(Context context, List<Classify> list) {
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.dropdownlistview_item, null);
			holder = new ViewHolder();
			holder.textView = (TextView) convertView.findViewById(R.id.dropdown_item_name);
			holder.count = (TextView) convertView.findViewById(R.id.dropdown_item_count);
			holder.layout = (LinearLayout) convertView.findViewById(R.id.colorlayout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 设置选中效果
		if (selectedPosition == position) {
			holder.layout.setBackgroundResource(R.drawable.sousuojieguo_zhuye_shaixuan_listleft_buttom_press);
		} else {
			holder.layout.setBackgroundResource(R.drawable.sousuojieguo_zhuye_shaixuan_listleft_buttom);
		}
		Classify c = (Classify) getItem(position);
		holder.textView.setText(c.classname);
//		int itemCount = c.subclasslist == null || (c.subclasslist != null && c.subclasslist.size() == 0) ? 0
//				: c.subclasslist.size();
//		holder.count.setText(position == 0 ? accountTotal() + "" : itemCount + "");
		holder.count.setText(c.count + "");
		holder.textView.setTextColor(Color.BLACK);
		return convertView;
	}
	
	private int accountTotal(){
		int i = 0;
		for(Classify c : list){
			i += c.subclasslist.size();
		}
		if(selectedPosition != -1)
			return i - list.get(selectedPosition).subclasslist.size();
		return i;
	}

	public static class ViewHolder {
		public TextView textView, count;
		public LinearLayout layout;
	}

	public void setSelectedPosition(int position) {
		selectedPosition = position;
		notifyDataSetChanged();
	}
//	public void setSelectString(Classify classify){
//		if(list.contains(classify)){
//			selectedPosition=list.indexOf(classify);
//			notifyDataSetChanged();
//		}
//	}
}
