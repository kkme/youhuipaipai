package com.example.youhuipaipai.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.w3c.dom.Text;

import android.text.Html;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.entity.AdPages;
import com.example.youhuipaipai.entity.AdPagesDetail;
import com.example.youhuipaipai.entity.BaseAdPagesDetail;

import frame.http.bean.FailureResult;
import frame.imgtools.ImgShowUtil;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.ToastUtil;

//广告页，抽奖详情，活动详情页
public class AdvertisementActivity extends BaseActivity{
	private TextView left_tv,choujiangtime_tv,contain_tv,adpage_name_tv;
	private ImageView choujiangimg_iv;
	private AdPages adPages;
	private AdPagesDetail adpageDetail;
	@Override
	protected int getContentViewID() {
		return R.layout.advertisement;
	}
	@Override
	protected void initView() {
		super.initView();
		left_tv=getView(R.id.left_tv);
		choujiangtime_tv=getView(R.id.choujiangtime_tv);
		contain_tv=getView(R.id.contain);
		adpage_name_tv=getView(R.id.adpage_name);
//		prize_tv=getView(R.id.prize_tv);
//		choujiangway_tv=getView(R.id.choujiangway_tv);
//		choujiangplace_tv=getView(R.id.choujiangplace_tv);
//		prizepost_tv=getView(R.id.prizepost_tv);
		choujiangimg_iv=getView(R.id.choujiangimg_iv);
//		updaTextView=getView(R.id.eventtime_tv);
	}
	@Override
	protected void setListener() {
		super.setListener();
		left_tv.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.left_tv:
			finish();
			break;

		default:
			break;
		}
	}
	@Override
	protected void getDatas() {
		super.getDatas();
		adPages=(AdPages) getIntent().getSerializableExtra("Adpage");
		if(null!=adPages){
			getadpagesDetail();
		}
		
	}
	private void getadpagesDetail(){
		//首页轮播图
		showProgressDialog("正在请求","请稍候...");
		HttpUtil hu=new HttpUtil();
		HashMap<String, String> hm=new HashMap<String, String>();
		hm.put("requestCommand", "AdPagesDetail");
		hm.put("adid", adPages.getAdpagesid());
		hu.post(this, hm, new CallbackListener() {
			
			@Override
			public void onSuccess(JSONObject result) {
				dismissProgressDialog();
				BaseAdPagesDetail detail=JSONObject.parseObject(result.toJSONString(), BaseAdPagesDetail.class);
				try {
					org.json.JSONObject jsonObject=new org.json.JSONObject(detail.getData());
					adpageDetail=new AdPagesDetail();
					adpageDetail.setAdpagesid(jsonObject.optString("adpagesid"));
					adpageDetail.setAdpagesimg(jsonObject.optString("adpagesimg"));
					adpageDetail.setAdpagesinfo(jsonObject.optString("adpagesinfo"));
					adpageDetail.setAdpagesname(jsonObject.optString("adpagesname"));
					adpageDetail.setDatetime(jsonObject.optString("datetime"));
					adpageDetail.setUpdatetime(jsonObject.optString("updatetime"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
//				adpageDetail=JSONObject.parseObject(detail.getData(),AdPagesDetail.class);
				adpage_name_tv.setText(adpageDetail.getAdpagesname());
				choujiangtime_tv.setText(adpageDetail.getDatetime());
				ImgShowUtil isuImgShowUtil=new ImgShowUtil(adpageDetail.getAdpagesimg(), adpageDetail.getAdpagesid());
				isuImgShowUtil.setCover(choujiangimg_iv,R.drawable.ic_launcher);
				String info=adpageDetail.getAdpagesinfo();
				contain_tv.setText(Html.fromHtml(info));
//				updaTextView.setText(adpageDetail.getUpdatetime());
			}
			@Override
			public void onFailed(FailureResult result) {
				dismissProgressDialog();
				ToastUtil.showShort(AdvertisementActivity.this, result.message);
			}
		});
	}
}
