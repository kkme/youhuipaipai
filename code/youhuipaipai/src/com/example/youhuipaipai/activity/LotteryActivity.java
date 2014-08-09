package com.example.youhuipaipai.activity;

import java.util.HashMap;

import org.json.JSONException;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.entity.IntegralExchangBean;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.JsonUtil;
import frame.util.MyPreference;
import frame.util.ToastUtil;

/**
 * @项目名:youhuipaipai
 * 
 * @类名:LotteryActivity.java
 * 
 * 
 * @类描述:抽奖页
 * 
 * @date:2014-5-5
 * 
 * @Version:1.0
 */
public class LotteryActivity extends BaseActivity {
    private TextView left_tv, right_tv, choujiangtime_tv, tv_content;
    String id = "";
    IntegralExchangBean bean;

    @Override
    protected int getContentViewID() {
        return R.layout.lottery;
    }

    @Override
    protected void initView() {
        super.initView();
        left_tv = getView(R.id.left_tv);
        right_tv = (TextView) findViewById(R.id.right_tv);
        choujiangtime_tv = getView(R.id.choujiangtime_tv);
        tv_content = getView(R.id.tv_content);
        right_tv.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setListener() {
        super.setListener();
        left_tv.setOnClickListener(this);
        right_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
        case R.id.left_tv:
            finish();
            break;
        case R.id.right_tv:
            lottery();
            break;
        }
    }

    @Override
    protected void getDatas() {
        super.getDatas();
        id = getIntent().getStringExtra("id");
        if (null != id && !"".equals(id)) {
            getadpagesDetail();
        }
    }

    private void lottery() {
        // 首页轮播图
        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "Luckdraw");
        hm.put("id", id);
        hm.put("userid", MyPreference.getString(this, Constant.USERID));
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                ToastUtil.showShort(LotteryActivity.this,
                        "您已成功获得抽奖序号，请至“我的消息”查看。");
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(LotteryActivity.this, result.message);
            }
        });
    }

    private void getadpagesDetail() {
        // 首页轮播图
        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "LuckdrawDetatil");
        hm.put("id", id);
        hm.put("userid", MyPreference.getString(this, Constant.USERID));
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                try {
                    bean = JsonUtil.jsonToIntegralExchangBean(result
                            .toJSONString());
                    choujiangtime_tv
                            .setText((bean.getStarttime().contains(" ") ? bean
                                    .getStarttime().split(" ")[0] : bean
                                    .getStarttime())
                                    + "-"
                                    + (bean.getEndtime().contains(" ") ? bean
                                            .getEndtime().split(" ")[0] : bean
                                            .getEndtime()));
                    tv_content.setText(Html.fromHtml(bean.getContent()));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(LotteryActivity.this, result.message);
            }
        });
    }
}
