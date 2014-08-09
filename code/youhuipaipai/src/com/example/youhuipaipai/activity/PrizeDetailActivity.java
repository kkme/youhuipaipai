package com.example.youhuipaipai.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.json.JSONException;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.adapter.ViewPageAdapter;
import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.entity.Prize;

import frame.http.bean.FailureResult;
import frame.imgtools.ImgShowUtil;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.JsonUtil;
import frame.util.MyPreference;
import frame.util.ToastUtil;

/**
 * @项目名:youhuipaipai
 * 
 * @类名:PrizeDetailActivity.java
 * 
 * 
 * @类描述:奖品详情
 * 
 * @date:2014-5-5
 * 
 * @Version:1.0
 */
public class PrizeDetailActivity extends BaseActivity {
    private TextView left_tv, right_tv, integalcount_tv, nums_tv, lasttime_tv,
            content_tv, place_tv;
    private String id;
    private ViewPager viewPager;
    private List<View> views;
    private ViewPageAdapter viewPageAdapter;
    Prize prize;

    @Override
    protected int getContentViewID() {
        return R.layout.prize_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        left_tv = getView(R.id.left_tv);
        right_tv = getView(R.id.right_tv);
        integalcount_tv = getView(R.id.integalcount_tv);
        nums_tv = getView(R.id.nums_tv);
        lasttime_tv = getView(R.id.lasttime_tv);
        content_tv = getView(R.id.content_tv);
        place_tv = getView(R.id.place_tv);
        viewPager = getView(R.id.viewPager);
        views = new ArrayList<View>();
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
        case R.id.right_tv:// 兑换
            if (null != prize) {
                getDataFromServer();
            } else {
                ToastUtil.showShort(this, "信息不完善，请重新获取");
            }
            break;
        }
    }

    @Override
    protected void getDatas() {
        super.getDatas();
        id = getIntent().getStringExtra("id");
        if (null != id && !"".equals(id)) {
            gePrizeDetail();
        }
    }

    private void getDataFromServer() {
        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "ExchangePrize");
        hm.put("userid", MyPreference.getString(this, Constant.USERID));
        hm.put("id", id);// 奖品信息id
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                // TODO Auto-generated method stub
                dismissProgressDialog();
                ToastUtil.showShort(PrizeDetailActivity.this,
                        "您已成功获得兑奖编码，请至“我的消息”查看。");
            }

            @Override
            public void onFailed(FailureResult result) {
                // TODO Auto-generated method stub
                dismissProgressDialog();
                ToastUtil.showShort(PrizeDetailActivity.this, result.message);
            }
        });
    }

    private void gePrizeDetail() {
        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "PrizeDetail");
        hm.put("userid", MyPreference.getString(this, Constant.USERID));
        hm.put("id", id);// 奖品信息id
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                try {
                    prize = JsonUtil.jsonToPrize(result.toJSONString());
                    String img = prize.getImg();
                    if (img.contains(",")) {
                        String[] imgs = img.split(",");
                        if (null != imgs && imgs.length > 0) {
                            for (int i = 0; i < imgs.length; i++) {
                                ImageView view = new ImageView(
                                        PrizeDetailActivity.this);
                                ImgShowUtil isuImgShowUtil = new ImgShowUtil(
                                        imgs[i], UUID.randomUUID().toString(),
                                        500);
                                isuImgShowUtil.setCoverDown(view,
                                        R.drawable.ic_launcher);
                                view.setLayoutParams(new LayoutParams(
                                        LayoutParams.MATCH_PARENT,
                                        LayoutParams.MATCH_PARENT));
                                views.add(view);
                            }
                            viewPageAdapter = new ViewPageAdapter(
                                    PrizeDetailActivity.this, views, false);
                            viewPager.setAdapter(viewPageAdapter);
                            viewPager
                                    .setOnPageChangeListener(new OnPageChangeListener() {

                                        @Override
                                        public void onPageSelected(int arg0) {
                                            // TODO Auto-generated method stub
                                            viewPager.setCurrentItem(arg0);
                                        }

                                        @Override
                                        public void onPageScrolled(int arg0,
                                                float arg1, int arg2) {
                                            // TODO Auto-generated method stub

                                        }

                                        @Override
                                        public void onPageScrollStateChanged(
                                                int arg0) {
                                            // TODO Auto-generated method stub

                                        }
                                    });
                            viewPager.setCurrentItem(0);
                        }
                    }
                    integalcount_tv.setText(prize.getIntegral() + "积分");
                    nums_tv.setText(prize.getCount() + "已兑");
                    content_tv.setText(prize.getTitle() + "/n"
                            + prize.getInfo());
                    place_tv.setText(prize.getAddress());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(PrizeDetailActivity.this, result.message);
            }
        });
    }
}
