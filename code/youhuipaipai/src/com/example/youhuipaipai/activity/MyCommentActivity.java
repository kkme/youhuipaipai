package com.example.youhuipaipai.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.json.JSONException;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.adapter.MyCommentAdapter;
import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.entity.MyComment;
import com.example.youhuipaipai.view.XListView;
import com.example.youhuipaipai.view.XListView.IXListViewListener;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.JsonUtil;
import frame.util.MyPreference;
import frame.util.TimeUtil;
import frame.util.ToastUtil;

public class MyCommentActivity extends BaseActivity implements
		IXListViewListener {

	private TextView left_tv, edit_tv;
	private XListView lv;
	private MyCommentAdapter adapter;
	private String userid;
	private List<MyComment> list;
	private boolean flag = true;// true 表示下拉刷新 FALSE 表示加载更多

	@Override
	protected int getContentViewID() {
		return R.layout.mycomment;
	}

	@Override
	protected void initView() {
		super.initView();
		left_tv = getView(R.id.left_tv);
		edit_tv = getView(R.id.edit_tv);
		lv = getView(R.id.listview);
		list = new ArrayList<MyComment>();
		adapter = new MyCommentAdapter(list, this);
		lv.setAdapter(adapter);
		lv.setXListViewListener(this);
		lv.setPullLoadEnable(true);
		lv.setPullRefreshEnable(true);

		userid = MyPreference.getString(this, Constant.USERID);
	}

	private void onLoad() {
		lv.stopRefresh();
		lv.stopLoadMore();
		lv.setRefreshTime(TimeUtil.getNowTime());
	}

	@Override
	protected void getDatas() {
		super.getDatas();
		// getMycomments();
		getMyCommentsList();
	}

	@Override
	protected void setListener() {
		super.setListener();
		left_tv.setOnClickListener(this);
		edit_tv.setOnClickListener(this);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				goByIntent("merchantid", list.get(position - 1).getMerchantid(),
						ShopDetailActivity.class, false);
			}

		});
	}

	private void getMycomments() {
		HttpUtil hu = new HttpUtil();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("requestCommand", "MyComments");
		hm.put("userid", userid);
		hu.post(this, hm, new CallbackListener() {

			@Override
			public void onSuccess(JSONObject result) {
				dismissProgressDialog();
				list = JSON.parseArray(result.toJSONString(), MyComment.class);
				adapter.appendToList(list);
			}

			@Override
			public void onFailed(FailureResult result) {
				dismissProgressDialog();
				ToastUtil.showShort(MyCommentActivity.this, result.errorString);
			}
		});
	}

	private int index = 1;
	private int sign = 20;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.left_tv:
			finish();
			break;

		case R.id.edit_tv:
			if (edit_tv.getText().equals("删除")) {
				adapter.setDelbtnVisible(true);
				edit_tv.setText("完成");
			} else {
				adapter.setDelbtnVisible(false);
				edit_tv.setText("删除");
				if (null != adapter.msgids && adapter.msgids.size() > 0) {
					ModifyDialog();
				}
			}
			break;
		}
	}

	/**
	 * 确认修改的对话框
	 * 
	 * @param ctx
	 * @param title
	 * @param content
	 */
	protected void ModifyDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("你要确认删除吗？");

		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// TODO
						// 删除选中的条目
						deleteMsg();
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();

	}

	public void deleteMsg() {
		showProgressDialog("正在请求", "请稍候...");
		HttpUtil hu = new HttpUtil();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("requestCommand", "DelMyComments");
		StringBuffer sb = new StringBuffer();
		for (String s : adapter.msgids) {
			sb = sb.append(s).append(",");
		}
		sb = sb.deleteCharAt(sb.length() - 1);// 去掉最后一个”，“
		hm.put("commentsid", sb.toString());
		hm.put("userid", MyPreference.getString(this, Constant.USERID));
		hu.post(this, hm, new CallbackListener() {

			@Override
			public void onSuccess(JSONObject result) {
				dismissProgressDialog();
				deleteItem();
				// adapter.notifyDataSetChanged();
				adapter.onDataChanged(list);
				ToastUtil.showShort(MyCommentActivity.this, "操作成功！");
			}

			@Override
			public void onFailed(FailureResult result) {
				dismissProgressDialog();
				ToastUtil.showShort(MyCommentActivity.this, result.errorString);
			}
		});
	}

	/**
	 * 获取我的评论列表
	 */
	private void getMyCommentsList() {

		HttpUtil hu = new HttpUtil();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("requestCommand", "MyCommentsList");
		hm.put("userid", userid);
		hm.put("index", index + "");
		hm.put("sign", sign + "");
		hu.post(this, hm, new CallbackListener() {

			@Override
			public void onSuccess(JSONObject result) {
				dismissProgressDialog();
				try {
					ArrayList<MyComment> comments = JsonUtil
							.jsonToMyComment(result.toJSONString());
					if (flag) {// 下拉刷新
						list.clear();
					}
					list.addAll(comments);
					adapter.onDataChanged(list);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					onLoad();
				}
				onLoad();

			}

			@Override
			public void onFailed(FailureResult result) {
				dismissProgressDialog();
				onLoad();
				ToastUtil.showShort(MyCommentActivity.this, result.errorString);
			}
		});
	}

	private List<MyComment> getTestList() {
		List<MyComment> list = new ArrayList<MyComment>();
		for (int i = 0; i < 10; i++) {
			MyComment mc = new MyComment();
			mc.setCommentsid(String.valueOf(i));
			mc.setContent("000" + String.valueOf(i));
			mc.setDatetime("000000000");
			mc.setFwsatisfaction("000");
			list.add(mc);
		}
		return list;

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		flag = true;
		index = 1;
		getMyCommentsList();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		flag = false;
		index++;
		getMyCommentsList();
	}

	private void deleteItem() {
		if (null != list) {
			ArrayList<MyComment> removeList = new ArrayList<MyComment>();
			for (MyComment comment : list) {
				if (comment.isFlag()) {
					// list.remove(comment);
					removeList.add(comment);
				}
			}
			list.removeAll(removeList);
		}
	}

}
