package com.example.youhuipaipai.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.adapter.CommentAdapter;
import com.example.youhuipaipai.entity.BaseComments;
import com.example.youhuipaipai.entity.Comment;
import com.example.youhuipaipai.view.XListView;
import com.example.youhuipaipai.view.XListView.IXListViewListener;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.ToastUtil;

/**
 * @author lvning
 * @version create time:2014-5-18_下午6:01:29
 * @Description 更多评论页面
 */
public class MoreCommentActivity extends BaseActivity implements IXListViewListener {
	private TextView left_tv;
	private TextView allRemark, wellRemark, badRemark;
	private XListView lv;
	private CommentAdapter adapter;
	private String merchantid;
	private List<Comment> list;
	private int index;
	private int type = 1;

	@Override
	protected int getContentViewID() {
		return R.layout.more_comment;
	}

	@Override
	protected void initView() {
		super.initView();
		left_tv = getView(R.id.left_tv);
		allRemark = getView(R.id.allremark_tv);
		wellRemark = getView(R.id.wellremark_tv);
		badRemark = getView(R.id.badremark_tv);
		lv = getView(R.id.listview);
		lv.setPullLoadEnable(true);
		lv.setPullRefreshEnable(true);
		adapter = new CommentAdapter(null, this);
		lv.setAdapter(adapter);
		merchantid = getIntent().getStringExtra("merchantid");
	}

	@Override
	protected void getDatas() {
		super.getDatas();
		index = 1;
		getMorecomments();
	}

	@Override
	protected void setListener() {
		super.setListener();
		left_tv.setOnClickListener(this);
		allRemark.setOnClickListener(this);
		wellRemark.setOnClickListener(this);
		badRemark.setOnClickListener(this);
		lv.setXListViewListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.allremark_tv:
			type = 1;
			allRemark.setBackgroundResource(R.drawable.shop_details_comment_all_selector);
			wellRemark.setBackgroundResource(R.drawable.fujin_shangjiaxiangqing_selectionbar_2_2);
			badRemark.setBackgroundResource(R.drawable.fujin_shangjiaxiangqing_selectionbar_2);
			allRemark.setTextColor(Color.parseColor("#ffffff"));
			wellRemark.setTextColor(Color.parseColor("#F5483F"));
			badRemark.setTextColor(Color.parseColor("#F5483F"));
			getMorecomments();
			break;
		case R.id.wellremark_tv:
			type = 2;
			allRemark.setBackgroundResource(R.drawable.fujin_shangjiaxiangqing_selectionbar_1);
			wellRemark.setBackgroundResource(R.drawable.shop_details_comment_good_selector);
			badRemark.setBackgroundResource(R.drawable.fujin_shangjiaxiangqing_selectionbar_2);
			allRemark.setTextColor(Color.parseColor("#F5483F"));
			wellRemark.setTextColor(Color.parseColor("#ffffff"));
			badRemark.setTextColor(Color.parseColor("#F5483F"));
			getMorecomments();
			break;
		case R.id.badremark_tv:
			type = 3;
			allRemark.setBackgroundResource(R.drawable.fujin_shangjiaxiangqing_selectionbar_1);
			wellRemark.setBackgroundResource(R.drawable.fujin_shangjiaxiangqing_selectionbar_2_2);
			badRemark.setBackgroundResource(R.drawable.shop_details_comment_bad_selector);
			allRemark.setTextColor(Color.parseColor("#F5483F"));
			wellRemark.setTextColor(Color.parseColor("#F5483F"));
			badRemark.setTextColor(Color.parseColor("#ffffff"));
			getMorecomments();
			break;
		case R.id.left_tv:
			finish();
			break;

		default:
			break;
		}
	}

	private void getMorecomments() {
		showProgressDialog("正在加载", "请稍候...");
		HttpUtil hu = new HttpUtil();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("requestCommand", "MerchantComments");
		hm.put("merchantid", merchantid);
		hm.put("page", index + "");
		hm.put("sign", "20");
		hm.put("type", type + "");
		hu.post(this, hm, new CallbackListener() {

			@Override
			public void onSuccess(JSONObject result) {
				dismissProgressDialog();
				BaseComments bcs = JSON.parseObject(result.toJSONString(), BaseComments.class);
				allRemark.setText("全部(" + bcs.getAll() + ")");
				wellRemark.setText("好评(" + bcs.getGood() + ")");
				badRemark.setText("差评(" + bcs.getBad() + ")");
				list = JSON.parseArray(JSON.toJSONString(bcs.getData()), Comment.class);
				switch (type) {
				case 1:
					lv.setPullLoadEnable(list.size() > Integer.valueOf(bcs.getAll()));
					break;
				case 2:
					lv.setPullLoadEnable(list.size() > Integer.valueOf(bcs.getGood()));
					break;
				case 3:
					lv.setPullLoadEnable(list.size() > Integer.valueOf(bcs.getBad()));
					break;
				}
				if (index == 1) {
					adapter.clear();
					adapter.appendToList(list);
					onListViewLoadFinish(false);
				} else {
					adapter.appendToList(list);
					onListViewLoadFinish(true);
				}
			}

			@Override
			public void onFailed(FailureResult result) {
				dismissProgressDialog();
				ToastUtil.showShort(MoreCommentActivity.this, result.message);
			}
		});
	}

	@Override
	public void onRefresh() {
		index = 1;
		getMorecomments();

	}

	@Override
	public void onLoadMore() {
		++index;
		getMorecomments();
	}

	public void onListViewLoadFinish(boolean more) {
		lv.stopLoadMore();
		lv.stopRefresh();
		if (more)
			return;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(new Date());
		lv.setRefreshTime(time);
	}
}
