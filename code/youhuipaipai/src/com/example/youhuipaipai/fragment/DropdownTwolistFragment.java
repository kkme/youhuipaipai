package com.example.youhuipaipai.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import baidu.LocationUtil;
import baidu.LocationUtil.OnLocationGetListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.activity.BaseActivity;
import com.example.youhuipaipai.activity.CouponHomeActivity;
import com.example.youhuipaipai.activity.MainActivity;
import com.example.youhuipaipai.adapter.MyAdapter;
import com.example.youhuipaipai.adapter.MyAdapter2;
import com.example.youhuipaipai.adapter.MyAdapter3;
import com.example.youhuipaipai.adapter.SubAdapter;
import com.example.youhuipaipai.adapter.SubAdapter2;
import com.example.youhuipaipai.entity.Area;
import com.example.youhuipaipai.entity.AreaAndBusiness;
import com.example.youhuipaipai.entity.Classify;
import com.example.youhuipaipai.entity.ClassifyEntity;
import com.example.youhuipaipai.entity.OrderType;
import com.example.youhuipaipai.entity.SubClassify;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.MyPreference;
import frame.util.HttpUtil.CallbackListener;
import frame.util.ToastUtil;

public class DropdownTwolistFragment extends Fragment implements OnClickListener {
	private TextView tv, tv2, tv3;
	private View view;
	private ListView listView;
	private ListView subListView;
	private MyAdapter myAdapter;
	private SubAdapter subAdapter;
	private MyAdapter2 myAdapter2;
	private SubAdapter2 subAdapter2;
	private MyAdapter3 myAdapter3;
	private boolean isclicktv, isclicktv2, isclicktv3;
	private WindowManager manager;
	private PopupWindow window;
	private INotifyListener listener;

	private List<Classify> classifyList;
	private List<AreaAndBusiness> arealist;
	//1 按距离  2 按人数 3 价格低到高  4 高到底  5 最新发布  6 即将结束
	private List<OrderType> orderList;

	private String curClassifyId = "0", curAreaId = "0", curOrderid = "0";
	private int offset, yOffset;
	private int threeWidth=1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.dropdowntwolist, container);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tv = (TextView) view.findViewById(R.id.tv);
		tv2 = (TextView) view.findViewById(R.id.tv2);
		tv3 = (TextView) view.findViewById(R.id.tv3);
		manager = (WindowManager) getActivity().getSystemService(Activity.WINDOW_SERVICE);
		tv.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);

		offset = getActivity().getResources().getDimensionPixelSize(R.dimen.dropdown_offset);
		yOffset = getActivity().getResources().getDimensionPixelSize(R.dimen.dropdown_y_offset);
	}

	private void setListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (isclicktv) {
//					tv.setText(subAdapter.getItem(position).subclassname);
					myAdapter.setSelectedPosition(position);
//					subAdapter.setSelectedPosition(position);

					subAdapter.updateList(classifyList.get(position).subclasslist);
					subListView.setVisibility(View.VISIBLE);
					window.update(tv, 0, yOffset, manager.getDefaultDisplay().getWidth(), manager.getDefaultDisplay()
							.getHeight() - offset);
					if(0==position){
						window.dismiss();
						curClassifyId=classifyList.get(position).classid+"";
						tv.setText(classifyList.get(position).classname);
						listener.notify(curClassifyId, curAreaId, curOrderid);
					}
				} else if (isclicktv2) {
//					tv2.setText(subAdapter2.getItem(position).getName());
					myAdapter2.setSelectedPosition(position);
//					subAdapter2.setSelectedPosition(position);

					subAdapter2.updateList(arealist.get(position).getSublist());
					subListView.setVisibility(View.VISIBLE);
					window.update(tv2, 0, yOffset, (manager.getDefaultDisplay().getWidth() / 3) * 2, manager
							.getDefaultDisplay().getHeight() - offset);
					if(0==position){   
						window.dismiss();
						curAreaId=arealist.get(position).getId();
						tv2.setText(arealist.get(position).getName());
						listener.notify(curClassifyId, curAreaId, curOrderid);
					}
				} else {
					window.dismiss();
					tv3.setText(myAdapter3.getItem(position).orderText);
					curOrderid = myAdapter3.getItem(position).orderId;
					debugLog();
					listener.notify(curClassifyId, curAreaId, curOrderid);
				}
			}
		});
		subListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				window.dismiss();
				if (isclicktv) {
					tv.setText(subAdapter.getItem(position).subclassname);
					
					curClassifyId = subAdapter.getItem(position).subclassid + "";
					debugLog();
					listener.notify(curClassifyId, curAreaId, curOrderid);
				} else if (isclicktv2) {
					tv2.setText(subAdapter2.getItem(position).getName());
					curAreaId = subAdapter2.getItem(position).getId();
					debugLog();
					listener.notify(curClassifyId, curAreaId, curOrderid);
				}
			}
		});
	}
	
	private void debugLog() {
		Log.d("", "----------------------------------");
		Log.d("", "curClassifyId = " + curClassifyId);
		Log.d("", "curAreaId = " + curAreaId);
		Log.d("", "curOrderid = " + curOrderid);
		Log.d("", "----------------------------------");
	}

	private void popAwindow(View v) {
		LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
				R.layout.dropdownlistview_popwindow, null);
		listView = (ListView) layout.findViewById(R.id.listView);
		subListView = (ListView) layout.findViewById(R.id.subListView);
		subListView.setVisibility(View.GONE);
		if (isclicktv) {
			myAdapter = new MyAdapter(getActivity(), classifyList);
			subAdapter = new SubAdapter(getActivity(), classifyList.get(0).subclasslist);
			listView.setAdapter(myAdapter);
			subListView.setAdapter(subAdapter);
//			myAdapter.setSelectString(classify);
//			subAdapter.setSelectString(subClassify);
		} else if (isclicktv2) {
			myAdapter2 = new MyAdapter2(getActivity(), arealist);
			subAdapter2 = new SubAdapter2(getActivity(), arealist.get(0).getSublist());
			listView.setAdapter(myAdapter2);
			subListView.setAdapter(subAdapter2);
//			myAdapter2.setSelectString(areaAndBusiness);
//			subAdapter2.setSelectString(area);
		} else {
			subListView.setVisibility(View.GONE);
			myAdapter3 = new MyAdapter3(getActivity(), orderList);
			listView.setAdapter(myAdapter3);
//			myAdapter3.setSelectString(ordername);
		}
		setListener();
		showPopwindow(layout, v);
	}
	
	public void setWidth(int width){
		this.threeWidth=width;
	}

	private void showPopwindow(LinearLayout l, View v) {

		if (isclicktv) {
			window = new PopupWindow(l, manager.getDefaultDisplay().getWidth() / 2, manager.getDefaultDisplay()
					.getHeight() - offset);
		} else if (isclicktv2) {
			window = new PopupWindow(l, manager.getDefaultDisplay().getWidth() / 2, manager.getDefaultDisplay()
					.getHeight() - offset);
		} else {
			window = new PopupWindow(l, manager.getDefaultDisplay().getWidth() / threeWidth, manager.getDefaultDisplay()
					.getHeight() - offset);
		}
		window.setBackgroundDrawable(getActivity().getResources().getDrawable(android.R.color.transparent));
		window.setOutsideTouchable(true);
		window.setFocusable(true);
		window.update();
		if (window != null && window.isShowing()) {
			window.dismiss();
			if (layer != null)
				layer.setVisibility(View.GONE);
		} else {
			window.showAsDropDown(v, 0, yOffset);
			if (layer != null)
				layer.setVisibility(View.VISIBLE);
		}
		window.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				if (layer != null)
					layer.setVisibility(View.GONE);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv:// 全部分类
			isclicktv = true;
			isclicktv2 = false;
			isclicktv3 = false;
			getClassifylist();
			break;
		case R.id.tv2:// 全部区域
			isclicktv = false;
			isclicktv2 = true;
			isclicktv3 = false;
			GetAreaSubList();
			break;
		case R.id.tv3:// 默认排序
			isclicktv = false;
			isclicktv2 = false;
			isclicktv3 = true;
			if(1==threeWidth){
				getDefaultSort2();

			}else{
				getDefaultSort();
				
			}
			break;
		default:
			break;
		}
	}

	public void setListener(INotifyListener listener) {
		this.listener = listener;
	}

	public interface INotifyListener {
		void notify(String classifyId, String areaId, String orderid);
	}

	private void getClassifylist() {// 获得分类
		((BaseActivity) getActivity()).showProgressDialog("正在加载", "请稍候...");
		HttpUtil hu = new HttpUtil();
		// "http://115.28.246.230:8088/Img/ContentPrint/20140508032443390.jpg"
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("requestCommand", "ClassList");
		hm.put("type",MyPreference.getString(getActivity(), "selectAreaId"));
		hu.post(getActivity(), hm, new CallbackListener() {
			@Override
			public void onSuccess(JSONObject result) {
				((BaseActivity) getActivity()).dismissProgressDialog();
				ClassifyEntity ce = JSON.parseObject(result.toJSONString(), ClassifyEntity.class);
				classifyList = JSON.parseArray(JSON.toJSONString(ce.data), Classify.class);
				popAwindow(tv);
			}

			@Override
			public void onFailed(FailureResult result) {
				((BaseActivity) getActivity()).dismissProgressDialog();
				ToastUtil.showShort(getActivity(), result.message);
			}
		});
	}

	private void GetAreaSubList() {// 获得所在地域地区，商圈
		((BaseActivity) getActivity()).showProgressDialog("正在加载", "请稍候...");
		HttpUtil hu = new HttpUtil();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("requestCommand", "GetAreaSubList");
		hm.put("type",MyPreference.getString(getActivity(), "selectAreaId"));
		hu.post(getActivity(), hm, new CallbackListener() {
			@Override
			public void onSuccess(JSONObject result) {
				((BaseActivity) getActivity()).dismissProgressDialog();
				ClassifyEntity ce = JSON.parseObject(result.toJSONString(), ClassifyEntity.class);
				arealist = JSON.parseArray(JSON.toJSONString(ce.data), AreaAndBusiness.class);
				popAwindow(tv2);
			}

			@Override
			public void onFailed(FailureResult result) {
				((BaseActivity) getActivity()).dismissProgressDialog();
				ToastUtil.showShort(getActivity(), result.message);
			}
		});
	}

	private void getDefaultSort() {// 获得默认排序
		orderList = new ArrayList<OrderType>();
		orderList.add(new OrderType("0", "默认排序"));
		orderList.add(new OrderType("1", "按距离"));
		orderList.add(new OrderType("2", "按人数"));
		orderList.add(new OrderType("3", "价格低到高"));
		orderList.add(new OrderType("4", "价格高到底"));
		orderList.add(new OrderType("5", "最新发布"));
		orderList.add(new OrderType("6", "即将结束"));
		popAwindow(tv3);
	}
	
	private void getDefaultSort2() {// 获得默认排序
		orderList = new ArrayList<OrderType>();
		orderList.add(new OrderType("0", "默认排序"));
		orderList.add(new OrderType("1", "按距离"));
		orderList.add(new OrderType("2", "签到积分"));
		orderList.add(new OrderType("3", "总评价数"));
		orderList.add(new OrderType("4", "评分高到低"));
		
		popAwindow(tv3);
	}

	private View layer;

	public void setLayer(View layer) {
		this.layer = layer;
	}
}
