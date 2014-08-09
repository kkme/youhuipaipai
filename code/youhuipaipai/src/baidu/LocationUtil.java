package baidu;

import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.activity.ShopDetailActivity;
import com.example.youhuipaipai.entity.Merchant;

public class LocationUtil {
	private static LocationClient client;
	private static Context mContext;
	private static BDLocation mLocation;
	private static LocationUtil util;
	private ArrayList<Merchant>list;
	private Context context;
	public static LocationUtil getInstance(Context context) {
		if (util == null) {
			util = new LocationUtil();
		}
		mContext = context;
		return util;
	}

	public interface OnLocationGetListener {
		void onGetLocation(BDLocation location);
	}

	private static void initLocationClient(final OnLocationGetListener l) {
		client = new LocationClient(mContext.getApplicationContext());
		client.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceivePoi(BDLocation arg0) {

			}

			@Override
			public void onReceiveLocation(BDLocation location) {
				if (location != null) {
					if (l != null) {
						l.onGetLocation(location);
					}
					mLocation = location;
					client.stop();
					client.unRegisterLocationListener(this);
				}
			}
		});
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		option.setPoiNumber(5); // 最多返回POI个数
		option.setPoiDistance(1000); // poi查询距离
		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
		client.setLocOption(option);
		client.start();
		client.requestLocation();
	}

	/**
	 * 获取当前位置
	 * 
	 * @param l
	 */
	public void getLocation(OnLocationGetListener l) {
		if (mLocation != null) {
			if (l != null) {
				l.onGetLocation(mLocation);
			}
		} else {
			initLocationClient(l);
		}
	}

	public void drawIcons(Context context,String longitude,String latitude,
			ArrayList<Merchant> list, MapView mapView) {
		Drawable merchantimg = mContext.getResources().getDrawable(
				R.drawable.fujin_ditu_icon_2);
		MyOverLay merchantOverlay = new MyOverLay(merchantimg, mapView);
		mapView.getOverlays().add(merchantOverlay);
		
		ArrayList<OverlayItem> overlayItems = new ArrayList<OverlayItem>();
		this.list=list;
		this.context=context;
		for (Merchant mc : list) {
			GeoPoint geoPoint = new GeoPoint((int) (Double.valueOf(mc.getLatitude()) * 1E6), (int) (Double.valueOf(mc.getLongitude()) * 1E6));
			OverlayItem item = new OverlayItem(geoPoint, "我", "你");
			View view= LayoutInflater.from(context).inflate(R.layout.mapview_item,null);
			view.setDrawingCacheEnabled(true);  
			ImageView sign_iv=(ImageView) view.findViewById(R.id.sign_iv);
			ImageView counpon_iv=(ImageView) view.findViewById(R.id.counpon_iv);
			ImageView card_iv=(ImageView) view.findViewById(R.id.card_iv);
			TextView nameText=(TextView) view.findViewById(R.id.food_name);
			TextView typeText=(TextView) view.findViewById(R.id.costper_tv);
			TextView distanceText=(TextView) view.findViewById(R.id.signintegral_tv);
			if ("1".equals(mc.getIssign())) {
				
				sign_iv.setVisibility(View.VISIBLE);
			} else {
				sign_iv.setVisibility(View.GONE);
			}
			if ("1".equals(mc.getIsdiscount())) {
				counpon_iv.setVisibility(View.VISIBLE);
			} else {
				counpon_iv.setVisibility(View.GONE);
			}
			if ("1".equals(mc.getIsmember())) {
				card_iv.setVisibility(View.VISIBLE);
			} else {
				card_iv.setVisibility(View.GONE);
			}
			nameText.setText(mc.getMerchantname());
			typeText.setText(mc.getClassname());
			distanceText.setText(mc.getDistance());
			
			view.setLayoutParams(new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
			view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
			
			view.buildDrawingCache();
			Bitmap bitmap=view.getDrawingCache();
			BitmapDrawable bitmapDrawable=new BitmapDrawable(bitmap);
			item.setMarker(bitmapDrawable);
			overlayItems.add(item);
//			showPop(mapView, view,(int) (Double.valueOf(mc.getLatitude()) * 1E6),(int) (Double.valueOf(mc.getLongitude()) * 1E6));
		}
		merchantOverlay.addItem(overlayItems);
		LinearLayout layout=new LinearLayout(context, null);
		layout.setOrientation(LinearLayout.VERTICAL);
		TextView tv=new TextView(context);
		tv.setGravity(Gravity.CENTER);
		tv.setText("我在这里");
		tv.setBackgroundResource(R.drawable.fujin_ditu_bg_2);
		layout.addView(tv);
		ImageView imageView=new ImageView(context);
		imageView.setImageResource(R.drawable.fujin_ditu_icon_1);
		layout.addView(imageView);
		showPop(mapView, layout,(int) (Double.valueOf(latitude) * 1E6),(int) (Double.valueOf(longitude) * 1E6));
//		showPop();
		mapView.refresh();

	}
	
	private class MyOverLay extends ItemizedOverlay<OverlayItem> {

		public MyOverLay(Drawable arg0, MapView arg1) {
			super(arg0, arg1);
		}

		@Override
		protected boolean onTap(int i) {
			Merchant merchant=list.get(i);
			Intent intent=new Intent(context,ShopDetailActivity.class);
			intent.putExtra("merchantid", merchant.getMerchantid());
			context.startActivity(intent);
			return true;
		}

	}

	private void showPop(MapView mapView, View view,int latitude,int longitude) {
		PopupOverlay pop = new PopupOverlay(mapView, new PopupClickListener() {
			@Override
			public void onClickedPopup(int index) {
				
			}
		});
		
		GeoPoint geo = new GeoPoint(latitude,longitude);
		pop.showPopup(view, geo, 32);
	}
}
