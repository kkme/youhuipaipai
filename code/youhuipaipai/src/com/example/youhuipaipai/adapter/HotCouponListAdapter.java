package com.example.youhuipaipai.adapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.entity.HotCouponList;

import frame.imgtools.ImgShowUtil;

/*
 * 最热优惠券列表适配器
 */
public class HotCouponListAdapter extends AdapterBase<HotCouponList> {
    private List<HotCouponList> entities;
    private Context context;
    public Set<String> msgids = new HashSet<String>();
    private boolean isdelbtnvisible;

    public void setDelbtnVisible(boolean b) {
        isdelbtnvisible = b;
        notifyDataSetChanged();
    }

    public HotCouponListAdapter(List<HotCouponList> entities, Context c) {
        if (entities == null) {
            this.entities = new ArrayList<HotCouponList>();
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
    public HotCouponList getItem(int position) {
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
                    R.layout.hot_coupon_listview_item, null);
            holder.coupon_iv = (ImageView) convertView
                    .findViewById(R.id.coupon_iv);
            holder.merchantname_tv = (TextView) convertView
                    .findViewById(R.id.merchantname_tv);
            holder.integral_tv = (TextView) convertView
                    .findViewById(R.id.integral_tv);
            holder.coupontime_tv = (TextView) convertView
                    .findViewById(R.id.coupontime_tv);
            holder.merchanttype_tv = (TextView) convertView
                    .findViewById(R.id.merchanttype_tv);
            holder.received_tv = (TextView) convertView
                    .findViewById(R.id.received_tv);
            holder.cb = (CheckBox) convertView.findViewById(R.id.del_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // HotCouponList m=getItem(position);
        // if("1".equals(m.getIntegral())){
        // holder.integral_tv.setVisibility(View.VISIBLE);
        // }else{
        // holder.integral_tv.setVisibility(View.GONE);
        // }05-04 15:27:32.905: E/urlPath(4485):
        // urlPath:http://115.28.246.230:8088/Img/ContentPrint/20140321032418209.jpg

        final HotCouponList m = getItem(position);
        ImgShowUtil is = new ImgShowUtil(m.getImg(), m.getCouponid());
        is.setCoverDown(holder.coupon_iv, R.drawable.shouye_box2_title_1);
        holder.merchantname_tv.setText(m.getMerchantname());
        holder.integral_tv.setText(m.getIntegral());
        holder.coupontime_tv.setText(getTime(m.getStarttime()) + "-"
                + getTime(m.getEndtime()));
        holder.merchanttype_tv.setText(m.getMerchanttype());
        holder.received_tv.setText(m.getReceived());
        if (isdelbtnvisible) {
            holder.cb.setVisibility(View.VISIBLE);
            if (m.isFlag()) {
                holder.cb.setChecked(true);
            } else {
                holder.cb.setChecked(false);
            }
            holder.cb.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (m.isFlag()) {
                        msgids.remove(m.getCouponid());
                        m.setFlag(false);
                    } else {
                        msgids.add(m.getCouponid());
                        m.setFlag(true);
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

    private class ViewHolder {
        private ImageView coupon_iv;// 图片
        private TextView merchantname_tv;// 商家名字
        private TextView integral_tv;// 积分，如果是免费的话，此项为0
        private TextView coupontime_tv;// 优惠开始——截止时间
        private TextView merchanttype_tv;// 商家类型 例：美食、火锅
        private TextView received_tv;// 已领取人数
        private CheckBox cb;
    }

}
