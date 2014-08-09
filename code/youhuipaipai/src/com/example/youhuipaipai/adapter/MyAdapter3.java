package com.example.youhuipaipai.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.entity.OrderType;

public class MyAdapter3 extends BaseAdapter {

	Context context;
	LayoutInflater inflater;
	List<OrderType> list;
	int last_item;
	private int selectedPosition = -1;

	public MyAdapter3(Context context, List<OrderType> list) {
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public OrderType getItem(int position) {

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
		holder.textView.setText(list.get(position).orderText);
		holder.textView.setTextColor(Color.BLACK);
		holder.count.setVisibility(View.GONE);
		return convertView;
	}

	public static class ViewHolder {
		public TextView textView, count;
		public LinearLayout layout;
	}

	public void setSelectedPosition(int position) {
		selectedPosition = position;
		notifyDataSetChanged();
	}

	public void setSelectString(String s) {
		if (list.contains(s)) {
			selectedPosition = list.indexOf(s);
			notifyDataSetChanged();
		}

	}
}
