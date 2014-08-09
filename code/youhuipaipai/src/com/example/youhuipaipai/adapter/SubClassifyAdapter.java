package com.example.youhuipaipai.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.entity.SubClassify;

public class SubClassifyAdapter extends AdapterBase<SubClassify> {
	
	private Context context;
	public SubClassifyAdapter(List<SubClassify> entities, Context c) {
		if(entities==null){
			this.entities=new ArrayList<SubClassify>();
		}else{
			this.entities = entities;
		}
		this.context = c;
	
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.classify_item, null);
			holder.name_tv = (TextView) convertView
					.findViewById(R.id.name_tv);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		SubClassify classify=(SubClassify) getItem(position);
		holder.name_tv.setText(classify.subclassname + "");
		return convertView;
	}
	
	private class ViewHolder {
		private TextView name_tv;// 名字
	}

}
