package com.example.youhuipaipai.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.youhuipaipai.R;
import com.example.youhuipaipai.activity.ImageDetailActivity;
import com.example.youhuipaipai.entity.Comment;

import frame.imgtools.ImgShowUtil;

public class CommentView extends BaseView {
	private LinearLayout commentpicture_ll;
	private ImageView head_iv;
	private TextView name_tv, time_tv, commentcontent_tv;
	private ImageView pic1, pic2, pic3;
	private Context context;

	public CommentView(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected int setLayout() {
		return R.layout.comment_item;
	}

	@Override
	protected void initViews() {
		super.initViews();
		head_iv = getView(R.id.head_iv);
		name_tv = getView(R.id.name_tv);
		time_tv = getView(R.id.time_tv);
		pic1 = getView(R.id.iv_comment_pic1);
		pic2 = getView(R.id.iv_comment_pic2);
		pic3 = getView(R.id.iv_comment_pic3);
		commentcontent_tv = getView(R.id.commentcontent_tv);
		commentpicture_ll = getView(R.id.commentpicture_ll);
	}

	public void setData(Comment c) {
		ImgShowUtil isu = new ImgShowUtil(c.getHeadimg(), c.getHeadimg());
		isu.setCoverDown(head_iv, R.drawable.ic_launcher);
		name_tv.setText(c.getUsernickname());
		time_tv.setText(c.getDatetime());
		commentcontent_tv.setText(c.getContent());
		if (c.getImgs() != null && !"".equals(c.getImgs())) {
			commentpicture_ll.setVisibility(View.VISIBLE);
			String[] imgs = c.getImgs().split(",");
			for (int i = 0; i < imgs.length; i++) {
				String img = imgs[i];
				if (i == 0) {
					new ImgShowUtil(img, img).setCoverDown(pic1, R.drawable.ic_launcher);
					pic1.setOnClickListener(new MPicOnclickListener(img));
				} else if (i == 1) {
					new ImgShowUtil(img, img).setCoverDown(pic2, R.drawable.ic_launcher);
					pic2.setOnClickListener(new MPicOnclickListener(img));
				} else {
					new ImgShowUtil(img, img).setCoverDown(pic3, R.drawable.ic_launcher);
					pic3.setOnClickListener(new MPicOnclickListener(img));
				}
			}
		} else {
			commentpicture_ll.setVisibility(View.GONE);
		}
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
}
