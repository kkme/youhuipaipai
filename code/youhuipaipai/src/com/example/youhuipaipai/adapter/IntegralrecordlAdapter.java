package com.example.youhuipaipai.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.entity.IntegralRecord;

public class IntegralrecordlAdapter extends BaseAdapter {
    private List<IntegralRecord> entities;
    private Context context;

    public IntegralrecordlAdapter(List<IntegralRecord> entities, Context c) {
        if (entities == null) {
            this.entities = new ArrayList<IntegralRecord>();
        } else {
            this.entities = entities;
        }
        this.context = c;

    }

    public void onDataChanged(List<IntegralRecord> entities) {
        if (entities == null) {
            this.entities = new ArrayList<IntegralRecord>();
        } else {
            this.entities = entities;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return entities.size();
    }

    @Override
    public IntegralRecord getItem(int position) {
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
                    R.layout.integalrecord_item, null);
            holder.integralnum_tv = (TextView) convertView
                    .findViewById(R.id.integralnum_tv);
            holder.type_tv = (TextView) convertView.findViewById(R.id.type_tv);
            holder.time_tv = (TextView) convertView.findViewById(R.id.time_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        IntegralRecord integralRecord = getItem(position);
        holder.integralnum_tv.setText(integralRecord.getScore() + "积分");
        holder.type_tv.setText(integralRecord.getSource());
        holder.time_tv.setText(integralRecord.getDatetime());
        return convertView;
    }

    private class ViewHolder {
        private TextView integralnum_tv;
        private TextView type_tv;
        private TextView time_tv;
    }

}
