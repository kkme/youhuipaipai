package com.example.youhuipaipai.adapter;

import java.util.UUID;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.youhuipaipai.R;

import frame.imgtools.ImgShowUtil;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private String[] list;

    public ImageAdapter(Context context, String[] list) {
        this.context = context;
        if (list != null) {
            this.list = list;
        } else {
            this.list = new String[] {};
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ItemView item = null;
        if (view == null) {
            item = new ItemView();
            view = LayoutInflater.from(context).inflate(R.layout.imageadapter,
                    null);
            item.iv = (ImageView) view.findViewById(R.id.iv);

            view.setTag(item);
        } else {
            item = (ItemView) view.getTag();
        }
        String value = list[position];
        new ImgShowUtil(value, UUID.randomUUID(), 500).setCoverDown(item.iv);
        return view;
    }

    class ItemView {
        public ImageView iv;

    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return list[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
