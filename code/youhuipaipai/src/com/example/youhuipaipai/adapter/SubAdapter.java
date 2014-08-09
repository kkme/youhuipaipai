package com.example.youhuipaipai.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.entity.SubClassify;

public class SubAdapter extends BaseAdapter {
	
	Context context;
	LayoutInflater layoutInflater;
	List<SubClassify> list;
	public int position;
	private int selectedPosition;
	public SubAdapter(Context context, List<SubClassify> list) {
		this.context = context;
		this.list = list;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public SubClassify getItem(int pos) {
	
		return list.get(pos);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void updateList(List<SubClassify> newList){
		list.clear();
		list.addAll(newList);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.dropdownlistview_subitem, null);
			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.textview1);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.textView.setText(list.get(pos).subclassname);
		viewHolder.textView.setTextColor(Color.BLACK);
		return convertView;
	}

	public static class ViewHolder {
		public TextView textView;
		public RelativeLayout layout;
	}
	public void setSelectedPosition(int position){
		this.position=position;
		notifyDataSetChanged();
	}
//	public void setSelectString(SubClassify subClassify){
//		if(list.contains(subClassify)){
//			selectedPosition=list.indexOf(subClassify);
//			notifyDataSetChanged();
//		}
//	}
}
