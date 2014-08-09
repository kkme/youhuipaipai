package com.example.youhuipaipai.adapter;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.entity.IntegralExchangBean;

import frame.imgtools.ImgShowUtil;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

public class IntegralAdapter extends BaseAdapter {

    private Context context;
    private List<IntegralExchangBean> list;

    public IntegralAdapter(Context context, List<IntegralExchangBean> list) {
        this.context = context;
        if (null != list) {
            this.list = list;
        } else {
            this.list = new ArrayList<IntegralExchangBean>();
        }
    }

    public void onDataChanged(List<IntegralExchangBean> list) {
        if (null != list) {
            this.list = list;
        } else {
            this.list = new ArrayList<IntegralExchangBean>();
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ItemView item = null;
        if (view == null) {
            item = new ItemView();
            view = LayoutInflater.from(context).inflate(R.layout.kajuan_item,
                    null);
            item.jifen_tv = (TextView) view.findViewById(R.id.jifen_tv);
            item.tv_title = (TextView) view.findViewById(R.id.tv_title);
            item.shopimg_iv = (ImageView) view.findViewById(R.id.shopimg_iv);
            item.cost_ll = (RelativeLayout) view.findViewById(R.id.cost_ll);
            item.time_tv = (TextView) view.findViewById(R.id.time_tv);
            item.iv_canyu = (ImageView) view.findViewById(R.id.iv_canyu);
            item.type_tv = (TextView) view.findViewById(R.id.type_tv);
            item.title_rl = (RelativeLayout) view.findViewById(R.id.title_rl);
            item.rl = (RelativeLayout) view.findViewById(R.id.rl);

            view.setTag(item);
        } else {
            item = (ItemView) view.getTag();
        }

        IntegralExchangBean value = list.get(position);

        item.time_tv.setText(value.getStarttime() + "-" + value.getEndtime());

        new ImgShowUtil(value.getImg(), value.getId(), 500).setCoverDown(
                item.shopimg_iv, R.drawable.ic_launcher);

        if (value.getState().equals("2")) {
            item.iv_canyu
                    .setBackgroundResource(R.drawable.ifenduihuan_choujiang_icon);
            item.iv_canyu.setVisibility(View.VISIBLE);
        } else if (value.getState().equals("3")) {
            item.iv_canyu
                    .setBackgroundResource(R.drawable.youhuiquanxiangqing_wode_icon);
            item.iv_canyu.setVisibility(View.VISIBLE);
        } else {
            item.iv_canyu.setVisibility(View.GONE);
        }

        switch (value.getCurrentType()) {
        case 1:// 抽奖
            item.shopimg_iv.setVisibility(View.GONE);
            item.type_tv.setVisibility(View.GONE);
            item.time_tv.setVisibility(View.VISIBLE);
            item.time_tv.setVisibility(View.VISIBLE);
            item.jifen_tv.setText("兑换需要积分：" + value.getIntegral());
            break;
        case 2:// 优惠券
            item.shopimg_iv.setVisibility(View.VISIBLE);
            item.time_tv.setVisibility(View.VISIBLE);
            item.type_tv.setVisibility(View.VISIBLE);
            item.jifen_tv.setText("积分：" + value.getIntegral());
            item.type_tv.setText(value.getType());
            item.time_tv.setVisibility(View.VISIBLE);
            break;
        case 3:// 奖品
            item.shopimg_iv.setVisibility(View.VISIBLE);
            item.time_tv.setVisibility(View.GONE);
            item.jifen_tv.setText("积分：" + value.getIntegral());
            item.type_tv.setText(value.getCount() + "人已兑");
            item.time_tv.setVisibility(View.GONE);
            break;
        }
        item.tv_title.setText(value.getTitile());

        return view;
    }

    class ItemView {
        public TextView jifen_tv;
        public TextView tv_title;
        public ImageView shopimg_iv;
        public RelativeLayout cost_ll;
        public TextView time_tv;
        public ImageView iv_canyu;
        public TextView type_tv;
        public RelativeLayout title_rl;
        public RelativeLayout rl;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
