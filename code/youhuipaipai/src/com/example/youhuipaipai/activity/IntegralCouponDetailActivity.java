package com.example.youhuipaipai.activity;

import java.util.HashMap;

import org.json.JSONException;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.entity.HotCouponDetail;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.JsonUtil;
import frame.util.MyPreference;
import frame.util.MyUtil;
import frame.util.ToastUtil;

/**
 * @项目名:youhuipaipai
 * 
 * @类名:CouponDetailActivity.java
 * 
 * 
 * @类描述:首页最热优惠券列表详情
 * 
 * @date:2014-3-20
 * 
 * @Version:1.0
 * 
 ******************************************/

public class IntegralCouponDetailActivity extends BaseActivity {

    private TextView back_tv, couponname_tv, couponinfo_tv, coupontime_tv,
            coupon_tv, couponcontent_tv, merchantname_tv, percapita_tv,
            address_tv, phonenum_tv;
    private Button receive_btn, btn_left, btn_right;
    // private ArrayList<HotCouponDetail> list;
    private HotCouponDetail hotCouponDetail;
    private String couponsid;// 优惠券id
    private String userid;// 用户id

    private LinearLayout ll_hhh, shopInfoLl;
    private TextView tv_nnn;
    private RatingBar starrating;
    private ImageView tv_late;

    private boolean showShopInfo;

    @Override
    protected int getContentViewID() {

        return R.layout.hot_coupon_shopdetail;
    }

    @Override
    protected void initView() {
        super.initView();
        couponsid = getIntent().getStringExtra("id");
        showShopInfo = getIntent().getBooleanExtra("showShopInfo", false);
        back_tv = getView(R.id.back_tv);
        couponname_tv = getView(R.id.couponname_tv);
        couponinfo_tv = getView(R.id.couponinfo_tv);
        coupontime_tv = getView(R.id.coupontime_tv);
        coupon_tv = getView(R.id.coupon_tv);
        couponcontent_tv = getView(R.id.couponcontent_tv);
        merchantname_tv = getView(R.id.merchantname_tv);
        percapita_tv = getView(R.id.percapita_tv);
        address_tv = getView(R.id.address_tv);
        phonenum_tv = getView(R.id.phonenum_tv);
        receive_btn = getView(R.id.receive_btn);
        receive_btn.setText("兑换");
        btn_left = getView(R.id.btn_left);
        btn_right = getView(R.id.btn_right);
        tv_nnn = getView(R.id.tv_nnn);
        ll_hhh = getView(R.id.ll_hh);
        starrating = getView(R.id.starrating_iv);
        tv_late = getView(R.id.tv_late);

        shopInfoLl = getView(R.id.addressRelRay);
        shopInfoLl.setVisibility(showShopInfo ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void setListener() {
        super.setListener();
        back_tv.setOnClickListener(this);
        receive_btn.setOnClickListener(this);
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        phonenum_tv.setOnClickListener(this);
        userid = MyPreference.getString(this, Constant.USERID);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
        case R.id.back_tv:
            finish();
            break;
        case R.id.receive_btn:
            getReceiveCoupons();
            break;
        case R.id.btn_left:
            ToastUtil.showShort(this, "下载到手机");
            break;
        case R.id.btn_right:
            ToastUtil.showShort(this, "发送到手机");
            break;
        case R.id.phonenum_tv:
            if (null != hotCouponDetail
                    && !"".equals(hotCouponDetail.getPhonenum())) {
                call();
            } else {
                ToastUtil.showShort(this, "手机号码不能为空");
            }
            break;
        }
    }

    /**
     * 打电话
     */
    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                + hotCouponDetail.getPhonenum()));
        startActivity(intent);
    }

    @Override
    protected void getDatas() {
        super.getDatas();
        getCouponDetailContent();
    }

    private void initData() {
        couponname_tv.setText(hotCouponDetail.getCouponname());
        couponinfo_tv.setText(hotCouponDetail.getCouponinfo());
        coupontime_tv.setText("有效期："
                + hotCouponDetail.getStarttime().split(" ")[0] + "-"
                + hotCouponDetail.getEndtime().split(" ")[0]);
        couponcontent_tv.setText(hotCouponDetail.getCouponcontent());
        merchantname_tv.setText(hotCouponDetail.getMerchantname());
        starrating.setNumStars(Integer.valueOf(MyUtil
                .getStringWithoutNullInt(hotCouponDetail.getStarrating())));
        percapita_tv.setText("人均：" + hotCouponDetail.getPercapita());
        address_tv.setText(hotCouponDetail.getAddress());
        phonenum_tv.setText(hotCouponDetail.getPhonenum());
        if ("1".equals(hotCouponDetail.getCouponstate())
                || "2".equals(hotCouponDetail.getCouponstate())) {
            // ll_hhh.setVisibility(View.VISIBLE);
            receive_btn.setVisibility(View.GONE);
            tv_nnn.setText(hotCouponDetail.getCouponno());
        }
        if ("3".equals(hotCouponDetail.getCouponstate())) {
            tv_late.setVisibility(View.VISIBLE);
            receive_btn.setEnabled(false);
            btn_left.setEnabled(false);
            btn_right.setEnabled(false);
        }

    }

    private void getCouponDetailContent() {
        HttpUtil ut = new HttpUtil();
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("requestCommand", "GetCouponsDetail");
        // FIXME
        map.put("couponsid", couponsid);// 优惠券编号
        map.put("userid", MyPreference.getString(this, Constant.USERID));// 用户Id（未登录情况下传0）

        ut.post(this, map, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                try {
                    hotCouponDetail = JsonUtil.jsonToHotCouponDetail(result
                            .toJSONString());
                    initData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(IntegralCouponDetailActivity.this,
                        result.message);
            }
        });
    }

    // 领取优惠券 --- 修改为兑换优惠券
    private void getReceiveCoupons() {

        HttpUtil ut = new HttpUtil();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("requestCommand", "ReceiveCoupons");
        // FIXME
        map.put("couponsid", couponsid);// 优惠券编号
        map.put("userid", MyPreference.getString(this, Constant.USERID));// 用户Id（未登录情况下传0）

        ut.post(this, map, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                ToastUtil.showShort(IntegralCouponDetailActivity.this,
                        "兑换成功，请到“我的消息”中查看");
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(IntegralCouponDetailActivity.this,
                        result.message);
            }
        });
    }

}
