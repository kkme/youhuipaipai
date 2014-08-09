package com.example.youhuipaipai.adapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.activity.ShopDetailActivity;
import com.example.youhuipaipai.entity.Merchant;

import frame.imgtools.ImgShowUtil;

public class MerchantAdapter extends AdapterBase<Merchant> {
    private List<Merchant> entities;
    private Context context;
    public Set<String> msgids = new HashSet<String>();
    private boolean isdelbtnvisible;

    public void setDelbtnVisible(boolean b) {
        isdelbtnvisible = b;
        notifyDataSetChanged();
    }

    public void onDataChanged(List<Merchant> entities) {
        if (entities == null) {
            this.entities = new ArrayList<Merchant>();
        } else {
            this.entities = entities;
        }
        notifyDataSetChanged();
    }

    public MerchantAdapter(List<Merchant> entities, Context c) {
        if (entities == null) {
            this.entities = new ArrayList<Merchant>();
        } else {
            this.entities = entities;
        }
        this.context = c;

    }

    @Override
    public int getCount() {
        return entities.size();
    }

    @Override
    public Merchant getItem(int position) {
        return entities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.shop_listview_item, null);
            holder.shop_listview_item_ll = (LinearLayout) convertView
                    .findViewById(R.id.shop_listview_item_ll);
            holder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.shopimg_iv = (ImageView) convertView
                    .findViewById(R.id.shopimg_iv);
            holder.sign_iv = (ImageView) convertView.findViewById(R.id.sign_iv);
            holder.counpon_iv = (ImageView) convertView
                    .findViewById(R.id.counpon_iv);
            holder.card_iv = (ImageView) convertView.findViewById(R.id.card_iv);
            holder.remark_rg = (RatingBar) convertView
                    .findViewById(R.id.remark_rg);
            holder.type_tv = (TextView) convertView.findViewById(R.id.type_tv);
            holder.signintegral_tv = (TextView) convertView
                    .findViewById(R.id.signintegral_tv);
            holder.costper_tv = (TextView) convertView
                    .findViewById(R.id.costper_tv);
            holder.place_tv = (TextView) convertView
                    .findViewById(R.id.place_tv);
            // holder.cb = (CheckBox) convertView.findViewById(R.id.del_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // for (int i = 0; i < entities.size(); i++) {
        final Merchant m = getItem(position);
        holder.name_tv.setText(m.getMerchantname());
        ImgShowUtil isu = new ImgShowUtil(m.getIcon(), m.getIcon());
        isu.setCoverDown(holder.shopimg_iv, R.drawable.shouye_box2_title_2);
        if ("1".equals(m.getIssign())) {
            holder.sign_iv.setVisibility(View.VISIBLE);
        } else {
            holder.sign_iv.setVisibility(View.GONE);
        }
        if ("1".equals(m.getIsdiscount())) {
            holder.counpon_iv.setVisibility(View.VISIBLE);
        } else {
            holder.counpon_iv.setVisibility(View.GONE);
        }
        if ("1".equals(m.getIsmember())) {
            holder.card_iv.setVisibility(View.VISIBLE);
        } else {
            holder.card_iv.setVisibility(View.GONE);
        }
        // FIXME
        // holder.shopimg_iv.setBackgroundResource(R.drawable.shouye_box2_title_2);
        holder.remark_rg.setRating(Float.valueOf(isNull(m.getStarrating())));
        holder.costper_tv.setText(m.getClassname());
        holder.signintegral_tv.setText("签到积分" + m.getIntegral());
        holder.type_tv.setText("人均" + m.getPercapita());
        // if (isdelbtnvisible) {
        // holder.cb.setVisibility(View.VISIBLE);
        // if (m.isFlag()) {
        // holder.cb.setChecked(true);
        // } else {
        // holder.cb.setChecked(false);
        // }
        // holder.cb.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // // TODO Auto-generated method stub
        // if (m.isFlag()) {
        // msgids.remove(m.getMerchantid());
        // m.setFlag(false);
        // } else {
        // msgids.add(m.getMerchantid());
        // m.setFlag(true);
        // }
        // }
        // });
        // } else {
        // holder.cb.setVisibility(View.GONE);
        // }
        holder.shop_listview_item_ll.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, ShopDetailActivity.class);
                intent.putExtra("merchantid", m.getMerchantid());
                context.startActivity(intent);
            }
        });
        holder.place_tv.setText(m.getAddress() + "\t" + m.getDistance());
        // }
        return convertView;
    }

    private class ViewHolder {
        private TextView name_tv;// 名字
        private ImageView shopimg_iv;
        private ImageView sign_iv;// 是否可以签到
        private ImageView counpon_iv;// 是否有优惠
        private ImageView card_iv;// 是否有会员卡
        private RatingBar remark_rg;// 星星
        private TextView type_tv;
        private TextView signintegral_tv;// 签到积分
        private TextView costper_tv;// 人均
        private TextView place_tv;
        private LinearLayout shop_listview_item_ll;
        // private CheckBox cb;

    }

    private String isNull(String s) {
        if (s == null) {
            return "0";
        }
        return s;
    }
}
