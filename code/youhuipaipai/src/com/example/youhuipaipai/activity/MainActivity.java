package com.example.youhuipaipai.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.adapter.ViewPageAdapter;
import com.example.youhuipaipai.entity.AdPages;
import com.example.youhuipaipai.entity.Area;
import com.example.youhuipaipai.entity.BaseAdPage;
import com.example.youhuipaipai.entity.BaseArea;
import com.example.youhuipaipai.entity.Merchant;
import com.example.youhuipaipai.view.PageDisplay;

import frame.http.bean.FailureResult;
import frame.imgtools.ImgShowUtil;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.MyPreference;
import frame.util.ToastUtil;
// 首页
public class MainActivity extends BaseActivity implements OnPageChangeListener{
	private ViewPager viewPager;
	private ScheduledExecutorService scheduledExecutorService;
	private int currentItem;
	private Button food_btn, shopping_btn, movie_btn, ktv_btn, cafe_btn,
			wineshop_btn, entertainment_btn, home_btn, search_btn,
			searchdel_btn;
	private RelativeLayout hot_rl, integral_rl, recommend_rl, lifeservice_rl;
	private View view;
	private EditText search_et;
	private LinearLayout dropdown_ll;
	private PopupWindow window;
	private PopAdapter adapter;
	
	private  Area selectArea;
	private TextView cityTextView;
	private int index;
	//搜索商家
    private String longitude;//经度
    private String latitude;//纬度
    private String citycode;//当前所在地区编号
    private String classifyid;//分类id(全部分类时此项值为0)
    private String areaid;//区域id或商圈id(全部分类时此项值为0)
    private String ordertype;//排序类型  (没有排序时此项值为0) 1 按距离  2 签到积分 3 总评价数  4 评分高到低
    private String value;//搜索关键字
	
	//搜索商家
	private ArrayList<Merchant> list;
	
	private ArrayList<AdPages>adPagesList;
	//导航焦点
	private PageDisplay pageDisplay;
	@Override
	protected int getContentViewID() {
		return R.layout.main;
	}
	@Override
	protected void initView() {
		super.initView();
		food_btn = (Button) findViewById(R.id.food_btn);
		shopping_btn = (Button) findViewById(R.id.shopping_btn);
		movie_btn = (Button) findViewById(R.id.movie_btn);
		ktv_btn = (Button) findViewById(R.id.ktv_btn);
		cafe_btn = (Button) findViewById(R.id.cafe_btn);
		wineshop_btn = (Button) findViewById(R.id.wineshop_btn);
		entertainment_btn = (Button) findViewById(R.id.entertainment_btn);
		home_btn = (Button) findViewById(R.id.home_btn);
		hot_rl = (RelativeLayout)findViewById(R.id.hot_rl);
		integral_rl = (RelativeLayout)findViewById(R.id.integral_rl);
		recommend_rl = (RelativeLayout) findViewById(R.id.recommend_rl);
		lifeservice_rl = (RelativeLayout) findViewById(R.id.lifeservice_rl);
		search_et = (EditText) findViewById(R.id.search_et);
		search_btn = (Button) findViewById(R.id.search_btn);
		searchdel_btn = (Button) findViewById(R.id.searchdel_btn);
		dropdown_ll = (LinearLayout)findViewById(R.id.dropdown_ll);
		cityTextView=getView(R.id.city_tv);
		pageDisplay=getView(R.id.pageDisplay);
		popAwindow();
		initViewPager();
	}
	@Override
	protected void setListener() {
		super.setListener();
		food_btn.setOnClickListener(this);
		shopping_btn.setOnClickListener(this);
		movie_btn.setOnClickListener(this);
		ktv_btn.setOnClickListener(this);
		cafe_btn.setOnClickListener(this);
		wineshop_btn.setOnClickListener(this);
		entertainment_btn.setOnClickListener(this);
		home_btn.setOnClickListener(this);
		hot_rl.setOnClickListener(this);
		search_btn.setOnClickListener(this);
		searchdel_btn.setOnClickListener(this);
		integral_rl.setOnClickListener(this);
		recommend_rl.setOnClickListener(this);
		lifeservice_rl.setOnClickListener(this);
		dropdown_ll.setOnClickListener(this);
		showProgressDialog("正在请求","请稍候...");
		getArea();
	}
	@Override
	public void onClick(View v) {
		if(null==selectArea){
			ToastUtil.showShort(this, "获取地区异常，不能点击");
			return;
		}
		Intent intent=new Intent();
		switch (v.getId()) {
		case R.id.food_btn:
			intent.setClass(this, NearbyHomeActivity.class);
			intent.putExtra("keyCode", "美食");
			break;
		case R.id.shopping_btn:
			intent.setClass(this, NearbyHomeActivity.class);
			intent.putExtra("keyCode", "购物");
			break;
		case R.id.movie_btn:
			intent.setClass(this, NearbyHomeActivity.class);
			intent.putExtra("keyCode", "电影");
			break;
		case R.id.ktv_btn:
			intent.setClass(this, NearbyHomeActivity.class);
			intent.putExtra("keyCode", "ktv");
			break;
		case R.id.cafe_btn:
			intent.setClass(this, NearbyHomeActivity.class);
			intent.putExtra("keyCode", "咖啡");
			break;
		case R.id.wineshop_btn:
			intent.setClass(this, NearbyHomeActivity.class);
			intent.putExtra("keyCode", "酒店");
			break;
		case R.id.entertainment_btn:
			intent.setClass(this, NearbyHomeActivity.class);
			intent.putExtra("keyCode", "娱乐");
			break;
		case R.id.home_btn:
			intent.setClass(this, NearbyHomeActivity.class);
			intent.putExtra("keyCode", "家居");
			break;
		case R.id.hot_rl://首页热门优惠券
			intent.setClass(this, AllshoplistActivity.class);
			intent.putExtra("type", selectArea.getId());
			break;
		case R.id.integral_rl://积分排行榜
			intent.setClass(this, CouponHomeActivity.class);
			intent.putExtra("requestCommand", "IntegralChartsList");
			break;
		case R.id.recommend_rl://小编推荐
			intent.setClass(this, CouponHomeActivity.class);
			intent.putExtra("requestCommand", "RecommendList");
			break;
		case R.id.lifeservice_rl://本地生活服务
			intent.setClass(this, LifeServiceMenuActivity.class);
			break;
		case R.id.search_btn://搜索结果
			String resultString=search_et.getText().toString();
			if("".equals(resultString)){
				ToastUtil.showShort(MainActivity.this, getString(R.string.no_search_content));
				return;
			}
			intent.putExtra("keyword", resultString);
			intent.setClass(this, HomeSearchActivity.class);
			break;
		case R.id.searchdel_btn:
			search_et.getText().clear();
			return;
		case R.id.dropdown_ll:
			if (window != null && window.isShowing()) {
				window.dismiss();
			} else {
				window.showAsDropDown(dropdown_ll, 0, 10);
			}
			return;
		}
		startActivity(intent);
	}
	

 private List<View> views;
 private  ViewPageAdapter viewPageAdapter ;
	/**
	 * 初始化广告页
	 */
	private void initViewPager() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 5, 5,
				TimeUnit.SECONDS);
		viewPager = (ViewPager)findViewById(R.id.viewPager);
		 views = new ArrayList<View>();
		adPagesList=new ArrayList<AdPages>();
		viewPageAdapter=new ViewPageAdapter(CTX, views,true);
		viewPager.setAdapter(viewPageAdapter);
		viewPager.setOnPageChangeListener(this);
//		viewPager.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);
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
	
	

	/**
	 * 获取一个View
	 */
	private void getPagerItem() {
		int adPageCount=adPagesList.size();
		if(adPageCount<=0){
			return;
		}
		pageDisplay.setPageCount(adPageCount);
		views.clear();
		for (final AdPages page : adPagesList) {
			ImageView view = new ImageView(this);
			ImgShowUtil isuImgShowUtil=new ImgShowUtil(page.getAdpagesimg(), page.getAdpagesid());
			isuImgShowUtil.setCoverDown(view);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {//广告页
					Intent intent=new Intent(MainActivity.this,AdvertisementActivity.class);
					intent.putExtra("Adpage", page);
					startActivity(intent);
				}
			});
			view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			views.add(view);
		}
		pageDisplay.setCurrentIndex(0);
		viewPageAdapter.notifyDataSetChanged();
	}
	private List<Area> areas=new ArrayList<Area>();
	
	/**
	 * 初始化地区pop
	 */
	private void popAwindow() {
		View layout = (View) LayoutInflater.from(this)
				.inflate(R.layout.popwindow_listview, null);
		ListView lv=(ListView) layout.findViewById(R.id.lv);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(areas.size()<position){
					window.dismiss();
					return;
				}
				selectArea=areas.get(position);
				String name=selectArea.getName();
				MyPreference.putString(MainActivity.this, "selectAreaId", selectArea.getId());
				cityTextView.setText(name);
				window.dismiss();
				getAdPages();
			}

		});
		adapter=new PopAdapter();
		lv.setAdapter(adapter);
		WindowManager manager = (WindowManager) getSystemService(Activity.WINDOW_SERVICE);
		window = new PopupWindow(layout, manager.getDefaultDisplay().getWidth() / 4,
				manager.getDefaultDisplay().getHeight()/2);
		window.setBackgroundDrawable(getResources().getDrawable(
				android.R.color.transparent));
		window.setFocusable(true);
//		window.setOutsideTouchable(true);
		window.update();
	}
	

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
		
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	//{"result":1,"data":[{"StartPage_ID":"9","StartPage_AdvertisementImg":
		//"http://115.28.246.230:8088/Img/ContentPrint/20140319095608841.jpg","StartPage_DateTime":"2014-03-19 09:54:16"},{"StartPage_ID":"8","StartPage_AdvertisementImg":"http://115.28.246.230:8088/Img/ContentPrint/20140319095620341.jpg","StartPage_DateTime":"2014-03-17 15:51:11"}],"info":"OK"}

		
	}
	@Override
	public void onPageSelected(int position) {
		pageDisplay.setCurrentIndex(position);
		viewPager.setCurrentItem(position);
		
	}
	/**
	 * 获取首页广告
	 */
	private void getAdPages() {
		HttpUtil ut=new HttpUtil();
		 HashMap<String,String> hm=new HashMap<String, String>();
		 hm.put("requestCommand", "AdPages");
		 hm.put("type", selectArea.getId());
		 ut.post(MainActivity.this, hm, new CallbackListener() {
	            @Override
	            public void onSuccess(JSONObject result) {
	            	BaseAdPage page=JSON.parseObject(result.toJSONString(), BaseAdPage.class);
	                adPagesList.clear();
	                adPagesList.addAll(page.getData());
	                getPagerItem();
	                dismissProgressDialog();
	            }
	            
	            @Override
	            public void onFailed(FailureResult result) {
	            	dismissProgressDialog();
	                ToastUtil.showShort(MainActivity.this, result.errorString);
	            }
	        });
	}
	/**
	 * 获得地区列表
	 */
	private void getArea(){
		HttpUtil hu = new HttpUtil();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("requestCommand", "GetAreaList");
		hu.post(this, hm, new CallbackListener() {
			@Override
			public void onSuccess(JSONObject result) {
				BaseArea area=JSON.parseObject(result.toJSONString(),BaseArea.class);
				areas.clear();
				areas=area.getData();
				adapter.notifyDataSetChanged();
				boolean isContain=false;
				for (Area areaName : areas) {
					if(areaName.getName().contains("乌鲁木齐")){
						selectArea=areaName;
						isContain=true;
						break;
					}else{
						isContain=false;
					}
				}
				if(!isContain){
					selectArea=areas.get(0);
				}
				MyPreference.putString(MainActivity.this, "selectAreaId", selectArea.getId());
				getAdPages();
				
			}
			@Override
			public void onFailed(FailureResult result) {
				dismissProgressDialog();
				ToastUtil.showShort(MainActivity.this, result.errorString);
			}
		});
	}
		class PopAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			return areas.size();
		}

		@Override
		public Object getItem(int position) {	
			return areas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView=LayoutInflater.from(MainActivity.this).inflate(R.layout.listview_item, null);
			TextView tv=(TextView) convertView.findViewById(R.id.tv);
			tv.setBackgroundResource(R.drawable.button_red_normal);
			tv.setText(areas.get(position).getName());
//			tv.setTextColor(color.white);
			tv.setTextColor(getResources().getColor(R.color.white));
			tv.setTextSize(15);
			return convertView;
		}
	}
}
