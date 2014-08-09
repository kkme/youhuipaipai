package com.example.youhuipaipai.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import baidu.LocationUtil;
import baidu.LocationUtil.OnLocationGetListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.adapter.ViewPageAdapter;
import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.entity.Comment;
import com.example.youhuipaipai.entity.Coupon;
import com.example.youhuipaipai.entity.Merchant;
import com.example.youhuipaipai.entity.MerchantDetail;
import com.example.youhuipaipai.entity.ShopDetailsEntity;
import com.example.youhuipaipai.listener.ViewPageChangeListener;
import com.example.youhuipaipai.view.CommentView;

import frame.http.bean.FailureResult;
import frame.imgtools.ImgShowUtil;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.MyPreference;
import frame.util.ToastUtil;
import frame.util.Utils;

public class ShopDetailActivity extends BaseActivity implements OnPageChangeListener {
	private static final int COMMENT_REQUEST_CODE = 0x9;
	private TextView left_tv, remark_tv, attention_tv, name_tv, costper_tv, signintegral_tv, satisfy_tv,
			environment_tv, place_tv, phone_tv, coupon1_tv, coupon2_tv, coupon3_tv, focusNum, signNum;
	private ImageView shopimg_iv, sign_iv, counpon_iv, card_iv;
	private RatingBar remark_rg;
	private LinearLayout place_rl, phone_rl, coupon1_rl, coupon2_rl, coupon3_rl, membercard_rl;
	private String merchantid;
	private MerchantDetail md;
	private ViewPager viewPager;
	private int currentItem;
	private Button morecomment_btn;
	private LinearLayout comment_ll;
	private List<Comment> commentmap;

	private ImageView indicate1, indicate2, indicate3;
	private List<ImageView> ivs;

	private TextView allRemark, wellRemark, badRemark;

	private String userId;

	@Override
	protected int getContentViewID() {
		return R.layout.shopdetail;
	}

	@Override
	protected void initView() {
		super.initView();
		morecomment_btn = getView(R.id.morecomment_btn);
		comment_ll = getView(R.id.comment_ll);
		coupon1_tv = getView(R.id.coupon1_tv);
		coupon2_tv = getView(R.id.coupon2_tv);
		coupon3_tv = getView(R.id.coupon3_tv);
		coupon1_rl = getView(R.id.coupon1_ll);
		coupon2_rl = getView(R.id.coupon2_ll);
		coupon3_rl = getView(R.id.coupon3_ll);
		sign_iv = getView(R.id.sign_iv);
		counpon_iv = getView(R.id.counpon_iv);
		card_iv = getView(R.id.card_iv);
		left_tv = getView(R.id.left_tv);
		remark_tv = getView(R.id.remark_tv);
		attention_tv = getView(R.id.attention_tv);
		shopimg_iv = getView(R.id.shopimg_iv);
		name_tv = getView(R.id.name_tv);
		remark_rg = getView(R.id.remark_rg);
		costper_tv = getView(R.id.costper_tv);
		signintegral_tv = getView(R.id.signintegral_tv);
		satisfy_tv = getView(R.id.satisfy_tv);
		environment_tv = getView(R.id.environment_tv);
		place_tv = getView(R.id.place_tv);
		phone_tv = getView(R.id.phone_tv);
		place_rl = getView(R.id.place_ll);
		phone_rl = getView(R.id.phone_ll);
		membercard_rl = getView(R.id.membercard_ll);

		indicate1 = (ImageView) findViewById(R.id.iv1);
		indicate2 = (ImageView) findViewById(R.id.iv2);
		indicate3 = (ImageView) findViewById(R.id.iv3);

		allRemark = (TextView) findViewById(R.id.allremark_tv);
		wellRemark = (TextView) findViewById(R.id.wellremark_tv);
		badRemark = (TextView) findViewById(R.id.badremark_tv);

		focusNum = getView(R.id.focus_people_num);
		signNum = getView(R.id.signintegral_people_num);

		// 从附近商家过来
		Intent intent = getIntent();
		if (null != intent) {
			merchantid = intent.getStringExtra("merchantid");
		}
		if (TextUtils.isEmpty(merchantid))
			throw new IllegalStateException("must give the merchantid of shop!");

	}

	@Override
	protected void getDatas() {
		super.getDatas();
		userId = MyPreference.getString(this, Constant.USERID);
		if (TextUtils.isEmpty(userId))
			userId = "0";
		getmerchantDetail();
	}

	@Override
	protected void setListener() {
		super.setListener();
		morecomment_btn.setOnClickListener(this);
		left_tv.setOnClickListener(this);
		place_rl.setOnClickListener(this);
		phone_rl.setOnClickListener(this);
		coupon1_rl.setOnClickListener(this);
		coupon2_rl.setOnClickListener(this);
		coupon3_rl.setOnClickListener(this);
		membercard_rl.setOnClickListener(this);

		allRemark.setOnClickListener(this);
		wellRemark.setOnClickListener(this);
		badRemark.setOnClickListener(this);

		remark_tv.setOnClickListener(this);
		attention_tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (md == null)
					return;
				if ("0".equals(userId)) {
					ToastUtil.showLong(CTX, "请先登录");
					return;
				}
				if ("1".equals(md.getIsattention())) {
					cancelAttention();
				} else if ("0".equals((md.getIsattention()))) {
					attention();
				} else {
					ToastUtil.showLong(CTX, "请先登录");
				}
			}
		});
	}

	/**
	 * 统计定位于电话
	 * 
	 * @param isMap
	 */
	private void clickShopMapOrCall(final boolean isMap) {
		LocationUtil.getInstance(this).getLocation(new OnLocationGetListener() {

			@Override
			public void onGetLocation(BDLocation location) {
				double longitude = location.getLongitude();
				double latitude = location.getLatitude();
				HttpUtil hu = new HttpUtil();
				HashMap<String, String> hm = new HashMap<String, String>();
				hm.put("requestCommand", isMap ? "LookMap" : "Call");
				hm.put("merchantid", merchantid);
				hm.put("userid", userId);
				hm.put("longitude", longitude + "");
				hm.put("latitude", latitude + "");
				hu.post(CTX, hm, null);
			}
		});
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (md == null)
			return;
		switch (v.getId()) {
		case R.id.left_tv:
			finish();
			break;
		case R.id.place_ll:
			clickShopMapOrCall(true);
			Merchant m = new Merchant();
			m.setIssign(md.getIssign());
			m.setIsdiscount(md.getIsdiscount());
			m.setIsmember(md.getIsmember());
			m.setMerchantname(md.getMerchantname());
			m.setClassname(md.getClassname());
			m.setLatitude(md.getLatitude());
			m.setLongitude(md.getLongitude());
			m.setMerchantid(md.getMerchantid());
			ArrayList<Merchant> list = new ArrayList<Merchant>();
			list.add(m);
			goByIntent("merchants", list, MapActivity.class, false);
			break;
		case R.id.phone_ll:
			clickShopMapOrCall(false);
			Utils.call(this, md.getPhonenum());
			break;
		case R.id.coupon1_ll:
			jump2CouponDetails(md.getCoupons().get(0).getCouponid());
			break;
		case R.id.coupon2_ll:
			jump2CouponDetails(md.getCoupons().get(1).getCouponid());
			break;
		case R.id.coupon3_ll:
			jump2CouponDetails(md.getCoupons().get(2).getCouponid());
			break;
		case R.id.membercard_ll:
			goByIntent("merchantid", md.getMerchantid(), MembercardActivity.class, false);
			break;
		case R.id.morecomment_btn:
			Intent moreIntent = new Intent(ShopDetailActivity.this, MoreCommentActivity.class);
			moreIntent.putExtra("merchantid", merchantid);
			startActivity(moreIntent);
			break;
		case R.id.allremark_tv:
			getComments(1, Integer.valueOf(md.getAll()) > 3);
			allRemark.setBackgroundResource(R.drawable.shop_details_comment_all_selector);
			wellRemark.setBackgroundResource(R.drawable.fujin_shangjiaxiangqing_selectionbar_2_2);
			badRemark.setBackgroundResource(R.drawable.fujin_shangjiaxiangqing_selectionbar_2);
			allRemark.setTextColor(Color.parseColor("#ffffff"));
			wellRemark.setTextColor(Color.parseColor("#F5483F"));
			badRemark.setTextColor(Color.parseColor("#F5483F"));
			break;
		case R.id.wellremark_tv:
			getComments(2, Integer.valueOf(md.getGood()) > 3);
			allRemark.setBackgroundResource(R.drawable.fujin_shangjiaxiangqing_selectionbar_1);
			wellRemark.setBackgroundResource(R.drawable.shop_details_comment_good_selector);
			badRemark.setBackgroundResource(R.drawable.fujin_shangjiaxiangqing_selectionbar_2);
			allRemark.setTextColor(Color.parseColor("#F5483F"));
			wellRemark.setTextColor(Color.parseColor("#ffffff"));
			badRemark.setTextColor(Color.parseColor("#F5483F"));
			break;
		case R.id.badremark_tv:
			getComments(3, Integer.valueOf(md.getBad()) > 3);
			allRemark.setBackgroundResource(R.drawable.fujin_shangjiaxiangqing_selectionbar_1);
			wellRemark.setBackgroundResource(R.drawable.fujin_shangjiaxiangqing_selectionbar_2_2);
			badRemark.setBackgroundResource(R.drawable.shop_details_comment_bad_selector);
			allRemark.setTextColor(Color.parseColor("#F5483F"));
			wellRemark.setTextColor(Color.parseColor("#F5483F"));
			badRemark.setTextColor(Color.parseColor("#ffffff"));
			break;
		case R.id.remark_tv:
			final String userId = MyPreference.getString(this, Constant.USERID);
            if (TextUtils.isEmpty(userId)) {
                startActivity(new Intent(ShopDetailActivity.this, LogActivity.class));
                ToastUtil.showLong(CTX, "请先登录");
                return;
            }
			Intent i = new Intent(ShopDetailActivity.this, RemarkActivity.class);
			i.putExtra("merchantid", merchantid);
			startActivityForResult(i, COMMENT_REQUEST_CODE);
			break;
		}
	}

	// 调往优惠券详情
	private void jump2CouponDetails(String couponid) {
		Intent i = new Intent(ShopDetailActivity.this, CouponDetailActivity.class);
		i.putExtra("couponsid", couponid);
		startActivity(i);
	}

	private void cancelAttention() {
		showProgressDialog("正在加载", "请稍候...");
		HttpUtil hu = new HttpUtil();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("requestCommand", "CancleAttentionMerchant");
		hm.put("merchantid", merchantid);
		hm.put("userid", userId);
		hu.post(this, hm, new CallbackListener() {

			@Override
			public void onSuccess(JSONObject result) {
				dismissProgressDialog();
				ToastUtil.showShort(ShopDetailActivity.this, "取消关注成功");
				md.setIsattention("0");
				attention_tv.setText("关注");
			}

			@Override
			public void onFailed(FailureResult result) {
				dismissProgressDialog();
				ToastUtil.showShort(ShopDetailActivity.this, result.message);
			}
		});
	}

	private void attention() {
		showProgressDialog("正在加载", "请稍候...");
		HttpUtil hu = new HttpUtil();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("requestCommand", "AttentionMerchant");
		hm.put("merchantid", merchantid);
		hm.put("userid", userId);
		hu.post(this, hm, new CallbackListener() {

			@Override
			public void onSuccess(JSONObject result) {
				dismissProgressDialog();
				md.setIsattention("1");
				ToastUtil.showShort(ShopDetailActivity.this, "关注成功");
				attention_tv.setText("取消关注");
			}

			@Override
			public void onFailed(FailureResult result) {
				dismissProgressDialog();
				ToastUtil.showShort(ShopDetailActivity.this, result.message);
			}
		});
	}

	private void getmerchantDetail() {
		showProgressDialog("正在加载", "请稍候...");
		HttpUtil hu = new HttpUtil();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("requestCommand", "MerchantInfoByID");
		hm.put("userid", userId);
		hm.put("merchantid", merchantid);
		hu.post(this, hm, new CallbackListener() {

			@Override
			public void onSuccess(JSONObject result) {
				dismissProgressDialog();
				ShopDetailsEntity re = JSON.parseObject(result.toJSONString(), ShopDetailsEntity.class);
				md = JSON.parseObject(JSON.toJSONString(re.data), MerchantDetail.class);
				md.setComments(JSON.parseArray(JSON.toJSONString(md.getComments()), Comment.class));
				md.setCoupons(JSON.parseArray(JSON.toJSONString(md.getCoupons()), Coupon.class));
				if (md.getComments() != null) {
					commentmap = md.getComments();
					getComments(1, Integer.valueOf(md.getAll()) > 3);
					allRemark.setText("全部(" + md.getAll() + ")");
					wellRemark.setText("好评(" + md.getGood() + ")");
					badRemark.setText("差评(" + md.getBad() + ")");
				}

				initViewPager(md.getImgs());

				initMerchant();
			}

			@Override
			public void onFailed(FailureResult result) {
				dismissProgressDialog();
				ToastUtil.showShort(ShopDetailActivity.this, result.message);
			}
		});
	}

	private void initMerchant() {
		ImgShowUtil isu = new ImgShowUtil(md.getPicture(), md.getPicture());
		isu.setCoverDown(shopimg_iv, R.drawable.shouye_box2_title_1);
		name_tv.setText(md.getMerchantname());
		String starrating = md.getStarrating();
		if (TextUtils.isEmpty(starrating))
			starrating = "0";
		remark_rg.setRating(Float.valueOf(starrating));
		costper_tv.setText("人均" + md.getPercapita());
		signintegral_tv.setText("签到积分" + md.getIntegral());
		satisfy_tv.setText("品质满意度:  " + md.getFwsatisfaction());
		environment_tv.setText("环境满意度:  " + md.getHjsatisfaction());
		place_tv.setText(md.getAddress());
		phone_tv.setText(md.getPhonenum());

		focusNum.setText("关注人数" + md.getAttention());
		signNum.setText("签到人数" + md.getSign());

		setUpCoupons();

		if ("1".equals(md.getIssign())) {
			sign_iv.setVisibility(View.VISIBLE);
		} else {
			sign_iv.setVisibility(View.GONE);
		}
		if ("1".equals(md.getIsdiscount())) {
			counpon_iv.setVisibility(View.VISIBLE);
		} else {
			counpon_iv.setVisibility(View.GONE);
		}
		if ("1".equals(md.getIsmember())) {
			card_iv.setVisibility(View.VISIBLE);
		} else {
			card_iv.setVisibility(View.GONE);
		}
		attention_tv.setText("1".equals(md.getIsattention()) ? "取消关注" : "关注");
	}

	private void setUpCoupons() {
		List<Coupon> coupons = md.getCoupons();
		coupon1_rl.setVisibility(View.GONE);
		coupon2_rl.setVisibility(View.GONE);
		coupon3_rl.setVisibility(View.GONE);
		switch (coupons.size()) {
		case 0:
			break;
		case 1:
			coupon1_rl.setVisibility(View.VISIBLE);
			coupon1_tv.setText(md.getCoupons().get(0).getCouponinfo());
			break;
		case 2:
			coupon1_rl.setVisibility(View.VISIBLE);
			coupon2_rl.setVisibility(View.VISIBLE);
			coupon1_tv.setText(md.getCoupons().get(0).getCouponinfo());
			coupon2_tv.setText(md.getCoupons().get(1).getCouponinfo());
			break;
		case 3:
			coupon1_rl.setVisibility(View.VISIBLE);
			coupon2_rl.setVisibility(View.VISIBLE);
			coupon3_rl.setVisibility(View.VISIBLE);
			coupon1_tv.setText(md.getCoupons().get(0).getCouponinfo());
			coupon2_tv.setText(md.getCoupons().get(1).getCouponinfo());
			coupon3_tv.setText(md.getCoupons().get(2).getCouponinfo());
			break;
		default:
			break;
		}
	}

	private void getComments(int type, boolean isMore) {
		if (commentmap == null)
			return;
		comment_ll.removeAllViews();
		addCommentView(type, isMore);
	}

	private void addCommentView(int type, boolean isMore) {
		for (Comment c : commentmap) {
			if (String.valueOf(type).equals(c.getType())) {
				CommentView cv = new CommentView(this);
				cv.setData(c);
				comment_ll.addView(cv.getContent());
			}
		}
		morecomment_btn.setVisibility(isMore ? View.VISIBLE : View.GONE);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);
			for (int i = 0; i < ivs.size(); i++) {
				ivs.get(i).setBackgroundColor(
						i == currentItem ? Color.parseColor("#F5483F") : Color.parseColor("#858585"));
			}
		};
	};

	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (this) {
				currentItem = (currentItem + 1) % 3;
				handler.obtainMessage().sendToTarget();
			}
		}
	}

	private void initViewPager(String imgs) {
		if (TextUtils.isEmpty(imgs))
			return;
		ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 3, 3, TimeUnit.SECONDS);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		List<View> views = new ArrayList<View>();
		ivs = new ArrayList<ImageView>();
		ivs.add(indicate1);
		ivs.add(indicate2);
		ivs.add(indicate3);
		String[] img = imgs.split(",");
		if (img.length > 0)
			views.add(getPagerItem(img[0]));
		if (img.length > 1)
			views.add(getPagerItem(img[1]));
		if (img.length > 2)
			views.add(getPagerItem(img[2]));
		viewPager.setAdapter(new ViewPageAdapter(CTX, views, true));
		viewPager.setOnPageChangeListener(new ViewPageChangeListener(viewPager, ivs));
	}

	/**
	 * 获取一个View
	 */
	private View getPagerItem(String img) {
		ImageView view = new ImageView(this);
		new ImgShowUtil(img, img).setCoverDown(view, R.drawable.ic_launcher);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return view;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		viewPager.setCurrentItem(position);
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		if(arg1 == RESULT_OK && arg0 == COMMENT_REQUEST_CODE){
			getmerchantDetail();
		}
	}
}
