package com.example.youhuipaipai.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.entity.HotCouponDetail;
import com.example.youhuipaipai.view.BgSelectPop;
import com.example.youhuipaipai.view.BgSelectPop.HeadPicListener;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.JsonUtil;
import frame.util.MyPreference;
import frame.util.MyUtil;
import frame.util.ShareUtil;
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

public class CouponDetailActivity extends BaseActivity {

	private TextView back_tv, couponname_tv, couponinfo_tv, coupontime_tv,
			coupon_tv, couponcontent_tv, merchantname_tv, percapita_tv,
			address_tv, phonenum_tv, tv_num;
	private Button receive_btn, btn_left, btn_right;
	// private ArrayList<HotCouponDetail> list;
	private HotCouponDetail hotCouponDetail;
	private String couponsid;// 优惠券id
	private String userId;// 用户id

	private LinearLayout ll_hhh, shopInfoLl;
	private TextView tv_nnn;
	private RatingBar starrating;
	private ImageView tv_late;

	private boolean showShopInfo;

	BgSelectPop bgSelectPop;

	private RelativeLayout membercard_rl;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case frame.util.Constant.SUCCESS:
				ToastUtil.showShort(CouponDetailActivity.this, "分享成功");
				break;
			case frame.util.Constant.ERROR:
				ToastUtil.showShort(CouponDetailActivity.this, "分享失败");
				break;
			case frame.util.Constant.CANCLE:
				ToastUtil.showShort(CouponDetailActivity.this, "分享取消");
				break;
			}
		};
	};

	/**
	 * 分享
	 */
	private void share() {
		bgSelectPop = new BgSelectPop(this, false);
		bgSelectPop.show(LayoutInflater.from(this).inflate(
				R.layout.hot_coupon_shopdetail, null));
		bgSelectPop.setHeadActionListener(new HeadPicListener() {

			@Override
			public void sendByWeiXin() {
				ShareUtil.weiXinShare2("", "", "", handler,
						CouponDetailActivity.this);
			}

			@Override
			public void sendBySina() {
				// TODO Auto-generated method stub
				// ToastUtil.show("新浪分享");
				ShareUtil.sinaShare(CouponDetailActivity.this,
						hotCouponDetail.getCouponname(), "", handler);
			}

			@Override
			public void sendByMessage() {
				// TODO Auto-generated method stub
				ShareUtil.sinaTencent(CouponDetailActivity.this,
						hotCouponDetail.getCouponname(), "", handler);
			}

			@Override
			public void sendByEmail() {
			}

			@Override
			public void sendByWeiXinFriend() {
				ShareUtil.weiXinShareFriend("", "", "", handler,
						CouponDetailActivity.this);
			}

			@Override
			public void savePic() {
				savePicToSD(convertViewToBitmap(membercard_rl));
			}

			@Override
			public void sendByMyMessage() {
				// Uri smsToUri = Uri.parse("smsto:");
				// Intent intent = new Intent(Intent.ACTION_VIEW, smsToUri);
				// intent.setType("vnd.android-dir/mms-sms");
				// intent.putExtra(Intent.EXTRA_TEXT, "生活系这个应用挺不错的");
				// startActivity(intent);
				ShareUtil.shareByMessage("生活系这个应用挺不错的");
			}
		});
	}

	@Override
	protected int getContentViewID() {

		return R.layout.hot_coupon_shopdetail;
	}

	@Override
	protected void initView() {
		super.initView();
		userId = MyPreference.getString(this, Constant.USERID);
		if (TextUtils.isEmpty(userId))
			userId = "0";
		couponsid = getIntent().getStringExtra("couponsid");
		showShopInfo = getIntent().getBooleanExtra("showShopInfo", false);
		back_tv = getView(R.id.back_tv);
		tv_num = getView(R.id.tv_num);
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
		btn_left = getView(R.id.btn_left);
		btn_right = getView(R.id.btn_right);
		tv_nnn = getView(R.id.tv_nnn);
		ll_hhh = getView(R.id.ll_hh);
		starrating = getView(R.id.starrating_iv);
		tv_late = getView(R.id.tv_late);
		membercard_rl = getView(R.id.membercard_rl);
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
			// ToastUtil.showShort(this, "下载到手机");
			break;
		case R.id.btn_right:
			// ToastUtil.showShort(this, "发送到手机");
			share();
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
		tv_num.setText("领取人数：" + hotCouponDetail.getReceiver());
		couponcontent_tv.setText(Html.fromHtml(hotCouponDetail
				.getCouponcontent()));
		merchantname_tv.setText(hotCouponDetail.getMerchantname());
		starrating.setNumStars(Integer.valueOf(MyUtil
				.getStringWithoutNullInt(hotCouponDetail.getStarrating())));
		percapita_tv.setText("人均：" + hotCouponDetail.getPercapita());
		address_tv.setText(hotCouponDetail.getAddress());
		phonenum_tv.setText(hotCouponDetail.getPhonenum());
		if ("1".equals(hotCouponDetail.getCouponstate())
				|| "2".equals(hotCouponDetail.getCouponstate())) {
			ll_hhh.setVisibility(View.VISIBLE);
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
		map.put("couponsid", couponsid);// 优惠券编号
		map.put("userid", userId);// 用户Id（未登录情况下传0）

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
				ToastUtil.showShort(CouponDetailActivity.this, result.message);
				finish();
			}
		});
	}

	// 领取优惠券
	private void getReceiveCoupons() {
		HttpUtil ut = new HttpUtil();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("requestCommand", "ReceiveCoupons");
		map.put("couponsid", couponsid);// 优惠券编号
		map.put("userid", userId);// 用户Id（未登录情况下传0）

		ut.post(this, map, new CallbackListener() {

			@Override
			public void onSuccess(JSONObject result) {
				dismissProgressDialog();
				ToastUtil.showShort(CouponDetailActivity.this, "领取成功!");
				getCouponDetailContent();
			}

			@Override
			public void onFailed(FailureResult result) {
				dismissProgressDialog();
				ToastUtil.showShort(CouponDetailActivity.this, result.message);
			}
		});
	}

	public static Bitmap convertViewToBitmap(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();

		return bitmap;
	}

	private void savePicToSD(Bitmap photo) {
		File file = createFile(Constant.PICPATH);
		FileOutputStream fileOutputStream;
		String imageNameSmall = getStringToday();
		try {
			String fileName = file.getPath() + "/" + imageNameSmall + ".jpg";
			// images.add(fileName);
			fileOutputStream = new FileOutputStream(fileName);
			photo.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
			fileOutputStream.close();
			ToastUtil.showShort(this, "图片已保存到" + Constant.PICPATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File createFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();// 创建目录，多级目录
		}
		return file;
	}

	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
}
