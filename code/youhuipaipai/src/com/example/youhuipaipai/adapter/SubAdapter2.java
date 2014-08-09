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
import com.example.youhuipaipai.entity.Area;
import com.example.youhuipaipai.entity.AreaAndBusiness;
import com.example.youhuipaipai.entity.SubClassify;

public class SubAdapter2 extends BaseAdapter {
	
	Context context;
	LayoutInflater layoutInflater;
	List<AreaAndBusiness> list;
	public int position;
	public SubAdapter2(Context context, List<AreaAndBusiness> list) {
		this.context = context;
		this.list = list;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public AreaAndBusiness getItem(int pos) {
	
		return list.get(pos);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void updateList(List<AreaAndBusiness> newList){
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
			viewHolder.layout=(RelativeLayout)convertView.findViewById(R.id.colorlayout);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// 设置选中效果
//		if (pos == position) {
//			viewHolder.layout.setBackgroundResource(R.drawable.sousuojieguo_zhuye_shaixuan_listleft_buttom_press);	
//		} else {
//			viewHolder.layout.setBackgroundResource(R.drawable.sousuojieguo_zhuye_shaixuan_listleft_buttom);
//		}
		viewHolder.textView.setText(list.get(pos).getName());
		viewHolder.textView.setTextColor(Color.BLACK);
		return convertView;
	}

	public static class ViewHolder {
		public TextView textView;
		private RelativeLayout layout;
	}
	public void setSelectedPosition(int position){
		this.position=position;
		notifyDataSetChanged();
	}
//	public void setSelectString(Area area){
//		if(list.contains(area)){
//			selectedPosition=list.indexOf(area);
//			notifyDataSetChanged();
//		}
//	}
}
