package com.example.youhuipaipai.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import baidu.LocationUtil;
import baidu.LocationUtil.OnLocationGetListener;

import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.estedge.base.data.test.TestDataMiddleware;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.adapter.MerchantAdapter;
import com.example.youhuipaipai.entity.Area;
import com.example.youhuipaipai.entity.BaseMerchant;
import com.example.youhuipaipai.entity.Merchant;
import com.example.youhuipaipai.entity.SubClassify;
import com.example.youhuipaipai.fragment.DropdownTwolistFragment;
import com.example.youhuipaipai.fragment.DropdownTwolistFragment.INotifyListener;
import com.example.youhuipaipai.view.XListView;
import com.example.youhuipaipai.view.XListView.IXListViewListener;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.MyPreference;
import frame.util.TimeUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.ToastUtil;

/**
 * @项目名:youhuipaipai
 * 
 * @类名:CouponHomeActivity.java
 * @类描述:优惠的首页，首页页面需要缓存数据所以和allshoplistactivity分开 首页的积分排行榜 首页的小编推荐
 * 
 * @date:2014-3-21
 * 
 * @Version:1.0
 * 
 ******************************************/
public class HomeSearchActivity extends BaseActivity implements
        IXListViewListener {
    private DropdownTwolistFragment fragment;
    private TextView left_tv, right_tv;
    private XListView lv;
    private MerchantAdapter adapter;
    private ArrayList<Merchant> list;
    private int index;
    private double longitude;
    private double latitude;
    private String citycode;
    private String classifyid="0";
    private String areaid="0";
    private String ordertype="0";
    private Intent intent;
    private String requestCommand="";
    private String keyword;
    
    @Override
    protected int getContentViewID() {
        return R.layout.allshoplist;
    }

    @Override
    protected void initView() {
        super.initView();
        fragment = (DropdownTwolistFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dropdownTwolistFragment);
        View layer = getView(R.id.youhui_layer);
        fragment.setLayer(layer);
        left_tv = getView(R.id.left_tv);
        left_tv.setVisibility(View.GONE);
        right_tv = getView(R.id.right_tv);
        TextView title = getView(R.id.center_tv);
        lv = getView(R.id.youhui_listview);
        list = new ArrayList<Merchant>();
        adapter = new MerchantAdapter(list, this);
        lv.setAdapter(adapter);
        lv.setPullLoadEnable(true);
        lv.setPullRefreshEnable(true);
        intent=getIntent();
        title.setText("商家列表");
        requestCommand="SearchMerchantList";
        keyword = intent.getStringExtra("keyword");
//        if(null!=nameString){
//        	requestCommand=nameString;
//        	if(nameString.contains("IntegralChartsList")){
//        		title.setText("积分排行榜");
//        	}else if("SearchMerchantList".equals(nameString)){
//        		title.setText("商铺列表");
//        		keyword = intent.getStringExtra("keyword");
//        	} else{
//        		title.setText("小编推荐");
//        	}
//        }else{
//        	requestCommand="CouponsMerchantList";
//        	title.setText("优惠");
////        	fragment.getView().setVisibility(View.GONE);
//        }
        citycode=MyPreference.getString(HomeSearchActivity.this, "selectAreaId");
        
        showProgressDialog("正在请求","请稍候...");
        String timeString=TimeUtil.getNowTime();
		MyPreference.putString(this, requestCommand+"timeNow", timeString);
		lv.setRefreshTime(timeString);
		lv.setXListViewListener(this);
		right_tv.setOnClickListener(this);
    }

    @Override
    protected void getDatas() {
        super.getDatas();
        LocationUtil.getInstance(this).getLocation(new OnLocationGetListener() {

            @Override
            public void onGetLocation(BDLocation location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                index = 1;
                getCouponsMerchantList();
            }
        });

    }
    private int merchantTotalPage=0;
    private void getCouponsMerchantList() {
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        /*
         * 优惠首页 requestCommand为 "CouponsMerchantList" 首页——积分排行榜 requestCommand为
         * “IntegralChartsList” 首页——小编推荐 requestCommand为 "RecommendList"
         */
        hm.put("requestCommand", requestCommand);
        hm.put("longitude", longitude + "");// 经度
        hm.put("latitude", latitude + "");// 纬度
        hm.put("index", index + "");// 当前页码
        hm.put("sign", 20 + "");// 一页显示几条数据
        hm.put("type", citycode);// 当前所在地区编号
        hm.put("classid", classifyid);// 分类id(全部分类时此项值为0)
        hm.put("areaid", areaid);// 区域id或商圈id(全部分类时此项值为0)
        hm.put("ordertype", ordertype);
//        if("SearchMerchantList".equals(requestCommand)){
        	hm.put("keyword", keyword);
//        }
        /*
         * 排序类型首页——积分排行榜 1 按距离 2 签到积分 3 总评价数 4 评分高到低首页——小编推荐 排序类型 (没有排序时此项值为0) 1
         * 按距离 2 签到积分 3 总评价数 4 评分高到低 优惠界面 1 按距离 2 按人数 3 价格低到高 4 高到底 5 最新发布 6
         * 即将结束
         */

        hu.post(this, hm, new CallbackListener() {
            @Override
            public void onSuccess(JSONObject result) {
                 dismissProgressDialog();
				BaseMerchant merchant=JSONObject.parseObject(result.toJSONString(),BaseMerchant.class);
				if(!lv.mPullLoading){
					list.clear();
				}
				list.addAll(merchant.getData());
				
				if(index==1){
					adapter.clear();
					if(list.size()==0){
						ToastUtil.showShort(HomeSearchActivity.this, getString(R.string.no_data));
					}
				}
				merchantTotalPage=Integer.parseInt(merchant.getTotalpage());
				adapter.resetData(list);
					if(lv.mPullRefreshing){
						lv.stopRefresh();
						String timeString=TimeUtil.getNowTime();
						MyPreference.putString(HomeSearchActivity.this, requestCommand+"timeNow", timeString);
					}
					if(lv.mPullLoading){
						lv.stopLoadMore();
					}
					if(merchantTotalPage==index||merchantTotalPage==0){
						lv.setPullLoadEnable(false);
//						ToastUtil.showShort(this, getString(R.string.xlistview_footer_add_over));
//						return;
					}else{
						lv.setPullLoadEnable(true);
					}
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil
                        .showShort(HomeSearchActivity.this, result.message);
            }
        });
    }

    @Override
    protected void setListener() {
        super.setListener();
        fragment.setListener(new INotifyListener() {

            @Override
            public void notify(String classifyId, String areaId, String orderid) {
                classifyid = classifyId;
                areaid = areaId;
                ordertype = orderid;
                index = 1;
                getCouponsMerchantList();
            }

        });
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Merchant merchant = (Merchant) parent.getAdapter().getItem(
                        position);
                goByIntent("merchantid", merchant.getMerchantid(),
                        ShopDetailActivity.class, false);
            }
        });
        
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
        case R.id.right_tv:
           
                    if (list.size() == 0) {
                        ToastUtil.showShort(HomeSearchActivity.this, "没有商家");
                        return ;
                    }
                    goByIntent("merchants", list, MapActivity.class, false);
        }
    }

    @Override
    public void onRefresh() {
    	index = 1;
		String refreshTime=MyPreference.getString(this, requestCommand+"timeNow");
		lv.setRefreshTime(refreshTime);
        getCouponsMerchantList();
    }

    @Override
    public void onLoadMore() {
        ++index;
        getCouponsMerchantList();
    }
    
   
	
}
