package com.example.youhuipaipai.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.estedge.base.data.test.TestDataMiddleware;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.adapter.HotCouponListAdapter;
import com.example.youhuipaipai.adapter.MerchantAdapter;
import com.example.youhuipaipai.entity.Area;
import com.example.youhuipaipai.entity.BaseHotCouponList;
import com.example.youhuipaipai.entity.HotCouponList;
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
/*
 * 首页最热优惠券列表
 */
public class AllshoplistActivity extends BaseActivity implements IXListViewListener{
	private DropdownTwolistFragment fragment;
	private TextView left_tv,right_tv,center_tv;
	private  XListView lv;
	private int index;
	private HotCouponListAdapter adapter;
	private List<HotCouponList> list;
	private String typeid="";
	private String classid="0";
	private String areaid="0";
	private String ordertype="0";
	private String keyword="";
	private String refreshTime;
	
	@Override
	protected int getContentViewID() {
		return R.layout.allshoplist;
	}
	@Override
	protected void initView() {
		super.initView();
		fragment=(DropdownTwolistFragment) getSupportFragmentManager().findFragmentById(R.id.dropdownTwolistFragment);
		left_tv=getView(R.id.left_tv);
		right_tv=getView(R.id.right_tv);
		center_tv=getView(R.id.center_tv);
		lv=getView(R.id.youhui_listview);
		list=(ArrayList<HotCouponList>) TestDataMiddleware.getHotCouponList();
		adapter=new HotCouponListAdapter(list, this);
		lv.setAdapter(adapter);
		lv.setXListViewListener(this);
		lv.setPullLoadEnable(true);
		lv.setPullRefreshEnable(true);
		Intent intent=getIntent();
		if(null!=intent){
			typeid=intent.getStringExtra("type");
		}
		right_tv.setText("查找");
		center_tv.setText("优惠券");
		fragment.setWidth(3);
		typeid=MyPreference.getString(AllshoplistActivity.this, "selectAreaId");
		showProgressDialog("正在请求","请稍候...");
	}
	@Override
	protected void getDatas() {
		super.getDatas();
		index=1;
		getHotCouponList();
	}
	@Override
	protected void setListener() {
		super.setListener();
		fragment.setListener(new INotifyListener() {
			
			@Override
			public void notify(String classifyId, String areaId, String orderid) {
				AllshoplistActivity.this.classid = classifyId;
				AllshoplistActivity.this.areaid = areaId;
				AllshoplistActivity.this.ordertype = orderid;
                index = 1;
				getHotCouponList();
			}
		});
		left_tv.setOnClickListener(this);
		right_tv.setOnClickListener(this);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(null!=list&&list.size()>=1&&position!=list.size()+1){
					String couponsid=list.get(position-1).getCouponid();
					goByIntent("couponsid", couponsid,CouponDetailActivity.class, false);
				}
			}
		});
		String timeString=TimeUtil.getNowTime();
		MyPreference.putString(AllshoplistActivity.this, "AllShoplisttimeNow", timeString);
		lv.setRefreshTime(timeString);
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.left_tv:
			finish();
			break;
		case R.id.right_tv:
			Intent intent=new Intent(AllshoplistActivity.this,CouponSearchActivity.class);
			startActivityForResult(intent, 0);
			break;
		default:
			break;
		}
	}
	
	private int merchantTotalPage=0;
	
	private void getHotCouponList(){
		HttpUtil ut=new HttpUtil();
		HashMap<String,String> map=new HashMap<String, String>();
		
		map.put("requestCommand", "HotCouponsList");
		map.put("type", typeid);//当前所在地区Id
		map.put("classid", classid);//分类id(全部分类时此项值为0)
		map.put("areaid", areaid);//区域Id或者商圈id(全部分类时此项值为0)
		// 排序类型 (没有排序时此项值为0) 1 按距离  2 按人数 3 价格低到高  4 高到底  5 最新发布  6 即将结束
		map.put("ordertype",ordertype);
		map.put("keyword",keyword);//搜索关键字（没有关键字时此项为“”）
		map.put("page", index+"");//当前页
		map.put("sign",20+"");//一页显示几条数据
		
		ut.post(this, map,new CallbackListener() {
			
			@Override
			public void onSuccess(JSONObject result) {
				dismissProgressDialog();
				BaseHotCouponList baseHotCouponList=JSON.parseObject(result.toJSONString(), 
						BaseHotCouponList.class);
				if(!lv.mPullLoading){
					list.clear();
				}
				list.addAll(baseHotCouponList.getData());
				
				if(index==1){
					adapter.clear();
					if(list.size()==0){
						ToastUtil.showShort(AllshoplistActivity.this, getString(R.string.no_data));
					}
				}
				merchantTotalPage=Integer.parseInt(baseHotCouponList.getTotalpage());
				adapter.resetData(list);
					if(lv.mPullRefreshing){
						lv.stopRefresh();
						String timeString=TimeUtil.getNowTime();
						MyPreference.putString(AllshoplistActivity.this, "AllShoplisttimeNow", timeString);
					}
					if(lv.mPullLoading){
						lv.stopLoadMore();
					}
					if(merchantTotalPage==index||merchantTotalPage==0){
						lv.setPullLoadEnable(false);
					}else{
						lv.setPullLoadEnable(true);
					}
			}
			
			@Override
			public void onFailed(FailureResult result) {
				dismissProgressDialog();
				ToastUtil.showShort(AllshoplistActivity.this, result.errorString);
			}
		});
		
	}
	@Override
	public void onRefresh() {
		index = 1;
		String refreshTime=MyPreference.getString(this, "AllShoplisttimeNow");
		lv.setRefreshTime(refreshTime);
		getHotCouponList();
	}

	@Override
	public void onLoadMore() {
		++index;
		getHotCouponList();
	}
	
	@Override
	protected void onActivityResult(int request, int result, Intent intent) {
		if(null==intent){
			return;
				}
		showProgressDialog("正在请求","请稍候...");
		keyword=intent.getStringExtra("keyword");	
		getHotCouponList();
		super.onActivityResult(request, result, intent);
	}
}
