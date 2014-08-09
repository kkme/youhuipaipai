package com.example.youhuipaipai.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.entity.MemberCard;

public class MyMembercardAdapter extends AdapterBase<MemberCard> {
    private List<MemberCard> entities;
    private Context context;
    private boolean b;
    public List<String> selectList = new ArrayList<String>();

    public MyMembercardAdapter(List<MemberCard> entities, Context c) {
        if (entities != null) {
            this.entities = entities;
        } else {
            this.entities = new ArrayList<MemberCard>();
        }
        this.context = c;
    }

    public void onDataChanged(List<MemberCard> entities){
        if (entities != null) {
            this.entities = entities;
        } else {
            this.entities = new ArrayList<MemberCard>();
        }
        notifyDataSetChanged();
    }
    
    
    @Override
    public int getCount() {
        return entities.size();
    }

    @Override
    public MemberCard getItem(int position) {
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
                    R.layout.my_nearby_membercarditemview, null);
            holder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.membercard_tv = (TextView) convertView
                    .findViewById(R.id.membercard_tv);
            holder.cardnumber_tv = (TextView) convertView
                    .findViewById(R.id.cardnumber_tv);
            holder.validdate_tv = (TextView) convertView
                    .findViewById(R.id.validdate_tv);
            holder.membercard_rl = (RelativeLayout) convertView
                    .findViewById(R.id.membercard_rl);
            holder.validateCount_tv=(TextView) convertView.findViewById(R.id.validateCount_tv);
            holder.cb = (CheckBox) convertView.findViewById(R.id.cb);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final MemberCard mc = getItem(position);
        holder.name_tv.setText(mc.getName());
        holder.membercard_tv.setText(mc.getInfo());
        holder.validateCount_tv.setText("已领取人数："+mc.getReceiver());
        holder.cardnumber_tv.setText("NO:" + mc.getNo());
        holder.validdate_tv.setText("有效期:" + getTime(mc.getStarttime()) + "-"
                + getTime(mc.getEndtime()));
        if ("2".equals(mc.getState())) {
            holder.membercard_rl.setBackgroundResource(R.drawable.membercard_3);
        } else if ("3".equals(mc.getState())) {
            holder.membercard_rl.setBackgroundResource(R.drawable.membercard_3);
        }
        if (b) {
            holder.cb.setVisibility(View.VISIBLE);
            if (mc.isFlag()) {
                holder.cb.setChecked(true);
            } else {
                holder.cb.setChecked(false);
            }
            holder.cb.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (mc.isFlag()) {
                        selectList.remove(mc.getId());
                        mc.setFlag(false);
                    } else {
                        selectList.add(mc.getId());
                        mc.setFlag(true);
                    }
                }
            });
        } else {
            holder.cb.setVisibility(View.GONE);
        }
        return convertView;
    }

    private String getTime(String time) {
        if (time.contains(" ")) {
            return time.split(" ")[0];
        }
        return time;
    }

    public void edit(boolean b) {
        this.b = b;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        private TextView name_tv;
        private TextView membercard_tv;
        private TextView cardnumber_tv;
        private TextView validdate_tv;
        private TextView validateCount_tv;
        private RelativeLayout membercard_rl;
        private CheckBox cb;
    }

}
