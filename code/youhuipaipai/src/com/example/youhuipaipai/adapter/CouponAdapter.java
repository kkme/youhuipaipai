package com.example.youhuipaipai.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.entity.Coupon;

public class CouponAdapter extends AdapterBase<Coupon> {
	private List<Coupon> entities;
	private Context context;
	public CouponAdapter(List<Coupon> entities, Context c) {
		if(entities==null){
			this.entities=new ArrayList<Coupon>();
		}else{
			this.entities = entities;
		}
		this.context = c;
	
	}

	@Override
	public int getCount() {
		return entities.size();
	}

	@Override
	public Coupon getItem(int position) {
		return entities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.couponlist_item, null);
			holder.name_tv = (TextView) convertView
					.findViewById(R.id.name_tv);
			holder.head_iv=(ImageView)convertView.findViewById(R.id.head_iv);
			holder.time_tv=(TextView)convertView.findViewById(R.id.time_tv);
			holder.jifen_tv=(TextView)convertView.findViewById(R.id.jifen_tv);
			holder.type_tv=(TextView)convertView.findViewById(R.id.type_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
	
	/*	ImgShowUtil isu=new ImgShowUtil(Coupon.getHeadimg(), Coupon.getHeadimg());
		isu.setCover(holder.head_iv);*/
	

		
		return convertView;
	}
	
	private class ViewHolder {
		private TextView name_tv;// 名字
		private ImageView head_iv;
		private TextView time_tv;
		private TextView jifen_tv;
		private TextView type_tv;
	}

}
