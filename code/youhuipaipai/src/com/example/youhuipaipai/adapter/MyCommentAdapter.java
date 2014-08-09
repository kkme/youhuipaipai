package com.example.youhuipaipai.adapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.activity.ShopDetailActivity;
import com.example.youhuipaipai.entity.IntegralRecord;
import com.example.youhuipaipai.entity.MyComment;
import com.example.youhuipaipai.view.MyGridView;

import frame.util.ToastUtil;

public class MyCommentAdapter extends AdapterBase<MyComment> {
    private List<MyComment> entities;
    private Context context;
    public Set<String> msgids = new HashSet<String>();
    private boolean isdelbtnvisible;

    public void setDelbtnVisible(boolean b) {
        isdelbtnvisible = b;
        notifyDataSetChanged();
    }

    public MyCommentAdapter(List<MyComment> entities, Context c) {
        if (entities == null) {
            this.entities = new ArrayList<MyComment>();
        } else {
            this.entities = entities;
        }
        this.context = c;

    }

    public void onDataChanged(List<MyComment> entities) {
        if (entities == null) {
            this.entities = new ArrayList<MyComment>();
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
    public MyComment getItem(int position) {
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
                    R.layout.mycomment_item, null);
            holder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.time_tv = (TextView) convertView.findViewById(R.id.time_tv);
            holder.commentcontent_tv = (TextView) convertView
                    .findViewById(R.id.commentcontent_tv);
            holder.commentpicture_ll = (MyGridView) convertView
                    .findViewById(R.id.commentpicture_ll);
            holder.remark_rg = (RatingBar) convertView
                    .findViewById(R.id.remark_rg);
            holder.satisfy_tv = (TextView) convertView
                    .findViewById(R.id.satisfy_tv);
            holder.environment_tv = (TextView) convertView
                    .findViewById(R.id.environment_tv);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.del_btn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final MyComment comment = getItem(position);
        holder.name_tv.setText(isNull(comment.getMerchantname()));
        holder.time_tv.setText(isNull(comment.getDatetime()));
        String s = comment.getContent();
        holder.commentcontent_tv.setText(isNull(comment.getContent()));
        holder.remark_rg.setRating(Integer.valueOf(isNull(comment
                .getStarrating())));
        holder.satisfy_tv.setText(isNull(comment.getFwsatisfaction()));
        holder.environment_tv.setText(isNull(comment.getHjsatisfaction()));
        if (null != comment.getPicture() && !"".equals(comment.getPicture())
                && comment.getPicture().contains(",")) {
            holder.commentpicture_ll.setVisibility(View.VISIBLE);
            String[] pics = comment.getPicture().split(",");
            ImageAdapter adapter = new ImageAdapter(context, pics);
            holder.commentpicture_ll.setAdapter(adapter);
//            holder.commentpicture_ll
//                    .setOnItemClickListener(new OnItemClickListener() {
//
//                        @Override
//                        public void onItemClick(AdapterView<?> arg0, View arg1,
//                                int arg2, long arg3) {
                            // TODO Auto-generated method stub
                            // ToastUtil.showShort(context, "点击事件");
//                        }
//                    });
        } else {
            holder.commentpicture_ll.setVisibility(View.GONE);
        }

        if (isdelbtnvisible) {
            holder.checkBox.setVisibility(View.VISIBLE);
            if (comment.isFlag()) {
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }
            holder.checkBox.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (comment.isFlag()) {
                        msgids.remove(comment.getCommentsid());
                        comment.setFlag(false);
                    } else {
                        msgids.add(comment.getCommentsid());
                        comment.setFlag(true);
                    }
                }
            });
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }
        convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context,ShopDetailActivity.class);
				intent.putExtra("merchantid", comment.getMerchantid());
				context.startActivity(intent);
			}
		});
        
        return convertView;
    }

    private class ViewHolder {
        private TextView name_tv;// 名字
        private TextView time_tv;
        private RatingBar remark_rg;
        private TextView commentcontent_tv, satisfy_tv, environment_tv;
        private MyGridView commentpicture_ll;// 评论图片
        private CheckBox checkBox;
    }

    private String isNull(String s) {
        if (s == null) {
            return "0";
        }
        return s;
    }

    private List<IntegralRecord> TestData() {
        List<IntegralRecord> list = new ArrayList<IntegralRecord>();
        for (int i = 0; i < 10; i++) {
            IntegralRecord ir = new IntegralRecord();
            ir.setDatetime("20130111");
            ir.setRecorded("0000");
            ir.setScore("-100");
            ir.setSource("积分来源");
            ir.setUserid("001");
            list.add(ir);
        }
        return list;
    }
}
