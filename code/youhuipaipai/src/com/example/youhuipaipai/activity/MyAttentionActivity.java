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
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.youhuipaipai.R;
import com.example.youhuipaipai.adapter.MerchantAdapter;
import com.example.youhuipaipai.adapter.MyMerchantAdapter;
import com.example.youhuipaipai.application.Constant;
import com.example.youhuipaipai.entity.Merchant;
import com.example.youhuipaipai.view.XListView;
import com.example.youhuipaipai.view.XListView.IXListViewListener;

import frame.http.bean.FailureResult;
import frame.util.HttpUtil;
import frame.util.HttpUtil.CallbackListener;
import frame.util.JsonUtil;
import frame.util.MyPreference;
import frame.util.TimeUtil;
import frame.util.ToastUtil;

public class MyAttentionActivity extends BaseActivity implements
        IXListViewListener {

    private TextView left_tv, edit_tv;
    private XListView lv;
    private MyMerchantAdapter adapter;
    private ArrayList<Merchant> list;
    // 我的-关注
    private String userid;// 当前登录的用户id;
    private int index = 1;

    private boolean flag = true;// true 表示下拉刷新 FALSE 表示加载更多

    @Override
    protected int getContentViewID() {
        return R.layout.myattention;
    }

    @Override
    protected void initView() {
        super.initView();
        left_tv = getView(R.id.left_tv);
        edit_tv = getView(R.id.edit_tv);
        lv = getView(R.id.listview);
        list = new ArrayList<Merchant>();
        adapter = new MyMerchantAdapter(list, this);
        lv.setAdapter(adapter);
        lv.setXListViewListener(this);
        lv.setPullLoadEnable(true);
        lv.setPullRefreshEnable(true);
    }

    @Override
    protected void setListener() {
        super.setListener();
        left_tv.setOnClickListener(this);
        edit_tv.setOnClickListener(this);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                Merchant merchant = (Merchant) arg0.getAdapter().getItem(
                        arg2 - 1);
                if (null != merchant) {
                    goByIntent("merchantid", merchant.getMerchantid(),
                            ShopDetailActivity.class, false);
                }
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
        case R.id.edit_tv:
            if (edit_tv.getText().equals("删除")) {
                adapter.setDelbtnVisible(true);
                edit_tv.setText("完成");
            } else {
                adapter.setDelbtnVisible(false);
                edit_tv.setText("删除");
                if (null != adapter.msgids && adapter.msgids.size() > 0) {
                    ModifyDialog();
                }
            }
            break;
        }
    }

    @Override
    protected void getDatas() {
        super.getDatas();
        getAttentionList();
    }

    // 我的-关注列表
    private void getAttentionList() {

        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "AttentionList");
        hm.put("userid", MyPreference.getString(this, Constant.USERID));// 当前登录的用户id
        hm.put("index", index + "");// 当前页
        // 116.481529,40.017782
        hm.put("longitude", "116.481529");// 经度
        hm.put("latitude", "40.017782");// 纬度

        hm.put("sign", 20 + "");// 一页显示几条数据
        hu.post(this, hm, new CallbackListener() {
            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                try {
                    ArrayList<Merchant> merchants = JsonUtil
                            .jsonToMerchants(result.toJSONString());
                    if (flag) {// 下拉刷新
                        list.clear();
                    }
                    list.addAll(merchants);
                    adapter.onDataChanged(list);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // if (flag) {// 下拉刷新
                // adapter.resetData(list);
                // } else {// 加载更多
                // adapter.appendToList(list);
                // }
                onLoad();
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                onLoad();
                ToastUtil.showShort(MyAttentionActivity.this,
                        result.errorString);
            }
        });
    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        index = 1;
        flag = true;
        getAttentionList();
    }

    @Override
    public void onLoadMore() {
        // TODO Auto-generated method stub
        flag = false;
        index++;
        getAttentionList();
    }

    private void onLoad() {
        lv.stopRefresh();
        lv.stopLoadMore();
        lv.setRefreshTime(TimeUtil.getNowTime());
    }

    public void deleteMsg() {
        showProgressDialog("正在请求", "请稍候...");
        HttpUtil hu = new HttpUtil();
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("requestCommand", "CancleAttentionMerchant");
        StringBuffer sb = new StringBuffer();
        for (String s : adapter.msgids) {
            sb = sb.append(s).append(",");
        }
        sb = sb.deleteCharAt(sb.length() - 1);// 去掉最后一个”，“
        hm.put("merchantid", sb.toString());
        hm.put("userid", MyPreference.getString(this, Constant.USERID));
        hu.post(this, hm, new CallbackListener() {

            @Override
            public void onSuccess(JSONObject result) {
                dismissProgressDialog();
                deleteItem();
                adapter.notifyDataSetChanged();
                ToastUtil.showShort(MyAttentionActivity.this, "操作成功！");
            }

            @Override
            public void onFailed(FailureResult result) {
                dismissProgressDialog();
                ToastUtil.showShort(MyAttentionActivity.this,
                        result.errorString);
            }
        });
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

    private void deleteItem() {
        if (null != list) {
            ArrayList<Merchant> removeList = new ArrayList<Merchant>();
            for (Merchant comment : list) {
                if (comment.isFlag()) {
                    // list.remove(comment);
                    removeList.add(comment);
                }
            }
            list.removeAll(removeList);
        }
    }
}
