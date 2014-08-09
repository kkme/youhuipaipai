package com.example.youhuipaipai.adapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.entity.Mymessage;

public class MymessageAdapter extends AdapterBase<Mymessage> {
    private List<Mymessage> entities;
    private Context context;
    public Set<String> msgids = new HashSet<String>();

    public MymessageAdapter(List<Mymessage> entities, Context c) {
        if (entities == null) {
            this.entities = new ArrayList<Mymessage>();
        } else {
            this.entities = entities;
        }
        this.context = c;

    }

    public void onDataChanged(List<Mymessage> entities) {
        if (entities == null) {
            this.entities = new ArrayList<Mymessage>();
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
    public Mymessage getItem(int position) {
        return entities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.mymessage_item, null);
            holder.title_tv = (TextView) convertView
                    .findViewById(R.id.title_tv);
            holder.time_tv = (TextView) convertView.findViewById(R.id.time_tv);
            holder.content_tv = (TextView) convertView
                    .findViewById(R.id.content_tv);
            holder.showcontent_rl = (RelativeLayout) convertView
                    .findViewById(R.id.showcontent_rl);
            holder.cb = (CheckBox) convertView.findViewById(R.id.del_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Mymessage mymessage = getItem(position);
        holder.title_tv.setText(mymessage.getTitle());
        holder.time_tv.setText(mymessage.getDatetime());
        holder.content_tv.setText(mymessage.getContent());
        if (isdelbtnvisible) {
            holder.cb.setVisibility(View.VISIBLE);
            if (mymessage.isFlag()) {
                holder.cb.setChecked(true);
            } else {
                holder.cb.setChecked(false);
            }
            holder.cb.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (mymessage.isFlag()) {
                        msgids.remove(mymessage.getMessageid());
                        mymessage.setFlag(false);
                    } else {
                        msgids.add(mymessage.getMessageid());
                        mymessage.setFlag(true);
                    }
                }
            });
        } else {
            holder.cb.setVisibility(View.GONE);
        }
        holder.showcontent_rl.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (holder.content_tv.getVisibility() == View.GONE) {
                    holder.content_tv.setVisibility(View.VISIBLE);
                } else {
                    holder.content_tv.setVisibility(View.GONE);
                }
            }
        });
        return convertView;
    }

    private boolean isdelbtnvisible;

    public void setDelbtnVisible(boolean b) {
        isdelbtnvisible = b;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        private TextView title_tv;// 名字
        private TextView time_tv;
        private TextView content_tv;
        private CheckBox cb;
        private RelativeLayout showcontent_rl;
    }

}
