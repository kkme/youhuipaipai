package com.example.youhuipaipai.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.adapter.HotCouponListAdapter;
import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.entity.HotCouponList;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.JsonUtil;
import frame.util.MyPreference;
import frame.util.ToastUtil;

public class MyCouponActivity extends BaseActivity {

    private TextView left_tv, right_tv;
    private ListView lv;
    private ArrayList<HotCouponList> list;
    private HotCouponListAdapter adapter;
    private String userid;// 用户id
    private String couponsid;// 优惠券编号（多个优惠券之间用逗号分开，例：1,2,3）

    @Override
    protected int getContentViewID() {
        return R.layout.mycoupon;
    }

    @Override
    protected void initView() {
        super.initView();
        left_tv = getView(R.id.left_tv);
        right_tv = getView(R.id.right_tv);
        lv = getView(R.id.listview);
        // list = (ArrayList<HotCouponList>)
        // TestDataMiddleware.getHotCouponList();
        adapter = new HotCouponListAdapter(list, this);
        lv.setAdapter(adapter);
        userid = MyPreference.getString(this, Constant.USERID);
    }

    @Override
    protected void setListener() {
        super.setListener();
        left_tv.setOnClickListener(this);
        right_tv.setOnClickListener(this);
        right_tv.setOnClickListener(this);
        // right_tv.setOnTouchListener(new OnTouchListener() {
        //
        // @Override
        // public boolean onTouch(View v, MotionEvent event) {
        // if (MotionEvent.ACTION_DOWN == event.getAction()) {
        // right_tv.setTextColor(Color.BLACK);
        // } else if (MotionEvent.ACTION_UP == event.getAction()) {
        // right_tv.setTextColor(Color.WHITE);
        // }
        // return true;
        // }
        // });
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                    long arg3) {
                HotCouponList coupon =list.get(position);
                goByIntent("couponsid", coupon.getCouponid(),
                        CouponDetailActivity.class, false);

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
        case R.id.left_tv:
            finish();
            break;
        // 我的-优惠-删除
        case R.id.right_tv:
            // getDelCoupons();
            if (right_tv.getText().equals("删除")) {
                adapter.setDelbtnVisible(true);
                right_tv.setText("完成");
            } else {
                adapter.setDelbtnVisible(false);
                right_tv.setText("删除");
                if (null != adapter.msgids && adapter.msgids.size() > 0) {
                    ModifyDialog();
                }
            }
            break;
        }
    }

    /**
     * 确认修改的对话框
     * 
     * @param ctx
     * @param title
     * @param content
     */
    protected void ModifyDialog() {
        AlertDialog.Builder builder = new Builder(this);
        builder.setMessage("你要确认删除吗？");

        builder.setPositiveButton("确认",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // TODO
                        // 删除选中的条目
                        deleteMsg();
                    }
                });
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();

    }

    public void deleteMsg() {
        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "DelCoupons");
        StringBuffer sb = new StringBuffer();
        for (String s : adapter.msgids) {
            sb = sb.append(s).append(",");
        }
        sb = sb.deleteCharAt(sb.length() - 1);// 去掉最后一个”，“
        hm.put("couponsid", sb.toString());
        hm.put("userid", MyPreference.getString(this, Constant.USERID));
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                deleteItem();
                adapter.notifyDataSetChanged();
                ToastUtil.showShort(MyCouponActivity.this, "操作成功！");
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(MyCouponActivity.this, result.errorString);
            }
        });
    }

    private void deleteItem() {
        if (null != list) {
            ArrayList<HotCouponList> removeList = new ArrayList<HotCouponList>();
            for (HotCouponList comment : list) {
                if (comment.isFlag()) {
                    // list.remove(comment);
                    removeList.add(comment);
                }
            }
            list.removeAll(removeList);
        }
    }

    @Override
    protected void getDatas() {
        super.getDatas();
        getCouponListContent();
    }

    private void getCouponListContent() {
        HttpUtil ut = new HttpUtil();
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("requestCommand", "MyCouponsList");
        map.put("userid", userid);// 用户id

        ut.post(this, map, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                try {
                    list = JsonUtil.jsonToHotCouponLists(result.toJSONString());
                    adapter = new HotCouponListAdapter(list,
                            MyCouponActivity.this);
                    lv.setAdapter(adapter);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(MyCouponActivity.this, result.errorString);
            }
        });
    }
}
