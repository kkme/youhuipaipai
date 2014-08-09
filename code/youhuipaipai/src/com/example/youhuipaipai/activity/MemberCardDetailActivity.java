package com.example.youhuipaipai.activity;

import java.util.HashMap;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.entity.MemberCard;
import com.example.youhuipaipai.entity.ObjectBaseEntity;
import com.example.youhuipaipai.view.MemberCardView;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.MyPreference;
import frame.util.ToastUtil;

//会员卡详情
public class MemberCardDetailActivity extends BaseActivity {
    private TextView left_tv,membercard_describe;
    private Button apply_btn;
    private String memberid;
    private String userid;
    private LinearLayout cardLl;

    private MemberCard mc;

    @Override
    protected int getContentViewID() {
        return R.layout.membercarddetail;
    }

    @Override
    protected void initView() {
        super.initView();
        left_tv = getView(R.id.left_tv);
        apply_btn = getView(R.id.apply_btn);
        cardLl = getView(R.id.card_ll);
        membercard_describe=getView(R.id.membercard_describe);
        Intent intent = getIntent();
        memberid = intent.getStringExtra("memberid");
        userid = MyPreference.getString(this, Constant.USERID);
        if (TextUtils.isEmpty(userid))
            userid = "0";
    }

    @Override
    protected void setListener() {
        super.setListener();
        left_tv.setOnClickListener(this);
        apply_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
        case R.id.left_tv:
            finish();
            break;
        case R.id.apply_btn:
            if (TextUtils.isEmpty(userid) || "0".equals(userid)) {
                ToastUtil.showShort(MemberCardDetailActivity.this, "请先登录");
                return;
            }
            applyMembercard();
            break;
        }
    }

    @Override
    protected void getDatas() {
        super.getDatas();
        getMembercardDetail();
    }

    private void getMembercardDetail() {
        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "MemberDetail");
        hm.put("memberid", memberid);
        hm.put("userid", userid);
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();

                ObjectBaseEntity<MemberCard> re = JSON.parseObject(
                        result.toJSONString(), ObjectBaseEntity.class);
                re.data = JSON.parseObject(JSON.toJSONString(re.data),
                        MemberCard.class);
                mc = re.data;
                initCardView(false);
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(MemberCardDetailActivity.this,
                        result.message);
            }
        });
    }

    private void initCardView(boolean setGone) {
        MemberCardView mcv = new MemberCardView(MemberCardDetailActivity.this,
                null);
        mcv.setData(mc, true);

        cardLl.addView(mcv.getContent());
        // 1 已申请 2 待审核 3 未申请 4 已过期
        // apply_btn.setVisibility("3".equals(mc.getState()) ? View.VISIBLE :
        // View.GONE);
        // if(setGone)
        // apply_btn.setVisibility(View.GONE);
        membercard_describe.setText(Html.fromHtml(mc.getContent()));
        apply_btn.setVisibility(View.VISIBLE);
    }

    private void applyMembercard() {
        int integral = Integer.valueOf(mc.getIntegral());
        Log.d("", "需要积分： " + integral);
        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "ApplyMember");
        hm.put("memberid", memberid);
        hm.put("userid", userid);
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                String no = result.getString("message");
                mc.setNo(no);
                cardLl.removeAllViews();
                initCardView(true);

                ToastUtil.showShort(MemberCardDetailActivity.this, "申请成功");
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(MemberCardDetailActivity.this,
                        result.message);
            }
        });
    }
}
