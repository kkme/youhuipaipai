package com.example.youhuipaipai.adapter;

 
 
import java.util.LinkedList;
import java.util.List;

import android.widget.BaseAdapter;

public abstract class AdapterBase<T> extends BaseAdapter {
	
	protected List<T> entities = new LinkedList<T>();
	
	public List<T> getList(){
		return entities;
	}
	
	public void appendToList(List<T> list) {
		if (null==list ) {
			return;
		}
		entities.addAll(list);
		notifyDataSetChanged();
	}

	public void appendToTopList(List<T> list) {
		if (list == null) {
			return;
		}
		entities.addAll(0, list);
		notifyDataSetChanged();
	}
	
	public void resetData(List<T> list){
		entities.clear();
		appendToList(list);
		notifyDataSetChanged();
	}

	public void clear() {
		entities.clear();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return entities.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(position > entities.size()-1){
			return null;
		}
		return entities.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
}
