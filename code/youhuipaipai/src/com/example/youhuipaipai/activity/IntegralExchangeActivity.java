package com.example.youhuipaipai.activity;

import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.adapter.IntegralAdapter;
import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.entity.IntegralExchangBean;
import com.example.youhuipaipai.view.XListView.IXListViewListener;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.JsonUtil;
import frame.util.MyPreference;
import frame.util.ToastUtil;

/**
 * @项目名:youhuipaipai
 * 
 * @类名:IntegralExchangeActivity.java
 * 
 * @date:2014-3-21
 * 
 * @Version:1.0
 * 
 ******************************************/
public class IntegralExchangeActivity extends BaseActivity implements
        OnClickListener, OnCheckedChangeListener, IXListViewListener {
    /**
     * 积分兑换单选框组
     */
    private RadioGroup rg_exchange;
    private RadioButton rb_choujiang, rb_kajuan, rb_jiangpin;
    private TextView tv_back;
    private ListView xlv;

    private IntegralAdapter adapter;
    private List<IntegralExchangBean> list;

    private String userid;
    private int currentPosition = 1;

    @Override
    protected int getContentViewID() {
        return R.layout.integral_exchange;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_back = getView(R.id.tv_back);
        initLayout();
        userid = MyPreference.getString(this, Constant.USERID);
    }

    private void initLayout() {
        rg_exchange = getView(R.id.rg_exchange);
        rb_choujiang = getView(R.id.rb_choujiang);
        rb_kajuan = getView(R.id.rb_kajuan);
        rb_jiangpin = getView(R.id.rb_jiangpin);
        rb_choujiang.setChecked(true);
        SetRadioButtonTc(0);

        xlv = getView(R.id.xlv);
        adapter = new IntegralAdapter(this, list);
        xlv.setAdapter(adapter);
        // xlv.setPullLoadEnable(false);// true 为显示加载更多的控件
        // xlv.setPullRefreshEnable(false);// true 为显示下拉刷新的控件

    }

    @Override
    protected void setListener() {
        super.setListener();
        tv_back.setOnClickListener(this);
        rg_exchange.setOnCheckedChangeListener(this);
        // xlv.setXListViewListener(this);// 监听下拉刷新和加载更多的监听
        xlv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                // TODO Auto-generated method stub
                IntegralExchangBean bean = list.get(arg2);
                Intent intent = new Intent();
                intent.putExtra("id", bean.getId());
                switch (bean.getCurrentType()) {
                case 1:// 抽奖
                       // ToastUtil.showShort(IntegralExchangeActivity.this,
                       // "抽奖");
                    intent.setClass(IntegralExchangeActivity.this,
                            LotteryActivity.class);
                    break;
                case 2:// 优惠券
                       // ToastUtil.showShort(IntegralExchangeActivity.this,
                       // "优惠券");
                    intent.putExtra("showShopInfo", true);
                    intent.setClass(IntegralExchangeActivity.this,
                            IntegralCouponDetailActivity.class);
                    return;
                case 3:// 奖品
                       // ToastUtil.showShort(IntegralExchangeActivity.this,
                       // "奖品");
                    intent.setClass(IntegralExchangeActivity.this,
                            PrizeDetailActivity.class);
                    break;
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        finish();
    }

    @Override
    protected void onResume() {
        getIntegralLuckdraw(currentPosition);
        super.onResume();
    }

    /**
     * 对单选按钮的处理操作
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO Auto-generated method stub
        switch (checkedId) {
        case R.id.rb_choujiang:// 抽奖
            SetRadioButtonTc(0);
            getIntegralLuckdraw(1);
            break;
        case R.id.rb_kajuan:// 兑换奖券
            SetRadioButtonTc(1);
            getIntegralLuckdraw(2);
            break;
        case R.id.rb_jiangpin:// 兑换奖品
            SetRadioButtonTc(2);
            getIntegralLuckdraw(3);
            break;
        default:
            break;
        }
    }

    /**
     * 给单选按钮的字体加上相应的颜色
     * 
     * @param position
     */
    private void SetRadioButtonTc(int position) {
        switch (position) {
        case 0:// 抽奖
            rb_choujiang.setTextColor(getResources().getColor(R.color.white));
            rb_kajuan.setTextColor(getResources().getColor(R.color.red));
            rb_jiangpin.setTextColor(getResources().getColor(R.color.red));
            currentPosition = 1;
            break;
        case 1:// 兑换奖券
            rb_choujiang.setTextColor(getResources().getColor(R.color.red));
            rb_kajuan.setTextColor(getResources().getColor(R.color.white));
            rb_jiangpin.setTextColor(getResources().getColor(R.color.red));
            currentPosition = 2;
            break;
        case 2:// 兑换奖品
            rb_choujiang.setTextColor(getResources().getColor(R.color.red));
            rb_kajuan.setTextColor(getResources().getColor(R.color.red));
            rb_jiangpin.setTextColor(getResources().getColor(R.color.white));
            currentPosition = 3;
            break;
        default:
            break;
        }
    }

    @Override
    protected void getDatas() {
        // getIntegralLuckdraw(1);
    }

    private void getIntegralLuckdraw(final int typeid) {
        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "IntegralLuckdraw");
        hm.put("typeid", typeid + "");
        hm.put("userid", userid);
        hu.post(this, hm, new CallbackListener() {
            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                // TODO 待修改
                try {
                    list = JsonUtil.jsonToIntegralExchangBeans(
                            result.toJSONString(), typeid);
                    adapter.onDataChanged(list);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(IntegralExchangeActivity.this,
                        result.errorString);
            }
        });
    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLoadMore() {
        // TODO Auto-generated method stub

    }

}
