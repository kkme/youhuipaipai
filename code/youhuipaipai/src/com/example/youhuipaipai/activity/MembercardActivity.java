package com.example.youhuipaipai.activity;

import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.entity.BaseEntity;
import com.example.youhuipaipai.entity.MemberCard;
import com.example.youhuipaipai.view.MemberCardView;
import com.example.youhuipaipai.view.MemberCardView.IOnClickListener;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.MyPreference;
import frame.util.HttpUtil.CallbackListener;
import frame.util.ToastUtil;
//会员卡列表
public class MembercardActivity extends BaseActivity {
	private TextView left_tv;
	private LinearLayout appliedcard_ll, notappliedcard_ll;
	private List<MemberCard> list;
	private String merchantid;
	private String userId;

	@Override
	protected int getContentViewID() {
		return R.layout.nearby_membercardlist;
	}

	@Override
	protected void initView() {
		super.initView();
		userId = MyPreference.getString(this, Constant.USERID);
		if (TextUtils.isEmpty(userId))
			userId = "0";
		left_tv = getView(R.id.left_tv);
		appliedcard_ll = getView(R.id.appliedcard_ll);
		notappliedcard_ll = getView(R.id.notappliedcard_ll);
		merchantid = getIntent().getStringExtra("merchantid");

	}

	@Override
	protected void setListener() {
		super.setListener();
		left_tv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.left_tv:
			finish();
			break;
		}
	}

	@Override
	protected void getDatas() {
		super.getDatas();
		getMembercard();
	}

	private void getMembercard() {
		HttpUtil hu = new HttpUtil();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("requestCommand", "GetMemberList");
		hm.put("merchantid", merchantid);
		hm.put("userid", userId);
		hu.post(this, hm, new CallbackListener() {

			@Override
			public void onSuccess(JSONObject result) {
				dismissProgressDialog();
				BaseEntity<MemberCard> re = JSON.parseObject(result.toJSONString(), BaseEntity.class);
				list = JSON.parseArray(JSON.toJSONString(re.data), MemberCard.class);
				for (final MemberCard mc : list) {
					MemberCardView.IOnClickListener listener = new IOnClickListener() {
						@Override
						public void click() {
							Intent intent = new Intent(MembercardActivity.this, MemberCardDetailActivity.class);
							intent.putExtra("memberid", mc.getId());
							startActivity(intent);
						}
					};
					MemberCardView mcv = new MemberCardView(MembercardActivity.this, listener);
					mcv.setData(mc, false);
					if ("1".equals(mc.getState())) {
						appliedcard_ll.addView(mcv.getContent());
					} else {
						notappliedcard_ll.addView(mcv.getContent());
					}
				}
			}

			@Override
			public void onFailed(FailureResult result) {
				dismissProgressDialog();
				ToastUtil.showShort(MembercardActivity.this, result.message);
			}
		});
	}
}
