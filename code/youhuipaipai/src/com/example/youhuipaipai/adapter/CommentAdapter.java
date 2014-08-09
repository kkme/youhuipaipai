package com.example.youhuipaipai.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.activity.ImageDetailActivity;
import com.example.youhuipaipai.entity.Comment;

import frame.imgtools.ImgShowUtil;

public class CommentAdapter extends AdapterBase<Comment> {
	private Context context;

	public CommentAdapter(List<Comment> entities, Context c) {
		if (entities == null) {
			this.entities = new ArrayList<Comment>();
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
	public Comment getItem(int position) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.comment_item, null);
			holder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
			holder.head_iv = (ImageView) convertView.findViewById(R.id.head_iv);
			holder.time_tv = (TextView) convertView.findViewById(R.id.time_tv);
			holder.commentcontent_tv = (TextView) convertView.findViewById(R.id.commentcontent_tv);
			holder.commentpicture_ll = (LinearLayout) convertView.findViewById(R.id.commentpicture_ll);
			holder.pic1 = (ImageView) convertView.findViewById(R.id.iv_comment_pic1);
			holder.pic2 = (ImageView) convertView.findViewById(R.id.iv_comment_pic2);
			holder.pic3 = (ImageView) convertView.findViewById(R.id.iv_comment_pic3);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Comment comment = getItem(position);
		holder.name_tv.setText(comment.getUsernickname());
		ImgShowUtil isu = new ImgShowUtil(comment.getHeadimg(), comment.getHeadimg());
		isu.setCoverDown(holder.head_iv, R.drawable.ic_launcher);
		holder.time_tv.setText(comment.getDatetime());
		holder.commentcontent_tv.setText(comment.getContent());
		if (!TextUtils.isEmpty(comment.getImgs())) {
			holder.commentpicture_ll.setVisibility(View.VISIBLE);
			String[] imgs = comment.getImgs().split(",");
			for (int i = 0; i < imgs.length; i++) {
				String img = imgs[i];
				if (i == 0) {
					new ImgShowUtil(img, img).setCoverDown(holder.pic1, R.drawable.ic_launcher);
					holder.pic1.setOnClickListener(new MPicOnclickListener(img));
				} else if (i == 1) {
					new ImgShowUtil(img, img).setCoverDown(holder.pic2, R.drawable.ic_launcher);
					holder.pic2.setOnClickListener(new MPicOnclickListener(img));
				} else {
					new ImgShowUtil(img, img).setCoverDown(holder.pic3, R.drawable.ic_launcher);
					holder.pic3.setOnClickListener(new MPicOnclickListener(img));
				}
			}
		} else {
			holder.commentpicture_ll.setVisibility(View.GONE);
		}

		return convertView;
	}

	private class MPicOnclickListener implements View.OnClickListener {

		private String img;

		public MPicOnclickListener(String img) {
			this.img = img;
		}

		@Override
		public void onClick(View arg0) {
			Intent i = new Intent(context, ImageDetailActivity.class);
			i.putExtra("picPath", img);
			context.startActivity(i);
		}
	}

	private class ViewHolder {
		private TextView name_tv;// 名字
		private ImageView head_iv;
		private TextView time_tv;
		private TextView commentcontent_tv;
		private LinearLayout commentpicture_ll;// 评论图片
		private ImageView pic1, pic2, pic3;
	}

}
