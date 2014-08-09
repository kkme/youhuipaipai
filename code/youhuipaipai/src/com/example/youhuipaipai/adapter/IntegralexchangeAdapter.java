package com.example.youhuipaipai.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.entity.IntegralRecord;

public class IntegralexchangeAdapter extends BaseAdapter {
    private Context ctx;
    private List<IntegralRecord> list;
    private int typeid = 1;

    public IntegralexchangeAdapter(List list, Context ctx) {
        this.ctx = ctx;
    }

    public void appendToList(List list) {
        if (list == null) {
            this.list = new ArrayList<IntegralRecord>();
            return;
        }
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=null;
        switch (typeid) {
        case 1:
            view = View.inflate(ctx,R.layout.choujiang_item,null);
            TextView tv_jifen=(TextView) view.findViewById(R.id.tv_jifen);
            TextView tv_riqi=(TextView) view.findViewById(R.id.tv_riqi);
            ImageView iv_canyu=(ImageView) view.findViewById(R.id.iv_canyu);
            //TODO
            
            
            break;
        case 2:
          view= View.inflate(ctx,R.layout.kajuan_item,null);
           //TODO
            break;
        case 3:
           view= View.inflate(ctx,R.layout.jiangpin_item,null);
           //TODO
            break;

        default:
            break;
        }
        
        return view;
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
